# WorldHelp - Plataforma de Gestión Social
📱 **Descripción**

Aplicación web que conecta organizaciones solidarias con voluntarios y donantes, permitiendo la publicación de noticias y gestión de donaciones. **Potenciada con IA para maximizar el impacto social.**

Es una plataforma web responsive donde diferentes tipos de usuarios pueden:

- Registrarse como Organización, Voluntario, Profesional, Donante o Reportero Ciudadano
- Publicar y visualizar noticias de interés social
- Realizar donaciones a través de PayPal
- Recibir notificaciones en tiempo real
- Acceder a un sistema de reportes y análisis básicos
- Comunicarse a través del sistema de mensajería integrado
- Participar en el sistema de gamificación y reconocimientos
- Sistema de likes y comentarios en publicaciones
- Búsqueda y filtrado avanzado de contenido
- Perfiles personalizados por tipo de usuario
- Dashboard analítico para organizaciones
- **🤖 Asistente IA integrado para recomendaciones personalizadas**

## 🛠 **Stack Tecnológico**

### Frontend
- **Core:** React 18.2 + JavaScript
- **UI:** Material-UI v5
- **Routing:** React Router v6
- **Real-time:** Socket.io Client
- **HTTP:** Axios
- **Visualización:** Chart.js

### Backend
- **Framework:** Spring Boot 3.2
- **Seguridad:** Spring Security + JWT
- **WebSocket:** Spring WebSocket
- **Base de Datos:** PostgreSQL con Spring Data JPA
- **Almacenamiento:** Firebase Storage (imágenes y archivos)
- **Pagos:** PayPal SDK
- **Docs:** Swagger/OpenAPI 3.0
- **🤖 IA:** Google Gemini API

### DevOps & Tools
- **Contenedores:** Docker + Docker Compose
- **Base de Datos:** PostgreSQL 15

## 🏗 **Arquitectura**

### Frontend - Estructura
```
frontend/
├── public/
├── src/
│   ├── components/      # Componentes reutilizables
│   ├── pages/          # Páginas/vistas
│   ├── services/       # Servicios API y WebSocket
│   ├── hooks/          # Custom hooks
│   ├── utils/          # Utilidades
│   ├── contexts/       # Context API para estado global
│   └── ai/             # 🤖 Servicios de IA
```

### Backend - Arquitectura MVC
```
backend/
├── src/main/java/com/worldhelp/
│   ├── controller/     # REST Controllers
│   ├── service/        # Lógica de negocio
│   ├── repository/     # Spring Data JPA
│   ├── entity/         # Entidades JPA
│   ├── dto/            # Data Transfer Objects
│   ├── security/       # Configuración JWT
│   ├── websocket/      # WebSocket handlers
│   ├── exception/      # Manejo de excepciones
│   ├── util/           # Utilidades
│   └── ai/             # 🤖 Servicios de IA
```

## 🚀 **Características**

### Frontend (React)
- SPA moderna con React 18
- Autenticación JWT con refresh tokens
- Estado global con Context API
- UI responsive con Material-UI v5
- Sistema de rutas protegidas con React Router v6
- Chat en tiempo real con Socket.io
- Modo oscuro con persistencia
- Gráficos y analytics con Chart.js
- **🤖 Chatbot asistente integrado**

### Backend (Spring Boot)
- REST API con Spring Boot 3.2
- Seguridad con Spring Security + JWT
- WebSockets para chat en tiempo real
- Base de datos PostgreSQL con JPA/Hibernate
- Integración PayPal SDK para donaciones
- Firebase Storage para archivos
- Búsqueda avanzada con criterios dinámicos
- Documentación con Swagger/OpenAPI 3.0
- Scheduled tasks para reportes automáticos
- **🤖 Motor de recomendaciones con IA**

### Sistema de Gamificación
- Puntos por participación y actividades
- Badges y logros desbloqueables (7 tipos)
- Ranking de voluntarios más activos
- Sistema de niveles con beneficios

## 🤖 **Funcionalidades de IA**

### **Recomendaciones Inteligentes**
- **Para Voluntarios:** IA analiza perfil, habilidades y ubicación → recomienda organizaciones compatibles
- **Para Organizaciones:** IA analiza proyectos y necesidades → sugiere voluntarios ideales
- **Matching Automático:** Algoritmo conecta automáticamente perfiles afines

### **Generación Automática de Contenido**
- **Descripciones de Proyectos:** IA genera textos atractivos para iniciativas solidarias
- **Posts para Redes Sociales:** IA crea contenido optimizado para difusión en redes
- **Templates de Campañas:** IA sugiere estructuras efectivas para campañas de donación

### **Chatbot Asistente**
- **Para Voluntarios:** Consultas sobre oportunidades, requisitos, ubicaciones
- **Para Organizaciones:** Asesoría sobre gestión de voluntarios y mejores prácticas
- **Disponible 24/7:** Respuestas inmediatas a preguntas frecuentes

### **Analytics Predictivos**
- **Predicción de Éxito:** IA analiza patrones históricos → predice qué campañas tendrán mayor impacto
- **Optimización de Donaciones:** IA identifica mejores momentos y estrategias para solicitar donaciones
- **Insights Personalizados:** Recomendaciones específicas para cada organización

## **Características Destacadas**

### Chat en Tiempo Real
- WebSocket bidireccional
- Salas por organización
- Historial persistente
- **🤖 Moderación automática con IA**

### Sistema de Donaciones
- Integración completa PayPal
- Tracking de conversiones
- Reportes financieros
- **🤖 Predicción de tendencias de donación**

