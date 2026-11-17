package usecases;

import entities.Usuario;

public class IniciarSesion {
    private final RepositorioUsuarios repositorio;

    public IniciarSesion(RepositorioUsuarios repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario ejecutar(String nombre, String clave) {
        return repositorio.listar().stream()
                .filter(u -> u.nombre().equals(nombre.trim()) && u.clave().equals(clave))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña incorrectos"));
    }
}
