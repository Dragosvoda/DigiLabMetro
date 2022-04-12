package ClaseFisiere.project;
import Interfaces.project.GenericDAO;

import SIL.project.SIL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RaportDAO implements GenericDAO<Raport> {
    private Connection conn;
    List<Raport> rapoarte = new ArrayList<>();
    
    public RaportDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int delete(Raport entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete from rapoarte where id = ?;");
        stmt.setLong(1, entity.getId());
        return stmt.executeUpdate();
        
    }
    
    public int deleteBySil(int sil_Id) throws SQLException{
            PreparedStatement stmt = conn.prepareStatement("Delete from rapoarte where SIL = ?;");
            stmt.setLong(1, sil_Id);
            return stmt.executeUpdate();
        }

    @Override
    public Optional get(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Select * from rapoarte where id = ?;");
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            Raport r = new Raport(
            rs.getInt("id"),
            rs.getString("nume"),
            rs.getString("tipul"),
            rs.getString("descriere"),
            rs.getInt("SIL"));
            return Optional.of(r);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Raport> getAll() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM rapoarte;");
        while(rs.next()){
            Raport r = new Raport(
            rs.getInt("id"),
            rs.getString("nume"),
            rs.getString("tipul"),
            rs.getString("descriere"),
            rs.getInt("SIL"));
            rapoarte.add(r);
        }
        return rapoarte;
    }
    
    public List<Raport> getAllBySIL(int silLink) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from rapoarte where SIL = ?;");
        stmt.setLong(1, silLink);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            Raport r = new Raport(
            rs.getInt("id"),
            rs.getString("nume"),
            rs.getString("tipul"),
            rs.getString("descriere"),
            rs.getInt("SIL"));
            rapoarte.add(r);
        }
        return rapoarte;
    }

    @Override
    public Raport save(Raport entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO rapoarte (nume, tipul, descriere, SIL)\n" +
                                                        "VALUES (?, ?, ?, ?);", new String[]{"id"});
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getTipul());
        stmt.setString(3, entity.getDescriere());
        stmt.setInt(4, entity.getSIL());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
           entity.setId(rs.getInt(1));
        }
        return entity;
    }

    @Override
    public int update(Raport entity) throws SQLException {
        PreparedStatement stmt = 
        conn.prepareStatement("UPDATE rapoarte SET nume = ?, tipul = ?, descriere = ?, SIL = ? WHERE id = ?;");
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getTipul());
        stmt.setString(3, entity.getDescriere());
        stmt.setInt(4, entity.getSIL());
        stmt.setLong(5, entity.getId());
        return stmt.executeUpdate();
    }
    
    public int updateBySIL(Raport entity) throws SQLException {
        PreparedStatement stmt = 
        conn.prepareStatement("UPDATE rapoarte SET nume = ?, tipul = ?, descriere = ? WHERE SIL = ?;");
        stmt.setString(1, entity.getNume());
        stmt.setString(2, entity.getTipul());
        stmt.setString(3, entity.getDescriere());
        stmt.setInt(4, entity.getSIL());
        return stmt.executeUpdate();
    }
}