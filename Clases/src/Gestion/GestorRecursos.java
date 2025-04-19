package Gestion;
import Excepciones.DatosErroneosException;
import Modelos.AudioLibro;
import Recursos.EstadoRecurso;
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
            throw new DatosErroneosException("Ingrese los datos correctamente");
        }
        AudioLibro audio = new AudioLibro(estate, lector, autor, name);
        recursos.add(audio);
        if (recursos.contains(audio)){
            System.out.println("Audiolibro agregado exitosamente");
        }

    }

    public void agregarRevista(EstadoRecurso estate, String marca, Integer issue, String name) {
        if (marca == null || issue == null ){
            throw new DatosErroneosException("Ingrese los datos correctamente");
        }
        Revista revista = new Revista(estate, marca, issue, name);
        recursos.add(revista);
        if (recursos.contains(revista)){
            System.out.println("Revista agregado exitosamente");
        }

    }


    public RecursoDigital buscarPorNombre(String name) {
        return recursos.stream()
                .filter(recurso -> recurso.getNombre().equalsIgnoreCase(name.trim()))
                .findFirst()
                .map(recurso -> {
                    recurso.showInfo();
                    return recurso;
                })
                .orElseGet(() -> {
                    System.out.println("Recurso no encontrado");
                    return null;
                });
    }

    public List<RecursoDigital> buscarPorTipo(Class<?> tipo) {
        List<RecursoDigital> filtrados = new ArrayList<>();

        for (RecursoDigital recurso : recursos) {
            if (tipo.isInstance(recurso)) {
                filtrados.add(recurso);
            }
        }

        if (filtrados.isEmpty()) {
            System.out.println("No se encontraron recursos del tipo: " + tipo.getSimpleName());
        } else {
            for (RecursoDigital recurso : filtrados) {
                recurso.showInfo();
            }
        }

        return filtrados;
    }

    public void ordenarPorNombre() {
        recursos.sort((r1, r2) -> r1.getNombre().compareToIgnoreCase(r2.getNombre()));
        System.out.println("Recursos ordenados por nombre:");
        listarRecursos();
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
