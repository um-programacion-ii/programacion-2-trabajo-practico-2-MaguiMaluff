package Servicios;

import Modelos.Usuario;

import java.time.LocalDateTime;

public class Reservas implements Comparable<Reservas> {
    private Usuario usuario;
    private LocalDateTime fechaReserva;

    public Reservas(Usuario usuario, LocalDateTime fechaReserva) {
        this.usuario = usuario;
        this.fechaReserva = fechaReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getFechaReserva() {
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

