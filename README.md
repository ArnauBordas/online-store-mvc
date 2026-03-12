# BugBusters - OnlineStore Producto 2

## **Descripción del Proyecto**

Este proyecto consiste en el desarrollo de una aplicación de escritorio basada en Java para la gestión integral de una tienda online. 
El sistema permite administrar el inventario de artículos, la base de datos de clientes (Estándar y Premium) y la lógica de pedidos, 
incluyendo cálculos de costos de envío bonificados y validaciones de tiempo de preparación.

Actualmente, la aplicación funciona mediante consola y almacena la información de forma interna en memoria, 
siguiendo un paradigma de Programación Orientada a Objetos (POO).

## **Arquitectura del Sistema**

Para este proyecto se ha implementado el patrón de diseño MVC (Modelo-Vista-Controlador), lo que permite una separación clara de responsabilidades:

* **Modelo (Model)**: Contiene la lógica de negocio y las entidades de datos (Artículos, Clientes, Pedidos).
* **Vista (View)**: Gestiona la interfaz de usuario por consola y la presentación de los datos.
* **Controlador (Controller)**: Actúa como intermediario, gestionando las peticiones del usuario y actualizando el modelo o la vista según corresponda.

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
- **Java Generics**: Clase `GenericoDAO` parametrizada para trabajar con cualquier tipo de objeto
- **Colecciones óptimas**: 
  - `LinkedHashMap` para artículos (mantiene orden de inserción)
  - `ArrayList` para pedidos (acceso secuencial)
### **2. Manejo de Excepciones**
El sistema implementa excepciones personalizadas para garantizar la integridad de los datos y una correcta experiencia de usuario:
- `YaExisteException`: Se lanza al intentar crear artículos o clientes con identificadores duplicados
- `RecursoNoEncontradoException`: Gestiona búsquedas de elementos inexistentes
- `EmailInvalidoException`: Validación del formato de correo electrónico
- `TipoClienteInvalidoException`: Control de tipos de cliente (1-Estándar, 2-Premium)
- `PedidoNoCancelableException`: Impide cancelar pedidos fuera del tiempo de preparación

### **3. Pruebas Unitarias con JUnit**
Se han implementado las pruebas unitarias requeridas para validar la lógica de negocio crítica:
- `testAnadirPedidoCorrecto()`: Verifica la creación correcta de pedidos
- `testPuedeCancelarFalse()`: Comprueba que un pedido no se puede cancelar si ya ha pasado el tiempo de preparación

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
