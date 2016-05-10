package web.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import main.domain.User;
import web.core.helpers.RedirectHelper;

/**
 *
 * @author Sam
 */
@Named
@SessionScoped
public class UserInfoBean implements Serializable {

    private User loggedInUser;

    public void logout() {
        loggedInUser = null;
        RedirectHelper.redirect("/index.xhtml?faces-redirect=true");
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
        }else{
            return "";
        }
    }

    /**
     * @param groupName name of group to be in.
     * @return true if loggedInUser is in given group.
     */
    public boolean isLoggedInUserInGroup(String groupName) {
        return loggedInUser.getUserGroup().getName().equals(groupName);
    }

}
