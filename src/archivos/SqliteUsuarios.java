package archivos;

import entities.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import usecases.RepositorioUsuarios;

public class SqliteUsuarios implements RepositorioUsuarios {

    public SqliteUsuarios() {
        try (Connection conexion = BaseDatos.conectar(); Statement sql = conexion.createStatement()) {
            sql.execute("CREATE TABLE IF NOT EXISTS usuarios (nombre TEXT PRIMARY KEY, clave TEXT NOT NULL, admin INTEGER NOT NULL)");
            sql.execute("INSERT OR IGNORE INTO usuarios VALUES ('admin', 'admin123', 1), ('usuario', 'usuario123', 0)");
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo abrir la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> listar() {
        try (Connection conexion = BaseDatos.conectar(); Statement sql = conexion.createStatement();
                ResultSet fila = sql.executeQuery("SELECT * FROM usuarios")) {
            List<Usuario> usuarios = new ArrayList<>();
            while (fila.next())
                usuarios.add(new Usuario(fila.getString("nombre"), fila.getString("clave"), fila.getBoolean("admin")));
            return usuarios;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron leer los usuarios: " + e.getMessage(), e);
        }
    }
}
