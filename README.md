# Personal Economy

Aplicación web Java EE para registrar ingresos, gastos y radiografiar la salud financiera de una persona o familia. Está construida sobre servlets, JSP y JPA con Hibernate, y calcula informes mensuales, acumulados y la "runway" (meses que puedes mantener tu estilo de vida con la liquidez disponible).

## Características principales
- Registro y autenticación de usuarios (username + email) con sesión basada en `HttpSession`.
- Gestión de gastos clasificados por tipo (fijos, necesarios, discrecionales) y soporte para reembolsos parciales.
- Registro de capital líquido mensual (cuentas, efectivo, hucha) y detección de duplicados por usuario + mes.
- Informes de cash flow: ingresos/egresos por mes, neto acumulado y porcentaje de caprichos.
- Cálculo de runway estimado con promedio móvil de 12 meses de gasto.
- Capas separadas DAO/servicio con validación Bean Validation y persistencia JPA + Hibernate.

## Stack tecnológico
- Java 21 (configurado mediante `maven-toolchains-plugin`).
- Jakarta Servlet 6, JSP + JSTL 3 para la capa web.
- JPA 3.1 con Hibernate 6 como proveedor ORM.
- MariaDB como base de datos relacional.
- Maven 3.9+ para la construcción (`war`).
- JUnit 5 para pruebas unitarias (aún sin suites definidas).

## Requisitos previos
- JDK 21 instalado y registrado en `~/.m2/toolchains.xml` (ver `toolchains.xml` de ejemplo en el repo del usuario).
- Maven 3.9 o superior.
- Servidor de aplicaciones compatible con Jakarta EE 10 (por ejemplo, Apache Tomcat 10.1+).
- Servidor MariaDB/MySQL accesible con una base `personal_economy`.

## Puesta en marcha local
1. **Clonar el repositorio**
   ```bash
   git clone git@github.com:tu-usuario/personal-economy.git
   cd personal-economy
   ```
2. **Crear la base de datos** (ajusta usuario y contraseña a tu entorno):
   ```sql
   CREATE DATABASE personal_economy CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
   CREATE USER 'economy_app'@'%' IDENTIFIED BY 'economy_app';
   GRANT ALL PRIVILEGES ON personal_economy.* TO 'economy_app'@'%';
   FLUSH PRIVILEGES;
   ```
3. **Configurar credenciales JPA** en `src/main/resources/META-INF/persistence.xml`:
   - Actualiza `jakarta.persistence.jdbc.url`, `user` y `password` para que apunten a tu instancia.
   - Por defecto el proyecto usa la *unidad* `economyPU` (modo `update`). Para entornos de desarrollo inicial puedes cambiar `JpaUtil` para usar `economyPU-dev` (modo `drop-and-create`).
4. **Compilar y empaquetar**:
   ```bash
   mvn clean package
   ```
   El artefacto `target/personal-economy.war` queda listo para desplegar.
5. **Desplegar**:
   - Copia el `.war` a `${TOMCAT_HOME}/webapps/` o usa la consola de tu servidor.
   - Inicia Tomcat y accede a `http://localhost:8080/personal-economy/`.

### Datos de acceso inicial
No hay usuarios por defecto. Regístrate en `/users/register` para crear la primera cuenta.

## Rutas principales (servlets)
| Ruta | Método | Descripción |
|------|--------|-------------|
| `/users/register` | GET/POST | Alta de nuevo usuario. |
| `/users/login` | GET/POST | Inicio de sesión. |
| `/users/loginout` | GET | Cierra sesión. |
| `/users/home` | GET | Dashboard con resumen del último mes y enlace a informes. |
| `/users/expenses/new` | GET/POST | Formulario de gasto con validación básica. |
| `/users/cashflow` | GET | Informe de ingresos/gastos agrupados por mes y año. |
| `/users/capital` | GET/POST | Registro/edición del snapshot de capital líquido mensual. |
| `/users/runway` | GET | Informe de runway calculado a partir de capital + gasto promedio. |

## Estructura del proyecto
```
├── pom.xml                  # Dependencias y plugins Maven
├── src/main/java
│   ├── com.economy.bootstrap # Listeners (carga temprana de JPA)
│   ├── com.economy.model     # Entidades JPA (User, Expense, Income, MonthlyCapital...)
│   ├── com.economy.service   # Lógica de negocio y validaciones
│   ├── com.economy.persistence
│   │   ├── dao               # Interfaces DAO
│   │   ├── jpa               # Implementaciones Hibernate/JPA
│   │   └── util              # Utilidades de EntityManager
│   └── com.economy.servlets  # Controladores HTTP (Servlets)
├── src/main/resources
│   └── META-INF/persistence.xml
└── src/main/webapp
    ├── WEB-INF/web.xml       # Configuración básica
    ├── auth/                 # JSP de login/registro
    ├── cashflow/, runway/, expenses/ # Vistas protegidas
    └── css/, img/, js/       # Recursos estáticos
```

## Pruebas
El proyecto incluye la dependencia de JUnit 5, pero aún no dispone de suites. Puedes ejecutar:
```bash
mvn test
```
para verificar que el entorno compila y los tests (si se añaden) pasan.

## Buenas prácticas y próximos pasos sugeridos
- Incorporar hashing de contraseñas (p.e. BCrypt ya está incluido como dependencia) antes de ir a producción.
- Crear CRUD completo de ingresos y reembolsos desde la interfaz web.
- Añadir tests unitarios/integración para la lógica de `CashflowService` y servicios DAO.
- Externalizar la configuración de la unidad de persistencia (por ejemplo, variables de entorno o `context.xml`).
- Añadir scripts de migración (Flyway/Liquibase) para gestionar el esquema en producción.

---

¡Disfruta analizando tus finanzas personales!
