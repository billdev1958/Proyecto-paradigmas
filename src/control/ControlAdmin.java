package control;

import entities.Jugador;
import gui.VentanaAdmin;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

class ControlAdmin {
    final VentanaAdmin vista = new VentanaAdmin();
    private Jugador actual; 

    ControlAdmin(Controlador c) {
        Controlador.alPulsar(vista.elegirJugador, () -> elegirImagen(vista.imagenJugador));
        Controlador.alPulsar(vista.elegirEquipo, () -> elegirImagen(vista.imagenEquipo));

        Controlador.alPulsar(vista.agregar, () -> {
            Jugador nuevo = leer();
            c.agregar.ejecutar(nuevo);
            mostrar(c.consultar.ejecutar(nuevo.equipo(), nuevo.numero()));
            aviso("Jugador agregado");
        });
        Controlador.alPulsar(vista.consultar, () ->
            mostrar(c.consultar.ejecutar(vista.equipo.getText(), entero(vista.numero, "número"))));
        Controlador.alPulsar(vista.modificar, () -> {
            if (actual == null) throw new IllegalArgumentException("Primero consulta al jugador que quieres modificar");
            Jugador nuevo = leer();
            c.modificar.ejecutar(actual.equipo(), actual.numero(), nuevo);
            mostrar(c.consultar.ejecutar(nuevo.equipo(), nuevo.numero()));
            aviso("Jugador modificado");
        });
        Controlador.alPulsar(vista.eliminar, () -> {
            Jugador jugador = c.consultar.ejecutar(vista.equipo.getText(), entero(vista.numero, "número"));
            int respuesta = JOptionPane.showConfirmDialog(vista, "¿Eliminar a " + jugador.nombre() + " de "
                    + jugador.equipo() + "?", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) return;
            c.eliminar.ejecutar(jugador.equipo(), jugador.numero());
            limpiar();
            aviso("Jugador eliminado");
        });
        Controlador.alPulsar(vista.limpiar, this::limpiar);
        Controlador.alPulsar(vista.album, c::mostrarAlbum);
        Controlador.alPulsar(vista.salir, c::mostrarLogin);
    }

    private Jugador leer() {
        return new Jugador(vista.equipo.getText(), vista.nombre.getText(), (String) vista.posicion.getSelectedItem(),
                entero(vista.numero, "número"), vista.nacionalidad.getText(), entero(vista.edad, "edad"),
                (String) vista.division.getSelectedItem(), vista.imagenJugador.getText(), vista.imagenEquipo.getText());
    }

    private int entero(JTextField campo, String nombre) {
        try {
            return Integer.parseInt(campo.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El dato " + nombre + " debe ser un número entero");
        }
    }

    private void mostrar(Jugador j) {
        actual = j;
        vista.equipo.setText(j.equipo());
        vista.nombre.setText(j.nombre());
        vista.posicion.setSelectedItem(j.posicion());
        vista.numero.setText("" + j.numero());
        vista.nacionalidad.setText(j.nacionalidad());
        vista.edad.setText("" + j.edad());
        vista.division.setSelectedItem(j.division());
        vista.imagenJugador.setText(j.imagenJugador());
        vista.imagenEquipo.setText(j.imagenEquipo());
        vista.estampilla.mostrar(j);
    }

    private void limpiar() {
        actual = null;
        for (JTextField campo : new JTextField[] {vista.equipo, vista.nombre, vista.numero, vista.nacionalidad,
            vista.edad, vista.imagenJugador, vista.imagenEquipo}) campo.setText("");
        vista.estampilla.mostrar(null);
    }

    private void elegirImagen(JTextField campo) {
        JFileChooser selector = new JFileChooser();
        selector.setFileFilter(new FileNameExtensionFilter("Imágenes (png, jpg, gif)", "png", "jpg", "jpeg", "gif"));
        if (selector.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION)
            campo.setText(selector.getSelectedFile().getPath());
    }

    private void aviso(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje);
    }
}
