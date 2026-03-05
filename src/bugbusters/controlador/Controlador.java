package bugbusters.controlador;

import bugbusters.modelo.Articulo;
import bugbusters.modelo.Datos;
import bugbusters.modelo.Cliente;
import bugbusters.modelo.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Clase Controlador
 *
 * Esta clase actúa como puente entre la Vista y el Modelo.
 *
 * En el patrón MVC:
 * - la Vista no debe acceder directamente al Modelo
 * - la Vista solo debe comunicarse con el Controlador
 * - el Controlador es quien llama a la clase Datos
 *
 * =========================================================
 * BLOQUE IMPLEMENTADO POR: Erick Coll Rodríguez
 * PARTE DESARROLLADA: Gestión de artículos
 * =========================================================
 */
public class Controlador {

    /*
     * Referencia al modelo principal de la aplicación.
     *
     * La clase Datos se encargará de almacenar toda la información
     * del sistema.
     */
    private Datos datos;

    /*
     * Constructor
     *
     * Crea el objeto Datos para poder trabajar con la información
     * de la aplicación.
     */
    public Controlador() {
        datos = new Datos();
    }

    /* =========================================================
       ================= GESTIÓN DE ARTÍCULOS ==================
       ========== IMPLEMENTADO POR ERICK COLL RODRÍGUEZ ========
       ========================================================= */

    /*
     * anadirArticulo()
     *
     * Recibe los datos de un artículo desde la vista,
     * crea el objeto Articulo y lo envía al modelo.
     *
     * Parámetros:
     * - codigo
     * - descripcion
     * - precioVenta
     * - gastosEnvio
     * - tiempoPreparacionMin
     */
    public void anadirArticulo(String codigo, String descripcion, double precioVenta,
                               double gastosEnvio, int tiempoPreparacionMin) {

        Articulo articulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacionMin);
        datos.anadirArticulo(articulo);
    }

    /*
     * buscarArticulo()
     *
     * Busca un artículo a partir de su código.
     *
     * Parámetro:
     * - codigo -> código del artículo
     *
     * Devuelve:
     * - el objeto Articulo si existe
     * - null si no existe
     */
    public Articulo buscarArticulo(String codigo) {
        return datos.buscarArticulo(codigo);
    }

    /*
     * existeArticulo()
     *
     * Comprueba si ya existe un artículo con ese código.
     *
     * Devuelve:
     * - true si existe
     * - false si no existe
     */
    public boolean existeArticulo(String codigo) {
        return datos.existeArticulo(codigo);
    }

    /*
     * obtenerTodosArticulos()
     *
     * Devuelve una lista con todos los artículos almacenados.
     *
     * Esta lista se utilizará desde la vista para mostrar
     * los artículos por pantalla.
     */
    public List<Articulo> obtenerTodosArticulos() {
        return datos.obtenerTodosArticulos();
    }

    /* =========================================================
       =================== GESTIÓN DE PEDIDOS ==================
       ========== BLOQUE PARA NUEVA FUNCIONALIDAD =============
       ========================================================= */

    /*
     * anadirPedido()
     *
     * Añade un pedido a la lista de pedidos.
     * Si el cliente no existe, lo crea y lo añade a la lista de clientes.
     * El artículo debe existir previamente.
     *
     * Parámetros:
     * - nombreCliente, emailCliente, esPremium -> datos del cliente
     * - codigoArticulo -> artículo a comprar
     * - cantidad -> número de unidades
     */
    public void anadirPedido(String nombreCliente, String emailCliente, boolean esPremium,
                             String codigoArticulo, int cantidad) {

        // Buscar cliente en datos
        Cliente cliente = datos.buscarCliente(nombreCliente);

        // Si el cliente no existe, crearlo y añadirlo
        if(cliente == null) {
            cliente = new Cliente(nombreCliente, emailCliente, esPremium);
            datos.anadirCliente(cliente);
        }

        // Buscar artículo
        Articulo articulo = datos.buscarArticulo(codigoArticulo);
        if(articulo == null) {
            System.out.println("El artículo no existe. No se puede crear el pedido.");
            return;
        }

        // Crear el pedido
        int numeroPedido = datos.generarNumeroPedido(); // método que genera IDs únicos
        Pedido pedido = new Pedido(numeroPedido, cliente, articulo, cantidad, LocalDateTime.now());

        // Añadir pedido a la lista de pedidos
        datos.anadirPedido(pedido);

        System.out.println("Pedido añadido correctamente: " + pedido);
    }

    /*
     * eliminarPedido()
     *
     * Elimina un pedido únicamente si no ha sido enviado.
     * Un pedido se considera enviado si ha pasado el tiempo de preparación del artículo.
     *
     * Parámetros:
     * - numeroPedido -> identificador del pedido
     */
    public void eliminarPedido(int numeroPedido) {
        Pedido pedido = datos.buscarPedido(numeroPedido);

        if(pedido == null) {
            System.out.println("No existe un pedido con ese número.");
            return;
        }

        if(pedido.puedeCancelar()) {
            datos.eliminarPedido(pedido);
            System.out.println("Pedido eliminado correctamente.");
        } else {
            System.out.println("No se puede eliminar el pedido porque ya ha sido enviado.");
        }
    }

    /*
     * mostrarPedidosPendientes()
     *
     * Muestra los pedidos que todavía no han sido enviados.
     * Se puede filtrar opcionalmente por nombre de cliente.
     *
     * Parámetros:
     * - nombreCliente -> si es null o vacío, muestra todos
     */
    public void mostrarPedidosPendientes(String nombreCliente) {
        List<Pedido> pedidos = datos.getPedidosPendientes();

        if(nombreCliente != null && !nombreCliente.isEmpty()) {
            pedidos = pedidos.stream()
                    .filter(p -> p.getCliente().getNombre().equalsIgnoreCase(nombreCliente))
                    .collect(Collectors.toList());
        }

        if(pedidos.isEmpty()) {
            System.out.println("No hay pedidos pendientes que mostrar.");
        } else {
            pedidos.forEach(System.out::println);
        }
    }

    /*
     * mostrarPedidosEnviados()
     *
     * Muestra los pedidos que ya han sido enviados.
     * Se puede filtrar opcionalmente por nombre de cliente.
     *
     * Parámetros:
     * - nombreCliente -> si es null o vacío, muestra todos
     */
    public void mostrarPedidosEnviados(String nombreCliente) {
        List<Pedido> pedidos = datos.getPedidosEnviados();

        if(nombreCliente != null && !nombreCliente.isEmpty()) {
            pedidos = pedidos.stream()
                    .filter(p -> p.getCliente().getNombre().equalsIgnoreCase(nombreCliente))
                    .collect(Collectors.toList());
        }

        if(pedidos.isEmpty()) {
            System.out.println("No hay pedidos enviados que mostrar.");
        } else {
            pedidos.forEach(System.out::println);
        }
    }
}