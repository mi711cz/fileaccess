import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * FileAccess - Processes input file and generates SQL queries from data.
 * Reads lines from input file, splits them, and generates SELECT statements.
 */
public class FileAccess {

    private static final Logger logger = LogManager.getLogger(FileAccess.class);
    private static final String[] REQUIRED_PARAMETERS = {"SplittingChar", "InputFile", "OutputFile"};

    public static void main(String[] args) {
        try {
            logger.info("Starting File Access Application");

            // Validate and load configuration
            CheckArguments.validateArguments(args);
            CheckConfigurationFile configChecker = new CheckConfigurationFile();
            configChecker.checkParameter(REQUIRED_PARAMETERS);

            // Get configuration parameters
            String splittingChar = CheckConfigurationFile.getParameter(REQUIRED_PARAMETERS[0]);
            String inputFile = CheckConfigurationFile.getParameter(REQUIRED_PARAMETERS[1]);
            String outputFile = CheckConfigurationFile.getParameter(REQUIRED_PARAMETERS[2]);

            // Validate input/output files
            String[] filesToCheck = {inputFile, outputFile};
            configChecker.checkConfigFiles(outputFile, filesToCheck);

            // Process files
            processInputFile(splittingChar, inputFile, outputFile);
            logger.info("File processing completed successfully");

        } catch (IOException e) {
            logger.error("An I/O error occurred: {}", e.getMessage(), e);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            logger.error("Configuration error: {}", e.getMessage(), e);
            System.exit(2);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            System.exit(3);
        }
    }

    private static void processInputFile(String splittingChar, String inputFile, String outputFile) throws IOException {
        logger.info("Starting to process file: {}", inputFile);

        try (BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8));
             BufferedWriter outputWriter = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(outputFile, false), StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = inputReader.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(splittingChar, -1);

                if (values.length < 2) {
                    logger.warn("Line {} has insufficient values, skipping: {}", lineNumber, line);
                    continue;
                }

                String sqlQuery = generateSqlQuery(values[0], values[1]);
                logger.debug("Line {}: Writing SQL query", lineNumber);
                outputWriter.write(sqlQuery);
                outputWriter.newLine();
            }

            logger.info("Successfully processed {} lines from input file", lineNumber);

        } catch (FileNotFoundException e) {
            throw new IOException("Input file not found: " + inputFile, e);
        }
    }

    private static String generateSqlQuery(String col1, String col2) {
        return "SELECT * FROM abc WHERE Col1 = '" + escapeSqlValue(col1) +
               "' AND Col2 = '" + escapeSqlValue(col2) + "';";
    }

    private static String escapeSqlValue(String value) {
        return value.replace("'", "''");
    }
}
