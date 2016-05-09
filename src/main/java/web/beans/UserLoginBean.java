package web.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.core.helper.PasswordGenerator;
import main.domain.User;
import main.service.UserService;
import web.core.helpers.FrontendHelper;
import web.core.helpers.RedirectHelper;

@Named
@ViewScoped
public class UserLoginBean implements Serializable {

    @Inject
    private UserService userService;
    @Inject
    private UserInfoBean userInfoBean;

    private String username;
    private String password;

    private String resetUsername;
    private String resetEmail;

    private String demoName;
    private String demoPhone;
    private String demoMail;
    private String demoCompany;

    private String to = "/dashboard.xhtml";

    @PostConstruct
    public void createDefault() {
        if (userService.getAll().isEmpty()) {
            User u = new User();
            u.setUsername("admin");
            u.setPassword(PasswordGenerator.encryptPassword("admin", "admin"));
            userService.create(u);
        }
    }

    public void login() {
        String typedUsername = username.toLowerCase();

        if (userInfoBean.getLoggedInUser() == null) {
            User user = userService.getUserByCredentials(typedUsername, password);
            if (user != null) {
                RedirectHelper.redirect(to);
            } else {
                FrontendHelper.displayErrorSmallBox("Kan niet inloggen");
            }
        } else {
            FrontendHelper.displayErrorSmallBox("Kan niet inloggen");
        }
    }

    public void emptyTemps() {
        this.resetUsername = "";
        this.resetEmail = "";
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public String getDemoPhone() {
        return demoPhone;
    }

    public void setDemoPhone(String demoPhone) {
        this.demoPhone = demoPhone;
    }

    public String getDemoMail() {
        return demoMail;
    }

    public void setDemoMail(String demoMail) {
        this.demoMail = demoMail;
    }

    public String getDemoCompany() {
        return demoCompany;
    }

    public void setDemoCompany(String demoCompany) {
        this.demoCompany = demoCompany;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetUsername() {
        return resetUsername;
    }

    public void setResetUsername(String _resetUsername) {
        this.resetUsername = _resetUsername;
    }

    public String getResetEmail() {
        return resetEmail;
    }

    public void setResetEmail(String _resetEmail) {
        this.resetEmail = _resetEmail;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String _to) {
        this.to = _to;
    }
    //</editor-fold>
}
