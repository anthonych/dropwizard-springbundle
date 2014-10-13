package sample.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sample.core.User;
import sample.dao.UserDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by anthonychen on 10/8/14.
 */
@Path("/user")
@Component
public class UserResource {

    @Autowired
    private UserDAO userDAO;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUser() {

        return userDAO.findAll();
    }
}
