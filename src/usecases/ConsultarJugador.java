package usecases;

import entities.Jugador;

public class ConsultarJugador {
    private final RepositorioJugadores repositorio;

    public ConsultarJugador(RepositorioJugadores repositorio) {
        this.repositorio = repositorio;
    }

    public Jugador ejecutar(String equipo, int numero) {
        return repositorio.listar().stream()
                .filter(j -> j.es(equipo, numero))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe un jugador con ese equipo y número"));
    }
}
