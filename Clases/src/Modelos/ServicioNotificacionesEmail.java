package Modelos;

import Interfaces.ServicioNotificaciones;

public class ServicioNotificacionesEmail implements ServicioNotificaciones {

    @Override
    public void enviarNotificacion(String mensaje) {
        System.out.println("Enviando notificación por Email: " + mensaje);
    }
}