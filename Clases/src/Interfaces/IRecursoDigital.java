package Interfaces;

import Modelos.EstadoRecurso;

public interface IRecursoDigital {
    String getIdentificador();
    EstadoRecurso getEstado();
    void actualizarEstado(EstadoRecurso estate);
}
