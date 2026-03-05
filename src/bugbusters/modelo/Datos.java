package bugbusters.modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Clase Datos
 *
 * Esta clase será la encargada de almacenar los datos principales
 * de la aplicación.
 *
 * En este trabajo grupal, esta clase irá creciendo con las aportaciones
 * de los distintos miembros del equipo.
 *
 * =========================================================
 * BLOQUE IMPLEMENTADO POR: Erick Coll Rodríguez
 * PARTE DESARROLLADA: Gestión de artículos y pedidos
 * =========================================================
 *
 * Colecciones implementadas:
 * - artículos
 * - pedidos
 */
public class Datos {

    /* =========================================================
       ================== COLECCIÓN DE ARTÍCULOS ===============
       ========================================================= */

    /*
     * Mapa donde se guardarán todos los artículos.
     *
     * La clave será el código del artículo en minúsculas,
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
     * Inicializa las colecciones vacías.
     */
    public Datos() {
        articulos = new LinkedHashMap<>();
        listaPedidos = new ArrayList<>();
        ultimoNumeroPedido = 0;
    }

    /* =========================================================
       ================= GESTIÓN DE ARTÍCULOS ==================
       ========================================================= */

    public void anadirArticulo(Articulo articulo) {
        articulos.put(articulo.getCodigo().toLowerCase(), articulo);
    }

    public Articulo buscarArticulo(String codigo) {
        return articulos.get(codigo.toLowerCase());
    }

    public boolean existeArticulo(String codigo) {
        return articulos.containsKey(codigo.toLowerCase());
    }

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
}