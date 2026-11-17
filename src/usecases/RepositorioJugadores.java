package usecases;

import entities.Jugador;
import java.util.List;

public interface RepositorioJugadores {
    List<Jugador> listar();

    void insertar(Jugador jugador);

    void actualizar(String equipo, int numero, Jugador jugador);

    void eliminar(String equipo, int numero);
}
