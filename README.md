# Gestión de Empleados - Aplicación CRUD en Java con JDBC y Arquitectura por Capas

Este proyecto es una aplicación de escritorio simple desarrollada en Java para la gestión básica de empleados, demostrando la implementación de operaciones CRUD (Crear, Leer, Actualizar, Eliminar) utilizando JDBC (Java Database Connectivity) y una arquitectura por capas robusta.

##  Características y Funcionalidades

La aplicación permite a los usuarios interactuar con una base de datos MySQL para realizar las siguientes operaciones sobre la entidad `Empleado`:

* **Crear (Agregar) Empleado:** Registrar nuevos empleados con sus datos (nombre, apellido, puesto, salario).
* **Leer (Ver) Empleados:**
    * Visualizar una lista de todos los empleados registrados.
    * Buscar y ver los detalles de un empleado específico por su ID.
* **Actualizar Empleado:** Modificar la información de un empleado existente (nombre, apellido, puesto, salario) basado en su ID.
* **Eliminar Empleado:** Eliminar un registro de empleado de la base de datos utilizando su ID.

##  Aspectos Técnicos Destacados



* **Arquitectura por Capas:** Implementa una arquitectura clara con capas distintas para:
    * **`modelo/`**: Entidades de datos (clase `Empleado`).
    * **`dao/`**: Capa de Acceso a Datos (DAO - `Data Access Object`) con `EmpleadoDAO` para encapsular toda la lógica de persistencia y operaciones CRUD con la base de datos.
    * **`excepciones/`**: Manejo de excepciones personalizadas (`EmpleadoException`) para una gestión de errores más granular y significativa.
    * **`gui/`**: Interfaz de usuario simple basada en `JOptionPane` para la interacción.
    * **`app/`**: Clase principal (`MainApp`) para la orquestación del inicio de la aplicación.
* **JDBC Nativo:** Utilización directa de JDBC para interactuar con la base de datos, proporcionando una comprensión profunda de cómo Java se conecta y manipula datos en SQL.
* **Manejo Robusto de Conexiones:** La clase `ConexionDB` gestiona de forma centralizada la configuración y el ciclo de vida de las conexiones a la base de datos (apertura y cierre de recursos), incluyendo la lectura de credenciales desde un archivo `application.properties`.
* **Preparación de Sentencias (PreparedStatement):** Uso de `PreparedStatement` para todas las operaciones SQL, garantizando la **seguridad contra ataques de inyección SQL** y mejorando el rendimiento.
* **Manejo de Excepciones Controlado:** Implementación de bloques `try-catch-finally` y `try-with-resources` para asegurar el cierre adecuado de recursos JDBC y una gestión de errores amigable, evitando caídas inesperadas de la aplicación.
* **Validación Básica de Entrada:** Implementación de validaciones en la capa de interfaz de usuario para asegurar que los datos ingresados por el usuario sean válidos antes de procesarlos.
* **Gestión de Dependencias con Maven:** El proyecto está configurado con Maven, facilitando la gestión de librerías (como el conector MySQL JDBC) y el empaquetado del proyecto.

## 🛠️ Tecnologías Utilizadas

* **Java 8+**
* **MySQL Server**
* **JDBC**
* **Maven**
* **Swing (JOptionPane)**

