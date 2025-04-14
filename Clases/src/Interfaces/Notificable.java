package Interfaces;
import Modelos.Notificacion;
import java.util.List;

public interface Notificable {
    void enviarNotificacion(String mensaje);
    List<Notificacion> getNotificacionesPendientes();
}