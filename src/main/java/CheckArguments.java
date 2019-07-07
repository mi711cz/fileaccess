import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckArguments {

    private static final Logger logger = LogManager.getLogger(CheckArguments.class);

    public void checkGivenArguments(String[] arguments) {

        if (arguments.length == 0) {
            logger.info("No files given ... please check.");
//            System.exit(1);
        }

        //    private static void checkFiles(String args) {
//        try {
//            Files.exists(Paths.get(args));
//                logger.info("x");
//
//        } catch (IOException e) {
//            logger.error("File not readalbe: " + args);
//            e.printStackTrace();
//        }
//    }

        // Print all files
//        for (String k: args) {
//            logger.info("Checking file ... " + k);
//        }

    }
}
