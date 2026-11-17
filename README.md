# Album

Álbum de estampillas de futbol (Liga MX varonil y femenil). Java + Swing; los datos se guardan en SQLite y el PDF se genera con OpenPDF (los dos jars están en `lib/`).

## Ejecutar

```
javac -cp "lib/*" -d bin $(find src -name '*.java')
java --enable-native-access=ALL-UNNAMED -cp "bin:lib/*" main.Main
```

`--enable-native-access` solo quita el aviso que Java 24+ muestra al cargar SQLite; sin él también funciona.

Se debe ejecutar desde la carpeta del proyecto, porque ahí se crean `datos/` e `imagenes/`.

| Usuario   | Contraseña   | Puede                                                    |
|-----------|--------------|----------------------------------------------------------|
| `admin`   | `admin123`   | Agregar, consultar, modificar y eliminar; ver el álbum   |
| `usuario` | `usuario123` | Ver el álbum, verlo en PDF, imprimirlo y descargarlo     |

Los usuarios y los jugadores están en la base de datos `datos/album.db` (tablas `usuarios` y `jugadores`). En el primer arranque se crean 10 equipos de ejemplo con imágenes genéricas.

## Paquetes

Las dependencias van siempre hacia adentro: `gui`/`control`/`archivos` → `usecases` → `entities`.

| Paquete    | Contenido                                                                                   |
|------------|---------------------------------------------------------------------------------------------|
| `entities` | Records `Jugador` y `Usuario`; `Jugador` valida sus propios datos                            |
| `usecases` | Un caso de uso por clase (agregar, consultar, modificar, eliminar, ver álbum, iniciar sesión) y las interfaces que necesitan |
| `archivos` | Implementan esas interfaces: jugadores y usuarios en SQLite, imágenes y PDF                  |
| `gui`      | Ventanas y dibujo de estampillas y hojas, solo diseño, a puro código                         |
| `control`  | Eventos de las ventanas; llaman a los casos de uso                                           |
| `main`     | `Main`: solo crea el `Controlador`                                                           |
