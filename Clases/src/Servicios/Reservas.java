package Servicios;

import Interfaces.Prestable;
import Modelos.Usuario;

import java.time.LocalDate;

public class Reservas implements Comparable<Reservas> {
    private Usuario usuario;
    private LocalDate fechaReserva;
    private Prestable recurso;

    public Reservas(Usuario usuario, LocalDate fechaReserva, Prestable recurso) {
        this.usuario = usuario;
        this.fechaReserva = fechaReserva;
        this.recurso = recurso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }
    public void showInfo() {
        System.out.println("Reserva de " + usuario.getName() + " el " + fechaReserva);
    }

    @Override
    public int compareTo(Reservas otra) {
        return this.fechaReserva.compareTo(otra.fechaReserva);
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Prestable getRecurso() {
        return recurso;
    }

    public void setRecurso(Prestable recurso) {
        this.recurso = recurso;
    }
}

