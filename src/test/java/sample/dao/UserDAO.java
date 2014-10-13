package sample.dao;

import sample.core.User;

import java.util.List;

/**
 * Created by anthonychen on 10/7/14.
 */
public interface UserDAO extends IGenericDAO<User>{

    public User findByName(String name);

}
