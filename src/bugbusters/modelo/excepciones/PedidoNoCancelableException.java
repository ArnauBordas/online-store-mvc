package bugbusters.modelo.excepciones;

/**
 * Excepción lanzada cuando se intenta cancelar un pedido que ya no puede ser cancelado.
 *
 * Esta excepción se utiliza para evitar la cancelación de pedidos que ya han sido
 * procesados y enviados al cliente, manteniendo la integridad del proceso de compra.
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class PedidoNoCancelableException extends Exception {
    public PedidoNoCancelableException(int numeroPedido) {
        super("[EXCEPCIÓN] El pedido " + numeroPedido + " no puede ser cancelado porque ya ha sido enviado.");
    }
}