package main;

import archivos.ArchivoImagenes;
import archivos.SqliteJugadores;
import archivos.ArchivoPdf;
import archivos.SqliteUsuarios;
import control.Controlador;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Controlador(
                new SqliteJugadores(), new SqliteUsuarios(), new ArchivoImagenes(), new ArchivoPdf()));
    }
}
