package usecases;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface ExportadorPdf {
    int ANCHO = 792, ALTO = 612;

    void exportar(List<BufferedImage> paginas, File destino);
}
