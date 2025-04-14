package Modelos;

import Recursos.RecursoDigital;

public class Libro extends RecursoDigital {
    private String autor;

    public Libro(EstadoRecurso estate, String autor, String name) {
        super(estate, name);
        this.autor = autor;

    }

    public void showInfo(RecursoDigital recurso) {
        Libro libro = (Libro) recurso;
        System.out.println("Nombre: " + libro.getNombre() + '\n' +
                            "Autor: " + libro.getAutor() + '\n' +
                            "Estado: " + libro.getEstado() + '\n' +
                            "ID: " + libro.getIdentificador() +'\n');
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
