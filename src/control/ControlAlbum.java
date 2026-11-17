package control;

import usecases.ExportadorPdf;
import entities.Jugador;
import gui.Dibujo;
import gui.VentanaAlbum;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class ControlAlbum {
    final VentanaAlbum vista = new VentanaAlbum();
    private final Map<String, List<Jugador>> album;
    private final List<String> equipos;

    ControlAlbum(Controlador c) {
        album = c.verAlbum.ejecutar();
        equipos = new ArrayList<>(album.keySet());
        equipos.forEach(vista.equipos::addItem);
        vista.equipos.addActionListener(e -> mostrarPagina());
        mostrarPagina();

        Controlador.alPulsar(vista.anterior, () -> mover(-1));
        Controlador.alPulsar(vista.siguiente, () -> mover(1));
        Controlador.alPulsar(vista.verPdf, () -> {
            File temporal = File.createTempFile("album", ".pdf");
            c.pdf.exportar(paginas(), temporal);
            Desktop.getDesktop().open(temporal);
        });
        Controlador.alPulsar(vista.descargar, () -> {
            JFileChooser selector = new JFileChooser();
            selector.setSelectedFile(new File("album.pdf"));
            if (selector.showSaveDialog(vista) != JFileChooser.APPROVE_OPTION) return;
            File destino = selector.getSelectedFile();
            if (!destino.getName().toLowerCase().endsWith(".pdf")) destino = new File(destino.getPath() + ".pdf");
            c.pdf.exportar(paginas(), destino);
            JOptionPane.showMessageDialog(vista, "Álbum guardado en " + destino);
        });
        Controlador.alPulsar(vista.imprimir, this::imprimir);

        vista.salir.setText(c.usuario.admin() ? "Regresar" : "Cerrar sesión");
        Controlador.alPulsar(vista.salir, () -> {
            if (c.usuario.admin()) c.mostrarAdmin();
            else c.mostrarLogin();
        });
    }

    private void mostrarPagina() {
        String equipo = (String) vista.equipos.getSelectedItem();
        if (equipo != null) vista.pagina.mostrar(equipo, album.get(equipo));
    }

    private void mover(int pasos) {
        if (equipos.isEmpty()) return;
        int indice = (vista.equipos.getSelectedIndex() + pasos + equipos.size()) % equipos.size();
        vista.equipos.setSelectedIndex(indice);
    }

    private List<BufferedImage> paginas() {
        if (equipos.isEmpty()) throw new IllegalStateException("El álbum no tiene jugadores");
        List<BufferedImage> paginas = new ArrayList<>();
        for (String equipo : equipos) {
            BufferedImage imagen = new BufferedImage(ExportadorPdf.ANCHO * 2, ExportadorPdf.ALTO * 2, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imagen.createGraphics();
            Dibujo.pagina(g, imagen.getWidth(), imagen.getHeight(), equipo, album.get(equipo));
            g.dispose();
            paginas.add(imagen);
        }
        return paginas;
    }

    private void imprimir() throws Exception {
        if (equipos.isEmpty()) throw new IllegalStateException("El álbum no tiene jugadores");
        PrinterJob trabajo = PrinterJob.getPrinterJob();
        PageFormat formato = trabajo.defaultPage();
        formato.setOrientation(PageFormat.LANDSCAPE);
        trabajo.setPrintable((g, hoja, indice) -> {
            if (indice >= equipos.size()) return Printable.NO_SUCH_PAGE;
            g.translate((int) hoja.getImageableX(), (int) hoja.getImageableY());
            Dibujo.pagina((Graphics2D) g, (int) hoja.getImageableWidth(), (int) hoja.getImageableHeight(),
                    equipos.get(indice), album.get(equipos.get(indice)));
            return Printable.PAGE_EXISTS;
        }, formato);
        if (trabajo.printDialog()) trabajo.print();
    }
}
