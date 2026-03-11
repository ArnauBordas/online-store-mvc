package bugbusters.modelo.excepciones;

public class PedidoNoCancelableException extends Exception {
    public PedidoNoCancelableException(int numeroPedido) {
        super("[EXCEPCION] No se puede cancelar el pedido: " + numeroPedido);
    }
}