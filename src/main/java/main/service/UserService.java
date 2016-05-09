/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.service;

import java.io.Serializable;
import javax.ejb.Stateless;
import main.core.helper.PasswordGenerator;
import main.dao.UserDao;
import main.domain.User;

/**
 *
 * @author maikel
 */
@Stateless
public class UserService extends UserDao implements Serializable{

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getUserByCredentials(String username, String password) {
         return super.getUserByCredentials(username, PasswordGenerator.encryptPassword(username, password));
    }
    
}
