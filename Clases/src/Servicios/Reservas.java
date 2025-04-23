package Servicios;

import Modelos.Usuario;

import java.time.LocalDate;

public class Reservas implements Comparable<Reservas> {
    private Usuario usuario;
    private LocalDate fechaReserva;

    public Reservas(Usuario usuario, LocalDate fechaReserva) {
        this.usuario = usuario;
        this.fechaReserva = fechaReserva;
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
}

