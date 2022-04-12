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

public class FisierDAO implements GenericDAO<Fisier> {
    private Connection conn;
    List<Fisier> fisiere = new ArrayList<>();

    public FisierDAO(Connection conn) {
     this.conn = conn;
    }

    @Override
    public int delete(Fisier entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete from fisiere where id = ?;");
        stmt.setLong(1, entity.getId());
        return stmt.executeUpdate();
    }
    
    public int deleteBySil(int sil_Id) throws SQLException{
            PreparedStatement stmt = conn.prepareStatement("Delete from fisiere where silLink = ?;");
            stmt.setLong(1, sil_Id);
            return stmt.executeUpdate();
        }
    
    public int deleteByPath(String path) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete from fisiere where locatie = ?;");
        stmt.setString(1, path);
        return stmt.executeUpdate();
    }

    @Override
    public Optional<Fisier> get(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Select * From fisiere where id = ?;");
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            Fisier f = new Fisier(
            rs.getInt("id"),
            rs.getString("locatie"),
            rs.getInt("silLink"),
            rs.getInt("raportLink"));
            return Optional.of(f);
        } else {
            return Optional.empty();
        }
    }
    public List<Fisier> getAllBySil(int silLink) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fisiere where silLink = ?;");
        stmt.setLong(1, silLink);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            Fisier f = new Fisier(
            rs.getInt("id"),
            rs.getString("locatie"));
            rs.getInt("silLink");
            rs.getInt("RaportLink");
            fisiere.add(f);
        }
           return fisiere;
    }

    @Override
    public List<Fisier> getAll() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM fisiere;");
        while(rs.next()){
            Fisier f = new Fisier(
            rs.getInt("id"),
            rs.getString("locatie"));
            rs.getInt("silLink");
            rs.getInt("RaportLink");
            fisiere.add(f);
        }
           return fisiere;
    }

    @Override
    public Fisier save(Fisier entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO fisiere (locatie, silLink, raportLink)\n" + 
                                                       "VALUES (?, ?, ?);", new String[]{"id"});
        stmt.setString(1, entity.getLocatie());
        stmt.setInt(2, entity.getSilLink());
        stmt.setInt(3, entity.getRaportLink());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
           entity.setId(rs.getInt(1));
        }
        return entity;
    }
    
    public Fisier saveNou(Fisier entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO fisiere (locatie, silLink, raportLink)\n" + 
                                                       "VALUES (?, ?, ?);", new String[]{"id"});
        stmt.setString(1, entity.getLocatie());
        stmt.setInt(2, entity.getSilLink());
        stmt.setInt(3, entity.getRaportLink());
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if(rs.next()){
            entity.setId(rs.getInt(1));
        }
        return entity;
    }

    @Override
    public int update(Fisier entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE fisiere SET locatie = ?, silLink = ?, " +
                                                       "raportLink = ? WHERE id = ?;");
        stmt.setString(1, entity.getLocatie());
        stmt.setInt(2, entity.getSilLink());
        stmt.setInt(3, entity.getRaportLink());
        stmt.setLong(4, entity.getId());
        return stmt.executeUpdate();
    }
    
    public int updateRaportLink(Raport entity) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE fisiere SET fisiere.Raportlink = " +
                                                       "(SELECT rapoarte.id FROM rapoarte WHERE rapoarte.id = ?);");
        stmt.setLong(1, entity.getId());
        return stmt.executeUpdate();
    }
}