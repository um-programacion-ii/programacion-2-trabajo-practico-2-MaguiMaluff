package Recursos;

public class Revista extends RecursoDigital{
    private String marca;
    private Integer issue;

    public Revista(String ID, EstadoRecurso estado, String marca, Integer issue) {
        super(ID, estado);
        this.marca = marca;
        this.issue = issue;
    }
}
