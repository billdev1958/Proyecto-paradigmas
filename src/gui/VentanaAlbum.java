package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaAlbum extends JFrame {
    public final JComboBox<String> equipos = Estilo.campo(new JComboBox<>());
    public final JButton anterior = new JButton("Anterior");
    public final JButton siguiente = new JButton("Siguiente");
    public final JButton verPdf = new JButton("Ver PDF");
    public final JButton imprimir = new JButton("Imprimir");
    public final JButton descargar = new JButton("Descargar PDF");
    public final JButton salir = new JButton("Cerrar sesión");
    public final PanelPagina pagina = new PanelPagina();

    public VentanaAlbum() {
        super("Album");
        Estilo.boton(anterior, "izquierda", Estilo.GRIS);
        Estilo.boton(siguiente, "derecha", Estilo.GRIS);
        Estilo.boton(verPdf, "pdf", Estilo.AZUL);
        Estilo.boton(imprimir, "imprimir", Estilo.AZUL);
        Estilo.boton(descargar, "descargar", Estilo.VERDE_CLARO);
        Estilo.boton(salir, "salir", Estilo.ROJO);

        JPanel barra = new JPanel();
        barra.setBackground(Estilo.VERDE_OSCURO);
        barra.add(Estilo.etiqueta("Equipo:", "album"));
        barra.add(equipos);
        for (JButton boton : new JButton[] {anterior, siguiente, verPdf, imprimir, descargar, salir}) barra.add(boton);

        pagina.setPreferredSize(new Dimension(990, 765));
        add(barra, BorderLayout.NORTH);
        add(pagina, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
}
