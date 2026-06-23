package integrado.prog2.services;

import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ReglaNegocioException;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private final List<Pedido> pedidos;
    private Long idCounter;

    public PedidoService() {
        this.pedidos = new ArrayList<>();
        this.idCounter = 1L;
    }

    public Pedido crear(Usuario usuario, FormaPago formaPago) throws ReglaNegocioException {
        if (usuario == null || usuario.isEliminado()) {
            throw new ReglaNegocioException("El pedido debe tener un usuario valido asociado.");
        }

        Pedido nuevo = new Pedido();
        nuevo.setId(idCounter++);
        nuevo.setUsuario(usuario);
        nuevo.setFormaPago(formaPago);
        
        pedidos.add(nuevo);
        return nuevo;
    }

    public void agregarDetalle(Pedido pedido, Producto producto, int cantidad) throws ReglaNegocioException {
        if (cantidad <= 0) {
            throw new ReglaNegocioException("La cantidad debe ser mayor a 0.");
        }
        if (producto.getStock() < cantidad) {
            throw new ReglaNegocioException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - cantidad);
        
        pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
    }

    public void cancelarCreacionPedido(Pedido pedido) {
        for (var detalle : pedido.getDetalles()) {
            Producto p = detalle.getProducto();
            p.setStock(p.getStock() + detalle.getCantidad());
        }
        pedidos.remove(pedido);
    }

    public List<Pedido> listar() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public Pedido buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro un pedido activo con el ID: " + id);
    }

    public void actualizarEstadoYFormaPago(Long id, Estado estado, FormaPago formaPago) throws EntidadNoEncontradaException {
        Pedido p = buscarPorId(id);
        if (estado != null) p.setEstado(estado);
        if (formaPago != null) p.setFormaPago(formaPago);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Pedido p = buscarPorId(id);
        p.setEliminado(true);
    }
}