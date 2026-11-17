package archivos;

import entities.Jugador;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

final class DatosIniciales {

    static List<Jugador> crear() {
        String[][] equipos = {
            {"América", "Varonil", "#E8C300"}, {"Guadalajara", "Varonil", "#C8102E"},
            {"Cruz Azul", "Varonil", "#0033A0"}, {"Pumas", "Varonil", "#B8962E"},
            {"Toluca", "Varonil", "#D50032"}, {"América Femenil", "Femenil", "#E8C300"},
            {"Guadalajara Femenil", "Femenil", "#C8102E"}, {"Tigres Femenil", "Femenil", "#F28C00"},
            {"Pachuca Femenil", "Femenil", "#1C3F94"}, {"Monterrey Femenil", "Femenil", "#0B2545"}};
        String[] posiciones = {"Portero", "Defensa", "Defensa", "Medio", "Delantero", "Director Técnico"};
        String[] hombres = {"Luis", "Carlos", "Diego", "Jorge", "Andrés", "Miguel", "Raúl", "Héctor"};
        String[] mujeres = {"María", "Sofía", "Daniela", "Andrea", "Karla", "Fernanda", "Lucía", "Paola"};
        String[] apellidos = {"Hernández", "García", "Martínez", "López", "Sánchez", "Ramírez", "Torres", "Flores", "Vega"};

        List<Jugador> jugadores = new ArrayList<>();
        for (int e = 0; e < equipos.length; e++) {
            String equipo = equipos[e][0], division = equipos[e][1];
            Color color = Color.decode(equipos[e][2]);
            String escudo = escudo("escudo_" + e, color, equipo.substring(0, 3).toUpperCase());
            String foto = silueta("jugador_" + e, color);
            String[] nombres = division.equals("Varonil") ? hombres : mujeres;
            for (int p = 0; p < posiciones.length; p++) {
                String nombre = nombres[(e + p) % nombres.length] + " " + apellidos[(e * 2 + p) % apellidos.length];
                jugadores.add(new Jugador(equipo, nombre, posiciones[p], p + 1, "México",
                        (p == 5 ? 45 : 20) + (e + p * 3) % 15, division, foto, escudo));
            }
        }
        return jugadores;
    }

    private static String escudo(String nombre, Color color, String siglas) {
        BufferedImage imagen = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = lienzo(imagen);
        g.setColor(color);
        g.fillOval(10, 10, 280, 280);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(12));
        g.drawOval(10, 10, 280, 280);
        g.setFont(new Font("SansSerif", Font.BOLD, 90));
        g.drawString(siglas, 150 - g.getFontMetrics().stringWidth(siglas) / 2, 182);
        return guardar(imagen, nombre);
    }

    private static String silueta(String nombre, Color color) {
        BufferedImage imagen = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = lienzo(imagen);
        g.setColor(new Color(225, 230, 235));
        g.fillRect(0, 0, 300, 400);
        g.setColor(new Color(120, 125, 135));
        g.fillOval(95, 70, 110, 130);
        g.setColor(color);
        g.fillRoundRect(30, 215, 240, 260, 120, 120); 
        return guardar(imagen, nombre);
    }

    private static Graphics2D lienzo(BufferedImage imagen) {
        Graphics2D g = imagen.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g;
    }

    private static String guardar(BufferedImage imagen, String nombre) {
        try {
            File archivo = new File("imagenes", nombre + ".png");
            archivo.getParentFile().mkdirs();
            ImageIO.write(imagen, "png", archivo);
            return "imagenes/" + archivo.getName();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudieron crear las imágenes de ejemplo", e);
        }
    }
}
