package Interfaces.project;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {
    Optional<T> get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    T save(T entity) throws SQLException;
    int update(T t) throws SQLException;
    int delete(T t) throws SQLException;
}