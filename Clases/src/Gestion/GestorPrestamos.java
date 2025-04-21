package Gestion;

import Excepciones.DatosErroneosException;
import Excepciones.RecursoNoDisponibleException;
import Excepciones.RecursoNoEncontradoException;
import Excepciones.UsuarioNoEncontradoException;
import Interfaces.Prestable;
import Modelos.Libro;
import Modelos.Revista;
import Servicios.ServicioPrestamo;
import Modelos.Usuario;
import Recursos.EstadoRecurso;
import Recursos.RecursoDigital;
import java.util.ArrayList;
import java.util.List;

public class GestorPrestamos {
    private List<ServicioPrestamo> prestamos = new ArrayList<>();

    public void registrarPrestamo(Usuario usuario, Prestable recurso)
            throws RecursoNoDisponibleException, UsuarioNoEncontradoException,
            DatosErroneosException, RecursoNoEncontradoException {
        if (recurso instanceof Prestable prestable) {
            prestable.prestar(usuario);
            ServicioPrestamo nuevo = new ServicioPrestamo(usuario, recurso);
            prestamos.add(nuevo);
        } else {
            throw new RecursoNoDisponibleException("El recurso no es prestable.");
        }
    }

    public void devolverPrestamo(Libro recurso) {
        for (ServicioPrestamo prestamo : prestamos) {
            if (prestamo.getRecurso().equals(recurso)) {
                prestamos.remove(prestamo);
                recurso.setEstado(EstadoRecurso.DISPONIBLE);
                recurso.resetearFechaEstado();
                break;
            }
        }
    }

    public void devolverPrestamo(Revista recurso) {
        for (ServicioPrestamo prestamo : prestamos) {
            if (prestamo.getRecurso().equals(recurso)) {
                prestamos.remove(prestamo);
                recurso.setEstado(EstadoRecurso.DISPONIBLE);
                recurso.resetearFechaEstado();
                break;
            }
        }
    }

    public void listarPrestamos() {
        for (ServicioPrestamo prestamo : prestamos) {
            System.out.println(prestamo);
        }
    }

    public ServicioPrestamo buscarPrestamo(RecursoDigital recurso) {
        for (ServicioPrestamo prestamo : prestamos) {
            if (prestamo.getRecurso().equals(recurso)) {
                return prestamo;
            }
        }
        throw new RecursoNoEncontradoException("El recurso no fue encontrado");
    }
}
