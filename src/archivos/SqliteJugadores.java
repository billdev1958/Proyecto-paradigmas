package archivos;

import entities.Jugador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import usecases.RepositorioJugadores;

public class SqliteJugadores implements RepositorioJugadores {

    public SqliteJugadores() {
        try (Connection conexion = BaseDatos.conectar(); Statement sql = conexion.createStatement()) {
            sql.execute("""
                    CREATE TABLE IF NOT EXISTS jugadores (
                        equipo TEXT NOT NULL, nombre TEXT NOT NULL, posicion TEXT NOT NULL, numero INTEGER NOT NULL,
                        nacionalidad TEXT NOT NULL, edad INTEGER NOT NULL, division TEXT NOT NULL,
                        imagen_jugador TEXT NOT NULL, imagen_equipo TEXT NOT NULL,
                        PRIMARY KEY (equipo, numero))""");
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo abrir la base de datos: " + e.getMessage(), e);
        }
        if (listar().isEmpty()) DatosIniciales.crear().forEach(this::insertar);
    }

    @Override
    public List<Jugador> listar() {
        try (Connection conexion = BaseDatos.conectar(); Statement sql = conexion.createStatement();
                ResultSet fila = sql.executeQuery("SELECT * FROM jugadores ORDER BY rowid")) {
            List<Jugador> jugadores = new ArrayList<>();
            while (fila.next())
                jugadores.add(new Jugador(fila.getString("equipo"), fila.getString("nombre"), fila.getString("posicion"),
                        fila.getInt("numero"), fila.getString("nacionalidad"), fila.getInt("edad"),
                        fila.getString("division"), fila.getString("imagen_jugador"), fila.getString("imagen_equipo")));
            return jugadores;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron leer los jugadores: " + e.getMessage(), e);
        }
    }

    @Override
    public void insertar(Jugador j) {
        ejecutar("INSERT INTO jugadores VALUES (?,?,?,?,?,?,?,?,?)",
                j.equipo(), j.nombre(), j.posicion(), j.numero(), j.nacionalidad(), j.edad(), j.division(),
                j.imagenJugador(), j.imagenEquipo());
    }

    @Override
    public void actualizar(String equipo, int numero, Jugador j) {
        ejecutar("""
                UPDATE jugadores SET equipo = ?, nombre = ?, posicion = ?, numero = ?, nacionalidad = ?, edad = ?,
                    division = ?, imagen_jugador = ?, imagen_equipo = ?
                WHERE equipo = ? AND numero = ?""",
                j.equipo(), j.nombre(), j.posicion(), j.numero(), j.nacionalidad(), j.edad(), j.division(),
                j.imagenJugador(), j.imagenEquipo(), equipo, numero);
    }

    @Override
    public void eliminar(String equipo, int numero) {
        ejecutar("DELETE FROM jugadores WHERE equipo = ? AND numero = ?", equipo, numero);
    }

    private void ejecutar(String sql, Object... valores) {
        try (Connection conexion = BaseDatos.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            for (int i = 0; i < valores.length; i++) sentencia.setObject(i + 1, valores[i]);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo guardar en la base de datos: " + e.getMessage(), e);
        }
    }
}
