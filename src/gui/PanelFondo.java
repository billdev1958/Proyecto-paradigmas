package gui;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
    public PanelFondo(LayoutManager distribucion) {
        super(distribucion);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(new GradientPaint(0, 0, Estilo.VERDE, 0, getHeight(), Estilo.VERDE_OSCURO));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
