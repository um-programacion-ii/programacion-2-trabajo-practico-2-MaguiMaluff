package Interfaces;

public interface Notificable {
    void enviarNotificacion(String mensaje);
    List<Notificacion> getNotificacionesPendientes();
}