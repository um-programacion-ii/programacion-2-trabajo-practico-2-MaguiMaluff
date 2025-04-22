package Modelos;

import Excepciones.RecursoNoDisponibleException;
import Interfaces.Prestable;
import Interfaces.Renovable;
import Recursos.CategoriaRecurso;
import Recursos.EstadoRecurso;
import Recursos.RecursoDigital;
import Servicios.ServicioNotificacionesSMS;

import java.time.LocalDateTime;

public class Revista extends RecursoDigital implements Prestable, Renovable {
    private String marca;
    private Integer issue;
    private LocalDateTime fechaDevolucion = null;
    private Usuario tiene = null;

    public Revista(EstadoRecurso estate, String marca, Integer issue, String name, CategoriaRecurso categoria) {
        super(estate, name, categoria);
        this.marca = marca;
        this.issue = issue;

    }

    public void showInfo() {
        System.out.println("Nombre: " + this.getNombre() + '\n' +
                            "Marca: " + this.getMarca() + '\n' +
                            "Issue: " + this.getIssue() + '\n' +
                            "Estado: " + this.getEstado() + '\n' +
                            "ID: " + this.getIdentificador() + '\n');
        if (this.getEstado().equals(EstadoRecurso.PRESTADO)){
            System.out.println("Fecha Devolucion: " + this.getFechaDevolucion() + '\n' +
                                "Usuario: " + this.getTiene());

        }
    }

    public synchronized void prestar(Usuario usuario) {
        System.out.println("[Hilo: " + Thread.currentThread().getName() + "] intentando prestar el recurso: " + this.getNombre());

        if (this.estaDisponible()) {
            this.setEstado(EstadoRecurso.PRESTADO);
            this.tiene = usuario;
            this.fechaDevolucion = LocalDateTime.now().plusDays(14);

            System.out.println("[Hilo: " + Thread.currentThread().getName() + "] préstamo exitoso: " + this.getNombre());
        } else {
            System.out.println("[Hilo: " + Thread.currentThread().getName() + "] préstamo fallido (no disponible): " + this.getNombre());
            throw new RecursoNoDisponibleException("El recurso no está disponible.");
        }
    }


    @Override
    public synchronized void resetearFechaEstado(){
        this.setEstado(EstadoRecurso.DISPONIBLE);
        this.setFechaDevolucion(null);
    }

    @Override
    public synchronized void renovar(Usuario usuario) {
        if (getEstado() == EstadoRecurso.PRESTADO && this.tiene.equals(usuario)) {
            this.fechaDevolucion = fechaDevolucion.plusDays(7);
        } else {
            throw new RecursoNoDisponibleException("La revista no está prestado, no se puede renovar.");
        }
    }

    @Override
    public synchronized boolean estaDisponible(){
        if(this.getEstado() == EstadoRecurso.DISPONIBLE){
            return true;
        }else{
            return false;}
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fecha) {
        this.fechaDevolucion = fecha;
    }

    public Usuario getTiene() {
        return tiene;
    }

    public void setTiene(Usuario tiene) {
        this.tiene = tiene;
    }

}
