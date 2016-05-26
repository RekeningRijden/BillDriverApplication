package web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.core.communication.Communicator;
import main.core.helper.PasswordGenerator;
import main.domain.User;
import main.service.UserService;
import web.core.helpers.FrontendHelper;
import web.core.helpers.RedirectHelper;

@Named
@ViewScoped
public class UserLoginBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private static final String defaultUsername = "admin";
    private static final String defaultPassword = "admin";

    @Inject
    private UserService userService;
    @Inject
    private UserInfoBean userInfoBean;

    private String username;
    private String password;

    private String to = "/pages/dashboard.xhtml";

    public void createDefault() {
        if (userService.getAll().isEmpty()) {
            User u = new User();
            u.setUsername(defaultUsername);
            u.setPassword(PasswordGenerator.encryptPassword(defaultUsername, defaultPassword));
            userService.create(u);
        }
    }

    public void login() {
        String typedUsername = username.toLowerCase();

        if (userInfoBean.getLoggedInUser() == null) {
            User user = userService.getUserByCredentials(typedUsername, password);
            if (user != null) {
                userInfoBean.setLoggedInUser(user);
                boolean subscribed = userInfoBean.getLoggedInUser().getDriver().getSubscribedToTrafficInfo();
                try {
                    userInfoBean.getLoggedInUser().setDriver(Communicator.getDriver(user.getId()));
                    userInfoBean.getLoggedInUser().getDriver().setSubscribedToTrafficInfo(subscribed);
                    userInfoBean.getCars().addAll(Communicator.getCars(user.getId()));
                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING, ex.toString());
                }
                RedirectHelper.redirect(to);
            } else {
                FrontendHelper.displayErrorSmallBox("Kan niet inloggen");
            }
        } else {
            RedirectHelper.redirect(to);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    //</editor-fold>
}
