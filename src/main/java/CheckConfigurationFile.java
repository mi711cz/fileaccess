import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

public class CheckConfigurationFile {

    private static final Logger logger = LogManager.getLogger(CheckConfigurationFile.class);
    private static final ResourceBundle rb = ResourceBundle.getBundle("config");

    public void checkparameter(String[] parameter) {

        logger.info("--> Checking configuration file. START ...");
        for (String a : parameter) {
            logger.info(a + "\t[" + readConfigFile(a) + "]");
        }
        logger.info("<-- Checking configuration file. END ...");
    }

    private static String readConfigFile(String parameter) {
        return rb.getString(parameter);
    }

    public static String getParameter(String parameter) {
        return rb.getString(parameter);
    }

    public void checkConfigFiles(String[] parameter) {

        for (String file : parameter) {
//            logger.info("Checking file: " + file);
            try {
                FileReader fr = new FileReader(file);

                fr.read();
                fr.close();
            } catch (IOException e) {
                logger.error("File is not readable: " + file);
            }
        }
    }
}
