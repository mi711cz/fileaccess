import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CheckArguments - Validates command line arguments.
 */
public class CheckArguments {

    private static final Logger logger = LogManager.getLogger(CheckArguments.class);

    public static void validateArguments(String[] arguments) throws IllegalArgumentException {
        if (arguments == null || arguments.length == 0) {
            logger.warn("No command line arguments provided");
        }
        logger.debug("Arguments validation completed. Count: {}", arguments != null ? arguments.length : 0);
    }
}
