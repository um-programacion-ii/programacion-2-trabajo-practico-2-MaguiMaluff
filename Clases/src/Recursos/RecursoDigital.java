package Recursos;

import Interfaces.IRecursoDigital;

import java.util.UUID;

public abstract class RecursoDigital implements IRecursoDigital {
    private String name;
    private String ID;
    private EstadoRecurso estate;
    private CategoriaRecurso categoria;

    public RecursoDigital(EstadoRecurso estate, String name, CategoriaRecurso categoria) {
        this.ID = UUID.randomUUID().toString();
        this.estate = estate;
        this.name = name;
        this.categoria = categoria;
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

    public CategoriaRecurso getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRecurso categoria) {
        this.categoria = categoria;
    }
}
