package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;

public class Icono implements Icon {
    private final String tipo;
    private final Color color;
    private final int tamano;

    public Icono(String tipo, Color color, int tamano) {
        this.tipo = tipo;
        this.color = color;
        this.tamano = tamano;
    }

    @Override
    public int getIconWidth() {
        return tamano;
    }

    @Override
    public int getIconHeight() {
        return tamano;
    }

    @Override
    public void paintIcon(Component c, Graphics grafico, int x, int y) {
        Graphics2D g = (Graphics2D) grafico.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.translate(x, y);
        g.scale(tamano / 16.0, tamano / 16.0);
        g.setColor(color);
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        switch (tipo) {
            case "mas" -> lineas(g, 8, 3, 8, 13, 3, 8, 13, 8);
            case "equis" -> lineas(g, 4, 4, 12, 12, 12, 4, 4, 12);
            case "lupa" -> {
                g.drawOval(2, 2, 8, 8);
                lineas(g, 9, 9, 14, 14);
            }
            case "lapiz" -> lineas(g, 3, 13, 12, 4, 3, 13, 7, 13, 3, 13, 3, 9);
            case "limpiar" -> {
                g.drawArc(3, 3, 10, 10, 90, 270);
                g.fillPolygon(new int[] {8, 8, 12}, new int[] {0, 6, 3}, 3);
            }
            case "album" -> {
                g.drawRect(1, 3, 6, 10);
                g.drawRect(9, 3, 6, 10);
            }
            case "salir" -> lineas(g, 7, 3, 3, 3, 3, 3, 3, 13, 3, 13, 7, 13, 7, 8, 14, 8, 11, 5, 14, 8, 11, 11, 14, 8);
            case "entrar" -> lineas(g, 2, 8, 13, 8, 9, 4, 13, 8, 9, 12, 13, 8);
            case "pdf" -> {
                g.drawRect(3, 2, 10, 12);
                lineas(g, 6, 6, 10, 6, 6, 10, 10, 10);
            }
            case "imprimir" -> {
                g.drawRect(2, 6, 12, 6);
                g.drawRect(5, 2, 6, 4);
                g.fillRect(5, 10, 6, 4);
            }
            case "descargar" -> lineas(g, 8, 2, 8, 10, 5, 7, 8, 10, 11, 7, 8, 10, 3, 13, 13, 13);
            case "izquierda" -> g.fillPolygon(new int[] {11, 4, 11}, new int[] {3, 8, 13}, 3);
            case "derecha" -> g.fillPolygon(new int[] {5, 12, 5}, new int[] {3, 8, 13}, 3);
            case "usuario" -> {
                g.fillOval(5, 2, 6, 6);
                g.fillArc(2, 9, 12, 12, 0, 180);
            }
            case "candado" -> {
                g.drawArc(5, 2, 6, 8, 0, 180);
                g.fillRect(3, 7, 10, 7);
            }
            case "carpeta" -> {
                g.fillRect(2, 5, 12, 8);
                g.fillRect(2, 3, 5, 2);
            }
            default -> g.fillOval(4, 4, 8, 8);
        }
        g.dispose();
    }

    private void lineas(Graphics2D g, int... p) {
        for (int i = 0; i < p.length; i += 4) g.drawLine(p[i], p[i + 1], p[i + 2], p[i + 3]);
    }
}
