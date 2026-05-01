# No Void Structures (NeoForge 1.21.1)

Mod para generar un datapack dinámico al iniciar servidor y adaptar estructuras/features en mundos de islas flotantes.

## Funciones
- Descubre estructuras de mods en runtime y genera JSON de worldgen.
- Soporta excepciones por ID y por namespace completo.
- Modo `underground structures`.
- Soporte opcional para `placed features`.
- Comandos administrativos para editar ignorados en caliente.
- Limpia salida previa en cada regeneración para evitar JSON obsoletos.

## Comandos
- `/nvs identify` → identifica la estructura en la que estás parado.
- `/nvs reload` → recarga reglas runtime y regenera datapack dinámico.
- `/nvs ignore here normal` → agrega/quita la estructura actual de ignorados normales.
- `/nvs ignore here underground` → agrega/quita la estructura actual en modo underground.
- `/nvs ignore here feature` → agrega/quita la estructura/feature actual en ignorados de features.
- `/nvs ignore namespace <namespace>` → agrega/quita namespace completo.

## Config (server)
- `enableStructureProcessing`
- `enableFeatureProcessing`
- `undergroundStructuresMode`
- `structureExceptions`
- `featureExceptions`

## Runtime rules
Se guardan en `config/no_void_structures_runtime_rules.json`.

## CI/CD
Este repo incluye un workflow manual: **Actions → Manual Build → Run workflow**.
No se ejecuta en cada commit; solo cuando lo dispares manualmente.
