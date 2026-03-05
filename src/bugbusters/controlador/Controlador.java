package bugbusters.controlador;

import bugbusters.modelo.Articulo;
import bugbusters.modelo.Datos;

import java.util.List;

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
}