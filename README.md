# Gestión Juego de Rol (Spring Boot + Oracle + Thymeleaf + Spring Security)

Aplicación web de **Juego de Rol** migrada desde un proyecto original **Java + Hibernate por consola** a **Spring Boot MVC**.  
El objetivo es mostrar: **POO/dominio**, **persistencia con JPA**, **MVC web con Thymeleaf**, **seguridad por roles** y una **API REST** mínima para practicar CRUD con Postman y documentar con Swagger.

---

## Tecnologías

- Java 21
- Spring Boot 4.x
- Spring MVC + Thymeleaf
- Spring Security (login + roles)
- Spring Data JPA (Hibernate)
- Oracle XE (schema `GETAFE`)
- Postman (pruebas de endpoints)
- Swagger/OpenAPI (Springdoc)

---

## Arranque del proyecto

Ejecutar:

- `es.cursojava.springbootrol.SpringBootRolApplication`

La app arranca en:

- `http://localhost:8085`

Configuración relevante (`application.properties`):

```properties
spring.application.name=SpringBootRol
server.port=8085

# Oracle XE
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=getafe
spring.datasource.password=password
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
spring.jpa.open-in-view=false

# Thymeleaf
spring.thymeleaf.cache=false

spring.flyway.enabled=false
```

---

## Base de Datos (Oracle) – Tablas y relaciones

> Estructura de BD según el diagrama actual del proyecto.  
> Herencia SINGLE_TABLE: `TB_EQUIPAMIENTO` y `TB_CRIATURA`.

### Tablas principales

#### `TB_USUARIO`
- `ID` (PK)
- `USERNAME` (UNIQUE)
- `EMAIL` (UNIQUE)
- `PASSWORD` (BCrypt)
- `ROL` (`JUGADOR` / `ADMINISTRADOR`)
- `ACTIVO` (0/1)
- `FECHA_ALTA`

#### `TB_PERSONAJE`
- `ID` (PK)
- `NOMBRE`
- `RAZA_TIPO` (ej: `MONGOL`, `RAPA NUI`, `TROGLODITA`)
- `NIVEL`, `EXPERIENCIA`
- `PUNTOS_VIDA`, `PUNTOS_VIDA_MAX`
- `PUNTOS_ATAQUE`, `INTELIGENCIA`, `SUERTE`
- `EPISODIO_ACTUAL`
- `USUARIO_ID` (FK → `TB_USUARIO.ID`)

#### `TB_EQUIPAMIENTO` (SINGLE_TABLE)
- `ID` (PK)
- `TIPO` (discriminator)
- `NOMBRE`, `PESO`, `DURABILIDAD`, `NIVEL_REQUERIDO`
- `PUNTOS_DE_VIDA` (si aplica a consumibles)
- `PERSONAJE_ID` (FK → `TB_PERSONAJE.ID`)

#### `TB_CRIATURA` (SINGLE_TABLE)
- `ID` (PK)
- `TIPO` (discriminator)
- `NOMBRE`, `NIVEL`, `EXPERIENCIA`
- `PUNTOS_ATAQUE`, `PUNTOS_VIDA`, `TIPO_ATAQUE`, `ALIAS` (si aplica)
- `PERSONAJE_ID` (FK → `TB_PERSONAJE.ID`)

#### `ACCIONES_EPISODIO` (tabla añadida)
- `ID` (PK)
- `LOG` (CLOB / texto largo)
- `PERSONAJE_ID` (FK → `TB_PERSONAJE.ID`)

### Relaciones

- `TB_USUARIO (1) ── (N) TB_PERSONAJE`  
  `TB_PERSONAJE.USUARIO_ID → TB_USUARIO.ID`

- `TB_PERSONAJE (1) ── (N) TB_EQUIPAMIENTO`  
  `TB_EQUIPAMIENTO.PERSONAJE_ID → TB_PERSONAJE.ID`

- `TB_PERSONAJE (1) ── (N) TB_CRIATURA`  
  `TB_CRIATURA.PERSONAJE_ID → TB_PERSONAJE.ID`

- `TB_PERSONAJE (1) ── (N) ACCIONES_EPISODIO`  
  `ACCIONES_EPISODIO.PERSONAJE_ID → TB_PERSONAJE.ID`

---

## Arquitectura (por capas) y paquetes

Estructura principal (según proyecto actual):

- `config/`  
  Seguridad y handlers:
  - `SecurityConfig`
  - `UserDetailsConfig`
  - `RoleBasedAuthSuccessHandler`
  - `CustomAuthFailureHandler`

- `controllers/` (MVC + Thymeleaf)
  - `HomeController`
  - `ControllerPersonaje`
  - `ControllerEpisodios`
  - `AdminController`

- `rest/` (API REST)
  - `JuegoApiController`

- `service/` + `service/impl/`
  - `PersonajeService` / `PersonajeServiceImpl`
  - `EquipamientoService` / `EquipamientoServiceImpl`
  - (y otros services del juego)

- `repositories/`
  - `UsuarioRepository`
  - `PersonajeRepository`
  - `EquipamientoRepository`
  - `CriaturaRepository`
  - `AccionesEpisodioRepository`

- `entities/`
  - `Usuario`, `Personaje`
  - `equipo/*` (equipamiento y subtipos)
  - `criatura/*` (criaturas y subtipos)
  - `episodios/*` (lógica de episodios)
  - `AccionesEpisodio` (log persistido)

- `model/`
  - DTOs de web: `UsuarioDto`, `PersonajeDto`, `EquipamientoDto`, etc.
  - DTOs de API: `model/api/*` (`CrearPersonajeRequest`, `UpdateNivelRequest`, `PersonajeApiDto`)

