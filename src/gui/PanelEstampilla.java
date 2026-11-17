package gui;

import entities.Jugador;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class PanelEstampilla extends JPanel {
    private Jugador jugador;

    public PanelEstampilla() {
        setPreferredSize(new Dimension(250, 350));
        setOpaque(false);
    }

    public void mostrar(Jugador jugador) {
        this.jugador = jugador;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = Math.min(getWidth() - 10, (getHeight() - 10) * 5 / 7), h = w * 7 / 5;
        Dibujo.estampilla((Graphics2D) g, jugador, (getWidth() - w) / 2, (getHeight() - h) / 2, w, h);
    }
}
