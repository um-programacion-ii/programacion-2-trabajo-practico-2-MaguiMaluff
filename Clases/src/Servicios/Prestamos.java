package Servicios;

import Interfaces.Prestable;
import Modelos.Usuario;
import Recursos.RecursoDigital;
import java.time.LocalDate;

public class Prestamos {
    private final Usuario usuario;
    private final Prestable recurso;
    private final LocalDate fechaPrestamo;
    private  LocalDate fechaDevolucionEstimada;

    public Prestamos(Usuario usuario, Prestable recurso) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEstimada = LocalDate.now().plusDays(14);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Prestable getRecurso() {
        return recurso;
    }


    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEstimada() {
        return fechaDevolucionEstimada;
    }
    public void setFechaDevolucionEstimada(LocalDate f){
        this.fechaDevolucionEstimada = f;

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

