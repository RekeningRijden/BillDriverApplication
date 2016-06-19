/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.service;

import java.io.Serializable;

import javax.ejb.Stateless;

import main.core.communication.Communicator;
import main.core.helper.PasswordGenerator;
import main.dao.UserDao;
import main.domain.User;

/**
 * @author maikel
 */
@Stateless
public class UserService extends UserDao implements Serializable {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User getUserByCredentials(String username, String password) {
        return super.getUserByCredentials(username, PasswordGenerator.encryptPassword(username, password));
    }

    /**
     * Register a new User in the system. This method is called from a REST-POST call with a User who hasn't been persisted yet.
     *
     * @param user to register.
     * @return the registered User.
     */
    public User registerUser(User user) {
        String password = PasswordGenerator.encryptPassword(user.getEmail(), user.getEmail().substring(0, user.getEmail().indexOf("@")));

        user.setUsername(user.getEmail());
        user.setPassword(password);

        return create(user);
    }

    /**
     * Update a User in this system and the Driver in the AdministrationSystem.
     *
     * @param user to update.
     * @return the updated User.
     * @throws Exception when something went wrong with the communication with the AdministrationSystem.
     */
    public User saveInSystems(User user) throws Exception {
        boolean subscribed = user.getDriver().getSubscribedToTrafficInfo();
        user.setDriver(Communicator.updateUser(user.getDriver()));
        user.getDriver().setSubscribedToTrafficInfo(subscribed);
        user = update(user);
        user.setDriver(Communicator.getDriver(user.getId()));
        user.getDriver().setSubscribedToTrafficInfo(subscribed);

        return user;
    }
}
