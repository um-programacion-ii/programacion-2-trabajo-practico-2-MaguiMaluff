package Recursos;

import Interfaces.IRecursoDigital;
import Interfaces.Prestable;
import Modelos.EstadoRecurso;
import Modelos.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class RecursoDigital implements IRecursoDigital {
    private String name;
    private String ID;
    private EstadoRecurso estate;

    public RecursoDigital(EstadoRecurso estate, String name) {
        this.ID = UUID.randomUUID().toString();
        this.estate = estate;
        this.name = name;
    }

    @Override
    public void actualizarEstado(EstadoRecurso estate){
        this.estate = estate;
    }

    @Override
    public EstadoRecurso getEstado() {
        return estate;
    }


    @Override
    public String getIdentificador() {
        return ID;
    }



    public void showInfo() {
    }

    public void setEstado(EstadoRecurso estate) {
        this.estate = estate;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String name) {
        this.name = name;
    }
}
