package Interfaces;

import Recursos.EstadoRecurso;

public interface IRecursoDigital {
    String getIdentificador();
    EstadoRecurso getEstado();
    void actualizarEstado(EstadoRecurso estate);
}
