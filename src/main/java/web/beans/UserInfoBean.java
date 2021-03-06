package web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import main.core.helper.PasswordGenerator;
import main.domain.Car;
import main.domain.Ownership;
import main.domain.User;
import main.service.UserService;
import web.core.helpers.FrontendHelper;
import web.core.helpers.PropertiesHelper;
import web.core.helpers.RedirectHelper;

/**
 *
 * @author Sam
 */
@Named
@SessionScoped
public class UserInfoBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(UserInfoBean.class.getName());

    @Inject
    private UserService userService;

    private User loggedInUser;

    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    private List<Car> cars = new ArrayList<>();

    private List<Ownership> ownerships = new ArrayList<>();

    private Properties properties;

    public void init(){
        properties = PropertiesHelper.getProperties(FacesContext.getCurrentInstance());
    }

    public void logout() {
        loggedInUser = null;
        cars.clear();
        RedirectHelper.redirect("/index.xhtml?faces-redirect=true");
    }

    /**
     * @param groupName Name of group to be in.
     * @return true if loggedInUser is in given group.
     */
    public boolean isLoggedInUserInGroup(String groupName) {
        return loggedInUser.getUserGroup().getName().equals(groupName);
    }

    /**
     * Saves the logged in user into the system.
     */
    public void save() {
        properties = PropertiesHelper.getProperties(FacesContext.getCurrentInstance());

        try {
            loggedInUser = userService.saveInSystems(loggedInUser);
            FrontendHelper.displaySuccessSmallBox(properties.getProperty("SAVED"));
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error: ", ex);
            FrontendHelper.displayErrorSmallBox(properties.getProperty("SOMETHING_WENT_WRONG"));
        }
    }

    /**
     * Changes the password of the user.
     */
    public void changePassword() {
        properties = PropertiesHelper.getProperties(FacesContext.getCurrentInstance());

        if (loggedInUser.getPassword().equals(PasswordGenerator.encryptPassword(loggedInUser.getUsername(), this.oldPassword))) {
            if (this.newPassword.equals(this.repeatPassword)) {
                loggedInUser.setPassword(PasswordGenerator.encryptPassword(loggedInUser.getUsername(), this.newPassword));
                userService.update(loggedInUser);
                FrontendHelper.hideModal("newPasswordModal");
                FrontendHelper.displaySuccessSmallBox(properties.getProperty("PASSWORD_CHANGED"));
            } else {
                FrontendHelper.displayErrorSmallBox(properties.getProperty("PASSWORDS_DONT_MATCH"));
            }
        } else {
            FrontendHelper.displayErrorSmallBox(properties.getProperty("WRONG_PASSWORD"));
        }
    }

    public synchronized User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInUsername() {
        if (getLoggedInUser() != null) {
            return getLoggedInUser().getUsername();
        } else {
            return "";
        }
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public List<Ownership> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(List<Ownership> ownerships) {
        this.ownerships = ownerships;
    }
}
