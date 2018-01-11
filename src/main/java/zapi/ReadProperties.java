package zapi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Jainik Bakaraniya 12/29/2017
 */
public class ReadProperties {

    /**
     * @param propertiesPath
     * @return
     */
    public Properties loadProperties(String propertiesPath) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesPath));
        } catch (IOException e) {
            System.out.println("Error while importing logging properties from path " + propertiesPath);
            e.printStackTrace();
        }
        return properties;
    }


}
