package DataBaseConn.project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConn {
    private Connection connection;
    public DataBaseConn(String connectionUrl, String username, String password) {
        try {
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
            return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}