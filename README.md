# WorldHelp - Plataforma de Gestión Social

> 🎯 **Proyecto Portfolio**: Demo técnica para mostrar habilidades en desarrollo web full-stack con React y Spring Boot.
Incluye datos de prueba y está optimizado para demostración, no para uso en producción.

Aplicación web que conecta organizaciones solidarias con voluntarios y donantes, permitiendo la publicación de noticias y gestión de donaciones.

## 📱 Descripción

Es una plataforma web responsive donde diferentes tipos de usuarios pueden:

- Registrarse como Organización, Voluntario, Profesional, Donante o Reportero Ciudadano (email o redes sociales)
- Publicar y visualizar noticias de interés social
- Realizar donaciones a través de PayPal
- Recibir notificaciones push en tiempo real
- Acceder a un sistema de reportes y análisis básicos
- Comunicarse a través del sistema de mensajería integrado
- Participar en el sistema de gamificación y reconocimientos
- Sistema de likes y comentarios en publicaciones
- Búsqueda y filtrado avanzado de contenido
- Perfiles personalizados por tipo de usuario
- Dashboard analítico para organizaciones
- Diseño responsive para móviles y desktop

🚀 Características
Frontend (React)

SPA moderna con React 18 y TypeScript
Autenticación JWT con refresh tokens y login social (Google/Facebook)
Gestión de estado global con Redux Toolkit
UI responsive con Material-UI v5
Sistema de rutas protegidas con React Router v6
Chat en tiempo real con WebSockets (Socket.io)
Notificaciones push web con Service Workers
PWA completa - instalable, offline, sincronización
Modo oscuro con persistencia
Internacionalización (ES/EN) con react-i18next
Mapas interactivos con Leaflet para geolocalización
Gráficos y analytics con Chart.js

Backend (Spring Boot)

REST API con Spring Boot 3.2
Seguridad con Spring Security + JWT + OAuth2
WebSockets para chat en tiempo real
Base de datos PostgreSQL con JPA/Hibernate
Integración PayPal SDK para donaciones
Sistema de notificaciones con WebPush
Búsqueda avanzada con criterios dinámicos
Caché distribuido con Redis
Documentación con Swagger/OpenAPI 3.0
Scheduled tasks para reportes automáticos
Auditoría de acciones importantes

Sistema de Gamificación

Puntos por participación y actividades
Badges y logros desbloqueables (10+ tipos)
Ranking de voluntarios más activos
Sistema de niveles con beneficios
Reconocimientos públicos mensuales

Características Avanzadas

Dashboard analytics con métricas en tiempo real
Exportación de reportes (PDF/Excel)
Sistema de moderación automática de contenido
API pública documentada para integraciones
Webhooks para eventos importantes

🛠 Stack Tecnológico
Frontend

React 18.2 + TypeScript 5
Redux Toolkit para estado global
Material-UI v5 para componentes
React Router v6 para navegación
Socket.io Client para WebSockets
Axios + React Query para API calls
React Hook Form + Yup para formularios
Chart.js para visualizaciones
Leaflet para mapas
Workbox para PWA

Backend

Spring Boot 3.2
Spring Security + JWT + OAuth2
Spring WebSocket para real-time
Spring Data JPA con PostgreSQL
Spring Cache con Redis
PayPal SDK para pagos
WebPush Java para notificaciones
MapStruct para mapeo de DTOs
Lombok para reducir boilerplate
Liquibase para migraciones

DevOps & Tools

Docker + Docker Compose
GitHub Actions CI/CD
PostgreSQL 15 + Redis 7
Nginx como reverse proxy
Prometheus + Grafana para monitoreo
ELK Stack para logs

🏗 Arquitectura
Frontend - Estructura
frontend/
├── public/
│   ├── manifest.json    # PWA manifest
│   └── service-worker.js
├── src/
│   ├── components/      # Componentes reutilizables
│   ├── features/        # Features con Redux slices
│   ├── pages/          # Páginas/vistas
│   ├── services/       # Servicios API y WebSocket
│   ├── hooks/          # Custom hooks
│   ├── store/          # Redux store config
│   ├── utils/          # Utilidades
│   ├── i18n/           # Traducciones
│   └── types/          # TypeScript types
Backend - Clean Architecture
backend/
├── src/main/java/com/worldhelp/
│   ├── application/
│   │   ├── controller/     # REST Controllers
│   │   ├── websocket/      # WebSocket handlers
│   │   └── dto/            # Data Transfer Objects
│   ├── domain/
│   │   ├── entity/         # Entidades de dominio
│   │   ├── service/        # Lógica de negocio
│   │   └── repository/     # Interfaces de repositorio
│   ├── infrastructure/
│   │   ├── persistence/    # Implementación JPA
│   │   ├── payment/        # PayPal integration
│   │   ├── notification/   # Push notifications
│   │   └── security/       # Security config
│   └── shared/
│       ├── exception/      # Excepciones personalizadas
│       └── util/           # Utilidades compartidas
📋 Requisitos

Node.js 18+ y npm/yarn
Java 17+ (OpenJDK recomendado)
PostgreSQL 14+
Redis 7+
Maven 3.8+
Docker & Docker Compose (opcional)
PayPal Developer Account (para donaciones)

⚡ Instalación
1. Clonar repositorio
bashgit clone https://github.com/tu-usuario/WorldHelp.git
cd WorldHelp
2. Configurar Base de Datos
sqlCREATE DATABASE worldhelp;
CREATE USER worldhelp_user WITH PASSWORD 'worldhelp_pass';
GRANT ALL PRIVILEGES ON DATABASE worldhelp TO worldhelp_user;
3. Backend Setup
bashcd backend

