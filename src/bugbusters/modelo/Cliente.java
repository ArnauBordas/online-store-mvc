package bugbusters.modelo;

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
