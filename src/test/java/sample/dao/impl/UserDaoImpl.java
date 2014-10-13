package sample.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import sample.core.User;
import sample.dao.UserDAO;

/**
 * Created by anthonychen on 10/7/14.
 */
@Repository("userDAO")
public class UserDaoImpl extends AbstractDAO<User> implements UserDAO {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByName(String name) {
        Query q = openSession().createQuery("from User u where u.name = :name");

        return (User) q.uniqueResult();
    }
}
