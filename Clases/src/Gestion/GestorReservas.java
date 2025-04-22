package Gestion;

import Excepciones.RecursoNoDisponibleException;
import Excepciones.RecursoNoEncontradoException;
import Interfaces.Prestable;
import Modelos.Usuario;
import Recursos.RecursoDigital;
import Servicios.Reservas;
import Servicios.ServicioNotificacionesEmail;
import Servicios.ServicioNotificacionesSMS;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class GestorReservas {
    private Map<Prestable, PriorityBlockingQueue<Reservas>> reservasPorRecurso = new ConcurrentHashMap<>();
    private GestorNotificaciones myGestorNotificaciones;

    public GestorReservas() {
        myGestorNotificaciones = new GestorNotificaciones();
        myGestorNotificaciones.agregarCanal(new ServicioNotificacionesEmail());
        myGestorNotificaciones.agregarCanal(new ServicioNotificacionesSMS());
    }


    public void reservarRecurso(Usuario usuario, Prestable recurso, LocalDateTime fechaDeseada) {
        if (fechaDeseada.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha deseada debe ser futura.");
        }

        if (!recurso.estaDisponible()) {
            LocalDateTime fechaDevolucion = recurso.getFechaDevolucion();
            if (fechaDeseada.isBefore(fechaDevolucion)) {
                throw new RecursoNoDisponibleException("El recurso está prestado hasta el " + fechaDevolucion + ", no se puede reservar para " + fechaDeseada + ".");
            }
        }

        reservasPorRecurso.putIfAbsent(recurso, new PriorityBlockingQueue<>());
        PriorityBlockingQueue<Reservas> cola = reservasPorRecurso.get(recurso);

        boolean yaReservadoEseDia = cola.stream()
                .anyMatch(r -> r.getFechaReserva().toLocalDate().equals(fechaDeseada.toLocalDate()));

        if (yaReservadoEseDia) {
            throw new RecursoNoDisponibleException("Ya existe una reserva para esa fecha.");
        }

        Reservas nueva = new Reservas(usuario, fechaDeseada);
        cola.add(nueva);
        myGestorNotificaciones.enviarNotificacion("Reserva realizada para el " + fechaDeseada);
        nueva.showInfo();
    }

    public void mostrarReservas(RecursoDigital recurso) {
        if (!(recurso instanceof Prestable prestable)) {
            throw new IllegalArgumentException("Este recurso no es reservable.");
        }

        PriorityBlockingQueue<Reservas> cola = reservasPorRecurso.get(prestable);

        if (cola == null || cola.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay reservas para este recurso.");
        } else {
            System.out.println("Reservas para " + recurso.getNombre() + ":");
            for (Reservas r : cola) {
                r.showInfo();
            }
        }
    }

    public Reservas siguienteReserva(Prestable recurso) {
        PriorityBlockingQueue<Reservas> cola = reservasPorRecurso.get(recurso);
        if (cola != null && !cola.isEmpty()) {
            return cola.poll();
        }
        return null;
    }

    public Map<Prestable, PriorityBlockingQueue<Reservas>> getReservas() {
        return reservasPorRecurso;
    }
}
