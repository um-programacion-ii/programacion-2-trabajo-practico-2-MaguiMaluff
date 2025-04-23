package Servicios;

import Interfaces.Notificacion;

public class ServicioNotificacionesSMS implements Notificacion {

    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando notificación por SMS: " + mensaje);
    }
}

