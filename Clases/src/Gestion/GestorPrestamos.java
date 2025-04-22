package Gestion;

import Excepciones.DatosErroneosException;
import Excepciones.RecursoNoDisponibleException;
import Excepciones.RecursoNoEncontradoException;
import Excepciones.UsuarioNoEncontradoException;
import Interfaces.Prestable;
import Interfaces.Renovable;
import Modelos.Libro;
import Modelos.Revista;
import Modelos.Usuario;
import Recursos.EstadoRecurso;
import Recursos.RecursoDigital;
import Servicios.Prestamos;
import Servicios.Reservas;
import Servicios.ServicioNotificacionesEmail;
import Servicios.ServicioNotificacionesSMS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.PriorityBlockingQueue;

public class GestorPrestamos {
    private List<Prestamos> prestamos = new ArrayList<>();
    private GestorReservas gestorReservas;
    private GestorNotificaciones myGestorNotificaciones;

    public GestorPrestamos(GestorReservas gestorReservas) {
        this.gestorReservas = gestorReservas;
        myGestorNotificaciones = new GestorNotificaciones();
        myGestorNotificaciones.agregarCanal(new ServicioNotificacionesEmail());
        myGestorNotificaciones.agregarCanal(new ServicioNotificacionesSMS());
    }

    public void registrarPrestamo(Usuario usuario, Prestable recurso)
            throws RecursoNoDisponibleException, UsuarioNoEncontradoException,
            DatosErroneosException, RecursoNoEncontradoException {

        System.out.println("Verificando si esta disponible... ");
        if (!recurso.estaDisponible()) {
            throw new RecursoNoDisponibleException("El recurso no está disponible para préstamo.");
        }
        System.out.println("Analizando reservas del recurso... ");
        PriorityBlockingQueue<Reservas> colaReservas = gestorReservas.getReservas().get(recurso);

        if (colaReservas != null && !colaReservas.isEmpty()) {
            Reservas siguiente = colaReservas.peek();
            System.out.println("Analizando el usuario... ");
            if (!siguiente.getUsuario().equals(usuario)) {
                System.out.println("Verificando fechas... ");
                LocalDateTime fechaReserva = siguiente.getFechaReserva();
                if (fechaReserva.isBefore(LocalDateTime.now().plusDays(14))) {
                    throw new RecursoNoDisponibleException("Este recurso está reservado por otro usuario durante este período.");
                }
            }
        }
        System.out.println("Todo bien, prestamo en curso...");
        recurso.prestar(usuario);
        Prestamos nuevoPrestamo = new Prestamos(usuario, recurso);
        prestamos.add(nuevoPrestamo);
        myGestorNotificaciones.enviarNotificacion("El recurso " + ((RecursoDigital) recurso).getNombre() +
                " ha sido prestado" + '\n' + "Fecha de devolución: " + recurso.getFechaDevolucion());
        System.out.println("Prestamo exitoso");
    }


    public void devolverPrestamo(RecursoDigital recurso){
        if(recurso instanceof Libro){
            devolverPrestamo((Libro) recurso);
        } else if (recurso instanceof Revista) {
            devolverPrestamo((Revista) recurso);
        }else{
            throw new RecursoNoDisponibleException("Este recurso no pudo ser prestado");
        }
    }

    public void devolverPrestamo(Libro recurso) {
        System.out.println("Buscando prestamo...");
        for (Prestamos prestamo : prestamos) {
            if (prestamo.getRecurso().equals(recurso)) {
                System.out.println("Devolviendo recurso... ");
                prestamos.remove(prestamo);
                recurso.setEstado(EstadoRecurso.DISPONIBLE);
                recurso.resetearFechaEstado();
                break;
            }
        }
    }

    public void devolverPrestamo(Revista recurso) {
        System.out.println("Buscando prestamo...");
        for (Prestamos prestamo : prestamos) {
            if (prestamo.getRecurso().equals(recurso)) {
                System.out.println("Devolviendo recurso... ");
                prestamos.remove(prestamo);
                recurso.setEstado(EstadoRecurso.DISPONIBLE);
                recurso.resetearFechaEstado();
                break;
            }
        }
    }

    public void renovarPrestamo(Renovable recurso, Usuario usuario) throws RecursoNoDisponibleException{
        recurso.renovar(usuario);
        myGestorNotificaciones.enviarNotificacion("El recurso " + recurso.getNombre() + " ha sido renovado." +
                '\n' + " Nueva fecha de devolución: " + recurso.getFechaDevolucion());
    }

    public void listarPrestamos() {
        for (Prestamos prestamo : prestamos) {
            prestamo.showInfo();
        }
    }

    public Prestamos buscarPrestamo(RecursoDigital recurso) {
        for (Prestamos prestamo : prestamos) {
            if (prestamo.getRecurso().equals(recurso)) {
                prestamo.showInfo();
            }
        }
        throw new RecursoNoEncontradoException("El recurso no fue encontrado");
    }
}
