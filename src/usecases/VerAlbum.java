package usecases;

import entities.Jugador;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VerAlbum {
    private final RepositorioJugadores repositorio;

    public VerAlbum(RepositorioJugadores repositorio) {
        this.repositorio = repositorio;
    }

    public Map<String, List<Jugador>> ejecutar() {
        return repositorio.listar().stream()
                .sorted(Comparator.comparing(Jugador::division).reversed()
                        .thenComparing(Jugador::equipo)
                        .thenComparing(Jugador::numero))
                .collect(Collectors.groupingBy(Jugador::equipo, LinkedHashMap::new, Collectors.toList()));
    }
}
