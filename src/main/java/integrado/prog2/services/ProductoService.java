package integrado.prog2.services;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ReglaNegocioException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final List<Producto> productos;
    private Long idCounter;

    public ProductoService() {
        this.productos = new ArrayList<>();
        this.idCounter = 1L;
    }

    public Producto crear(String nombre, Double precio, String descripcion, int stock, String imagen, Boolean disponible, Categoria categoria) throws ReglaNegocioException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre del producto no puede estar vacio.");
        }
        if (precio < 0) {
            throw new ReglaNegocioException("El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new ReglaNegocioException("El stock no puede ser negativo.");
        }
        if (categoria == null || categoria.isEliminado()) {
            throw new ReglaNegocioException("La categoria asignada no es valida o fue eliminada.");
        }

        Producto nuevo = new Producto(idCounter++, nombre, precio, descripcion, stock, imagen, disponible, categoria);
        productos.add(nuevo);
        return nuevo;
    }

    public List<Producto> listar() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro un producto activo con el ID: " + id);
    }

    public void editar(Long id, String nombre, Double precio, String descripcion, Integer stock, String imagen, Boolean disponible, Categoria categoria) throws EntidadNoEncontradaException, ReglaNegocioException {
        Producto p = buscarPorId(id);

        if (nombre != null && !nombre.trim().isEmpty()) {
            p.setNombre(nombre);
        }
        if (precio != null) {
            if (precio < 0) throw new ReglaNegocioException("El precio no puede ser negativo.");
            p.setPrecio(precio);
        }
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            p.setDescripcion(descripcion);
        }
        if (stock != null) {
            if (stock < 0) throw new ReglaNegocioException("El stock no puede ser negativo.");
            p.setStock(stock);
        }
        if (imagen != null && !imagen.trim().isEmpty()) {
            p.setImagen(imagen);
        }
        if (disponible != null) {
            p.setDisponible(disponible);
        }
        if (categoria != null) {
            p.setCategoria(categoria);
        }
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Producto p = buscarPorId(id);
        p.setEliminado(true);
    }
}
