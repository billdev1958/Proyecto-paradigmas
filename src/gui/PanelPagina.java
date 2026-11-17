package gui;

import entities.Jugador;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;

public class PanelPagina extends JPanel {
    private String equipo = "";
    private List<Jugador> jugadores = List.of();

    public void mostrar(String equipo, List<Jugador> jugadores) {
        this.equipo = equipo;
        this.jugadores = jugadores;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dibujo.pagina((Graphics2D) g, getWidth(), getHeight(), equipo, jugadores);
    }
}
