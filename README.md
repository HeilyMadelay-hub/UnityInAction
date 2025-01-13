# UnityInAction
README: Aplicación de Gestión de Usuarios, Noticias y 
Donaciones 
Descripción del proyecto 
Esta aplicación tiene como objetivo servir como plataforma para la gestión de usuarios, 
publicación de noticias y donaciones relacionadas con iniciativas solidarias, 
voluntariados, organizaciones profesionales o reporterismo ciudadano. 
La intención principal es proporcionar un punto de encuentro donde diferentes tipos de 
usuarios (Organizaciones, Voluntarios, Profesionales, Donantes y Reporteros 
Ciudadanos) puedan registrarse, iniciar sesión, publicar y visualizar noticias de interés, 
así como gestionar donaciones a Organizaciones. 
Características Principales 
1. Gestión de Usuarios: 
o Registro de diferentes tipos de usuarios: Organización, Reportero Ciudadano, 
Voluntario, Profesional y Donante. 
o Cada tipo de usuario tiene campos específicos adaptados a sus necesidades 
(ej. profesiones, certificados, motivaciones, disponibilidad, datos de pago, 
etc.). 
o Validación de datos al registrar, tales como formato de email, nombre, 
teléfono, etc. 
2. Iniciar Sesión: 
o Permite a los usuarios autenticarse utilizando su correo electrónico y 
contraseña. 
o Tras el inicio de sesión, se guardan datos en SharedPreferences para 
mantener la sesión activa. 
3. Gestión de Noticias: 
o Los usuarios (especialmente ciertos tipos, como Organizaciones o Reporteros) 
pueden subir noticias. 
o Las noticias cuentan con información como título, contenido, tags, ubicación, 
requerimientos y necesidades. 
o La lista de noticias se puede filtrar por palabra clave desde un buscador 
integrado. 
o Los usuarios dueños de las noticias pueden editarlas o eliminarlas. 
4. Donaciones: 
o Los Donantes pueden hacer donaciones a Organizaciones. 
o Validación de datos de la donación, como tarjeta de crédito, CVV, fecha de 
caducidad, etc. 
5. Visualización General de Noticias: 
o Todos los usuarios, incluyendo no autenticados, pueden visualizar las noticias 
públicas. 
Estructura del Proyecto 
• ActivityIniciar: Pantalla de inicio de sesión. 
• ActivityRegistrarse: Pantalla de registro donde se elige el tipo de usuario y se 
muestran campos dinámicos. 
• ActivityPerfilUsuario: Pantalla para que el usuario autenticado visualice, edite o 
elimine sus noticias. 
• ActivityVernoticias: Pantalla principal de noticias públicas; incluye buscador. 
• ActivityDonaciones: Permite registrar una donación a una organización existente. 
• EditarNoticiaActivity: Para editar una noticia existente. 
• MainActivity: Pantalla principal con enlaces a registrarse, iniciar sesión, ver noticias o 
hacer donaciones. 
• DatabaseHelper: Clase para la gestión de la base de datos SQLite (creación de tablas, 
inserciones, consultas). 
• NoticiasAdapter: Adaptador para el RecyclerView que lista las noticias. 
• Noticia: Clase modelo para representar una noticia.
->Faltaría una activity para que al dar clic en la noticia puedes ver toda la información entera. 
Requisitos Previos 
• Android Studio o cualquier IDE compatible con proyectos Android. 
• Gradle instalado (normalmente viene con Android Studio). 
• Un dispositivo Android físico o un emulador con versión mínima Android 5.0 (Lollipop) 
o superior. 
• Conexión a Internet (opcional, dependiendo de si se desea integrar más funcionalidad 
futura). 
Instalación 
1. Clona este repositorio o copia los archivos del proyecto en tu máquina local. 
2. Abre Android Studio. 
3. Selecciona "Open an existing project" (Abrir proyecto existente). 
4. Navega hasta la carpeta del proyecto y selecciónala. 
5. Espera a que Gradle sincronice el proyecto. 
Ejecución 
1. Conecta tu dispositivo Android con el modo desarrollador habilitado o inicia el 
emulador desde Android Studio. 
2. Haz clic en el botón "Run" (ícono de ▶) en la barra de herramientas de Android 
Studio. 
3. Selecciona el dispositivo o emulador donde quieres ejecutar la app. 
4. Una vez compilado, la aplicación se iniciará en el dispositivo seleccionado. 
Uso de la Aplicación (si funcionara correctamente) 
Registro de Usuarios 
1. Desde la MainActivity, pulsa el botón "Register" (o "Registrarse"). 
2. En la pantalla de registro:  
o Selecciona el tipo de usuario en el Spinner. 
o Se generarán dinámicamente los campos específicos para ese tipo de usuario 
(por ejemplo, NIF/CIF para Organizaciones, área de experiencia para 
Voluntarios, etc.). 
o Rellena todos los campos requeridos siguiendo las validaciones (ej: emails con 
formato correcto, nombres sin números, teléfonos con el formato 
internacional). 
o Sube una foto de perfil opcionalmente. 
o Pulsa el botón "Registrarse". 
3. Si todos los datos son válidos, el usuario se creará en la base de datos. 
Inicio de Sesión 
1. Desde la MainActivity, pulsa en el botón "Login" (o "Iniciar Sesión"). 
2. Ingresa tu correo electrónico y contraseña, selecciona tu tipo de usuario en el Spinner. 
3. Si las credenciales son correctas, entrarás a la sección correspondiente (por ejemplo, el 
perfil de usuario si corresponde). 
Ver Noticias 
1. Sin iniciar sesión, puedes pulsar "Ver Noticias" para ver las noticias públicas. 
2. Usa la barra de búsqueda para filtrar las noticias por texto en el título o contenido. 
3. Si iniciaste sesión como usuario que sube noticias, podrás crear nuevas noticias desde 
tu perfil. 
Subir Noticias (para usuarios autenticados) 
1. Inicia sesión como un usuario que pueda subir noticias (por ejemplo, Reportero 
Ciudadano u Organización). 
2. En la pantalla principal de noticias, pulsa el botón "Subir Noticias" que te llevará a tu 
perfil. 
3. En tu perfil, pulsa "Subir Noticia" (si existe esa opción) para crear una nueva noticia 
ingresando título, contenido, tags, ubicaciones, necesidades, etc. 
4. Al guardar, la noticia aparecerá en tu perfil y será visible para todos (si es pública). 
Editar o Eliminar Noticias (para el autor) 
1. Inicia sesión con el usuario que publicó la noticia. 
2. Ve a tu perfil (donde se listan tus noticias). 
3. Selecciona la noticia que desees editar o eliminar. 
4. Pulsa el botón "Editar" para modificar los campos y volver a guardar. 
5. Pulsa el botón "Eliminar" para borrarla de la base de datos. 
Donar a una Organización 
1. Desde la MainActivity, pulsa el botón "Donaciones". 
2. Ingresa el nombre de la organización, la fecha, tu nombre, datos de tarjeta, CVV, fecha 
de caducidad y método de pago (Bizum, Visa, Mastercard). 
3. Presiona "Donar". 
4. Si todos los datos son correctos, la donación se registrará (asumiendo que la 
organización y el donante existen en la base de datos). 
Problemas Conocidos 
• La estructura de la base de datos no está bien diseñada, lo que puede ocasionar 
errores o dificultades al mantener la integridad de los datos. 
• No existe una separación clara entre las lógicas de negocio y la vista. 
• No se implementó un control de errores robusto ni una gestión de excepciones 
adecuada. 
• Muchas validaciones son rígidas y podrían causar fallos ante escenarios no 
contemplados. 
• Falta coherencia entre las clases de datos y las tablas de la base de datos (ejemplo: 
algunos campos como "requisitos" o "archivo" podrían no tener la utilidad esperada). 
• La lógica para manejar perfiles específicos (como el nombre del autor en las noticias) 
está incompleta. 
Mejoras Futuras 
• Rediseñar la base de datos con relaciones más claras y normalizadas. 
• Implementar una capa de acceso a datos (DAO) y un modelo de arquitectura más 
limpio (MVC, MVP o MVVM). 
• Añadir autenticación segura (encriptación de contraseñas, tokens de sesión). 
• Mejorar la validación de datos y la experiencia del usuario. 
• Permitir subir imágenes y otros archivos multimedia de manera robusta. 
• Agregar paginación o lazy loading en la lista de noticias. 
• Integrar notificaciones push para nuevas noticias o donaciones. 
• Mejorar la interfaz de usuario para hacerla más intuitiva. 
Licencia 
Este proyecto se presenta sin una licencia específica. 
