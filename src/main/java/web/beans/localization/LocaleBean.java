package web.beans.localization;

import web.core.helpers.CookieHelper;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;

/**
 * Created by martijn on 3-6-2016.
 */
@Named
@SessionScoped
public class LocaleBean implements Serializable {
    private final int cookieExpiration = 1209600;
    private Locale locale;

    /**
     * Retrieve the requested locale from a cookie, and set this locale as the website locale
     */
    @PostConstruct
    public void init() {
        String localeString;
        Cookie localeCookie = CookieHelper.getCookie("locale");
        if (localeCookie != null) {
            localeString = localeCookie.getValue();
            localeString = localeString.substring(0, 2);
        } else {
            localeString = "en";
            CookieHelper.setCookie("locale", localeString, cookieExpiration);
        }

        locale = stringToLocale(localeString);
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(locale);
    }

    /**
     * Set website locale to english
     */
    public void SetEnglishLocale() {
        locale = Locale.ENGLISH;
        SetLocale();
    }

    /**
     * Set website locale to dutch
     */
    public void SetDutchLocale() {
        locale = stringToLocale("nl");
        SetLocale();
    }

    /**
     * Set website locale
     */
    private void SetLocale() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

        String localeString = localeToString(locale);
        CookieHelper.deleteCookie("locale");
        CookieHelper.setCookie("locale", localeString, cookieExpiration);
    }

    /**
     * Convert a locale to its ISO string value
     * @param l locale to convert
     * @return ISO string value
     */
    private String localeToString(Locale l) {
        return l.getLanguage() + "," + l.getCountry();
    }

    /**
     * Convert ISO string value to java locale
     * @param s string to convert
     * @return java locale
     */
    private Locale stringToLocale(String s) {
        StringTokenizer tempStringTokenizer = new StringTokenizer(s, ",");

        String l = "";
        String c = "";
        if (tempStringTokenizer.hasMoreTokens())
            l = (String) tempStringTokenizer.nextElement();
        if (tempStringTokenizer.hasMoreTokens())
            c = (String) tempStringTokenizer.nextElement();

        return new Locale(l, c);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
