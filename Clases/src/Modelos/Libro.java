package Modelos;

import Recursos.RecursoDigital;

public class Libro extends RecursoDigital {
    private String autor;

    public Libro(EstadoRecurso estate, String autor, String name) {
        super(estate, name);
        this.autor = autor;

    }

    public void showInfo() {
        System.out.println("Nombre: " + this.getNombre() + '\n' +
                            "Autor: " + this.getAutor() + '\n' +
                            "Estado: " + this.getEstado() + '\n' +
                            "ID: " + this.getIdentificador() +'\n');
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
