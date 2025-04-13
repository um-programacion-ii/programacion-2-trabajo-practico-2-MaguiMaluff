package Recursos;

public class Libro extends RecursoDigital{
    private String autor;
    private String nombre;

    public Libro(String ID, EstadoRecurso estado, String autor, String nombre) {
        super(ID, estado);
        this.autor = autor;
        this.nombre = nombre;
    }
}
