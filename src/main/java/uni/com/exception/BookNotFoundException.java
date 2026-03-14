package uni.com.exception;

/**
 * Custom exception that is thrown when a book cannot be found in the catalogue.
 */
public class BookNotFoundException extends Exception {
    /**
     * Constructs a new BookNotFoundException with the specified message
     *
     * @param message explanation of the error
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}
