# HANDOFF

## Estado
Implementación extendida y estabilizada: generación dinámica + comandos runtime + limpieza/validación de salida.

## ¿En qué consiste el mod?
No Void Structures es un mod NeoForge 1.21.1 que crea automáticamente un datapack dinámico cuando inicia el servidor para adaptar estructuras y features (incluyendo mods) a mundos de islas flotantes, evitando registros manuales por modpack.

## Logros completados
1. Generación dinámica de estructuras y features desde registros runtime.
2. Configuración de servidor para excepciones y modo underground.
3. Comandos in-game para identificar, ignorar y recargar (`/nvs ...`).
4. Reglas runtime persistidas en `config/no_void_structures_runtime_rules.json`.
5. Filtro por namespace completo para ignorar mods enteros.
6. Limpieza de archivos viejos del datapack generado en cada regeneración.
7. Validación mínima de JSON antes de escribir salida (campos esenciales).
8. Logging con conteos de estructuras/features generadas.

9. GitHub Action manual (`workflow_dispatch`) para compilar el proyecto solo bajo demanda.

## Comandos
- `/nvs identify`
- `/nvs reload`
- `/nvs ignore here normal`
- `/nvs ignore here underground`
- `/nvs ignore here feature`
- `/nvs ignore namespace <namespace>`

## Nota actual
Queda recomendado validar en servidor real con Lithostitched del modpack objetivo para ajustar cualquier detalle fino de compatibilidad de schema por versión específica.
