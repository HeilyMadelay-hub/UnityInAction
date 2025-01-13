# UnityInAction

**Aplicación de Gestión de Usuarios, Noticias y Donaciones**

---

## 📖 Descripción del Proyecto
UnityInAction es una plataforma diseñada para facilitar la gestión de usuarios, publicación de noticias y donaciones relacionadas con iniciativas solidarias, voluntariados, organizaciones profesionales o reporterismo ciudadano.

El objetivo principal es proporcionar un espacio donde diferentes tipos de usuarios (Organizaciones, Voluntarios, Profesionales, Donantes y Reporteros Ciudadanos) puedan:
- Registrarse e iniciar sesión.
- Publicar y visualizar noticias de interés.
- Gestionar donaciones para apoyar a Organizaciones.

---

## ✨ Características Principales
1. **Gestión de Usuarios**
   - Registro de diferentes tipos de usuarios: Organización, Reportero Ciudadano, Voluntario, Profesional y Donante.
   - Campos específicos para cada tipo de usuario (ej. profesiones, certificados, disponibilidad, datos de pago, etc.).
   - Validación de datos como correo electrónico, nombre y teléfono.

2. **Inicio de Sesión**
   - Autenticación con correo electrónico y contraseña.
   - Gestión de sesión activa mediante `SharedPreferences`.

3. **Gestión de Noticias**
   - Subida de noticias por usuarios autorizados (Organizaciones, Reporteros).
   - Información detallada: título, contenido, tags, ubicación, requerimientos, etc.
   - Edición y eliminación de noticias propias.
   - Visualización completa de una noticia al seleccionarla mediante `ActivityDetalleNoticia`.
   - Búsqueda integrada para filtrar noticias por palabras clave.

4. **Donaciones**
   - Donaciones por parte de usuarios a Organizaciones.
   - Validación de datos de pago: tarjeta de crédito, CVV, fecha de caducidad.

5. **Visualización General de Noticias**
   - Noticias públicas visibles para todos los usuarios, incluso no autenticados.

6. **Notificaciones Push** 
   - Integración con Firebase Cloud Messaging para enviar notificaciones cuando:
     - Se publica una nueva noticia.
     - Se realiza una donación.
     - Se actualiza una noticia importante.

7. **Subida de Imágenes**
   - Posibilidad de agregar imágenes o archivos multimedia en las noticias.

8. **Autenticación Mejorada** 
   - Contraseñas encriptadas para mayor seguridad.
   - Posibilidad de autenticación con Google o Facebook.

9. **Geolocalización y Mapas** 
   - Filtro de noticias basadas en la ubicación actual del usuario.
   - Visualización de la ubicación de las noticias en un mapa interactivo (Google Maps API).

10. **Tema Oscuro**
    - Modo oscuro para mejorar la experiencia del usuario.

---

## 📂 Estructura del Proyecto
- **Activities Principales:**
  - `ActivityIniciar` - Pantalla de inicio de sesión.
  - `ActivityRegistrarse` - Registro dinámico según tipo de usuario.
  - `ActivityPerfilUsuario` - Gestión de perfil y noticias propias.
  - `ActivityVernoticias` - Visualización general de noticias con buscador.
  - `ActivityDetalleNoticia` - Pantalla para visualizar la información completa de una noticia seleccionada.
  - `ActivityDonaciones` - Gestión de donaciones.
  - `EditarNoticiaActivity` - Edición de noticias existentes.
  - `MainActivity` - Pantalla principal con accesos rápidos.

- **Clases Soporte:**
  - `DatabaseHelper` - Gestión de base de datos SQLite.
  - `NoticiasAdapter` - Adaptador para la lista de noticias.
  - `Noticia` - Modelo de datos para las noticias.

---

## 📋 Requisitos Previos
- **Software:**
  - Android Studio o IDE compatible con Android.
  - Gradle instalado (incluido en Android Studio).
- **Dispositivos:**
  - Android 5.0 (Lollipop) o superior (físico o emulador).
- **Opcionales:**
  - Conexión a Internet para funcionalidades futuras.

---

## 🚀 Instalación y Ejecución

### Instalación
1. Clona este repositorio o copia los archivos del proyecto a tu máquina local.
2. Abre Android Studio.
3. Selecciona **"Open an existing project"**.
4. Navega hasta la carpeta del proyecto y selecciónala.
5. Espera a que Gradle sincronice el proyecto.

### Ejecución
1. Conecta tu dispositivo Android o inicia un emulador.
2. Haz clic en el botón **"Run"** (ícono ▶).
3. Selecciona el dispositivo o emulador deseado.
4. Una vez compilado, la aplicación se iniciará automáticamente.

---

## 🛠️ Problemas Conocidos
- Diseño de base de datos con limitaciones de integridad.
- Lógica de negocio acoplada a la vista.
- Validaciones rígidas y faltas de control de errores.
- Incoherencia entre clases y tablas de la base de datos.

---

## 🌟 Mejoras Futuras

1. Incorporar soporte para múltiples idiomas (español e inglés).

---

## 📌 Licencia
Este proyecto no cuenta con una licencia específica. Se presenta como código abierto para su revisión y mejora.

---

