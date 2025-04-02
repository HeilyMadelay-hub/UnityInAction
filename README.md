# UnityInAction

Una aplicación Android que conecta organizaciones solidarias con voluntarios, profesionales y donantes.

## 📱 Descripción

UnityInAction facilita la publicación de noticias sobre iniciativas solidarias y permite gestionar donaciones de manera segura. La aplicación soporta cinco tipos de usuarios (Organizaciones, Voluntarios, Profesionales, Donantes y Reporteros Ciudadanos), cada uno con funcionalidades específicas.

## ✨ Características principales

* **Sistema multi-usuario**: Registro e inicio de sesión para 5 perfiles diferentes
* **Gestión de noticias**: Publicación, visualización y búsqueda de noticias
* **Sistema de donaciones**: Proceso simplificado con integración PayPal (sandbox)
* **Tema oscuro/claro**: Soporte para ambos modos de visualización
* **Diseño Material**: Interfaz moderna siguiendo Material Design

## 🛠️ Tecnologías utilizadas

* **Frontend**: Java, XML, Material Design 3
* **Backend**: Firebase (Authentication, Firestore, Storage)
* **Pagos**: PayPal SDK para Android (modo sandbox)

## 🏗️ Estructura del proyecto

```
org.meicode.tfg.src/
│
├── activities/         # Pantallas principales de la aplicación
├── adapters/           # Adaptadores para RecyclerView
├── fragments/          # Fragmentos de UI reutilizables
├── models/             # Clases de datos (POJO)
└── utils/              # Utilidades y helpers
```

## 📋 Requisitos

* Android 5.0 (API 21) o superior
* Conexión a Internet
* Cuenta de Firebase
* Cuenta de desarrollador PayPal (para pruebas)

## 🚀 Instalación

1. Clonar el repositorio
2. Abrir el proyecto en Android Studio
3. Configurar `google-services.json` con credenciales de Firebase
4. Ejecutar en dispositivo o emulador

## 🔮 Funcionalidades futuras

* Geolocalización y mapas
* Sincronización offline
* Soporte multi-idioma

## 📄 Licencia

Este proyecto está licenciado bajo [Licencia Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)

