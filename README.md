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

## 📅 Cronograma de Desarrollo (2 Meses)

## 🚀 Semana 1-2: Base del Proyecto

Configuración entorno Android

Creación/configuración Firebase

Mockups básicos de interfaces

Firebase Authentication básico

Interfaces registro e inicio sesión

## 👤 Semana 3-4: Gestión Usuarios

Modelado datos usuarios (Firestore)

Perfiles de los 5 tipos de usuario

Clase FirestoreHelper

Interfaz visualización/edición perfil

Testing autenticación y perfiles

## 📰 Semana 5-6: Sistema Noticias

Modelo datos noticias (Firestore)

Interfaz creación noticias

RecyclerView + NoticiasAdapter

Vista detallada de noticias

Funcionalidad búsqueda básica

Testing funcionalidades noticias

## 💰 Semana 7: Sistema Donaciones

Integración PayPal SDK (sandbox)

Interfaz básica donaciones

Flujo confirmación donaciones

Registro donaciones en Firestore

Testing donaciones

## 🔍 Semana 8: Pulido y Finalización

Revisión/corrección bugs

Implementación tema oscuro

Optimización rendimiento

Testing final general

Finalización documentación





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