### Analytics Dashboard
- Métricas en tiempo real
- Gráficos interactivos con Chart.js
- **🤖 Insights automáticos generados por IA**

## 📱 **Páginas y Funcionalidades**

### Públicas
- **Landing:** Página de inicio con estadísticas
- **Explorar:** Feed de noticias públicas
- **Organizaciones:** Directorio de ONGs
- **Login/Registro:** Con email

### Autenticadas
- **Dashboard:** Panel personalizado por rol con **recomendaciones IA**
- **Noticias:** CRUD completo con editor rich text + **generación automática IA**
- **Chat:** Mensajería en tiempo real + **asistente IA**
- **Donaciones:** Integración con PayPal + **analytics predictivos**
- **Perfil:** Gestión de cuenta y achievements
- **Rankings:** Leaderboard de voluntarios

## 🔐 **Seguridad Implementada**
- JWT con refresh tokens (15min/7días)
- CORS configurado estrictamente
- Input validation en todos los endpoints
- SQL injection prevención con JPA
- CSRF protection
- Bcrypt para hash de contraseñas
- **🤖 Validación de contenido generado por IA**

## 📊 **Performance**
- Lazy loading de componentes
- Image optimization con Firebase Storage
- Database indexing optimizado
- Connection pooling configurado
- Pagination en todas las listas
- **🤖 Cache inteligente para recomendaciones IA**

## 📋 **Requisitos**
- Node.js 18+ y npm
- Java 17+ (OpenJDK recomendado)
- PostgreSQL 14+
- Maven 3.8+
- Docker & Docker Compose
- PayPal Developer Account (para donaciones)
- Firebase Account (para storage)
- **🤖 Google Gemini API Key**

## ⚡ **Instalación**

### 1. Clonar repositorio
```bash
git clone https://github.com/tu-usuario/WorldHelp.git
cd WorldHelp
```

### 2. Configurar Base de Datos
```sql
CREATE DATABASE worldhelp;
CREATE USER worldhelp_user WITH PASSWORD 'worldhelp_pass';
GRANT ALL PRIVILEGES ON DATABASE worldhelp TO worldhelp_user;
```

### 3. Backend Setup
```bash
cd backend

# Configurar application.yml
cp src/main/resources/application.yml.example src/main/resources/application.yml

# Editar con tus credenciales:
# - Database
# - PayPal API
# - JWT Secret
# - Firebase credentials
# - 🤖 Google Gemini API Key

# Ejecutar con Maven
./mvnw clean install
./mvnw spring-boot:run
```

### 4. Frontend Setup
```bash
cd frontend

# Instalar dependencias
npm install

# Configurar variables de entorno
cp .env.example .env.local

# Variables requeridas:
# REACT_APP_API_URL=http://localhost:8080/api
# REACT_APP_WS_URL=ws://localhost:8080/ws
# REACT_APP_PAYPAL_CLIENT_ID=your-paypal-client-id
# 🤖 REACT_APP_GEMINI_API_KEY=your-gemini-api-key

# Ejecutar en desarrollo
npm start
```

### 5. Docker Compose (Opción rápida)
```bash
# Levanta todo el stack
docker-compose up -d

# Frontend: http://localhost:3000
# Backend: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui
```

## 🔌 **API Endpoints Principales**

### Autenticación
```
POST   /api/auth/register     - Registro
POST   /api/auth/login        - Login
POST   /api/auth/refresh      - Refresh token
POST   /api/auth/logout       - Logout
```

### Noticias
```
GET    /api/news              - Listar (paginado, filtros)
GET    /api/news/{id}         - Detalle
POST   /api/news              - Crear
PUT    /api/news/{id}         - Actualizar
DELETE /api/news/{id}         - Eliminar
POST   /api/news/{id}/like    - Like/unlike
```

### 🤖 **IA Endpoints**
```
POST   /api/ai/recommendations - Obtener recomendaciones
POST   /api/ai/generate-content - Generar contenido
POST   /api/ai/chat           - Chatbot asistente
GET    /api/ai/analytics      - Analytics predictivos
```

### Chat (WebSocket)
```
CONNECT /ws/chat              - Conectar al chat
SEND    /app/chat.send        - Enviar mensaje
SUBSCRIBE /topic/messages     - Recibir mensajes
```

### Donaciones
```
POST   /api/donations/create   - Crear orden PayPal
POST   /api/donations/capture  - Capturar pago
GET    /api/donations/history  - Historial
```

## 🧪 **Testing**

### Backend
```bash
# Unit tests con JUnit
./mvnw test
```

### Frontend
```bash
# Unit tests con Jest
npm test
```

## 📦 **Scripts Útiles**

### Development
```bash
# Backend + Frontend simultáneo
npm run dev:all

# Solo backend
./mvnw spring-boot:run

# Solo frontend
npm start
```

## 🐳 **Docker**
Stack incluye:
- PostgreSQL 15 con volumen persistente
- Backend Spring Boot
- Frontend React

## 📸 **Screenshots**
[Incluir 3-4 capturas de pantalla de la app mostrando las funcionalidades de IA]

## 👨‍💻 **Autor**
**Heily Madelay Tandazo**

- GitHub: @HeilyMadelay-Hub
- LinkedIn: Heily Madelay Tandazo

## 💡 **Proyecto desarrollado para demostrar competencias en:**
- Desarrollo Full Stack con React y Spring Boot
- **Integración de IA para impacto social**
- Integración de sistemas de pago
- Comunicación en tiempo real
- Arquitectura MVC y buenas prácticas
- Integración con servicios cloud (Firebase)
- **Machine Learning aplicado a voluntariado**
