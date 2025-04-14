package Modelos;

import Recursos.RecursoDigital;

public class Revista extends RecursoDigital {
    private String marca;
    private Integer issue;

    public Revista(EstadoRecurso estate, String marca, Integer issue, String name) {
        super(estate, name);
        this.marca = marca;
        this.issue = issue;
    }

    public void showInfo(RecursoDigital recurso) {
        Revista revista = (Revista) recurso;
        System.out.println("Nombre: " + revista.getNombre() + '\n' +
                            "Marca: " + revista.getMarca() + '\n' +
                            "Issue: " + revista.getIssue() + '\n' +
                            "Estado: " + revista.getEstado() + '\n' +
                            "ID: " + revista.getIdentificador() + '\n');
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
}
