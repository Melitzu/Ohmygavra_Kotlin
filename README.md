# Ohmygavra Kotlin

Aplicacion nativa de Android para el catalogo de joyas Ohmygavra. El proyecto
incluye una app Android en Kotlin y un backend local en Ktor conectado a Neon
PostgreSQL mediante Exposed.

## Estructura

```text
app/        Aplicacion Android (AppCompat, ViewBinding, RecyclerView)
backend/    API Ktor, Exposed y PostgreSQL/Neon
gradle/     Gradle Wrapper
```

Los modulos `:app` y `:backend` se declaran en `settings.gradle.kts` y usan
Kotlin DSL para Gradle.

## Estado actual

- La app Android mantiene su arquitectura por capas y las pantallas de login y
  catalogo ya existentes.
- El backend tiene registro, login simple sin token y CRUD completo de
  productos contra Neon.
- El backend crea las tablas `users` y `products` automaticamente al iniciar.
- La integracion remota de la app Android con la API (cliente HTTP,
  repositorios remotos y formulario CRUD) aun no esta implementada en esta
  rama. Por ahora las pantallas Android conservan sus repositorios locales de
  demostracion.

## API local

El backend se inicia por defecto en `http://localhost:8080`. Cuando la app
Android se conecte desde un emulador, la URL que debe usar es
`http://10.0.2.2:8080`.

| Metodo | Ruta | Resultado |
| --- | --- | --- |
| POST | `/auth/register` | Crea un usuario y devuelve `UserResponse` (`201`). |
| POST | `/auth/login` | Valida email y contrasena, y devuelve `UserResponse` (`200`) o `401`. |
| GET | `/products` | Devuelve el catalogo de productos (`200`). |
| GET | `/products/{id}` | Devuelve un producto (`200`), `400` por id invalido o `404` si no existe. |
| POST | `/products` | Crea un producto (`201`). |
| PUT | `/products/{id}` | Actualiza un producto (`200`). |
| DELETE | `/products/{id}` | Elimina un producto (`204`). |

Para crear o actualizar un producto se envia JSON con esta forma:

```json
{
  "nombre": "Anillo Aurora",
  "precio": 38990,
  "descripcion": "Anillo dorado con piedra central.",
  "imagen": "jewel_ring",
  "stock": 8
}
```

El backend valida nombre, descripcion e imagen obligatorios, precio mayor a
cero y stock mayor o igual a cero. Las respuestas de validacion usan `400` y
las operaciones sobre un producto inexistente usan `404`.

## Configuracion de Neon

No se guardan credenciales en el repositorio. Antes de iniciar el backend, abre
PowerShell en la raiz del proyecto y carga las variables de entorno de tu base
de datos Neon:

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
$env:DB_URL = "jdbc:postgresql://<host-neon>/<base>?sslmode=require"
$env:DB_USER = "<rol-neon>"
$env:DB_PASSWORD = "<password-neon>"
```

`DB_URL` debe ser una URL JDBC y no debe incluir el usuario ni la contrasena.
Si Neon entrega una cadena que comienza con `postgresql://`, conserva el host,
la base de datos y los parametros, cambia el prefijo a `jdbc:postgresql://` y
separa usuario y contrasena en `DB_USER` y `DB_PASSWORD`.

## Ejecutar el proyecto

Con las variables de entorno cargadas, crea las tablas y agrega los datos de
demostracion opcionales:

```powershell
.\gradlew.bat :backend:setupDatabase
```

Luego inicia la API local:

```powershell
.\gradlew.bat :backend:run
```

El backend deberia quedar escuchando en el puerto `8080`. Para compilar ambos
modulos sin iniciar el servidor:

```powershell
.\gradlew.bat :backend:compileKotlin :app:assembleDebug
```

Para ejecutar la aplicacion Android, abre el proyecto en Android Studio, inicia
un emulador y ejecuta la configuracion `app`.

## Verificacion en Neon

En el SQL Editor de Neon puedes verificar los datos con:

```sql
SELECT id, nombre, precio, descripcion, imagen, stock
FROM products
ORDER BY id;

SELECT id, name, email, age
FROM users
ORDER BY id;
```

Los datos de demostracion se insertan solo cuando las tablas estan vacias. No
compartas ni subas los valores reales de `DB_URL`, `DB_USER` o `DB_PASSWORD`.
