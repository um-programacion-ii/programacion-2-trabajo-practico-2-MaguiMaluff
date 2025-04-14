package Modelos;

import Recursos.RecursoDigital;

public class AudioLibro extends RecursoDigital {
    private String lector;
    private String autor;

    public AudioLibro(EstadoRecurso estate, String lector, String autor, String name) {
        super(estate, name);
        this.lector = lector;
        this.autor = autor;
    }

    public void showInfo() {
        System.out.println("Nombre: " + this.getNombre() + '\n' +
                            "Autor: " + this.getAutor() + '\n' +
                            "Lector: " + this.getLector() + '\n' +
                            "Estado: " + this.getEstado() + '\n' +
                            "ID: " + this.getIdentificador() + '\n');
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
