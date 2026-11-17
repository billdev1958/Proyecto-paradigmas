package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame {
    public final JTextField usuario = Estilo.campo(new JTextField(15));
    public final JPasswordField clave = Estilo.campo(new JPasswordField(15));
    public final JButton entrar = new JButton("Entrar");

    public VentanaLogin() {
        super("Album - Acceso");
        JLabel titulo = new JLabel("Álbum de estampillas", new Icono("album", Color.WHITE, 28), SwingConstants.CENTER);
        titulo.setFont(Estilo.TITULO);
        titulo.setForeground(Color.WHITE);
        titulo.setIconTextGap(10);
        Estilo.boton(entrar, "entrar", Estilo.AZUL);

        JPanel campos = new JPanel(new GridLayout(0, 2, 8, 10));
        campos.setOpaque(false);
        campos.add(Estilo.etiqueta("Usuario:", "usuario"));
        campos.add(usuario);
        campos.add(Estilo.etiqueta("Contraseña:", "candado"));
        campos.add(clave);

        JPanel contenido = new PanelFondo(new BorderLayout(10, 18));
        contenido.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(campos, BorderLayout.CENTER);
        contenido.add(entrar, BorderLayout.SOUTH);

        setContentPane(contenido);
        getRootPane().setDefaultButton(entrar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }
}
