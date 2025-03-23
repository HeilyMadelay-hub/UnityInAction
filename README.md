# UnityInAction

## 📱 Aplicación de Gestión de Usuarios, Noticias y Donaciones

## 📝 Descripción del Proyecto

UnityInAction es una plataforma Android diseñada para conectar organizaciones solidarias con voluntarios, profesionales y donantes, facilitando la publicación de noticias y gestión de donaciones.

Este README presenta un plan realista para desarrollar la aplicación **en 2.5 meses por una única desarrolladora con primera experiencia en Firebase**.

## 🎯 Objetivos Principales

- Crear una plataforma funcional donde los usuarios puedan conectarse
- Implementar un sistema de publicación y visualización de noticias
- Desarrollar un sistema básico de donaciones
- Aprender e implementar Firebase como solución backend

## 📅 Cronograma de Desarrollo (2.5 Meses)

### 🚀 Fase 1: Base del Proyecto (Semanas 1-2)
- [  ] Configuración del entorno de desarrollo Android
- [  ] Creación y configuración del proyecto en Firebase
- [  ] Diseño de las pantallas principales (mockups/wireframes)
- [  ] Implementación de Firebase Authentication básico
- [  ] Desarrollo de interfaces de registro e inicio de sesión

### 👤 Fase 2: Gestión de Usuarios (Semanas 3-5)
- [  ] Modelado de datos de usuarios en Firestore
- [  ] Implementación de perfiles para los 5 tipos de usuarios
- [  ] Desarrollo de la clase FirestoreHelper 
- [  ] Interfaz de usuario para visualización/edición de perfiles
- [  ] Testing de las funcionalidades de autenticación

### 📰 Fase 3: Sistema de Noticias (Semanas 6-8)
- [  ] Diseño del modelo de datos para noticias
- [  ] Desarrollo de la interfaz para crear noticias
- [  ] Implementación del listado de noticias (RecyclerView + NoticiasAdapter)
- [  ] Desarrollo de la vista detallada de noticias
- [  ] Implementación de funcionalidad de búsqueda básica
- [  ] Testing de las funcionalidades CRUD de noticias

### 💰 Fase 4: Sistema de Donaciones (Semanas 9-10)
- [  ] Integración básica con PayPal SDK (ambiente sandbox)
- [  ] Implementación de la interfaz de donaciones
- [  ] Desarrollo del flujo de confirmación de donaciones
- [  ] Almacenamiento del registro de donaciones en Firestore
- [  ] Testing del proceso de donación

### 🔍 Fase 5: Pulido y Finalización (Semana 11)
- [  ] Revisión general y corrección de bugs
- [  ] Implementación del tema oscuro
- [  ] Optimización de rendimiento
- [  ] Testing final de todas las funcionalidades
- [  ] Finalización de la documentación

## 🛠️ Funcionalidades Incluidas vs. Pospuestas

### ✅ Incluidas en la Primera Versión
- Autenticación básica con email/contraseña
- Perfiles de usuario para los 5 tipos de usuarios
- CRUD completo de noticias
- Búsqueda simple de noticias por palabras clave
- Sistema básico de donaciones con PayPal (sandbox)
- Tema oscuro/claro

### ⏳ Pospuestas para Versiones Futuras
- Autenticación con Google/Facebook
- Notificaciones push (FCM)
- Geolocalización y mapas
- Sistema de voluntariados
- MVP de seguridad con IA
- Sincronización offline
- Soporte multi-idioma

## 💻 Estructura del Proyecto

### Activities Principales
- MainActivity - Pantalla principal y navegación
- ActivityIniciar - Inicio de sesión
- ActivityRegistrarse - Registro con campos dinámicos
- ActivityPerfilUsuario - Gestión de perfil
- ActivityVernoticias - Listado de noticias
- ActivityDetalleNoticia - Vista detallada de noticia
- ActivityDonaciones - Gestión de donaciones

### Componentes Clave
- FirestoreHelper.java - Gestión de conexiones a Firestore
- NoticiasAdapter.java - Adaptador para RecyclerView de noticias
- Models/ - Clases de modelos de datos (Usuario, Noticia, Donación)
- Utils/ - Clases de utilidad (validación, formateo, etc.)

## 🔧 Tecnologías a Utilizar

### Frontend
- Java para Android
- XML para layouts
- Material Design Components

### Backend (Firebase)
- Firestore - Base de datos NoSQL
- Authentication - Gestión de usuarios
- Storage - Almacenamiento de imágenes (básico)

### Integración de Pago
- PayPal SDK para Android (modo sandbox)

## 📚 Recursos de Aprendizaje

### Firebase
- [Documentación oficial de Firebase para Android](https://firebase.google.com/docs/android/setup)
- [Codelab: Firebase para Android](https://firebase.google.com/codelabs/firebase-android)
- [Video tutorial: Firebase Authentication](https://www.youtube.com/watch?v=Z-RE1QuUWPg)
- [Video tutorial: Cloud Firestore para Android](https://www.youtube.com/watch?v=MILE4PVx1kE)

### PayPal
- [Guía de integración de PayPal SDK](https://developer.paypal.com/docs/business/checkout/configure-payments/single-page-app/)
- [PayPal Android SDK en GitHub](https://github.com/paypal/PayPal-Android-SDK)




