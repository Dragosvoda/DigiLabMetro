package SIL.project;
import Interfaces.project.GenericDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SILDAO implements GenericDAO<SIL> {
    private Connection conn;
    List<SIL> silList = new ArrayList<>();

    public SILDAO(Connection conn) {
     this.conn = conn;
    }

    @Override
    public int delete(SIL entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete from sil where id = ?;");
        stmt.setLong(1,entity.getId());
        return stmt.executeUpdate();
    }
    
    public int stergeDupaNume(String nume) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete from sil where nume = ?;");
        stmt.setString(1, nume);
        return stmt.executeUpdate();
    }

    @Override
    public Optional<SIL> get(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Select * From sil where id = ?;");
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            SIL sil = new SIL(
            rs.getInt("id"),
            rs.getString("nume"),
            rs.getString("tip"),
            rs.getString("descriere"));
            return Optional.of(sil);
        } else {
            return Optional.empty();
        }
    }
    
    public SIL getSilDupaNume(String nume) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Select * from sil where nume = ?;");
        stmt.setString(1, nume);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            SIL sil = new SIL(
            rs.getInt("id"),
            rs.getString("nume"),
            rs.getString("tip"),
            rs.getString("descriere"));
            return sil;
        }
        else {
            return null;
        }  
    } 

    @Override
    public List<SIL> getAll() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sil;");
        while(rs.next()){
            SIL sil = new SIL(
            rs.getInt("id"),
            rs.getString("tip"),
            rs.getString("nume"),
            rs.getString("descriere"));
            silList.add(sil);
        }
        return silList;
    }

    @Override
    public SIL save(SIL entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO sil (nume, tip, descriere)\n" +
                                                        "VALUES (?, ?, ?);", new String[]{"id"});
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getTip());
        stmt.setString(3, entity.getDescriere());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
           entity.setId(rs.getInt(1));
        }
           return entity;
    }

    @Override
    public int update(SIL entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE sil SET nume = ?, tip = ?, descriere = ? WHERE id = ?;");
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getTip());
        stmt.setString(3, entity.getDescriere());
        stmt.setLong(4, entity.getId());
        return stmt.executeUpdate();
    }
}