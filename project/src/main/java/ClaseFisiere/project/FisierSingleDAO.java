package ClaseFisiere.project;
import Interfaces.project.GenericDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FisierSingleDAO implements GenericDAO<FisierSingle> {
    private Connection conn;
    List<FisierSingle> fisiere = new ArrayList<>();

    public FisierSingleDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int delete(FisierSingle entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete from fisieresingle where id = ?");
        stmt.setLong(1, entity.getId());
        return stmt.executeUpdate();
    }
    
    public int deleteByPath(String path) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete from fisieresingle where locatie = ?");
        stmt.setString(1, path);
        return stmt.executeUpdate();
    }

    @Override
    public Optional<FisierSingle> get(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Select * From fisieresingle where id = ?");
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            FisierSingle f = new FisierSingle(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("tip"),
                rs.getString("descriere"),
                rs.getString("locatie"));
            return Optional.of(f);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<FisierSingle> getAll() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM fisieresingle;");
        while(rs.next()){
            FisierSingle f = new FisierSingle(
            rs.getInt("id"),
            rs.getString("nume"),
            rs.getString("tip"),
            rs.getString("descriere"),
            rs.getString("locatie"));
            fisiere.add(f);
        }
           return fisiere;
    }

    @Override
    public FisierSingle save(FisierSingle entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO fisieresingle (nume, tip, descriere, locatie)\n" +
                                                        "VALUES (?, ?, ?, ?);",new String[]{"id"});
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getTip());
        stmt.setString(3, entity.getDescriere());
        stmt.setString(4, entity.getLocatie());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
           entity.setId(rs.getInt(1));
        }
           return entity;
    }
    
    public FisierSingle saveNou(FisierSingle entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO fisieresingle (nume, tip, descriere, locatie)\n" +
                                                        "VALUES (?, ?, ?, ?);",new String[]{"id"});
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getTip());
        stmt.setString(3, entity.getDescriere());
        stmt.setString(4, entity.getLocatie());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
            entity.setId(rs.getInt(1));
        }
        return entity;
    }

    @Override
    public int update(FisierSingle entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE fisieresingle SET nume = ?, tip = ?, descriere = ?," +
                                                       "locatie = ? WHERE id = ? ");
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getTip());
        stmt.setString(3, entity.getDescriere());
        stmt.setString(4, entity.getLocatie());
        return stmt.executeUpdate();
    }
}
