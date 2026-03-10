package bugbusters.modelo;

import java.time.LocalDateTime;

public class Pedido {

    private int numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHora;

    // Constructor
    public Pedido(int numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public int getNumeroPedido() {
        return numeroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Método para calcular el total del pedido
    public double calcularTotal() {
        double precioBase = articulo.getPrecioVenta() * cantidad;
        double envio = articulo.getGastosEnvio();

        // Aplicar descuento solo al envío si el cliente es premium
        if (cliente.esPremium()) {
            envio = envio * (1 - cliente.descuentoEnvio()); // descuentoEnvio() devuelve 0.2 para premium
        }

        return precioBase + envio;
    }

    // Método para comprobar si el pedido se puede cancelar
    public boolean puedeCancelar() {
        // Se puede cancelar solo si no ha pasado el tiempo de preparación
        LocalDateTime limiteCancelacion = fechaHora.plusMinutes(articulo.getTiempoPreparacionMin());
        return LocalDateTime.now().isBefore(limiteCancelacion);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido=" + numeroPedido +
                ", cliente=" + cliente.getNombre() +
                ", articulo=" + articulo.getDescripcion() +
                ", cantidad=" + cantidad +
                ", fechaHora=" + fechaHora +
                ", total=" + calcularTotal() +
                ", puedeCancelar=" + puedeCancelar() +
                '}';
    }
}