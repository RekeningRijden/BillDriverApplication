package web.core.helpers;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by martijn on 3-6-2016.
 */
public class CookieHelper {

    /**
     * Set a cookie
     *
     * @param name   Name of the cookie
     * @param value  Value of the cookie
     * @param expiry Expiry of the cookie
     */
    public static void setCookie(String name, String value, int expiry) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }

        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath("/");
        }

        cookie.setMaxAge(expiry);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }

    /**
     * Get a cookie
     *
     * @param name Name of the cookie
     * @return Found cookie, null if no cookie can be found
     */
    public static Cookie getCookie(String name) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                }
            }
        }

        return cookie;
    }

    /**
     * Delete a cookie
     *
     * @param name Name of the cookie
     */
    public static void deleteCookie(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                }
            }
        }

        if(cookie != null) {
            cookie.setValue(null);
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(0);

            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.addCookie(cookie);
        }
    }

    /**
     * Clear all cookies set by the application
     */
    public static void clearCookies() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setValue(null);
                cookies[i].setPath(request.getContextPath());
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
        }
    }
}
