package web.beans.localization;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import web.core.localization.Localization;

@Named
@RequestScoped
public class TxtBean {

    public TxtBean() {
    }

    public String g(String s) {
        return Localization.g(s);
    }

    public String g(String s, String param1) {
        return getWithParams(s, param1);
    }

    public String g(String s, String param1, String param2) {
        return getWithParams(s, param1, param2);
    }

    public String gp(String s, List<String> params) {
        return params == null ? g(s) : Localization.g(s, params);
    }

    private String getWithParams(String s, Object... params) {
        return Localization.g(s, params);
    }
}
