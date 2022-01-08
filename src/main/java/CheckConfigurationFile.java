import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;

public class CheckConfigurationFile {

    private static final Logger logger = LogManager.getLogger(CheckConfigurationFile.class);
    private static final ResourceBundle rb = ResourceBundle.getBundle("config");

    public void checkParameter(String[] parameter) {

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

/*    public void checkConfigFiles(String[] parameter) {

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
}*/

    // Check input and output files for access
    public void checkConfigFiles(String outputFile, String[] fileList) throws IOException {
        for (String file : fileList) {
            try {
                FileReader fr = new FileReader(file);
                fr.read();
                fr.close();
            } catch (IOException e) {
                logger.warn("File is not readable: " + file);
            }
        }

        File file = new File(outputFile);
        if(file.exists()) {
            boolean deleteResult = file.delete();
            if (deleteResult) {
                logger.info("Output datei vorhanden ... wird gelöscht.");
            } else {
                logger.warn("Output file could not be deleted.");
                System.exit(9);
            }
        }

        boolean checkFileCreated = file.createNewFile();
        if (!checkFileCreated) {
            logger.error("Outputfile could not be created: " + file);
            System.exit(9);
        } else {
            logger.info("Output file recreated: " + file);
        }
    }
}