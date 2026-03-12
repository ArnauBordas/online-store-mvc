package bugbusters.modelo;

/**
 * Clase que representa un cliente estándar de la tienda.
 *
 * Los clientes estándar no pagan cuota ni tienen descuento en los gastos de envío.
 * Hereda de la clase abstracta Cliente e implementa los métodos abstractos.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class ClienteEstandar extends Cliente{
    public ClienteEstandar (String email, String nombre, String domicilio, String nif){
        super(email, nombre, domicilio, nif);
    }

    @Override
    public double calcularCuota() {
        return 0.0;
    }

    @Override
    public double descuentoEnvio() {
        return 0.0;
    }

    @Override
    public String toString() {
        return "[Cliente Estandar] "+
                "Email: " + getEmail() +
                " | Nombre: " + getNombre() +
                " | Domicilio: " + getDomicilio() +
                " | NIF: " + getNif();
    }
}
