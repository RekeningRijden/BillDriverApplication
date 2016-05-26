package web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.core.communication.Communicator;
import main.core.helper.PasswordGenerator;
import main.domain.Car;
import main.domain.User;
import main.service.UserService;
import web.core.helpers.FrontendHelper;
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

    public void logout() {
        loggedInUser = null;
        cars.clear();
        RedirectHelper.redirect("/index.xhtml?faces-redirect=true");
    }

    /**
     * @param groupName name of group to be in.
     * @return true if loggedInUser is in given group.
     */
    public boolean isLoggedInUserInGroup(String groupName) {
        return loggedInUser.getUserGroup().getName().equals(groupName);
    }

    public void save() {
        try {
            boolean subscribed = loggedInUser.getDriver().getSubscribedToTrafficInfo();
            loggedInUser.setDriver(Communicator.updateUser(loggedInUser.getDriver()));
            loggedInUser.getDriver().setSubscribedToTrafficInfo(subscribed);
            loggedInUser = userService.update(loggedInUser);
            loggedInUser.setDriver(Communicator.getDriver(loggedInUser.getId()));
            loggedInUser.getDriver().setSubscribedToTrafficInfo(subscribed);
            FrontendHelper.displaySuccessSmallBox("Succesvol opgeslagen!");
        } catch (IOException ex) {
            LOGGER.warning(ex.toString());
            FrontendHelper.displayErrorSmallBox("Opslaan mislukt!");
        }
    }

    public void changePassword() {
        if (loggedInUser.getPassword().equals(PasswordGenerator.encryptPassword(loggedInUser.getUsername(), this.oldPassword))) {
            if (this.newPassword.equals(this.repeatPassword)) {
                loggedInUser.setPassword(PasswordGenerator.encryptPassword(loggedInUser.getUsername(), this.newPassword));
                userService.update(loggedInUser);
                FrontendHelper.hideModal("newPasswordModal");
                FrontendHelper.displaySuccessSmallBox("Wachtwoord is aangepast!");
            } else {
                FrontendHelper.displayErrorSmallBox("Wachtwoord komt niet overeen!");
            }
        } else {
            FrontendHelper.displayErrorSmallBox("Oud wachtwoord onjuist!");
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
}
