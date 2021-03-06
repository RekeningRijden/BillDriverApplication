package main.api;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.domain.User;
import main.service.UserService;

/**
 * @author maikel
 */
@Path("/users")
@Stateless
public class UserApi {

    @Inject
    private UserService userService;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(User user) {
        return userService.registerUser(user);
    }
}
