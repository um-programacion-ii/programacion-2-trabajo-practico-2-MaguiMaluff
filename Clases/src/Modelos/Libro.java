package Modelos;

import Excepciones.RecursoNoDisponibleException;
import Interfaces.Prestable;
import Interfaces.Renovable;
import Recursos.CategoriaRecurso;
import Recursos.EstadoRecurso;
import Recursos.RecursoDigital;

import java.time.LocalDate;

public class Libro extends RecursoDigital implements Prestable, Renovable {
    private String autor;
    private LocalDate fechaDevolucion = null;
    private Usuario tiene = null;

    public Libro(EstadoRecurso estate, String autor, String name, CategoriaRecurso categoria) {
        super(estate, name, categoria);
        this.autor = autor;

    }

    public void showInfo() {
        System.out.println("Nombre: " + this.getNombre() + '\n' +
                            "Autor: " + this.getAutor() + '\n' +
                            "Estado: " + this.getEstado() + '\n' +
                            "ID: " + this.getIdentificador() +'\n');
        if (this.getEstado().equals(EstadoRecurso.PRESTADO)){
            System.out.println("Fecha Devolucion: " + this.getFechaDevolucion() + '\n' +
                                "Usuario: " + this.getTiene());

        }
    }
    public synchronized void renovar(Usuario usuario) {
        System.out.println("Verificando que este prestado...");
        System.out.println("Verificando usuario... ");
        if (getEstado() == EstadoRecurso.PRESTADO && this.tiene.equals(usuario)) {
            System.out.println("Renovando!...");
            this.fechaDevolucion = fechaDevolucion.plusDays(7);
        } else {
            throw new RecursoNoDisponibleException("El libro no está prestado, no se puede renovar.");
        }
    }


    @Override
    public synchronized boolean estaDisponible() {
        if (this.getEstado() == EstadoRecurso.DISPONIBLE) {
            if (this.getFechaDevolucion() != null && LocalDate.now().isBefore(this.getFechaDevolucion())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public synchronized void prestar(Usuario usuario) {
        System.out.println("[Hilo: " + Thread.currentThread().getName() + "] intentando prestar el recurso: " + this.getNombre());

        if (this.estaDisponible()) {
            this.setEstado(EstadoRecurso.PRESTADO);
            this.tiene = usuario;
            this.fechaDevolucion = LocalDate.now().plusDays(14);

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

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Usuario getTiene() {
        return tiene;
    }

    public void setTiene(Usuario tiene) {
        this.tiene = tiene;
    }

}
