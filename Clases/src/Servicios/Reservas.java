package Servicios;

import Modelos.Usuario;

import java.time.LocalDateTime;

public class ServicioReservas {
    private Usuario usuario;
    private LocalDateTime fechaReserva;

    public ServicioReservas(Usuario usuario) {
        this.usuario = usuario;
        this.fechaReserva = LocalDateTime.now();
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
}

