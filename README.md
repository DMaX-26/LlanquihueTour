🧠 Evaluación final Transversal: Programación Orientada a Objetos 1

👤 Autor del proyecto
```text
Nombre completo: Maximiliano Alvear
Sección: DESARROLLO ORIENTADO A OBJETOS I_003A
Carrera: Analista Programador Computacional
Sede: Sede Online
```
---

📘 Descripción general del sistema

Este proyecto corresponde a la Evaluación final Transversal de la asignatura Desarrollo Orientado a Objetos I. Consiste en el desarrollo de un sistema para la agencia de turismo Llanquihue Tour, el cual permite cargar información desde un archivo txt, incluyendo datos de reservas, tours, proveedores de alojamiento y transporte, clientes y guías turísticos.

Este proyecto implementa las clases Direccion, Rut, Persona, GuiaTuristico, ProveedorTransporte, ProveedorAlojamiento, Tour, Reserva y una interfaz Registrable que define un método común que debe ser implementado por todas las clases que la utilizan.

Para administrar estos datos, se crea la clase GestorDatos, responsable de recopilar toda la información dentro del archivo txt y de crear instancias de cada clase que implementa la interfaz Registrable y almacenarlas en una colección para su posterior gestión.

El proyecto fue desarrollado a partir de un caso contextualizado, abordando problemáticas reales y proponiendo una solución estructurada, modular y reutilizable.

🧱 Estructura general del proyecto
```text
📁 src/
├── app/         # Clase principal con el método main
├── model/       # Clases de dominio (Direccion, Rut, Persona, GuiaTuristico, Cliente, ProveedorTransporte, ProveedorAlojamiento, Tour, Reserva, GestorDatos)
├── exception/   # Excepciones personalizadas (EdadInvalidaException, PrecioInvalidoException, RutInvalidoException, CantidadPersonasInvalidaException)
├── data/        # Gestión y acceso de datos (GestorDatos)
└── interfaces/  # Interfaces que definen contratos y comportamientos comunes implementados por las clases del sistema (Registrable)
```
---
⚙️ Instrucciones para compilar y ejecutar el proyecto
```text
Abre el proyecto en IntelliJ IDEA

Dirígete a File > Open - selecciona la carpeta del proyecto y presiona el botón "Select Folder".

Ejecuta el archivo Main.java desde el paquete app.

Repositorio GitHub: [https://github.com/DMaX-26/LlanquihueTour] Fecha de entrega: 19/07/2026
```
---