package bugbusters.modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*
 * Clase Datos
 *
 * BLOQUE IMPLEMENTADO POR: Erick Coll Rodríguez
 * PARTE DESARROLLADA: Gestión de artículos y pedidos
 * =========================================================
 *
 * En esta primera versión se implementa:
 * - la colección de artículos
 * - el alta de artículos
 * - la búsqueda de artículos
 * - la comprobación de existencia
 * - la obtención del listado completo
 *
 * Hemos elegido LinkedHashMap porque:
 * - permite buscar rápido por clave
 * - mantiene el orden de inserción
 *
 * En el caso de artículos:
 * - clave  -> código del artículo
 * - valor  -> objeto Articulo
 * Colecciones implementadas:
 * - artículos
 * - pedidos
      ========================================================= */
public class Datos {


    /*
     * BLOQUE DE ERICK COLL RODRÍGUEZ
     *
     * Mapa donde se guardarán todos los artículos.
     *
     * La clave será el código del artículo en minúsculas,
     * para evitar problemas si un usuario introduce el código
     * con mayúsculas o minúsculas diferentes.
     * para evitar problemas de mayúsculas/minúsculas.
     */
    private Map<String, Articulo> articulos;

    /* =========================================================
       =================== COLECCIÓN DE PEDIDOS ===============
       ========================================================= */

    /*
     * Lista de todos los pedidos realizados.
     */
    private List<Pedido> listaPedidos;

    /*
     * Último número de pedido generado
     */
    private int ultimoNumeroPedido;

    /*
     * Constructor
     *
     * Inicializa la colección de artículos vacía.
     *
     * Más adelante, cuando otros compañeros añadan su parte,
     * aquí también se inicializarán las colecciones de clientes
     * y pedidos.
     * Inicializa las colecciones vacías.
     */
    private GenericoDAO<String, Cliente> clientes;
    public Datos() {
        articulos = new LinkedHashMap<>();
        listaPedidos = new ArrayList<>();
        clientes = new GenericoDAO<>();
        ultimoNumeroPedido = 0;
    }

    /* =========================================================
       ================= GESTIÓN DE ARTÍCULOS ==================
       ========== IMPLEMENTADO POR ERICK COLL RODRÍGUEZ ========
       ========================================================= */

    /*
     * anadirArticulo()
     *
     * Añade un artículo a la colección.
     *
     * Parámetro:
     * - articulo -> objeto Articulo que se quiere guardar
     *
     * Funcionamiento:
     * - se usa el código del artículo como clave
     * - se convierte la clave a minúsculas para unificar búsquedas
     *
     * IMPORTANTE:
     * En esta versión básica, si se añade un artículo con un código
     * que ya existe, el nuevo artículo sustituirá al anterior,
     * porque usamos put().
     *
     * Si más adelante el grupo decide evitar duplicados,
     * se podrá ampliar fácilmente con validaciones o excepciones.
     */
    public void anadirArticulo(Articulo articulo) {
        articulos.put(articulo.getCodigo().toLowerCase(), articulo);
    }

    /*
     * buscarArticulo()
     *
     * Busca un artículo a partir de su código.
     *
     * Parámetro:
     * - codigo -> código del artículo que se quiere buscar
     *
     * Devuelve:
     * - el objeto Articulo si existe
     * - null si no existe
     *
     * Se convierte el código a minúsculas para que la búsqueda
     * sea consistente con la forma en que se guardan los datos.
     */
    public Articulo buscarArticulo(String codigo) {
        return articulos.get(codigo.toLowerCase());
    }

    /*
     * existeArticulo()
     *
     * Comprueba si ya existe un artículo con un código concreto.
     *
     * Parámetro:
     * - codigo -> código que se desea comprobar
     *
     * Devuelve:
     * - true si el artículo existe
     * - false si no existe
     */
    public boolean existeArticulo(String codigo) {
        return articulos.containsKey(codigo.toLowerCase());
    }

    /*
     * obtenerTodosArticulos()
     *
     * Devuelve todos los artículos guardados en forma de lista.
     *
     * Esto es útil porque luego, desde la vista, será mucho más fácil
     * recorrer la colección y mostrar los artículos por pantalla.
     *
     * Se crea una nueva lista a partir de los valores del mapa
     * para no exponer directamente la estructura interna.
     */
    public List<Articulo> obtenerTodosArticulos() {
        return new ArrayList<>(articulos.values());
    }

    /* =========================================================
       ================= GESTIÓN DE PEDIDOS ===================
       ========================================================= */

    /*
     * Genera un número de pedido único incremental
     */
    public int generarNumeroPedido() {
        ultimoNumeroPedido++;
        return ultimoNumeroPedido;
    }

    /*
     * Añade un pedido a la lista de pedidos.
     */
    public void anadirPedido(Pedido pedido) {
        listaPedidos.add(pedido);
    }

    /*
     * Elimina un pedido de la lista
     */
    public void eliminarPedido(Pedido pedido) {
        listaPedidos.remove(pedido);
    }

    /*
     * Busca un pedido por número
     */
    public Pedido buscarPedido(int numeroPedido) {
        for (Pedido p : listaPedidos) {
            if (p.getNumeroPedido() == numeroPedido) return p;
        }
        return null;
    }

    /*
     * Devuelve la lista de pedidos pendientes (no enviados)
     */
    public List<Pedido> getPedidosPendientes() {
        return listaPedidos.stream()
                .filter(Pedido::puedeCancelar)
                .collect(Collectors.toList());
    }

    /*
     * Devuelve la lista de pedidos enviados
     */
    public List<Pedido> getPedidosEnviados() {
        return listaPedidos.stream()
                .filter(p -> !p.puedeCancelar())
                .collect(Collectors.toList());
    }

    // ==========================================
    //       GESTIÓN DE CLIENTES
    // ==========================================

    /**
     * Añadir con validación de clave única (email).
     */
    public boolean anadirCliente(Cliente cliente) {
        String email = cliente.getEmail();

        if (clientes.existe(email)) {
            return false; // El email ya existe, no se añade.
        }

        clientes.anadir(email, cliente);
        return true;
    }

    /**
     * Busca un cliente por su email.
     */
    public Cliente buscarCliente(String email) {
        return clientes.buscar(email);
    }

    /**
     * Devuelve la lista completa de clientes.
     */
    public ArrayList<Cliente> obtenerTodosClientes() {
        return clientes.obtenerTodos();
    }

    /**
     * Filtra y devuelve solo los clientes de tipo Estándar.
     */
    public ArrayList<Cliente> obtenerClientesEstandar() {
        ArrayList<Cliente> listaEstandar = new ArrayList<>();
        // 'datos' es accesible por ser protected en GenericoDAO
        for (Cliente c : clientes.obtenerTodos()) {
            if (c instanceof ClienteEstandar) {
                listaEstandar.add(c);
            }
        }
        return listaEstandar;
    }

    /**
     * Filtra y devuelve solo los clientes de tipo Premium.
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
     */
    public boolean eliminarCliente(String email) {
        if (clientes.existe(email)) {
            clientes.eliminar(email);
            return true;
        }
        return false;
    }
}
