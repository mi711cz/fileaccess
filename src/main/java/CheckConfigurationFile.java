import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * CheckConfigurationFile - Manages configuration file validation and parameter retrieval.
 */
public class CheckConfigurationFile {

    private static final Logger logger = LogManager.getLogger(CheckConfigurationFile.class);
    private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config_de");

    public void checkParameter(String[] parameters) {
        logger.info("Checking configuration file...");
        for (String parameter : parameters) {
            try {
                String value = CONFIG.getString(parameter);
                logger.info("{} = [{}]", parameter, value);
            } catch (Exception e) {
                logger.error("Configuration parameter not found: {}", parameter);
                throw new IllegalArgumentException("Missing configuration: " + parameter, e);
            }
        }
        logger.info("Configuration validation completed");
    }

    public static String getParameter(String parameter) {
        try {
            return CONFIG.getString(parameter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Configuration parameter not found: " + parameter, e);
        }
    }

    public void checkConfigFiles(String outputFile, String[] fileList) throws IOException {
        logger.info("Checking input files...");

        // Check input files
        for (String file : fileList) {
            if (file.equals(outputFile)) {
                continue; // Skip output file in input check
            }
            checkFileReadable(file);
        }

        // Prepare output file
        prepareOutputFile(outputFile);
    }

    private static void checkFileReadable(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File does not exist: " + filePath);
        }
        if (!Files.isReadable(path)) {
            throw new IOException("File is not readable: " + filePath);
        }
        logger.debug("File is readable: {}", filePath);
    }

    private static void prepareOutputFile(String outputFilePath) throws IOException {
        File file = new File(outputFilePath);

        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete existing output file: " + outputFilePath);
            }
            logger.debug("Deleted existing output file");
        }

        if (!file.createNewFile()) {
            throw new IOException("Failed to create output file: " + outputFilePath);
        }
        logger.debug("Output file created successfully: {}", outputFilePath);
    }
}