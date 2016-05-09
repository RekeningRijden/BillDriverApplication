package web.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.domain.User;
import main.service.UserGroupService;

/**
 *
 * @author Sam
 */
@Named
@SessionScoped
public class UserInfoBean implements Serializable {

    @Inject
    private UserGroupService userGroupService;

    private User loggedInUser;

    public String logout() {
        loggedInUser = null;
        return "/login.xhtml?faces-redirect=true";
    }

    public synchronized User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInUsername() {
        return getLoggedInUser().getUsername();
    }

    /**
     * @param groupName name of group to be in.
     * @return true if loggedInUser is in given group.
     */
    public boolean isLoggedInUserInGroup(String groupName) {
        return loggedInUser.getUserGroup().getName().equals(groupName);
    }

}
