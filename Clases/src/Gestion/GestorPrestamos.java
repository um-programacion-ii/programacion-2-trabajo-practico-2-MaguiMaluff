package Gestion;

import Excepciones.DatosErroneosException;
import Excepciones.RecursoNoDisponibleException;
import Excepciones.RecursoNoEncontradoException;
import Excepciones.UsuarioNoEncontradoException;
import Interfaces.Prestable;
import Modelos.Libro;
import Modelos.Revista;
import Modelos.Usuario;
import Recursos.EstadoRecurso;
import Recursos.RecursoDigital;
import Servicios.Prestamos;
import Servicios.Reservas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class GestorPrestamos {
    private List<Prestamos> prestamos = new ArrayList<>();
    private GestorReservas gestorReservas;

    public GestorPrestamos(GestorReservas gestorReservas) {
        this.gestorReservas = gestorReservas;
    }

    public void registrarPrestamo(Usuario usuario, Prestable recurso)
            throws RecursoNoDisponibleException, UsuarioNoEncontradoException,
            DatosErroneosException, RecursoNoEncontradoException {

        if (!recurso.estaDisponible()) {
            throw new RecursoNoDisponibleException("El recurso no está disponible para préstamo.");
        }

        PriorityBlockingQueue<Reservas> colaReservas = gestorReservas.getReservas().get(recurso);

        if (colaReservas != null && !colaReservas.isEmpty()) {
            Reservas siguiente = colaReservas.peek();

            if (!siguiente.getUsuario().equals(usuario)) {
                LocalDateTime fechaReserva = siguiente.getFechaReserva();
                if (fechaReserva.isBefore(LocalDateTime.now().plusDays(14))) {
                    throw new RecursoNoDisponibleException("Este recurso está reservado por otro usuario durante este período.");
                }
            }
        }
        recurso.prestar(usuario);
        Prestamos nuevoPrestamo = new Prestamos(usuario, recurso);
        prestamos.add(nuevoPrestamo);
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
        for (Prestamos prestamo : prestamos) {
            if (prestamo.getRecurso().equals(recurso)) {
                prestamos.remove(prestamo);
                recurso.setEstado(EstadoRecurso.DISPONIBLE);
                recurso.resetearFechaEstado();
                break;
            }
        }
    }

    public void devolverPrestamo(Revista recurso) {
        for (Prestamos prestamo : prestamos) {
            if (prestamo.getRecurso().equals(recurso)) {
                prestamos.remove(prestamo);
                recurso.setEstado(EstadoRecurso.DISPONIBLE);
                recurso.resetearFechaEstado();
                break;
            }
        }
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
