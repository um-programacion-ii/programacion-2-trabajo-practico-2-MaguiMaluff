package Modelos;

import Interfaces.Prestable;
import Interfaces.Renovable;
import Interfaces.ServicioNotificaciones;
import Recursos.RecursoDigital;

import java.time.LocalDateTime;
import java.util.SortedMap;

public class Libro extends RecursoDigital implements Prestable, Renovable {
    private String autor;
    private LocalDateTime fechaDevolucion = null;
    private Usuario tiene = null;
    private ServicioNotificacionesEmail servicioNotificaciones = new ServicioNotificacionesEmail();

    public Libro(EstadoRecurso estate, String autor, String name) {
        super(estate, name);
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
    public void renovar(Usuario usuario) {
        if (getEstado() == EstadoRecurso.PRESTADO && this.tiene.equals(usuario)) {
            this.fechaDevolucion = fechaDevolucion.plusDays(7);
            System.out.println("Renovación exitosa. Nueva fecha: " + fechaDevolucion);
            servicioNotificaciones.enviarNotificacion("El recurso " + this.getNombre() + " ha sido renovado." +
                        '\n' + " Nueva fecha de devolución: " + fechaDevolucion);
        } else {
            System.out.println("El libro no está prestado, no se puede renovar.");
        }
    }


    @Override
    public boolean estaDisponible(){
        if (this.getEstado() == EstadoRecurso.DISPONIBLE){
            return true;
        }else{
            return false;}
    }

    @Override
    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void prestar(Usuario usuario){
        if(this.getEstado().equals(EstadoRecurso.DISPONIBLE)){
            this.setEstado(EstadoRecurso.PRESTADO);
            this.tiene = usuario;
            this.fechaDevolucion = LocalDateTime.now().plusDays(14);
            servicioNotificaciones.enviarNotificacion("El recurso " + this.getNombre() +
                    " ha sido prestado" + '\n' + "Fecha de devolución: " + fechaDevolucion);
            System.out.println("Prestamo exitoso");
        }else{
            System.out.println("Libro no Disponible");
        }

    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
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
