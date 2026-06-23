package integrado.prog2.entities;

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido() {
        super();
    }

    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }

    private Double calcularSubtotal() {
        if (this.producto != null && this.producto.getPrecio() != null) {
            return this.cantidad * this.producto.getPrecio();
        }
        return 0.0;
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
        this.subtotal = calcularSubtotal();
    }

    public Double getSubtotal() { return subtotal; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { 
        this.producto = producto; 
        this.subtotal = calcularSubtotal();
    }

    @Override
    public String toString() {
        return cantidad + "x " + (producto != null ? producto.getNombre() : "Producto Desconocido") + " | Subtotal: $" + subtotal;
    }
}