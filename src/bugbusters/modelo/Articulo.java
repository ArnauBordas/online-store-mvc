package bugbusters.modelo;

/**
 * Clase que representa un artículo de la tienda.
 *
 * Contiene la información básica necesaria para gestionar la venta y el envío de un artículo,
 * incluyendo su precio, gastos de envío y tiempo de preparación.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */
public class Articulo {

    private String codigo;
    private String descripcion;
    private double precioVenta;
    private double gastosEnvio;
    private int tiempoPreparacionMin; // minutos

    public Articulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacionMin) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public int getTiempoPreparacionMin() {
        return tiempoPreparacionMin;
    }

    public void setTiempoPreparacionMin(int tiempoPreparacionMin) {
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }

    @Override
    public String toString() {
        return "Artículo: " +
                "Código: " + codigo +
                " | Descripción: " + descripcion +
                " | Precio: " + String.format("%.2f €", precioVenta) +
                " | Gastos envío: " + String.format("%.2f €", gastosEnvio) +
                " | Tiempo preparación: " + tiempoPreparacionMin + " min";
    }
}