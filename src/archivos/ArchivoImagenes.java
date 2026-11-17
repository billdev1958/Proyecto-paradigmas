package archivos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import usecases.AlmacenImagenes;

public class ArchivoImagenes implements AlmacenImagenes {
    public static final int MINIMO = 100, MAXIMO = 4000;

    private final Path carpeta = Path.of("imagenes").toAbsolutePath();

    @Override
    public String guardar(String ruta) {
        Path origen = Path.of(ruta).toAbsolutePath();
        try {
            BufferedImage imagen = ImageIO.read(origen.toFile());
            if (imagen == null)
                throw new IllegalArgumentException("El archivo no es una imagen: " + origen.getFileName());
            int ancho = imagen.getWidth(), alto = imagen.getHeight();
            if (ancho < MINIMO || alto < MINIMO || ancho > MAXIMO || alto > MAXIMO)
                throw new IllegalArgumentException("La imagen " + origen.getFileName() + " mide " + ancho + "x" + alto
                        + "; debe medir entre " + MINIMO + "x" + MINIMO + " y " + MAXIMO + "x" + MAXIMO + " pixeles");

            if (!origen.getParent().equals(carpeta)) { 
                Files.createDirectories(carpeta);
                Path destino = carpeta.resolve(System.currentTimeMillis() + "_" + origen.getFileName());
                Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
                origen = destino;
            }
            return "imagenes/" + origen.getFileName();
        } catch (IOException e) {
            throw new IllegalArgumentException("No se pudo leer la imagen: " + ruta);
        }
    }
}
