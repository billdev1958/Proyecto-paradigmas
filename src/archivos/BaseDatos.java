package archivos;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class BaseDatos {
    static Connection conectar() throws SQLException {
        new File("datos").mkdirs();
        return DriverManager.getConnection("jdbc:sqlite:datos/album.db");
    }
}
