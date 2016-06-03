package web.core.helpers;

import javax.faces.context.FacesContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

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
        Locale browserLocale = facesContext.getExternalContext().getRequestLocale();

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
            e.printStackTrace();
        }
        return null;
    }
}
