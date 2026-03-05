package bugbusters.vista;

import bugbusters.controlador.Controlador;
import bugbusters.modelo.Articulo;

import java.util.List;
import java.util.Scanner;

/*
 * Clase Vista
 *
 * Se encarga de toda la interacción con el usuario por consola:
 * - mostrar menús
 * - pedir datos
 * - mostrar resultados
 *
 * En MVC:
 * - La vista NO debe acceder al modelo directamente.
 * - La vista solo usa el Controlador.
 *
 * =========================================================
 * BLOQUE IMPLEMENTADO POR: Erick Coll Rodríguez
 * PARTE DESARROLLADA: Menú de artículos
 * =========================================================
 */
public class Vista {

    private Scanner teclado;
    private Controlador controlador;

    /*
     * Constructor
     * Inicializa el Scanner y el Controlador.
     */
    public Vista() {
        teclado = new Scanner(System.in);
        controlador = new Controlador();
    }

    /*
     * iniciar()
     * Muestra el menú principal en bucle hasta que el usuario salga.
     */
    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    menuArticulos(); // ✅ TU PARTE
                    break;

                case 2:
                    System.out.println("Gestión de clientes (pendiente de implementar).");
                    break;

                case 3:
                    System.out.println("Gestión de pedidos (pendiente de implementar).");
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /* =========================================================
       =================== MENÚ PRINCIPAL ======================
       ========================================================= */

    private void mostrarMenuPrincipal() {
        System.out.println("\n====================================");
        System.out.println("         MENÚ PRINCIPAL");
        System.out.println("====================================");
        System.out.println("1. Gestión de artículos");
        System.out.println("2. Gestión de clientes");
        System.out.println("3. Gestión de pedidos");
        System.out.println("0. Salir");
        System.out.println("====================================");
    }

    /* =========================================================
       ================= MENÚ DE ARTÍCULOS =====================
       ========== IMPLEMENTADO POR ERICK COLL RODRÍGUEZ ========
       ========================================================= */

    private void menuArticulos() {
        int opcion;

        do {
            System.out.println("\n--- GESTIÓN DE ARTÍCULOS ---");
            System.out.println("1. Añadir artículo");
            System.out.println("2. Mostrar artículos");
            System.out.println("0. Volver");
            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    anadirArticulo();
                    break;
                case 2:
                    mostrarArticulos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /*
     * anadirArticulo()
     * Pide datos por teclado, crea el artículo desde el controlador y lo guarda.
     */
    private void anadirArticulo() {
        System.out.println("\nAñadir artículo");

        String codigo = leerTexto("Código: ");
        String descripcion = leerTexto("Descripción: ");
        double precioVenta = leerDouble("Precio de venta: ");
        double gastosEnvio = leerDouble("Gastos de envío: ");
        int tiempoPreparacionMin = leerEntero("Tiempo de preparación (minutos): ");

        // Opción simple: permitir sobrescribir si ya existe (como está en Datos)
        // Si luego queréis evitar repetidos, se mejora con validación/excepciones.
        controlador.anadirArticulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacionMin);

        System.out.println("Artículo añadido correctamente.");
    }

    /*
     * mostrarArticulos()
     * Obtiene la lista desde el controlador y la imprime.
     */
    private void mostrarArticulos() {
        System.out.println("\nListado de artículos:");

        List<Articulo> articulos = controlador.obtenerTodosArticulos();

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
        } else {
            for (Articulo a : articulos) {
                System.out.println(a);
            }
        }
    }

    /* =========================================================
       ================== MÉTODOS AUXILIARES ===================
       ========================================================= */

    /*
     * leerTexto()
     * Lee una línea completa de texto.
     */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine();
    }

    /*
     * leerEntero()
     * Lee un entero. (Versión simple)
     * Si el usuario escribe algo que no es número, puede fallar.
     * Más adelante se puede mejorar con validación.
     */
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(teclado.nextLine());
    }

    /*
     * leerDouble()
     * Lee un decimal. (Versión simple)
     * Más adelante se puede mejorar con validación.
     */
    private double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return Double.parseDouble(teclado.nextLine());
    }
}