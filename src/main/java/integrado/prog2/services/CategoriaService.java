package integrado.prog2.services;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ReglaNegocioException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    private List<Categoria> categorias;
    private Long idCounter;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.idCounter = 1L;
    }

    public Categoria crear(String nombre, String descripcion) throws ReglaNegocioException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre de la categoría no puede estar vacío.");
        }
        
        for (Categoria cat : categorias) {
            if (cat.getNombre().equalsIgnoreCase(nombre) && !cat.isEliminado()) {
                throw new ReglaNegocioException("Ya existe una categoría activa con el nombre: " + nombre);
            }
        }
        
        Categoria nueva = new Categoria(idCounter++, nombre, descripcion);
        categorias.add(nueva);
        return nueva;
    }

    public List<Categoria> listar() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria cat : categorias) {
            if (!cat.isEliminado()) {
                activas.add(cat);
            }
        }
        return activas;
    }

    public Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Categoria cat : categorias) {
            if (cat.getId().equals(id) && !cat.isEliminado()) {
                return cat;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró ninguna categoría activa con el ID: " + id);
    }

    public void editar(Long id, String nuevoNombre, String nuevaDescripcion) throws EntidadNoEncontradaException, ReglaNegocioException {
        Categoria cat = buscarPorId(id);
        
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            if (!cat.getNombre().equalsIgnoreCase(nuevoNombre)) {
                for (Categoria c : categorias) {
                    if (c.getNombre().equalsIgnoreCase(nuevoNombre) && !c.isEliminado()) {
                        throw new ReglaNegocioException("Ya existe otra categoría con el nombre: " + nuevoNombre);
                    }
                }
            }
            cat.setNombre(nuevoNombre);
        }
        
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            cat.setDescripcion(nuevaDescripcion);
        }
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Categoria cat = buscarPorId(id);
        cat.setEliminado(true);
    }
}