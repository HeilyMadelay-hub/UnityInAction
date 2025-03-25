# UnityInAction

## 📱 Aplicación de Gestión de Usuarios, Noticias y Donaciones

## 📝 Descripción del Proyecto

UnityInAction es una plataforma Android diseñada para conectar organizaciones solidarias con voluntarios, profesionales y donantes, facilitando la publicación de noticias y la gestión de donaciones. La aplicación permite a los usuarios registrarse según su rol, acceder a noticias relevantes y realizar donaciones seguras.

Este proyecto está planificado para ser desarrollado en 2 meses por una desarrolladora individual con primera experiencia en Firebase.

## 🎯 Objetivos Principales

- Crear una plataforma funcional donde diferentes tipos de usuarios puedan registrarse e iniciar sesión
- Implementar un sistema básico de publicación y visualización de noticias
- Desarrollar un sistema simple de donaciones
- Aplicar Firebase como solución backend (Authentication y Firestore)

## 🛠️ Funcionalidades Incluidas

### 👤 Gestión de Usuarios
- Sistema de registro con 5 tipos de usuarios: Organización, Voluntario, Profesional, Donante y Reportero Ciudadano
- Formularios específicos según el tipo de usuario
- Autenticación con email y contraseña mediante Firebase
- Perfiles básicos para cada usuario

### 📰 Sistema de Noticias
- Creación de noticias por usuarios autorizados (Organizaciones y Reporteros)
- Visualización de lista de noticias con imagen destacada, título y resumen
- Vista detallada de noticias seleccionadas
- Funcionalidad básica de búsqueda por palabras clave
- Edición y eliminación de noticias propias

### 💰 Sistema Básico de Donaciones
- Formulario simple para realizar donaciones a organizaciones
- Integración básica con PayPal SDK (modo sandbox)
- Registro de donaciones en Firestore
- Confirmación de donación realizada

### 🎨 Interfaz de Usuario
- Diseño intuitivo basado en Material Design
- Implementación de tema oscuro/claro
- Navegación fluida entre pantallas principales

## 📅 Cronograma de Desarrollo (2 Meses)

### Semanas 1-2: Configuración y Base del Proyecto
- Configuración del entorno de desarrollo Android
- Creación y configuración del proyecto en Firebase
- Diseño de las pantallas principales (wireframes)
- Implementación de la estructura básica de la aplicación

### Semanas 3-4: Autenticación y Gestión de Usuarios
- Implementación de Firebase Authentication
- Desarrollo de las pantallas de registro e inicio de sesión
- Creación de la estructura de datos en Firestore
- Implementación de perfiles de usuario básicos

### Semanas 5-6: Sistema de Noticias
- Modelado de datos para noticias en Firestore
- Desarrollo de interfaces para crear/editar noticias
- Implementación del listado de noticias con RecyclerView
- Desarrollo de la vista detallada de noticias
- Implementación de búsqueda básica

### Semanas 7-8: Sistema de Donaciones y Finalización
- Integración básica con PayPal SDK (sandbox)
- Desarrollo del flujo de donaciones
- Implementación del tema oscuro/claro
- Testing general y corrección de bugs
- Documentación final y preparación para entrega

## 📊 Estructura del Proyecto

### Activities Principales
- `MainActivity` - Pantalla principal con navegación
- `ActivityIniciar` - Inicio de sesión
- `ActivityRegistrarse` - Registro de usuarios
- `ActivityPerfilUsuario` - Visualización y edición de perfil
- `ActivityVernoticias` - Listado de noticias con búsqueda
- `ActivityDetalleNoticia` - Vista detallada de noticia seleccionada
- `ActivityCrearNoticia` - Creación de nuevas noticias
- `ActivityDonaciones` - Formulario de donaciones

### Clases Soporte
- `FirestoreHelper` - Gestión de conexiones con Firestore
- `NoticiasAdapter` - Adaptador para RecyclerView de noticias
- `Modelos` - Clases para datos (Usuario, Noticia, Donación)
- `Utils` - Clases de utilidad para validaciones y formateos

## 💻 Tecnologías a Utilizar

### Frontend
- Java para Android
- XML para layouts
- Material Design Components

### Backend
- Firebase Authentication para gestión de usuarios
- Firebase Firestore como base de datos NoSQL
- Firebase Storage (básico) para almacenamiento de imágenes

### Integración de Pagos
- PayPal SDK para Android (modo sandbox)

## 🔮 Funcionalidades para Futuras Versiones

Las siguientes funcionalidades quedan fuera del alcance actual del proyecto (2 meses) y se implementarán en versiones futuras:

- Autenticación con Google/Facebook
- Sistema de notificaciones push con Firebase Cloud Messaging
- Geolocalización y mapas
- Sistema avanzado de voluntariados
- Características de seguridad avanzadas
- Sincronización offline
- Soporte multi-idioma

## 📋 Requisitos de Desarrollo

- Android Studio
- JDK 8 o superior
- Dispositivo Android con API 21 (Lollipop) o superior
- Cuenta de Firebase
- Cuenta de desarrollador de PayPal (para modo sandbox)

## 📚 Recursos de Aprendizaje

### Firebase
- [Documentación oficial de Firebase para Android](https://firebase.google.com/docs/android/setup)
- [Codelab: Firebase para Android](https://firebase.google.com/codelabs/firebase-android)

### PayPal
- [Guía de integración de PayPal SDK](https://developer.paypal.com/docs/business/checkout/configure-payments/single-page-app/)


