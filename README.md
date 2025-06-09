# WorldHelp - Plataforma de Gestión Social

Aplicación Android que conecta organizaciones solidarias con voluntarios y donantes, permitiendo la publicación de noticias y gestión de donaciones.

## 📱 Descripción

Es una plataforma móvil donde diferentes tipos de usuarios pueden:

- Registrarse como Organización, Voluntario, Profesional, Donante o Reportero Ciudadano (email o redes sociales)
- Publicar y visualizar noticias de interés social
- Realizar donaciones a través de PayPal
- Recibir notificaciones push en tiempo real
- Acceder a un sistema de reportes y análisis básicos
- Comunicarse a través del sistema de mensajería integrado
- Participar en el sistema de gamificación y reconocimientos

## 🚀 Características

### App Android

- **Autenticación** con Firebase Auth (email/contraseña y redes sociales)
- **Perfiles diferenciados** según tipo de usuario
- **Gestión de noticias** con búsqueda y filtros
- **Sistema de comentarios** en noticias con moderación
- **Chat integrado** para comunicación entre organizaciones y voluntarios
- **Sistema de favoritos** para guardar noticias y organizaciones
- **Compartir en redes sociales** las noticias
- **Donaciones** integradas con PayPal
- **Notificaciones push** con Firebase Cloud Messaging
- **Geolocalización** para filtrar contenido cercano
- **Modo oscuro**
- **Soporte multiidioma** (Español/Inglés)
- **Sistema de reportes avanzado**
- **Dashboard analítico** para organizaciones

#### Sistema de Gamificación
- **Puntos** por participación y actividades
- **Badges** y logros desbloqueables
- **Ranking** de voluntarios más activos
- **Sistema de reconocimientos** públicos

#### Modo Offline
- Lectura de noticias guardadas
- Visualización de perfiles en caché
- Acceso a mensajes descargados
- Guardado de borradores de publicaciones
- Sincronización automática al recuperar conexión

### API Backend (Spring Boot)

- **REST API** para gestión de datos
- **Firebase Admin SDK** para sincronización
- **Sistema de notificaciones** automatizado
- **Validación** de organizaciones
- **Gestión de chat** y mensajes
- **Moderación de contenido** (comentarios)
- **Sistema de puntuación** para gamificación

## 🛠 Tecnologías

- **Android**: Java, XML, Material Design
- **Backend**: Spring Boot, Firebase Admin SDK
- **Base de datos**: Firebase Firestore
- **Notificaciones**: Firebase Cloud Messaging (FCM)
- **Pagos**: PayPal SDK
- **Mapas**: Google Maps API
- **Chat**: Firebase Realtime Database
- **Cache**: Room Database (offline support)

## 🏗 Arquitectura

Patrón **MVVM** para separación de responsabilidades y mejor testabilidad. Los ViewModels sobreviven a cambios de configuración.

```
app/
├── ui/
│   ├── activities/     # Views
│   ├── viewmodels/     # ViewModels con LiveData
│   └── fragments/      # Fragments reutilizables
├── data/
│   ├── repository/     # Repository pattern
│   ├── local/          # Room Database para offline
│   └── remote/         # Firebase/API calls
└── domain/
    ├── model/          # Modelos de negocio
    └── usecase/        # Casos de uso
```

## 📂 Estructura del Proyecto

```
WorldHelp/
├── app/                    # Aplicación Android
│   ├── ui/
│   │   ├── activities/     # Pantallas principales
│   │   ├── fragments/      # Fragmentos reutilizables
│   │   ├── viewmodels/     # ViewModels con LiveData
│   │   └── adapters/       # Adaptadores RecyclerView
│   ├── data/
│   │   ├── repository/     # Repository pattern
│   │   ├── local/          # Room database
│   │   └── remote/         # Firebase/API calls
│   ├── domain/
│   │   ├── model/          # Modelos de negocio
│   │   └── usecase/        # Casos de uso
│   ├── services/           # Firebase Messaging, Sync
│   └── utils/              # Utilidades y helpers
├── backend/                # API Spring Boot
│   ├── controller/         # Endpoints REST
│   ├── service/           # Lógica de negocio
│   ├── repository/        # Acceso a datos
│   └── config/            # Configuración Firebase
└── README.md
```

## 📋 Requisitos

- Android Studio
- JDK 11+
- Cuenta Firebase
- Cuenta PayPal Developer (para pruebas)
- API Key de Google Maps

## ⚡ Instalación

### 1. Clonar repositorio
```bash
git clone https://github.com/tu-usuario/WorldHelp.git
```

