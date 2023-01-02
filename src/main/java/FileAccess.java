import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileAccess {

    private static final Logger logger = LogManager.getLogger(FileAccess.class);

    public static void main(String[] args) throws IOException {
        logger.info("Starting: " + System.getProperty("sun.java.command").split(" ")[0]);
// Define used parameter
        String[] parameter = {"SplittingChar", "InputFile", "OutputFile"};
// Check arguments
        CheckArguments checkArguments = new CheckArguments();
        checkArguments.checkGivenArguments(args);
// Check configuration file
        CheckConfigurationFile check = new CheckConfigurationFile();
        check.checkParameter(parameter);

        String[] files = {CheckConfigurationFile.getParameter(parameter[1]), CheckConfigurationFile.getParameter(parameter[2])};
        check.checkConfigFiles(CheckConfigurationFile.getParameter(parameter[2]), files);

// Call executing method generateResultFile after checking arguments/configuration
        try {
            generateResultFile(CheckConfigurationFile.getParameter(parameter[0]), CheckConfigurationFile.getParameter(parameter[1]), CheckConfigurationFile.getParameter(parameter[2]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateResultFile(String splittingChar, String input, String output) throws IOException {
// Open InputFile file
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);

// initializing variables
        String zeile;
        int var1 = 1;
        int maxLinesInput;
        int maxLinesOutput;

// Call function countLines for first file
        maxLinesInput = countLines(input);
        logger.info("Count lines in file " + input + " --> [" + maxLinesInput + "]");
        maxLinesOutput = countLines(output);
        logger.info("Count lines in file " + output + " --> [" + maxLinesOutput + "]");

// Read first file until EOF
        logger.info("Starting generating result file ...");
        while ((zeile = br.readLine()) != null) {
            String[] zeilenValues = zeile.split(splittingChar);
// Print all lines from first lines
            logger.info("Reading line: [" + var1 + "/" + maxLinesInput + "] Values: " + zeile);
            var1++;
// Call method writeFile
            writeFile(zeilenValues[0], zeilenValues[1], output);
        }

// Close first file
        br.close();
    }

    //  Method without return
    private static void writeFile(String zeilenValues0, String zeilenValues1, String output) throws IOException {
// Open second file
        FileWriter fw = new FileWriter(output, true);
        BufferedWriter bw = new BufferedWriter(fw);

// Generate new line
        String zeile;
        zeile = "select * from abc where Col1 = '" + zeilenValues0 + "' and Col2 = '" + zeilenValues1 + "';";

// Print new line
        logger.info("Writing line: " + zeile);
// Write new line in second file, call method write
        bw.write(zeile);
// break line
        bw.newLine();
// Close second file
        bw.close();
    }

    // Method with return :)
    private static int countLines(String file) throws IOException {
// Open file
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
// Initializing variables
        int countLine = 0;
// Count lines in file
        while (br.readLine() != null) {
            countLine++;
        }
<<<<<<< HEAD
// close BufferReader :)
//
=======
// close BufferReader 
>>>>>>> origin/change-004
        br.close();
// return amount of lines
        return countLine;
    }
}
