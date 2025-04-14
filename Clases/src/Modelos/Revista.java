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

    public void showInfo() {
        System.out.println("Nombre: " + this.getNombre() + '\n' +
                            "Marca: " + this.getMarca() + '\n' +
                            "Issue: " + this.getIssue() + '\n' +
                            "Estado: " + this.getEstado() + '\n' +
                            "ID: " + this.getIdentificador() + '\n');
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
