package Gestion;
import Excepciones.DatosErroneosException;
import Excepciones.RecursoNoDisponibleException;
import Excepciones.RecursoNoEncontradoException;
import Modelos.AudioLibro;
import Recursos.EstadoRecurso;
import Modelos.Libro;
import Modelos.Revista;
import Recursos.*;
import java.util.ArrayList;
import java.util.List;

public class GestorRecursos {
    private List<RecursoDigital> recursos = new ArrayList<>();


    public void agregarLibro(EstadoRecurso estate, String nombre, String autor, CategoriaRecurso categoria) {
        if (nombre == null || autor == null ){
            throw new DatosErroneosException("Ingrese los datos correctamente");
        }
        Libro libro = new Libro(estate, autor, nombre, categoria);
        recursos.add(libro);
        if (recursos.contains(libro)){
            System.out.println("Libro agregado exitosamente");
        }

    }

    public void agregarAudioLibro(EstadoRecurso estate, String name, String autor, String lector, CategoriaRecurso categoria) {
        if (name == null || autor == null || lector == null){
            throw new DatosErroneosException("Ingrese los datos correctamente");
        }
        AudioLibro audio = new AudioLibro(estate, lector, autor, name, categoria);
        recursos.add(audio);
        if (recursos.contains(audio)){
            System.out.println("Audiolibro agregado exitosamente");
        }

    }

    public void agregarRevista(EstadoRecurso estate, String marca, Integer issue, String name, CategoriaRecurso categoria) {
        if (marca == null || issue == null ){
            throw new DatosErroneosException("Ingrese los datos correctamente");
        }
        Revista revista = new Revista(estate, marca, issue, name, categoria);
        recursos.add(revista);
        if (recursos.contains(revista)){
            System.out.println("Revista agregado exitosamente");
        }

    }


    public RecursoDigital buscarPorNombre(String name) {
        return recursos.stream()
                .filter(recurso -> recurso.getNombre().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("El recurso no fue encontrado"));
    }

    public List<RecursoDigital> buscarPorTipo(Class<?> tipo) {
        List<RecursoDigital> filtrados = new ArrayList<>();

        for (RecursoDigital recurso : recursos) {
            if (tipo.isInstance(recurso)) {
                filtrados.add(recurso);
            }
        }

        if (filtrados.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron recursos del tipo: " + tipo.getSimpleName());
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

    public void buscarPorCategoria(CategoriaRecurso categoria) {
        List<RecursoDigital> filtrados = recursos.stream()
                .filter(r -> r.getCategoria() == categoria)
                .toList();

        if (filtrados.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron recursos con la categoria");
        } else {
            for (RecursoDigital recurso : filtrados) {
                recurso.showInfo();
            }
        }
    }


    public void listarRecursos() {
        if (recursos.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay recursos cargados");
        } else {
            for (RecursoDigital recurso : recursos) {
                recurso.showInfo();
            }
        }
    }

    public  void mostrarCategoriasDisponibles(){
        for (CategoriaRecurso categoria : CategoriaRecurso.values()){
            System.out.println(categoria);
        }
    }

}
