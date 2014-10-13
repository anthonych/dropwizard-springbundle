package sample.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anthonychen on 10/9/14.
 */
public interface IGenericDAO<T extends Serializable> {

    public T find(String id);

    public List<T> findAll();

    public void create(T entity);

    public void update(T entity);

    public void delete(T entity);

    public void delete(String entityId);
}
