package Users.project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersDAO {
    private Connection conn;
    protected String[] numeUtilizatori;

    public UsersDAO(Connection conn) {
        this.conn = conn;
    }
    
    public Map<String, String> getAll() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Utilizatori;");
        Map<String, String> utilizatori = new HashMap<>();
        while (rs.next()) {
            Users u = new Users(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("parola"));
            utilizatori.put(rs.getString("nume"), rs.getString("parola"));
        }
        return utilizatori;
    }

    public  ArrayList<String> getNames() throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Utilizatori;");
        while (rs.next()) {
            names.add(rs.getString("nume"));
        }
        return names;
    }

    public Users save(Users entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Utilizatori (nume, parola)\n"
                + "VALUES (?, ?);", new String[]{"id"});
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getParola());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            entity.setId(rs.getInt(1));
        }
        return entity;
    }

    public int update(Users entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE Utilizatori SET nume = ? , parola = ? WHERE id = ?;");
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getParola());
        return stmt.executeUpdate();
    }
        
    public int updateParolaByName(String nume, String parola) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE Utilizatori SET parola = ? WHERE nume = ?;");
        stmt.setString(1, parola);
        stmt.setString(2, nume);
        return stmt.executeUpdate();
    }

    public int delete(Users entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete from Utilizatori where nume = ?;");
        stmt.setString(1, entity.getNume());
        return stmt.executeUpdate();
    }
}