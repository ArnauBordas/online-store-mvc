package bugbusters.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase genérica para la gestión de colecciones de datos en el sistema.
 *
 * Esta clase proporciona una implementación base para operaciones CRUD
 * (Crear, Leer, Actualizar, Eliminar) sobre cualquier tipo de objeto
 * almacenado en el sistema. Utiliza un {@link HashMap} como estructura
 * subyacente para garantizar un acceso rápido por clave.
 *
 * @param <K> Tipo de la clave identificadora (ej. String para email, Integer para ID)
 * @param <T> Tipo del objeto a almacenar (ej. Cliente, Artículo, Pedido)
 *
 * @author BugBusters
 * @version 1.0
 * @since 1.0
 */
public class GenericoDAO<K, T> {

    /**
     * Mapa que almacena los objetos del sistema.
     *
     * La clave es el identificador único del objeto y el valor es el objeto en sí.
     * Se utiliza {@link HashMap} por su rendimiento en operaciones de búsqueda.
     * El modificador {@code protected} permite que las clases hijas accedan
     * directamente a los datos si necesitan realizar filtrados específicos.
     */
    protected Map<K, T> datos;

    /**
     * Constructor que inicializa el DAO genérico.
     * Crea un nuevo {@link HashMap} vacío para almacenar los objetos.
     */
    public GenericoDAO() {
        this.datos = new HashMap<>();
    }

    /**
     * Añade un objeto al almacén con su clave correspondiente.
     *
     * @param llave  Clave única identificadora del objeto
     * @param objeto Objeto a almacenar
     */
    public void anadir(K llave, T objeto) {
        datos.put(llave, objeto);
    }

    /**
     * Busca y devuelve un objeto por su clave.
     *
     * @param llave Clave del objeto a buscar
     * @return El objeto si existe, {@code null} si no se encuentra
     */
    public T buscar(K llave) {
        return datos.get(llave);
    }

    /**
     * Comprueba si existe un objeto con la clave especificada.
     *
     * @param llave Clave a comprobar
     * @return {@code true} si existe un objeto con esa clave, {@code false} en caso contrario
     */
    public boolean existe(K llave) {
        return datos.containsKey(llave);
    }

    /**
     * Obtiene una lista con todos los objetos almacenados.
     *
     * @return {@link ArrayList} con todos los objetos del almacén
     */
    public ArrayList<T> obtenerTodos() {
        return new ArrayList<>(datos.values());
    }

    /**
     * Elimina un objeto del almacén por su clave.
     *
     * @param llave Clave del objeto a eliminar
     */
    public void eliminar(K llave) {
        datos.remove(llave);
    }
}
