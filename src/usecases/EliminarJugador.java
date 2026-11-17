package usecases;

import entities.Jugador;

public class EliminarJugador {
    private final RepositorioJugadores repositorio;

    public EliminarJugador(RepositorioJugadores repositorio) {
        this.repositorio = repositorio;
    }

    public void ejecutar(String equipo, int numero) {
        Jugador jugador = repositorio.listar().stream()
                .filter(j -> j.es(equipo, numero))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe un jugador con ese equipo y número"));
        repositorio.eliminar(jugador.equipo(), jugador.numero());
    }
}
