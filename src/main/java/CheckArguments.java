import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckArguments {

    private static final Logger logger = LogManager.getLogger(CheckArguments.class);

    public void checkGivenArguments(String[] arguments) {

        if (arguments.length == 0) {
            logger.info("No files given ... please check.");

        }

    }
}
