/**
 * Main program class for the Ticket Management System.
 *
 * <p>This is the system's startup class responsible for initializing the application
 * and launching the main interface.</p>
 *
 * @author Ziyue Ren
 * @version 1.0.0
 * @see PurchaseTicketPlatform
 * @since 2024
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

        //Please open clerk.txt with a monospaced font (e.g., Consolas).
    }
}