# Configurar application.yml
cp src/main/resources/application.yml.example src/main/resources/application.yml

# Editar con tus credenciales:
# - Database
# - Redis
# - PayPal API
# - JWT Secret
# - OAuth2 (Google/Facebook)

# Ejecutar con Maven
./mvnw clean install
./mvnw spring-boot:run
4. Frontend Setup
bashcd frontend

# Instalar dependencias
npm install

# Configurar variables de entorno
cp .env.example .env.local

# Variables requeridas:
# REACT_APP_API_URL=http://localhost:8080/api
# REACT_APP_WS_URL=ws://localhost:8080/ws
# REACT_APP_PAYPAL_CLIENT_ID=your-paypal-client-id

# Ejecutar en desarrollo
npm start
5. Docker Compose (Opción rápida)
bash# Levanta todo el stack
docker-compose up -d

# Frontend: http://localhost:3000
# Backend: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui
📱 Páginas y Funcionalidades
Públicas

Landing: Página de inicio con estadísticas
Explorar: Feed de noticias públicas
Organizaciones: Directorio de ONGs
Login/Registro: Con email o redes sociales

Autenticadas

Dashboard: Panel personalizado por rol
Noticias: CRUD completo con editor rich text
Chat: Mensajería en tiempo real
Donaciones: Integración con PayPal
Perfil: Gestión de cuenta y achievements
Rankings: Leaderboard de voluntarios
Reportes: Analytics y exportación

🔌 API Endpoints Principales
Autenticación
POST   /api/auth/register          - Registro
POST   /api/auth/login             - Login
POST   /api/auth/refresh           - Refresh token
POST   /api/auth/logout            - Logout
GET    /api/auth/oauth2/{provider} - OAuth2 login
Noticias
GET    /api/news                   - Listar (paginado, filtros)
GET    /api/news/{id}              - Detalle
POST   /api/news                   - Crear
PUT    /api/news/{id}              - Actualizar
DELETE /api/news/{id}              - Eliminar
POST   /api/news/{id}/like         - Like/unlike
GET    /api/news/{id}/comments     - Comentarios
POST   /api/news/{id}/comments     - Comentar
Chat (WebSocket)
CONNECT /ws/chat                   - Conectar al chat
SEND    /app/chat.send            - Enviar mensaje
SUBSCRIBE /topic/messages          - Recibir mensajes
SUBSCRIBE /user/queue/private      - Mensajes privados
Donaciones
POST   /api/donations/create       - Crear orden PayPal
POST   /api/donations/capture      - Capturar pago
GET    /api/donations/history      - Historial
GET    /api/donations/statistics   - Estadísticas
Gamificación
GET    /api/gamification/profile   - Mi perfil gaming
GET    /api/gamification/badges    - Badges disponibles
GET    /api/gamification/ranking   - Ranking global
POST   /api/gamification/claim     - Reclamar logro
🧪 Testing
Backend
bash# Unit tests
./mvnw test

# Integration tests
./mvnw verify

# Test coverage
./mvnw clean test jacoco:report
# Report en: target/site/jacoco/index.html
Frontend
bash# Unit tests con Jest
npm test

# Test coverage
npm test -- --coverage

# E2E con Cypress
npm run cypress:open

# Linting
npm run lint
🚀 Características Destacadas para Portfolio

Chat en Tiempo Real

WebSocket bidireccional
Salas por organización
Indicadores de escritura
Historial persistente


Sistema de Donaciones

Integración completa PayPal
Tracking de conversiones
Reportes financieros
Recibos automáticos


PWA Completa

Instalable en móviles
Funciona offline
Sincronización en background
Push notifications


Gamificación

Sistema de puntos dinámico
15+ tipos de badges
Niveles con beneficios
Eventos especiales


Analytics Dashboard

Métricas en tiempo real
Gráficos interactivos
Exportación de datos
Reportes automatizados



🔐 Seguridad Implementada

JWT con refresh tokens (15min/7días)
OAuth2 con Google y Facebook
Rate limiting por IP y usuario
CORS configurado estrictamente
Input validation en todos los endpoints
SQL injection prevención con JPA
XSS prevención con DOMPurify
CSRF tokens en formularios críticos
Helmet.js headers de seguridad
Bcrypt para hash de contraseñas

📊 Performance

Lazy loading de componentes y rutas
Image optimization con lazy loading
Redis cache para datos frecuentes
Database indexing optimizado
Gzip compression en respuestas
CDN para assets estáticos
Connection pooling configurado
Pagination en todas las listas

📦 Scripts Útiles
Development
bash# Backend + Frontend simultáneo
npm run dev:all

# Solo backend con hot reload
npm run dev:backend

# Solo frontend
npm run dev:frontend

# Limpiar y reconstruir
npm run clean:all
Production
bash# Build completo
npm run build:all

# Deploy con Docker
npm run docker:deploy

# Backup base de datos
npm run db:backup
🐳 Docker
yaml# Stack incluye:
- PostgreSQL 15 con volumen persistente
- Redis 7 para caché y sesiones
- Backend Spring Boot
- Frontend React con Nginx
- Reverse proxy Nginx
- Adminer para gestión BD


## 📸 Screenshots

[Incluir 3-4 capturas de pantalla de la app]

## 👨‍💻 Autor

**Heily Madelay Tandazo**

- GitHub: [@HeilyMadelay-Hub](https://github.com/HeilyMadelay-Hub)
- LinkedIn: [Heily Madelay Tandazo](https://linkedin.com/in/heily-madelay-tandazo)
