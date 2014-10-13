package sample.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anthonychen on 10/6/14.
 */
public abstract class AbstractDAO<T extends Serializable>{

    @Autowired
    SessionFactory sessionFactory;
    Class<T> clazz;

    public AbstractDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    public T find(String id) {
        return (T) openSession().get(clazz, id);
    }

    public List<T> findAll() {
        return openSession().createQuery("from " + clazz.getName()).list();
    }

    public void create(T entity) {
        openSession().persist(entity);
    }

    public void update(T entity) {
        openSession().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        openSession().delete(entity);
    }

    public void delete(String entityId) {
        T entity = find(entityId);
        delete(entity);
    }

    protected final Session openSession() {
        return sessionFactory.openSession();
    }
}
