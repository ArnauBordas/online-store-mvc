package bugbusters.modelo.excepciones;

/**
 * Excepción lanzada cuando se proporciona una dirección de correo electrónico
 * que no cumple con el formato válido esperado.
 *
 * Esta excepción se utiliza para validar que los emails introducidos en el sistema
 * tengan una estructura correcta (usuario@dominio.extension) antes de crear
 * o modificar clientes.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class EmailInvalidoException extends Exception {
    public EmailInvalidoException(String email) {
        super("El email no tiene un formato válido.");
    }
}