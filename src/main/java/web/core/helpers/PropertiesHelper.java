package web.core.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

/**
 * Created by martijn on 3-6-2016.
 */
public class PropertiesHelper {

    /**
     * Looks at the used browser language and returns the corresponding properties file.
     *
     * @param facesContext To get the browser language from.
     * @return Corresponding properties file.
     */
    public static Properties getProperties(FacesContext facesContext) {
        Locale browserLocale = facesContext.getViewRoot().getLocale();

        try {
            Properties props = new Properties();

            InputStream inputStream;
            switch (browserLocale.getLanguage()) {
                case "en":
                    inputStream = PropertiesHelper.class.getClassLoader().getResourceAsStream("labels/labels_en.properties");
                    break;

                case "nl":
                    inputStream = PropertiesHelper.class.getClassLoader().getResourceAsStream("labels/labels_nl.properties");
                    break;

                default:
                    inputStream = PropertiesHelper.class.getClassLoader().getResourceAsStream("labels/labels_en.properties");
                    break;
            }

            if (inputStream != null) {
                props.load(inputStream);
                return props;
            } else {
                return null;
            }
        } catch (IOException e) {
            Logger.getLogger(PropertiesHelper.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
