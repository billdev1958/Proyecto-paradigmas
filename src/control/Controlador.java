package control;

import entities.Usuario;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import usecases.AgregarJugador;
import usecases.AlmacenImagenes;
import usecases.ConsultarJugador;
import usecases.EliminarJugador;
import usecases.ExportadorPdf;
import usecases.IniciarSesion;
import usecases.ModificarJugador;
import usecases.RepositorioJugadores;
import usecases.RepositorioUsuarios;
import usecases.VerAlbum;

public class Controlador {
    final IniciarSesion iniciarSesion;
    final AgregarJugador agregar;
    final ConsultarJugador consultar;
    final ModificarJugador modificar;
    final EliminarJugador eliminar;
    final VerAlbum verAlbum;
    final ExportadorPdf pdf;

    Usuario usuario;
    private JFrame ventana;

    public Controlador(RepositorioJugadores jugadores, RepositorioUsuarios usuarios, AlmacenImagenes imagenes,
            ExportadorPdf pdf) {
        this.iniciarSesion = new IniciarSesion(usuarios);
        this.agregar = new AgregarJugador(jugadores, imagenes);
        this.consultar = new ConsultarJugador(jugadores);
        this.modificar = new ModificarJugador(jugadores, imagenes);
        this.eliminar = new EliminarJugador(jugadores);
        this.verAlbum = new VerAlbum(jugadores);
        this.pdf = pdf;
        mostrarLogin();
    }

    void mostrarLogin() {
        usuario = null;
        cambiar(new ControlLogin(this).vista);
    }

    void mostrarAdmin() {
        cambiar(new ControlAdmin(this).vista);
    }

    void mostrarAlbum() {
        cambiar(new ControlAlbum(this).vista);
    }

    private void cambiar(JFrame nueva) {
        nueva.setLocationRelativeTo(null);
        nueva.setVisible(true);
        if (ventana != null) ventana.dispose();
        ventana = nueva;
    }

    interface Accion {
        void ejecutar() throws Exception;
    }

    static void alPulsar(AbstractButton boton, Accion accion) {
        boton.addActionListener(evento -> {
            try {
                accion.ejecutar();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(boton.getTopLevelAncestor(), e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
