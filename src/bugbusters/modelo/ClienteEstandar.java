package bugbusters.modelo;

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
