package control;

import gui.VentanaLogin;

class ControlLogin {
    final VentanaLogin vista = new VentanaLogin();

    ControlLogin(Controlador c) {
        Controlador.alPulsar(vista.entrar, () -> {
            c.usuario = c.iniciarSesion.ejecutar(vista.usuario.getText(), new String(vista.clave.getPassword()));
            if (c.usuario.admin()) c.mostrarAdmin();
            else c.mostrarAlbum();
        });
    }
}
