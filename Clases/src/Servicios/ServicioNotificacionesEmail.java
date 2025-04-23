package Servicios;

import Interfaces.Notificacion;

public class ServicioNotificacionesEmail implements Notificacion {

    @Override
    public void enviar(String mensaje) {
        System.out.println("Enviando notificación por Email: " + mensaje);
    }
}