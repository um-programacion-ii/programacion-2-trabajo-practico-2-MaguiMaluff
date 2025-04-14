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

    public void showInfo(RecursoDigital recurso) {
        AudioLibro audioLibro = (AudioLibro) recurso;
        System.out.println("Nombre: " + audioLibro.getNombre() + '\n' +
                            "Autor: " + audioLibro.getAutor() + '\n' +
                            "Lector: " + audioLibro.getLector() + '\n' +
                            "Estado: " + audioLibro.getEstado() + '\n' +
                            "ID: " + audioLibro.getIdentificador() + '\n');
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
