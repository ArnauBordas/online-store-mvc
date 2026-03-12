package bugbusters.modelo;

/**
 * Clase abstracta que representa un cliente genérico de la tienda.
 *
 * Define los atributos comunes a todos los clientes y los métodos abstractos
 * que deben implementar las clases hijas para calcular la cuota y el descuento de envío.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public abstract class Cliente {
    private String email;
    private String nombre;
    private String domicilio;
    private String nif;

    public Cliente (String email, String nombre, String domicilio, String nif){
        this.email = email;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public abstract double calcularCuota();
    public abstract double descuentoEnvio();

    // Devuelve true si el cliente es premium
    public boolean esPremium() {
        return this instanceof ClientePremium;
    }
}
