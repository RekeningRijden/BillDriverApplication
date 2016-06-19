package web.beans.localization;

import web.core.helpers.CookieHelper;

import java.io.Serializable;
import java.util.Locale;
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
    private static final int COOKIE_EXPIRATION = 1209600;
    private static final String LOCALE = "locale";

    private Locale locale;

    /**
     * Retrieve the requested locale from a cookie, and set this locale as the website locale
     */
    @PostConstruct
    public void init() {
        String localeString;
        Cookie localeCookie = CookieHelper.getCookie(LOCALE);
        if (localeCookie != null) {
            localeString = localeCookie.getValue();
            localeString = localeString.substring(0, 2);
        } else {
            localeString = "en";
            CookieHelper.setCookie(LOCALE, localeString, COOKIE_EXPIRATION);
        }

        locale = stringToLocale(localeString);
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(locale);
    }

    /**
     * Set website locale to english
     */
    public void setEnglishLocale() {
        locale = Locale.ENGLISH;
        setLocale();
    }

    /**
     * Set website locale to dutch
     */
    public void setDutchLocale() {
        locale = stringToLocale("nl");
        setLocale();
    }

    /**
     * Set website locale
     */
    private void setLocale() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);

        String localeString = localeToString(locale);
        CookieHelper.deleteCookie(LOCALE);
        CookieHelper.setCookie(LOCALE, localeString, COOKIE_EXPIRATION);
    }

    /**
     * Convert a locale to its ISO string value
     *
     * @param l locale to convert
     * @return ISO string value
     */
    private static String localeToString(Locale l) {
        return l.getLanguage() + "," + l.getCountry();
    }

    /**
     * Convert ISO string value to java locale
     *
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
