# UnityInAction - Aplicación de Gestión de Usuarios, Noticias y Donaciones

## 📚 Descripción del Proyecto
UnityInAction es una plataforma diseñada para facilitar la gestión de usuarios, publicación de noticias y donaciones relacionadas con iniciativas solidarias, voluntariados, organizaciones profesionales o reporterismo ciudadano.

El objetivo principal es proporcionar un espacio donde diferentes tipos de usuarios (Organizaciones, Voluntarios, Profesionales, Donantes y Reporteros Ciudadanos) puedan:

- Registrarse e iniciar sesión.
- Publicar y visualizar noticias de interés.
- Gestionar donaciones para apoyar a Organizaciones.
- Ofrecerse para realizar voluntariados con las Organizaciones.

## 🌐 Conectividad
La aplicación se conecta a servidores remotos de Firebase para:
- Almacenamiento de datos en tiempo real
- Autenticación de usuarios
- Gestión de archivos
- Envío de notificaciones push
- Procesamiento de donaciones

## ✨ Características Principales

### Gestión de Usuarios
- Registro de diferentes tipos de usuarios: Organización, Reportero Ciudadano, Voluntario, Profesional y Donante.
- Campos específicos para cada tipo de usuario (ej. profesiones, certificados, disponibilidad, datos de pago, etc.).
- Validación de datos como correo electrónico, nombre y teléfono.

### Inicio de Sesión
- Autenticación con correo electrónico y contraseña mediante Firebase Auth.
- Gestión de sesión activa mediante SharedPreferences.

### Gestión de Noticias
- Subida de noticias por usuarios autorizados (Organizaciones, Reporteros).
- Información detallada: título, contenido, tags, ubicación, requerimientos, etc.
- Edición y eliminación de noticias propias.
- Visualización completa de una noticia al seleccionarla mediante ActivityDetalleNoticia.
- Búsqueda integrada para filtrar noticias por palabras clave.

### Donaciones
- Los usuarios pueden realizar donaciones a Organizaciones dentro de la aplicación.
- Se soporta el pago mediante PayPal, lo que permite realizar transacciones seguras y rápidas.
- El proceso de pago se realiza a través de la API de PayPal, garantizando la seguridad de la información financiera de los usuarios.
- Se validan los datos de la transacción antes de confirmar la donación.
- Confirmación instantánea de la donación con notificaciones en tiempo real.

### Notificaciones Push
Integración con Firebase Cloud Messaging para enviar notificaciones cuando:
- Se publica una nueva noticia.
- Se realiza una donación.
- Se actualiza una noticia importante.

### Seguridad y Autenticación Mejorada
- Contraseñas encriptadas para mayor seguridad.
- Autenticación con Google o Facebook.

### Geolocalización y Mapas
- Filtro de noticias basadas en la ubicación actual del usuario.
- Visualización de la ubicación de las noticias en un mapa interactivo (Google Maps API).

### Tema Oscuro
- Modo oscuro para mejorar la experiencia del usuario.

## 🗂 Estructura del Proyecto

### Activities Principales
- ActivityIniciar - Pantalla de inicio de sesión.
- ActivityRegistrarse - Registro dinámico según tipo de usuario.
- ActivityPerfilUsuario - Gestión de perfil y noticias propias.
- ActivityVernoticias - Visualización general de noticias con buscador.
- ActivityDetalleNoticia - Pantalla para visualizar la información completa de una noticia seleccionada.
- ActivityDonaciones - Gestión de donaciones.
- EditarNoticiaActivity - Edición de noticias existentes.
- MainActivity - Pantalla principal con accesos rápidos.

### Clases Soporte
- FirestoreHelper - Gestión de conexiones con Firebase Firestore.
- NoticiasAdapter - Adaptador para la lista de noticias.
- Noticia - Modelo de datos para las noticias.

## 📊 Arquitectura del Proyecto
La aplicación sigue una arquitectura cliente-servidor con los siguientes componentes:

### Backend (Servidor Remoto)
- Firebase Firestore: Base de datos NoSQL en la nube para almacenar usuarios, noticias y donaciones.
- Firebase Authentication: Gestión de autenticación de usuarios.
- Firebase Cloud Functions: Lógica de backend para validar donaciones y enviar notificaciones.

### Frontend (Android)
- Activities y Fragments: Interfaz de usuario en Java/XML.
- SDK de Firebase: Comunicación con el backend mediante Firestore y Auth.

## 🔧 Requisitos Previos

### Software
- Android Studio o IDE compatible con Android.
- Gradle instalado (incluido en Android Studio).
- Cuenta de Firebase y archivo de configuración google-services.json.

### Dispositivos
- Android 5.0 (Lollipop) o superior (físico o emulador).
- Conexión a Internet para acceder a los servicios de Firebase.

## 🚀 Instalación y Ejecución

### Instalación
1. Clona este repositorio o copia los archivos del proyecto a tu máquina local.
2. Abre Android Studio.
3. Selecciona "Open an existing project".
4. Navega hasta la carpeta del proyecto y selecciónala.
5. Espera a que Gradle sincronice el proyecto.
6. Configura Firebase:
   - Añade el archivo google-services.json al proyecto
   - Verifica las dependencias de Firebase en build.gradle

### Ejecución
1. Conecta tu dispositivo Android o inicia un emulador.
2. Haz clic en el botón "Run" (icono ▶).
3. Selecciona el dispositivo o emulador deseado.
4. Una vez compilado, la aplicación se iniciará automáticamente.

## 🔧 Pruebas
Se han implementado pruebas para garantizar el correcto funcionamiento de la aplicación:
- Pruebas unitarias: Usando JUnit y Mockito para validar la lógica de negocio.
- Pruebas de interfaz: Usando Espresso para verificar la navegación y funcionalidad de la UI.

## 🛠️ Seguridad y Mejoras
- Cifrado de contraseñas: Implementado para evitar almacenamiento en texto plano.
- Reglas de seguridad en Firestore: Control de acceso granular a los datos.
- Autenticación multifactor disponible.

## 💡 Mejoras Futuras
- Incorporar soporte para múltiples idiomas (español e inglés).
- Optimizar la UI con Jetpack Compose.
- Implementar sincronización offline para mejorar la experiencia sin conexión.

## 💻 Tecnologías Utilizadas

### Frontend
- Java
- XML (layouts y Material Design)

### Backend y Base de Datos (Servidor Remoto)
Firebase:
- Firestore: Base de datos NoSQL en la nube
- Authentication: Gestión de usuarios
- Cloud Functions: Lógica de servidor
- Storage: Almacenamiento de archivos
- FCM: Notificaciones push
Pasarela de Pago:
-PayPal API: Procesamiento seguro de donaciones
### Herramientas de Desarrollo
- Android Studio
- Gradle
- GitHub
- Espresso, JUnit y Mockito para pruebas

## 👉 Licencia
Este proyecto no cuenta con una licencia específica. Se presenta como código abierto para su revisión y mejora.


