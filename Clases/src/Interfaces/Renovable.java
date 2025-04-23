package Interfaces;

import Modelos.Usuario;

import java.time.LocalDate;

public interface Renovable {
    void renovar(Usuario usuario);
    LocalDate getFechaDevolucion();
    String  getNombre();
}
