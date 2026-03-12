package bugbusters.vista;

/**
 * Clase principal que inicia la aplicación BugBusters.
 *
 * Esta clase contiene el método {@code main} que sirve como punto de entrada
 * al programa. Su única responsabilidad es crear una instancia de la clase
 * {@link Vista} e invocar su método {@code iniciar()} para comenzar la
 * interacción con el usuario.
 *
 * <p>Siguiendo el patrón MVC, esta clase se encarga únicamente de arrancar
 * la capa de presentación, que a su vez se comunicará con el controlador
 * para gestionar la lógica de la aplicación.</p>
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class Main {
    public static void main(String[] args) {
        Vista vista = new Vista();
        vista.iniciar();
    }
}