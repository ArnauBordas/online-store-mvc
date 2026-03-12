package bugbusters.modelo;

import bugbusters.modelo.excepciones.YaExisteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase que gestiona todos los datos de la aplicación.
 *
 * Actúa como repositorio central que almacena y administra las colecciones
 * de artículos, clientes y pedidos. Proporciona métodos para añadir, buscar,
 * eliminar y filtrar los diferentes recursos del sistema.
 *
 * Las colecciones utilizadas son:
 * - {@link LinkedHashMap} para artículos (mantiene orden de inserción)
 * - {@link ArrayList} para pedidos
 * - {@link GenericoDAO} para clientes (implementación genérica)
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */

public class Datos {
    /**
     * Colección de artículos almacenados por código.
     * Se utiliza {@link LinkedHashMap} para mantener el orden de inserción.
     */
    private Map<String, Articulo> articulos;

    /* =========================================================
       =================== COLECCIÓN DE PEDIDOS ===============
       ========================================================= */

    /**
     * Lista de todos los pedidos realizados en el sistema.
     */
    private List<Pedido> listaPedidos;

    /**
     * Último número de pedido generado.
     * Se utiliza para asignar números secuenciales a los nuevos pedidos.
     */
    private int ultimoNumeroPedido;

    /**
     * DAO genérico para la gestión de clientes.
     * Utiliza el email como clave identificadora.
     */
    private GenericoDAO<String, Cliente> clientes;

    /**
     * Constructor que inicializa todas las colecciones vacías.
     */
    public Datos() {
        articulos = new LinkedHashMap<>();
        listaPedidos = new ArrayList<>();
        clientes = new GenericoDAO<>();
        ultimoNumeroPedido = 0;
    }

    /* =========================================================
       ================= GESTIÓN DE ARTÍCULOS ==================
       ========================================================= */

    /**
     * Añade un artículo a la colección.
     *
     * @param articulo El artículo a añadir
     */
    public void anadirArticulo(Articulo articulo) {
        String codigo = articulo.getCodigo().toLowerCase();
        articulos.put(codigo, articulo);
    }

    /**
     * Busca un artículo a partir de su código.
     *
     * @param codigo Código del artículo que se quiere buscar
     * @return El objeto Articulo si existe, null si no existe
     */
    public Articulo buscarArticulo(String codigo) {
        return articulos.get(codigo.toLowerCase());
    }

    /**
     * Comprueba si ya existe un artículo con un código concreto.
     *
     * @param codigo Código que se desea comprobar
     * @return true si el artículo existe, false si no existe
     */
    public boolean existeArticulo(String codigo) {
        return articulos.containsKey(codigo.toLowerCase());
    }

    /**
     * Devuelve todos los artículos guardados en forma de lista.
     *
     * @return Lista con todos los artículos
     */
    public List<Articulo> obtenerTodosArticulos() {
        return new ArrayList<>(articulos.values());
    }

    /* =========================================================
       ================= GESTIÓN DE PEDIDOS ===================
       ========================================================= */

    /**
     * Genera un número de pedido único incremental.
     *
     * @return Número de pedido único
     */
    public int generarNumeroPedido() {
        ultimoNumeroPedido++;
        return ultimoNumeroPedido;
    }

    /**
     * Añade un pedido a la lista de pedidos.
     *
     * @param pedido El pedido a añadir
     */
    public void anadirPedido(Pedido pedido) {
        listaPedidos.add(pedido);
    }

    /**
     * Elimina un pedido de la lista.
     *
     * @param pedido El pedido a eliminar
     */
    public void eliminarPedido(Pedido pedido) {
        listaPedidos.remove(pedido);
    }

    /**
     * Busca un pedido por su número.
     *
     * @param numeroPedido Número del pedido a buscar
     * @return El pedido si existe, null si no existe
     */
    public Pedido buscarPedido(int numeroPedido) {
        for (Pedido p : listaPedidos) {
            if (p.getNumeroPedido() == numeroPedido) return p;
        }
        return null;
    }

    /**
     * Devuelve la lista de pedidos pendientes (no enviados).
     *
     * @return Lista de pedidos pendientes
     */
    public List<Pedido> getPedidosPendientes() {
        return listaPedidos.stream()
                .filter(Pedido::puedeCancelar)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve la lista de pedidos ya enviados.
     *
     * @return Lista de pedidos enviados
     */
    public List<Pedido> getPedidosEnviados() {
        return listaPedidos.stream()
                .filter(p -> !p.puedeCancelar())
                .collect(Collectors.toList());
    }

    /* =========================================================
      ================= GESTIÓN DE CLIENTES =====================
      ========================================================= */
    /**
     * Añade un cliente a la colección.
     *
     * @param cliente El cliente a añadir
     */
    public void anadirCliente(Cliente cliente) {
        clientes.anadir(cliente.getEmail(), cliente);
    }

    /**
     * Comprueba si ya existe un cliente con un email concreto.
     *
     * @param email Email que se desea comprobar
     * @return true si el cliente existe, false si no existe
     */
    public boolean existeCliente(String email) {
        return clientes.existe(email);
    }

    /**
     * Busca un cliente por su email.
     *
     * @param email Email del cliente a buscar
     * @return El cliente si existe, null si no existe
     */
    public Cliente buscarCliente(String email) {
        return clientes.buscar(email);
    }

    /**
     * Devuelve la lista completa de clientes.
     *
     * @return ArrayList con todos los clientes
     */
    public ArrayList<Cliente> obtenerTodosClientes() {
        return clientes.obtenerTodos();
    }

    /**
     * Filtra y devuelve solo los clientes de tipo Estándar.
     *
     * @return ArrayList con los clientes estándar
     */
    public ArrayList<Cliente> obtenerClientesEstandar() {
        ArrayList<Cliente> listaEstandar = new ArrayList<>();
        for (Cliente c : clientes.obtenerTodos()) {
            if (c instanceof ClienteEstandar) {
                listaEstandar.add(c);
            }
        }
        return listaEstandar;
    }

    /**
     * Filtra y devuelve solo los clientes de tipo Premium.
     *
     * @return ArrayList con los clientes premium
     */
    public ArrayList<Cliente> obtenerClientesPremium() {
        ArrayList<Cliente> listaPremium = new ArrayList<>();
        for (Cliente c : clientes.obtenerTodos()) {
            if (c instanceof ClientePremium) {
                listaPremium.add(c);
            }
        }
        return listaPremium;
    }

    /**
     * Elimina un cliente por su email.
     *
     * @param email Email del cliente a eliminar
     * @return true si se eliminó correctamente, false si no existía
     */
    public boolean eliminarCliente(String email) {
        if (clientes.existe(email)) {
            clientes.eliminar(email);
            return true;
        }
        return false;
    }
}