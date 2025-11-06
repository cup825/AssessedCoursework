/**
 * Main program class for the Ticket Management System.
 *
 * <p>This is the system's startup class responsible for initializing the application
 * and launching the main interface.</p>
 *
 * @author Ziyue Ren
 * @version 1.0.0
 * @since 2024
 * @see PurchaseTicketPlatform
 */
public class MainProgram {
    /**
     * Main program entry point.
     *
     * @param args command line arguments (not used in current version)
     */
    public static void main(String[] args) {
        PurchaseTicketPlatform platform = new PurchaseTicketPlatform();
        platform.run();
    }
}