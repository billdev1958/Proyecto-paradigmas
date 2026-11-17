package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public final class Estilo {
    public static final Color VERDE = new Color(0, 104, 71), VERDE_OSCURO = new Color(0, 45, 30);
    public static final Color VERDE_CLARO = new Color(46, 160, 67), AZUL = new Color(31, 111, 235);
    public static final Color NARANJA = new Color(219, 109, 40), ROJO = new Color(207, 34, 46);
    public static final Color GRIS = new Color(87, 96, 106);

    public static final Font TITULO = new Font("SansSerif", Font.BOLD, 24);
    public static final Font NEGRITA = new Font("SansSerif", Font.BOLD, 14);
    public static final Font TEXTO = new Font("SansSerif", Font.PLAIN, 14);

    private Estilo() {
    }

    public static void boton(JButton boton, String icono, Color color) {
        boton.setIcon(new Icono(icono, Color.WHITE, 16));
        boton.setFont(NEGRITA);
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static JLabel etiqueta(String texto, String icono) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(NEGRITA);
        etiqueta.setForeground(Color.WHITE);
        if (icono != null) etiqueta.setIcon(new Icono(icono, Color.WHITE, 16));
        return etiqueta;
    }

    public static <T extends JComponent> T campo(T campo) {
        campo.setFont(TEXTO);
        return campo;
    }
}
