package bugbusters.modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
 * PARTE DESARROLLADA: Gestión de artículos
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
 */
public class Datos {

    /* =========================================================
       ================== COLECCIÓN DE ARTÍCULOS ===============
       ========================================================= */

    /*
     * BLOQUE DE ERICK COLL RODRÍGUEZ
     *
     * Mapa donde se guardarán todos los artículos.
     *
     * La clave será el código del artículo en minúsculas,
     * para evitar problemas si un usuario introduce el código
     * con mayúsculas o minúsculas diferentes.
     */
    private Map<String, Articulo> articulos;

    /*
     * Constructor
     *
     * Inicializa la colección de artículos vacía.
     *
     * Más adelante, cuando otros compañeros añadan su parte,
     * aquí también se inicializarán las colecciones de clientes
     * y pedidos.
     */
    public Datos() {
        articulos = new LinkedHashMap<>();
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
}