package Interfaces;
import Modelos.Usuario;

import java.time.LocalDate;

public interface Prestable {
    boolean estaDisponible();
    LocalDate getFechaDevolucion();
    void prestar(Usuario usuario);
    void resetearFechaEstado();
}