package bugbusters.modelo.excepciones;

/**
 * Excepción lanzada cuando se intenta acceder a un recurso que no existe en el sistema.
 *
 * Esta excepción se utiliza para indicar que una operación de búsqueda o acceso
 * ha fallado porque el recurso solicitado no se encuentra en la base de datos.
 * Es comúnmente utilizada en operaciones de búsqueda, modificación o eliminación
 * de artículos, clientes y pedidos.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class RecursoNoEncontradoException extends Exception {
    public RecursoNoEncontradoException(String tipo, String identificador) {
        super("[EXCEPCION] " + tipo + " no encontrado: " + identificador + ". Vuelve a intentarlo.");
    }
}