### 2. Configurar Firebase
1. Crear proyecto en [Firebase Console](https://console.firebase.google.com/)
2. Descargar `google-services.json`
3. Colocarlo en `app/google-services.json`
4. Habilitar Authentication, Firestore, Realtime Database y Storage

### 3. Configurar API Backend (opcional)
```bash
cd backend
# Copiar firebase-service-account.json a src/main/resources/
./mvnw spring-boot:run
```

### 4. Ejecutar App
1. Abrir proyecto en Android Studio
2. Sincronizar Gradle
3. Run en dispositivo/emulador

## 📱 Pantallas Principales

- **Login/Registro**: Autenticación con email/contraseña o redes sociales
- **Feed de Noticias**: Lista de publicaciones con buscador, filtros y comentarios
- **Detalle Noticia**: Información completa, comentarios y opciones para compartir
- **Perfil**: Gestión de cuenta, noticias propias, badges y puntos
- **Chat**: Mensajería entre organizaciones y voluntarios
- **Favoritos**: Noticias y organizaciones guardadas
- **Donaciones**: Integración con PayPal
- **Dashboard Analítico**: Métricas y reportes para organizaciones
- **Ranking**: Tabla de voluntarios más activos

## 🔔 Sistema de Notificaciones

### Flujo de Notificaciones
1. Usuario publica noticia → Se guarda en Firestore
2. Backend detecta nueva noticia → Envía notificación FCM
3. Usuarios reciben push notification en tiempo real
4. Notificaciones de chat, comentarios y gamificación

### Endpoints API
```
POST /api/noticias          - Crear noticia
GET  /api/noticias          - Listar noticias
POST /api/notificaciones    - Enviar notificación
PUT  /api/usuarios/{id}/fcmToken - Actualizar token
POST /api/comentarios       - Crear comentario
GET  /api/chat/mensajes     - Obtener mensajes
POST /api/chat/enviar       - Enviar mensaje
GET  /api/ranking           - Obtener ranking voluntarios
```

## 🧪 Testing

**Coverage**: 65% en ViewModels y Repositories

### Unit Tests
```java
// Ejemplo: Test de ViewModel
@Test
fun `when login succeeds, navigate to home`() {
    // Given
    fakeAuthRepository.setLoginResult(Success)
    
    // When  
    viewModel.login("test@email.com", "password")
    
    // Then
    assertEquals(NavigateToHome, viewModel.navigationEvent.value)
}
```

### Testing por Componente
- **Unit Tests**: ViewModels y Repositories con test doubles
- **UI Tests**: Flujos principales (login, crear noticia, donar, chat)
- **Test Doubles**: Fake repositories para aislamiento

```bash
# Android
./gradlew test              # Unit tests
./gradlew connectedTest     # UI tests

# Backend
./mvnw test                 # Spring Boot tests
```

## 🚀 Optimizaciones

### Performance
- Paginación en lista de noticias (20 items por página)
- Caché de imágenes con Glide
- Lazy loading en RecyclerViews
- Caché local con Room para modo offline
- Sync incremental para reducir uso de datos

### Manejo de Estados
```java
public abstract class UiState {
    
    // Constructor privado para evitar extensiones externas
    private UiState() {}
    
    // Estado de carga
    public static class Loading extends UiState {
        public static final Loading INSTANCE = new Loading();
        
        private Loading() {}
        
        @Override
        public String toString() {
            return "Loading";
        }
    }
    
    // Estado de éxito con datos
    public static class Success extends UiState {
        private final Object data;
        
        public Success(Object data) {
            this.data = data;
        }
        
        public Object getData() {
            return data;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Success success = (Success) obj;
            return Objects.equals(data, success.data);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
        
        @Override
        public String toString() {
            return "Success{data=" + data + "}";
        }
    }
    
    // Estado de error con mensaje
    public static class Error extends UiState {
        private final String message;
        
        public Error(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Error error = (Error) obj;
            return Objects.equals(message, error.message);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(message);
        }
        
        @Override
        public String toString() {
            return "Error{message='" + message + "'}";
        }
    }
}
```

## 🔐 Seguridad

- ProGuard configurado para ofuscar código
- SSL Pinning en llamadas a API críticas
- Validación de inputs en cliente y servidor
- Moderación de contenido (comentarios y chat)
- Autenticación de dos factores para organizaciones

## 📸 Screenshots

[Incluir 3-4 capturas de pantalla de la app]

## 👨‍💻 Autor

**Heily Madelay Tandazo**

- GitHub: [@HeilyMadelay-Hub](https://github.com/HeilyMadelay-Hub)
- LinkedIn: [Heily Madelay Tandazo](https://linkedin.com/in/heily-madelay-tandazo)

---

Proyecto desarrollado como parte de mi portfolio profesional para demostrar competencias en desarrollo Android y backend con Java/Spring Boot.
