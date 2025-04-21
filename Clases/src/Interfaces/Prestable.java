package Interfaces;
import java.time.LocalDateTime;
import Modelos.Usuario;

public interface Prestable {
    boolean estaDisponible();
    LocalDateTime getFechaDevolucion();
    void prestar(Usuario usuario);
    void resetearFechaEstado();
}