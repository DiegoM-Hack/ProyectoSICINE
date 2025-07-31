# ProyectoSICINE
Descripción
Este es un sistema completo de gestión para cines, diseñado en Java con Swing y MongoDB como base de datos. Permite gestionar películas, salas, funciones, usuarios y ventas de boletos con generación de códigos QR. Ofrece una interfaz diferenciada para administradores y cajeros.

🚀 Funcionalidades
Login con roles diferenciados y control de acceso.

Administración de películas, funciones y salas.

Venta de entradas con generación automática de boletos con código QR.

Consulta de historial de ventas.

Estadísticas y reportes adaptados al rol del usuario.

Validación de formularios y manejo de errores.

Interfaz visual estilizada con estructura modular.

🖼️ Capturas de pantalla (opcional)
(Agrega aquí imágenes del login, panel de administración, boletos, etc.)

🧩 Estructura del Proyecto
bash
Copiar
Editar
/src
├── Modelos/
├── Servicios/
├── Formularios/
├── Utilidades/
└── DataBase/
Modelos/: Clases como Pelicula, Sala, Venta, Usuario, etc.

Servicios/: Lógica para interactuar con MongoDB.

Formularios/: Interfaz gráfica para usuario final (Swing).

Utilidades/: Estilos visuales y utilidades QR.

DataBase/: Conexión con MongoDB.

🛠️ Tecnologías usadas
Java 17+

MongoDB

Swing

Maven/Gradle (si lo usas)

ZXing (para generación de QR)

⚙️ Instalación y ejecución
bash
Copiar
Editar
git clone https://github.com/tu-usuario/policine.git
cd policine
# Abre el proyecto en tu IDE Java (IntelliJ, Eclipse, NetBeans)
# Asegúrate de tener MongoDB ejecutándose
Importante: Crea una base de datos CineDB en MongoDB con las siguientes colecciones:

Peliculas

Funciones

Salas

Usuarios

Ventas

Boletos
