package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaAdmin extends JFrame {
    public final JTextField equipo = new JTextField(20);
    public final JTextField nombre = new JTextField();
    public final JComboBox<String> posicion = new JComboBox<>(new String[] {"Portero", "Defensa", "Medio",
        "Delantero", "Director Técnico", "Auxiliar Técnico", "Preparador Físico", "Médico"});
    public final JTextField numero = new JTextField();
    public final JTextField nacionalidad = new JTextField();
    public final JTextField edad = new JTextField();
    public final JComboBox<String> division = new JComboBox<>(new String[] {"Varonil", "Femenil"});
    public final JTextField imagenJugador = new JTextField();
    public final JTextField imagenEquipo = new JTextField();
    public final JButton elegirJugador = new JButton("Elegir");
    public final JButton elegirEquipo = new JButton("Elegir");

    public final JButton agregar = new JButton("Agregar");
    public final JButton consultar = new JButton("Consultar");
    public final JButton modificar = new JButton("Modificar");
    public final JButton eliminar = new JButton("Eliminar");
    public final JButton limpiar = new JButton("Limpiar");
    public final JButton album = new JButton("Ver álbum");
    public final JButton salir = new JButton("Cerrar sesión");

    public final PanelEstampilla estampilla = new PanelEstampilla();

    public VentanaAdmin() {
        super("Album - Administrador");
        imagenJugador.setEditable(false);
        imagenEquipo.setEditable(false);

        Estilo.boton(elegirJugador, "carpeta", Estilo.GRIS);
        Estilo.boton(elegirEquipo, "carpeta", Estilo.GRIS);
        Estilo.boton(agregar, "mas", Estilo.VERDE_CLARO);
        Estilo.boton(consultar, "lupa", Estilo.AZUL);
        Estilo.boton(modificar, "lapiz", Estilo.NARANJA);
        Estilo.boton(eliminar, "equis", Estilo.ROJO);
        Estilo.boton(limpiar, "limpiar", Estilo.GRIS);
        Estilo.boton(album, "album", Estilo.GRIS);
        Estilo.boton(salir, "salir", Estilo.GRIS);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.setOpaque(false);
        fila(formulario, "Nombre del equipo:", equipo);
        fila(formulario, "Número:", numero);
        fila(formulario, "Nombre del jugador / cuerpo técnico:", nombre);
        fila(formulario, "Posición:", posicion);
        fila(formulario, "Nacionalidad:", nacionalidad);
        fila(formulario, "Edad:", edad);
        fila(formulario, "División:", division);
        fila(formulario, "Imagen del jugador:", conBoton(imagenJugador, elegirJugador));
        fila(formulario, "Imagen del equipo (escudo):", conBoton(imagenEquipo, elegirEquipo));

        JPanel botones = new JPanel();
        botones.setOpaque(false);
        for (JButton boton : new JButton[] {agregar, consultar, modificar, eliminar, limpiar, album, salir})
            botones.add(boton);

        JLabel titulo = Estilo.etiqueta("Administración de jugadores", "usuario");
        titulo.setFont(Estilo.TITULO);
        JLabel ayuda = Estilo.etiqueta("Para consultar, modificar o eliminar: escribe equipo y número, y pulsa Consultar.", null);
        ayuda.setFont(Estilo.TEXTO.deriveFont(Font.ITALIC));
        JPanel encabezado = new JPanel(new GridLayout(0, 1, 0, 6));
        encabezado.setOpaque(false);
        encabezado.add(titulo);
        encabezado.add(ayuda);

        JPanel contenido = new PanelFondo(new BorderLayout(15, 15));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        contenido.add(encabezado, BorderLayout.NORTH);
        contenido.add(formulario, BorderLayout.CENTER);
        contenido.add(estampilla, BorderLayout.EAST);
        contenido.add(botones, BorderLayout.SOUTH);

        setContentPane(contenido);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private void fila(JPanel formulario, String etiqueta, JComponent campo) {
        formulario.add(Estilo.etiqueta(etiqueta, null));
        formulario.add(Estilo.campo(campo));
    }

    private JPanel conBoton(JTextField campo, JButton boton) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setOpaque(false);
        panel.add(Estilo.campo(campo), BorderLayout.CENTER);
        panel.add(boton, BorderLayout.EAST);
        return panel;
    }
}
