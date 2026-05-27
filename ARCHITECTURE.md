# Arquitectura del proyecto

El proyecto usa Clean Architecture con tres capas principales:

- `presentation`: contiene la pantalla, el estado de UI y el ViewModel. Su responsabilidad es mostrar datos y reaccionar a eventos del usuario.
- `domain`: contiene modelos, contratos de repositorio y casos de uso. Aqui viven las reglas de negocio del login.
- `data`: contiene implementaciones concretas de repositorios y, mas adelante, fuentes remotas o locales.

La dependencia principal apunta hacia el dominio: la capa `presentation` usa casos de uso de `domain`, y `domain` depende de interfaces, no de implementaciones concretas. La capa `data` implementa esas interfaces.

Estructura base:

```text
com.example.ohmygavra_kotlin
|-- presentation
|   |-- screens
|   |-- components
|   |-- navigation
|   |-- viewmodel
|   `-- login
|-- domain
|   |-- model
|   |-- repository
|   `-- usecase
|-- data
|   |-- remote
|   |   |-- api
|   |   |-- dto
|   |   `-- datasource
|   |-- local
|   |   |-- dao
|   |   |-- entity
|   |   `-- database
|   |-- repository
|   `-- mapper
|-- di
|-- utils
`-- MainActivity.kt
```

Para esta primera entrega, el login usa `FakeAuthRepository` como fuente de datos temporal. Luego puede reemplazarse por una API, base de datos local o Firebase sin modificar las reglas de negocio del dominio.
