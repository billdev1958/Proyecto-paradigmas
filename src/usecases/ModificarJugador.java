package usecases;

import entities.Jugador;

public class ModificarJugador {
    private final RepositorioJugadores repositorio;
    private final AlmacenImagenes imagenes;

    public ModificarJugador(RepositorioJugadores repositorio, AlmacenImagenes imagenes) {
        this.repositorio = repositorio;
        this.imagenes = imagenes;
    }

    public void ejecutar(String equipo, int numero, Jugador nuevo) {
        Jugador anterior = null;
        for (Jugador j : repositorio.listar()) {
            if (j.es(equipo, numero)) anterior = j;
            else if (j.es(nuevo.equipo(), nuevo.numero()))
                throw new IllegalArgumentException("Ya existe el número " + nuevo.numero() + " en " + nuevo.equipo());
        }
        if (anterior == null) throw new IllegalArgumentException("No existe un jugador con ese equipo y número");

        repositorio.actualizar(anterior.equipo(), anterior.numero(),
                nuevo.conImagenes(imagenes.guardar(nuevo.imagenJugador()), imagenes.guardar(nuevo.imagenEquipo())));
    }
}
