package usecases;

import entities.Jugador;
import java.util.List;

public class AgregarJugador {
    /** 26 jugadores + 6 de cuerpo técnico. */
    public static final int MAXIMO_POR_EQUIPO = 32;

    private final RepositorioJugadores repositorio;
    private final AlmacenImagenes imagenes;

    public AgregarJugador(RepositorioJugadores repositorio, AlmacenImagenes imagenes) {
        this.repositorio = repositorio;
        this.imagenes = imagenes;
    }

    public void ejecutar(Jugador nuevo) {
        List<Jugador> delEquipo = repositorio.listar().stream().filter(j -> j.equipo().equalsIgnoreCase(nuevo.equipo())).toList();

        if (delEquipo.stream().anyMatch(j -> j.numero() == nuevo.numero()))
            throw new IllegalArgumentException("Ya existe el número " + nuevo.numero() + " en " + nuevo.equipo());
        if (delEquipo.stream().anyMatch(j -> j.nombre().equalsIgnoreCase(nuevo.nombre())))
            throw new IllegalArgumentException(nuevo.nombre() + " ya está registrado en " + nuevo.equipo());
        if (delEquipo.size() >= MAXIMO_POR_EQUIPO)
            throw new IllegalArgumentException("El equipo ya tiene el máximo de " + MAXIMO_POR_EQUIPO + " integrantes");

        repositorio.insertar(nuevo.conImagenes(imagenes.guardar(nuevo.imagenJugador()), imagenes.guardar(nuevo.imagenEquipo())));
    }
}
