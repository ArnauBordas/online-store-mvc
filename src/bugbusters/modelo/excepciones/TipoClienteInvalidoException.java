package bugbusters.modelo.excepciones;

/**
 * Excepción lanzada cuando se proporciona un tipo de cliente no válido.
 *
 * Esta excepción se utiliza para validar que el tipo de cliente introducido
 * sea uno de los valores permitidos en el sistema: 1 para cliente Estándar
 * o 2 para cliente Premium.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class TipoClienteInvalidoException extends Exception {
    public TipoClienteInvalidoException(int tipo) {
        super("[EXCEPCION] Tipo de cliente inválido: " + tipo + ". Debes escribir 1 para Estándar o 2 para Premium.");
    }
}