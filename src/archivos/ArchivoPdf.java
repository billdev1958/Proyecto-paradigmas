package archivos;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import usecases.ExportadorPdf;

public class ArchivoPdf implements ExportadorPdf {
    @Override
    public void exportar(List<BufferedImage> paginas, File destino) {
        Document documento = new Document(new Rectangle(ANCHO, ALTO), 0, 0, 0, 0);
        try (FileOutputStream salida = new FileOutputStream(destino)) {
            PdfWriter.getInstance(documento, salida);
            documento.open();
            for (BufferedImage pagina : paginas) {
                Image imagen = Image.getInstance(pagina, null);
                imagen.scaleAbsolute(ANCHO, ALTO);
                imagen.setAbsolutePosition(0, 0);
                documento.newPage();
                documento.add(imagen);
            }
            documento.close();
        } catch (IOException | RuntimeException e) {
            throw new IllegalStateException("No se pudo crear el PDF: " + e.getMessage(), e);
        }
    }
}
