/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import main.domain.User;

/**
 *
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
    public User addUser(User user){
        User newUser = user;
        newUser.setUsername(user.getEmail());
        newUser.setPassword(user.getEmail().substring(user.getEmail().indexOf("@")));
        return userService.create(newUser);
    }
}
