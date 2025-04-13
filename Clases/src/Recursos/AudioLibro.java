package Recursos;

public class AudioLibro extends RecursoDigital{
    private String lector;
    private String autor;
    private String nombre;

    public AudioLibro(String ID, EstadoRecurso estado, String lector, String autor, String nombre) {
        super(ID, estado);
        this.lector = lector;
        this.autor = autor;
        this.nombre = nombre;
    }
}
