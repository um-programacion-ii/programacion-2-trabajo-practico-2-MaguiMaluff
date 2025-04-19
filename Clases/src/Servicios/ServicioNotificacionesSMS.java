package Servicios;

import Interfaces.ServicioNotificaciones;

public class ServicioNotificacionesSMS implements ServicioNotificaciones {

    @Override
    public void enviarNotificacion(String mensaje) {
        System.out.println("Enviando notificación por SMS: " + mensaje);
    }
}

