package Gestion;
import Modelos.AudioLibro;
import Modelos.EstadoRecurso;
import Modelos.Libro;
import Modelos.Revista;
import Recursos.*;
import java.util.ArrayList;
import java.util.List;

public class GestorRecursos {
    private List<RecursoDigital> recursos = new ArrayList<>();


    public void agregarLibro(EstadoRecurso estate, String nombre, String autor) {
        if (nombre == null || autor == null ){
            System.out.println("Ingrese los datos correctamente");
        }
        Libro libro = new Libro(estate, autor, nombre);
        recursos.add(libro);
        if (recursos.contains(libro)){
            System.out.println("Libro agregado exitosamente");
        }

    }

    public void agregarAudioLibro(EstadoRecurso estate, String name, String autor, String lector) {
        if (name == null || autor == null || lector == null){
            System.out.println("Ingrese los datos correctamente");
        }
        AudioLibro audio = new AudioLibro(estate, lector, autor, name);
        recursos.add(audio);
        if (recursos.contains(audio)){
            System.out.println("Audiolibro agregado exitosamente");
        }

    }

    public void agregarRevista(EstadoRecurso estate, String marca, Integer issue, String name) {
        if (marca == null || issue == null ){
            System.out.println("Ingrese los datos correctamente");
        }
        Revista revista = new Revista(estate, marca, issue, name);
        recursos.add(revista);
        if (recursos.contains(revista)){
            System.out.println("Revista agregado exitosamente");
        }

    }


    public void buscarPorNombre(String name) {
        boolean notFound = true;
        for (RecursoDigital recurso : recursos) {
            if (recurso.getNombre().trim().equalsIgnoreCase(name.trim())){
                recurso.showInfo();
                notFound = false;
                break;
            }
        }
        if (notFound){
            System.out.println("Recurso no encontrado");
        }

    }

    public void listarRecursos() {
        if (recursos.isEmpty()) {
            System.out.println("No hay recursos cargados");
        } else {
            for (RecursoDigital recurso : recursos) {
                recurso.showInfo();
            }
        }
    }

}
