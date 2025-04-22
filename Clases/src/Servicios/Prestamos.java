package Servicios;

import Interfaces.Prestable;
import Modelos.Usuario;
import Recursos.RecursoDigital;
import java.time.LocalDateTime;

public class Prestamos {
    private final Usuario usuario;
    private final Prestable recurso;
    private final LocalDateTime fechaPrestamo;
    private final LocalDateTime fechaDevolucionEstimada;

    public Prestamos(Usuario usuario, Prestable recurso) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaPrestamo = LocalDateTime.now();
        this.fechaDevolucionEstimada = LocalDateTime.now().plusDays(14);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Prestable getRecurso() {
        return recurso;
    }


    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDateTime getFechaDevolucionEstimada() {
        return fechaDevolucionEstimada;
    }

    public void showInfo() {
        System.out.println("Préstamo:\n" +
                "Usuario: " + usuario.getName() + "\n" +
                "Recurso: " + obtenerNombre(recurso) + "\n" +
                "Desde: " + fechaPrestamo + "\n" +
                "Hasta: " + fechaDevolucionEstimada + "\n");
    }

    public String obtenerNombre(Prestable recurso) {
        if (recurso instanceof RecursoDigital rd) {
            return rd.getNombre();
        }
        return "Desconocido";
    }


}

