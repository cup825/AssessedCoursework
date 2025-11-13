/**
 * Exception thrown when there are not enough tickets available for a purchase operation.
 *
 * @author Ziyue Ren
 * @version 1.0
 * @since 2025
 */
public class NotEnoughTicketsException extends RuntimeException {
    /**
     * Constructs a new NotEnoughTicketsException with no detail message.
     */
    public NotEnoughTicketsException() {
        super();
    }
}