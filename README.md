# Gestión de ofertas de empleo · Java + ObjectDB

Aplicación de escritorio **CRUD** en Java para gestionar ofertas de empleo. La persistencia usa **Jakarta Persistence (JPA)** sobre **ObjectDB**, una base de datos orientada a objetos, y la interfaz gráfica está hecha con **Swing**.

Proyecto desarrollado en 1º del CFGS de Desarrollo de Aplicaciones Web (IES Clara del Rey, Madrid).

## Funcionalidades

- Insertar nuevas ofertas de empleo
- Listar todas las ofertas guardadas
- Modificar ofertas existentes
- Eliminar ofertas

Cada oferta guarda título, empresa, ubicación, salario, número de vacantes, experiencia requerida, tipo de contrato y fecha de publicación.

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java | Lenguaje principal |
| Maven | Gestión de dependencias y compilación |
| Jakarta Persistence (JPA) 3.2 | Mapeo objeto-persistencia y consultas JPQL |
| ObjectDB 2.9 | Base de datos orientada a objetos (archivo `ejemplo.odb`) |
| Swing | Interfaz gráfica de escritorio |

## Estructura

```
src/main/java/
├── modelo/      → OfertaLaboral: entidad JPA (@Entity, @Id, @GeneratedValue)
├── dao/         → OfertaDAOImplObjectDB: operaciones CRUD con transacciones
├── conexion/    → DBObjectDB: EntityManagerFactory compartido
└── Interfaz/    → OfertaLaboralWB: ventana principal (Swing)
src/main/resources/img/ → recursos gráficos
```

El acceso a datos está separado de la interfaz siguiendo el patrón **DAO**.

## Cómo ejecutarlo

1. Clona el repositorio:
   ```bash
   git clone https://github.com/jessicamarceela/gestion-ofertas-empleo-java.git
   ```
2. Ábrelo en IntelliJ IDEA o Eclipse como proyecto **Maven** (descargará las dependencias desde el repositorio de ObjectDB).
3. Ejecuta la clase `Interfaz.OfertaLaboralWB`.

La base de datos `ejemplo.odb` se crea automáticamente en la carpeta del proyecto la primera vez que se ejecuta.

## Autora

**Jessica Marcela Simbaña Guaman** · [LinkedIn](https://www.linkedin.com/in/jessica-marcela-s-a15a64157)
