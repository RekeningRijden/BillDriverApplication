package web.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;

/**
 * Created by martijn on 30-5-2016.
 */
@Named
@ViewScoped
public class ProfileBean implements Serializable {

    @Inject
    private UserInfoBean userInfoBean;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            if (userInfoBean.getLoggedInUser().getDriver() == null || userInfoBean.getLoggedInUser().getDriver().getAddress() == null) {
                FrontendHelper.displayWarningSmallBox("No data found!");
            }
        }
    }
}
