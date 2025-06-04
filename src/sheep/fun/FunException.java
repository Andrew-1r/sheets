package sheep.fun;

/**
 * fun exception
 * @provided
 */
public class FunException extends Exception {
    /**
     * fun exception
     * @param message given message
     */
    public FunException(String message) {
        super(message);
    }

    /**
     * fun exception
     * @param base base exception
     */
    public FunException(Exception base) {
        super(base);
    }
}
