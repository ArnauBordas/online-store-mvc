# BugBusters - OnlineStore Producto 2

## **Descripción del Proyecto**

Este proyecto consiste en el desarrollo de una aplicación de escritorio basada en Java para la gestión integral de una tienda online. 
El sistema permite administrar el inventario de artículos, la base de datos de clientes (Estándar y Premium) y la lógica de pedidos, 
incluyendo cálculos de costos de envío bonificados y validaciones de tiempo de preparación.

Actualmente, la aplicación funciona mediante consola y almacena la información de forma interna en memoria, 
siguiendo un paradigma de Programación Orientada a Objetos (POO).

## **Arquitectura del Sistema**

Para este proyecto se ha implementado el patrón de diseño MVC (Modelo-Vista-Controlador), lo que permite una separación clara de responsabilidades:

* **Modelo (Model)**: Contiene la lógica de negocio y las entidades de datos. Gestiona el almacenamiento y la recuperación de la información.
* **Vista (View)**: Gestiona la interfaz de usuario por consola. Se encarga de mostrar los menús, solicitar datos al usuario y presentar los resultados.
* **Controlador (Controller)**: Actúa como intermediario entre la Vista y el Modelo. Procesa las solicitudes de la vista, interactúa con el modelo y devuelve los resultados.

## **Requisitos Funcionales**
### 1. Gestión de Artículos  
* Registro de nuevos artículos (Código alfanumérico, descripción, precio, gastos de envío, tiempo de preparación).
* Visualización del catálogo completo.

### 2. Gestión de Clientes   
 * Registro de clientes con email como identificador único.
 * Tipos de clientes:
    * **Estándar:** Sin cuota anual.
    * **Premium:** Cuota anual de 30 euros y 20% de descuento en gastos de envío.
 * Filtros de visualización por tipo de cliente.

### 3. Gestión de Pedidos
* **Creación**: Cada pedido contiene un artículo (y sus unidades). Si el cliente no existe, se registra automáticamente.
* **Eliminación**: Solo se permite si el tiempo transcurrido no supera el tiempo de preparación del artículo.
* **Visualización**: Opción de filtrar pedidos pendientes o enviados por cliente.

## **Características Implementadas**

### **1. Estructuras de Datos**
- **Flujo de inicialización**:  Las colecciones se crean en el constructor de Datos cuando se instancia el Controlador desde la Vista, quedando listas antes de mostrar el menú principal.
- **Java Generics**: `GenericoDAO<K, T>` parametrizada que centraliza la gestión de colecciones mediante `HashMap`, permitiendo reutilizar la misma lógica para clientes, artículos y pedidos.
- **Colecciones**:
  - `GenericoDAO<String, Cliente>` para clientes (clave = email)
  - `GenericoDAO<String, Articulo>` para artículos (clave = código)
  - `GenericoDAO<Integer, Pedido>` para pedidos (clave = número de pedido)
- **Selección de colecciones**:
  - `HashMap` como estructura principal. Se eligió por su rapidez en operaciones de búsqueda, inserción y eliminación (complejidad O(1)), ideal para acceder a clientes por email, artículos por código y pedidos por número. Además, garantiza claves únicas y no requiere ordenación.
  - `ArrayList` como contenedor temporal: Se utiliza únicamente en métodos como obtenerTodos() y en los filtros de pedidos (getPedidosPendientes(), getPedidosEnviados()) para devolver resultados de forma cómoda a la vista, sin alterar la estructura principal de almacenamiento.
- **Ventajas**: Evita duplicación de código, mejora la mantenibilidad y escalabilidad del proyecto.

### **2. Gestión de Excepciones**
- **Arquitectura MVC**: El Controlador lanza excepciones mediante throw al detectar violaciones de reglas de negocio, la Vista las captura con try-catch y muestra mensajes al usuario, y el Modelo delega la validación en el Controlador.
- **Excepciones personalizadas**: El sistema implementa excepciones personalizadas para garantizar la integridad de los datos y una correcta experiencia de usuario:
  - `YaExisteException`: Se lanza al intentar crear artículos o clientes con identificadores duplicados
  - `RecursoNoEncontradoException`: Gestiona búsquedas de elementos inexistentes
  - `EmailInvalidoException`: Validación del formato de correo electrónico
  - `TipoClienteInvalidoException`: Control de tipos de cliente (1-Estándar, 2-Premium)
  - `PedidoNoCancelableException`: Impide cancelar pedidos fuera del tiempo de preparación
- **Caso especial**: Campos obligatorios se validan con `leerTextoNoVacio()` (bucle hasta valor no vacío) y campos opcionales con `leerTextoOpcional()`, evitando excepciones innecesarias.

### **3. Pruebas Unitarias con JUnit**
Se han implementado las pruebas unitarias requeridas para verificar el correcto funcionamiento del sistema y el cumplimiento de las reglas de negocio mediante pruebas automatizadas:
- `testAnadirPedidoCorrecto()`: Comprueba que se puede crear un pedido con datos válidos. Valida que el pedido se registre correctamente, se le asigne un número único y el cálculo del total sea preciso (precio × cantidad + gastos de envío).
- `testPuedeCancelarFalse()`: Verifica que no se puede cancelar un pedido cuando ha superado el tiempo de preparación. El resultado esperado es que `puedeCancelar()` devuelva `false`, confirmando que el pedido se considera enviado.

## **Reglas de Git y Flujo de Trabajo**

Para mantener la integridad del código, seguimos estrictamente estas pautas:

**IMPORTANTE:** Nunca editar directamente la rama MAIN.

Crear una nueva rama desde Main

    - Asegurar local actualizado: git checkout main -> git pull origin main
    - Crear y cambiar a rama: git checkout -b nombreNuevaRama
    - Subir rama al remoto: git push -u origin nombreNuevaRama

Trabajo en la rama propia

    - git add .
    - git commit -m "Descripción del cambio"
    - git push

Actualizar rama con cambios de Main (Rebase)

Si alguien ha mergeado en main mientras trabajas:

    - git fetch origin
    - git rebase origin main

Antes de crear una Pull Request (PR)
Asegurar que todos los cambios de main están aplicados:

    - git checkout NombreRama
    - git fetch origin
    - git rebase origin/main
    - git push --force-with-lease

## **Tecnologías Utilizadas**

* **Lenguaje**: Java
* **Entorno**: IntelliJ IDEA
* **Control de Versiones**: Git y GitHub


## **Equipo de Desarrollo**

### Grupo BugBusters

- Erick Coll Rodriguez
- Ferran Arnaus Garcia
- Arnau Bordas Fornieles
- Jennifer Hernandez Marin