- `exceptions/`
  - `ReglaJuegoException` (errores de reglas del juego)

- `utilidades/`
  - `JuegoActions` (acumula acciones del episodio como texto/log para mostrar en web y persistir)

---

## Seguridad: Login y roles

### Login
- Página de login: `GET /`
- Procesado de login (Spring Security): `POST /login`
- Logout: `POST /logout` (según config)

### Roles
- `ADMINISTRADOR`
- `JUGADOR`

### Redirecciones por rol
- Si el usuario tiene `ROLE_ADMINISTRADOR` → redirect `/admin`
- Si no → redirect `/home`

---

## Manual de usuario

### Jugador (JUGADOR)
1. Entra a `http://localhost:8085/`
2. Inicia sesión
3. Gestiona personajes (lista/crea)
4. Entra al **home del personaje** y juega episodios
5. La app ejecuta acciones **aleatorias** y guarda el resultado al finalizar el episodio (incluyendo el log en `ACCIONES_EPISODIO`)

### Administrador (ADMINISTRADOR)
1. Entra a `http://localhost:8085/`
2. Inicia sesión con rol admin
3. Redirige a `/admin`
4. Accede a funcionalidades administrativas (según implementación)

---

## Endpoints WEB (exactos por `HomeController`)

Estos endpoints están confirmados por el código actual (`HomeController`):

### Login / registro
- `GET /` → Página de login (muestra también lista de usuarios de test)
- `GET /registro` → Formulario de registro
- `POST /registro` → Crear usuario

### Personajes (web)
- `GET /personajes` → Lista personajes del usuario autenticado
- `POST /personajes/crear` → Crear personaje (asociado al usuario autenticado)

### Navegación home (web)
- `GET /home?pid={personajeId}` → Home del personaje (si pid es null muestra vacío)
- `GET /home/criaturas?pid={personajeId}`
- `GET /home/objetos?pid={personajeId}`
- `GET /home/armas?pid={personajeId}`
- `GET /home/escudos?pid={personajeId}`

> Nota: existen además controllers `ControllerEpisodios`, `ControllerPersonaje` y `AdminController`.
> Sus rutas dependen de su código (no incluidas aquí), pero el flujo ya funciona con login+roles y home/personajes.

---

## API REST (Postman) – endpoints y pruebas

Controlador: `es.cursojava.springbootrol.rest.JuegoApiController`  
Base path: `/api`

### Autenticación en Postman
- **Authorization → Basic Auth**
- Usuario y contraseña existentes en BD

Si no mandas credenciales → **401 Unauthorized** (esto sirve como prueba).

---

## Endpoints REST (exactos por `JuegoApiController`)

### 1) CREATE personaje (201)
`POST http://localhost:8085/api/personajes`

Body:
```json
{
  "usuarioId": 1,
  "nombre": "Manueee",
  "raza": "MONGOL"
}
```

### 2) READ personaje (200)
`GET http://localhost:8085/api/personajes/{personajeId}`

### 3) READ inventario (200)
`GET http://localhost:8085/api/personajes/{pid}/inventario`

### 4) UPDATE nivel (200 / 400)
`PATCH http://localhost:8085/api/personajes/{personajeId}/nivel`

Body:
```json
{
  "nivel": 5
}
```

### 5) DELETE inventario (204 / 404)
`DELETE http://localhost:8085/api/personajes/{pid}/inventario/{equipId}`

---

## Ejemplos Postman (listas para demo)

### A) Sin credenciales → 401 Unauthorized
1. En Postman, NO pongas Authorization.
2. Lanza un POST a `/api/personajes`.

Resultado esperado: **401 Unauthorized**

![401 Unauthorized](docs/images/postman-401-unauthorized.png)

---

### B) CREATE personaje → 201 Created
1. Authorization → **Basic Auth**
2. Endpoint: `POST http://localhost:8085/api/personajes`
3. Body JSON (raw):

```json
{
  "usuarioId": 301,
  "nombre": "Pepe",
  "raza": "MONGOL"
}
```

Resultado esperado: **201 Created** + JSON del personaje

![201 Created](docs/images/postman-201-created-personaje.png)

---

### C) Caso típico de error por validación → 400 Bad Request
1. Authorization → **Basic Auth**
2. `PATCH /api/personajes/{id}/nivel`
3. Body:

```json
{ "nivel": 0 }
```

Resultado esperado: **400 Bad Request** (si `UpdateNivelRequest` valida mínimo 1)

---

### D) Forbidden (403) por rol (demo)
- Intentar acceder a `/admin/**` con un usuario **JUGADOR**  
Resultado esperado: **403 Forbidden**

---

## Swagger / OpenAPI (documentación)

### Dependencia (Maven)
```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.5.0</version>
</dependency>
```

### URL
- `http://localhost:8085/swagger-ui/index.html`

---

## Problema de la migración (Consola → Web) y simplificación del juego

En el proyecto original (consola), cada episodio tenía muchas decisiones mediante un menú (8-9 acciones), lo que en web se traduciría en **muchísimas rutas/pantallas** y gestión compleja del estado.

Para poder completar el proyecto a tiempo sin perder la esencia, se simplificó el flujo web:
- Un botón principal **JUGAR** (ejecuta episodio)
- Un botón **SALIR**
- Las acciones internas se ejecutan **aleatoriamente**
- Al finalizar el episodio se persiste el resultado en BD:
  - stats del personaje
  - inventario/equipamiento
  - criaturas
  - **log del episodio** en `ACCIONES_EPISODIO` para mostrarlo en la web

Esto permite demostrar correctamente:
- MVC + Thymeleaf
- JPA/Hibernate + Oracle
- Seguridad por roles
- API REST CRUD + Postman
- Documentación Swagger

---
