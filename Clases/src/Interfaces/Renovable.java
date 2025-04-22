package Interfaces;

import Modelos.Usuario;

import java.time.LocalDateTime;

public interface Renovable {
    void renovar(Usuario usuario);
    LocalDateTime getFechaDevolucion();
    String  getNombre();
}
