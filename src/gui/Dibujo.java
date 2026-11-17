package gui;

import entities.Jugador;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/** Dibuja la estampilla (figura 2) y la hoja del álbum (figura 1). Sirve igual para pantalla, PDF e impresora. */
public final class Dibujo {
    private static final Color VERDE = Estilo.VERDE, VERDE_OSCURO = Estilo.VERDE_OSCURO;
    private static final Map<String, Image> IMAGENES = new HashMap<>();

    private Dibujo() {
    }

    public static Image imagen(String ruta) {
        if (ruta == null || ruta.isBlank()) return null;
        return IMAGENES.computeIfAbsent(ruta, r -> {
            try {
                return ImageIO.read(new File(r));
            } catch (IOException e) {
                return null;
            }
        });
    }

    /** Hoja de un equipo: encabezado con escudo y nombre, y sus estampillas en cuadrícula. */
    public static void pagina(Graphics2D g, int ancho, int alto, String equipo, List<Jugador> jugadores) {
        suavizar(g);
        g.setPaint(new GradientPaint(0, 0, VERDE, 0, alto, VERDE_OSCURO));
        g.fillRect(0, 0, ancho, alto);
        if (jugadores.isEmpty()) return;

        int margen = ancho / 40, encabezado = alto / 6;
        Image escudo = imagen(jugadores.get(0).imagenEquipo());
        if (escudo != null) g.drawImage(escudo, margen, margen, encabezado - margen, encabezado - margen, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, encabezado * 2 / 5));
        g.drawString(equipo.toUpperCase(), margen + encabezado, margen + encabezado * 2 / 5);
        g.setFont(new Font("SansSerif", Font.PLAIN, encabezado / 6));
        g.drawString("Liga " + jugadores.get(0).division() + "  ·  " + jugadores.size() + " estampillas",
                margen + encabezado, margen + encabezado * 2 / 3);

        // se elige el número de columnas con el que las estampillas quedan más grandes
        int n = jugadores.size(), arriba = encabezado + margen, columnas = 1;
        double anchoEstampa = 0;
        for (int c = 1; c <= n; c++) {
            int filas = (n + c - 1) / c;
            double a = Math.min((ancho - 2.0 * margen) / c * 0.92, (alto - arriba - margen) / (double) filas * 0.92 * 5 / 7);
            if (a > anchoEstampa) {
                anchoEstampa = a;
                columnas = c;
            }
        }
        int filas = (n + columnas - 1) / columnas;
        double celdaAncho = (ancho - 2.0 * margen) / columnas, celdaAlto = (alto - arriba - margen) / (double) filas;
        int w = (int) anchoEstampa, h = w * 7 / 5;
        for (int i = 0; i < n; i++) {
            int x = (int) (margen + (i % columnas) * celdaAncho + (celdaAncho - w) / 2);
            int y = (int) (arriba + (i / columnas) * celdaAlto + (celdaAlto - h) / 2);
            estampilla(g, jugadores.get(i), x, y, w, h);
        }
    }

    /** Tarjeta de un jugador o integrante del cuerpo técnico. */
    public static void estampilla(Graphics2D g, Jugador j, int x, int y, int w, int h) {
        suavizar(g);
        int m = Math.max(2, w / 25);
        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, w, h, m * 3, m * 3);

        int fotoAncho = w - 2 * m, fotoAlto = h * 70 / 100;
        g.setColor(new Color(225, 230, 235));
        g.fillRect(x + m, y + m, fotoAncho, fotoAlto);
        if (j != null) {
            recortada(g, imagen(j.imagenJugador()), x + m, y + m, fotoAncho, fotoAlto);
            Image escudo = imagen(j.imagenEquipo());
            if (escudo != null) g.drawImage(escudo, x + 2 * m, y + 2 * m, w / 4, w / 4, null);

            int d = w / 5; // círculo con el número
            g.setColor(VERDE);
            g.fillOval(x + w - 2 * m - d, y + 2 * m, d, d);
            g.setColor(Color.WHITE);
            centrado(g, "" + j.numero(), x + w - 2 * m - d / 2, y + 2 * m + d * 7 / 10, d, d / 2, Font.BOLD);

            g.setColor(Color.BLACK);
            centrado(g, j.nombre().toUpperCase(), x + w / 2, y + h * 81 / 100, fotoAncho, h * 7 / 100, Font.BOLD);
            g.setColor(Color.DARK_GRAY);
            centrado(g, j.posicion() + " · " + j.division(), x + w / 2, y + h * 88 / 100, fotoAncho, h / 20, Font.PLAIN);
            centrado(g, j.nacionalidad() + " · " + j.edad() + " años", x + w / 2, y + h * 95 / 100, fotoAncho, h / 20, Font.PLAIN);
        }
        g.setColor(VERDE);
        g.setStroke(new BasicStroke(Math.max(1f, w / 80f)));
        g.drawRoundRect(x, y, w, h, m * 3, m * 3);
    }

    /** Dibuja la imagen llenando el recuadro sin deformarla (recorta lo que sobra). */
    private static void recortada(Graphics2D g, Image imagen, int x, int y, int w, int h) {
        if (imagen == null) return;
        int iw = imagen.getWidth(null), ih = imagen.getHeight(null);
        double escala = Math.max((double) w / iw, (double) h / ih);
        int sw = (int) (w / escala), sh = (int) (h / escala), sx = (iw - sw) / 2, sy = (ih - sh) / 2;
        g.drawImage(imagen, x, y, x + w, y + h, sx, sy, sx + sw, sy + sh, null);
    }

    /** Texto centrado en cx; si no cabe en el ancho, se reduce la letra. */
    private static void centrado(Graphics2D g, String texto, int cx, int y, int ancho, int tamano, int estilo) {
        Font fuente = new Font("SansSerif", estilo, Math.max(tamano, 5));
        while (fuente.getSize() > 5 && g.getFontMetrics(fuente).stringWidth(texto) > ancho)
            fuente = fuente.deriveFont(fuente.getSize() - 1f);
        g.setFont(fuente);
        g.drawString(texto, cx - g.getFontMetrics().stringWidth(texto) / 2, y);
    }

    private static void suavizar(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }
}
