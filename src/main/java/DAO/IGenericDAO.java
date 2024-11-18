package DAO;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO <T, ID extends Serializable>{
    void update(T entity);
    void create(T entity);
    T get(ID id);
    void delete (ID id);
    List<T> findAll();
    public Class<T> getEntityClass();
}
