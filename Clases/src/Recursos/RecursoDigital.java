package Recursos;

import Interfaces.IRecursoDigital;
import Interfaces.Prestable;
import Modelos.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class RecursoDigital implements IRecursoDigital, Prestable {

    private String ID;
    private EstadoRecurso estado;
    private LocalDateTime fechaDevolucion;

    public RecursoDigital(String ID, EstadoRecurso estado) {

        this.ID = UUID.randomUUID().toString();
        this.estado = estado;
    }

    @Override
    public void actualizarEstado(EstadoRecurso estado){
        this.estado = estado;
    }

    @Override
    public EstadoRecurso getEstado() {
        return estado;
    }

    @Override
    public boolean estaDisponible(){
        if (this.estado == EstadoRecurso.DISPONIBLE){
            return true;
        }else{
            return false;}
    }

    @Override
    public LocalDateTime getFechaDevolucion(){
        return fechaDevolucion;
    }

    @Override
    public String getIdentificador() {
        return ID;
    }

    @Override
    public void prestar(Usuario usuario){

    }

    public void setEstado(EstadoRecurso estado) {
        this.estado = estado;
    }



}
