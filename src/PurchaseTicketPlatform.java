import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main platform class for ticket purchase and management system.
 * Handles member registration, ticket inventory, purchase operations, and cancellation.
 *
 * @author Ziyue Ren
 * @version 1.0
 * @see Member
 * @see Ticket
 * @see SortedLinkedList
 */
public class PurchaseTicketPlatform {
    private final SortedLinkedList<Member> memberList = new SortedLinkedList<>(); // Member list
    private final SortedLinkedList<Ticket> ticketList = new SortedLinkedList<>(); // Show list
    private Member currentMember;
    private Ticket currentTicket;
    private final Scanner input = new Scanner(System.in); // Scanner for user input

    /**
     * Loads member and ticket data from input file.
     */
    public void loadList() {
        try {
            Scanner s = new Scanner(new File("input_data.txt"));
            int memberCount = s.nextInt(); // First line number
            s.nextLine(); // Move to name line
            for (int i = 0; i < memberCount; i++) {
                String line = s.nextLine(); // Read member name
                String[] str = line.split(" ");
                Member mem = new Member(str[0].trim(), str[1].trim());
                memberList.add(mem);
            }

            int showCount = s.nextInt();
            s.nextLine(); // Move to next line
            for (int i = 0; i < showCount; i++) { // Read three lines per show
                try {
                    Ticket tic = new Ticket(
                            s.nextLine(),
                            Integer.parseInt(s.nextLine()),
                            Double.parseDouble(s.nextLine())
                    );
                    ticketList.add(tic);
                } catch (NumberFormatException e) {
                    System.out.println("Failed to convert int/double");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("The file can not find!");
        }
    }

    /**
     * Displays the main menu options.
     */
    public void printMenu() {
        System.out.print("""
                ╭──────── Show & Member Management Menu ────────╮
                │ t: List all shows' messages                   │
                │ m: List all members' messages                 │
                │ b: Buy new tickets                            │
                │ c: Cancel tickets                             │
                ╰──────── Enter 'f' to quit the program ────────╯
                Please type your option and press Enter >""");
    }

    /**
     * Main program loop handling user interactions.
     */
    public void run() {
        try (PrintWriter clearFile = new PrintWriter("letters.txt")) {
            clearFile.print(""); // Clear file content
        } catch (FileNotFoundException e) {
            System.out.println("Cannot clear letters.txt");
        }

        loadList();
        boolean flag = true;
        while (flag) try {
            printMenu();
            String line = input.nextLine();
            if (line.isEmpty()) { // Check empty input
                System.out.println("Input can not be empty!");
                continue;
            }
            if (line.length() != 1) { // Check input length
                System.out.println("Please type valid option!");
                continue;
            }
            char ch = line.charAt(0);
            switch (ch) {
                case 'f':  // Exit program
                    System.out.println("You have exited the program.");
                    flag = false;
                    break;
                case 't':
                    displayShows();
                    break;
                case 'm':
                    displayMembers();
                    break;
                case 'b':
                    buy();
                    break;
                case 'c':
                    cancel();
                    break;
                default:
                    System.out.println("Please type valid option!");
                    break;
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all shows with availability and pricing.
     */
    public void displayShows() {
        System.out.println("┌─────────────────────────────────────────────┬──────────────────┬─────────┐");
        System.out.printf("│%-45s│%-18s│%-9s│%n", "SHOW NAME", "TICKETS AVAILABLE", "PRICE(£)");
        System.out.println("├─────────────────────────────────────────────┼──────────────────┼─────────┤");
        for (Ticket t : ticketList)
            System.out.println(t.toString());
        System.out.println("└─────────────────────────────────────────────┴──────────────────┴─────────┘\n");
    }

    /**
     * Displays all members with their purchase records.
     */
    public void displayMembers() {
        System.out.println("───────────────┬──────────────────────────────────────────────────");
        System.out.printf("%-15s│%-50s %n", "MEMBER NAME", "PURCHASE RECORD");
        for (Member m : memberList) {
            System.out.println(m.getMemberMessage(ticketList));
        }
        System.out.println("───────────────┴──────────────────────────────────────────────────");
    }

    /**
     * Finds a member in the member list.
     *
     * @param mem the member to find
     * @return the found member or null if not found
     */
    public Member findMember(Member mem) {
        for (Member m : memberList) {
            if (m.compareTo(mem) == 0) {
                return m; // Return existing object
            } else if (m.compareTo(mem) > 0) {
                return null;
            }
        }
        return null;
    }

    /**
     * Finds a ticket in the ticket list.
     *
     * @param tic the ticket to find
     * @return the found ticket or null if not found
     */
    public Ticket findTicket(Ticket tic) {
        for (Ticket t : ticketList) {
            if (t.compareTo(tic) == 0) {
                return t; // Return existing object
            } else if (t.compareTo(tic) > 0) {
                return null;
            }
        }
        return null;
    }

    /**
     * Gets current timestamp in formatted string.
     *
     * @return formatted current time string
     */
    public String getTime() {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return current.format(formatter);
    }

    /**
     * Validates member name input and finds member in list.
     */
    public void checkName() {
        System.out.print("Please enter your full name(split by space)>");
        try {
            String[] userName = input.nextLine().split(" ");
            currentMember = new Member(userName[0].trim(), userName[1].trim());
            if (findMember(currentMember) == null) {
                throw new IllegalStateException("Name is not exist!");
            } else {
                currentMember = findMember(currentMember);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Input invalid format name!");
            currentMember = null; // Reset to prevent using wrong object
        }
    }

    /**
     * Handles ticket purchase process including validation and updates.
     */
    public void buy() {
        checkName(); // Validate member name

        if (currentMember == null)
            return;

        // Validate show name exists
        boolean flag = true;
        while (flag) {
            System.out.print("Please enter the show name you want to buy >");
            String show = input.nextLine();
            currentTicket = new Ticket(show.trim());
            if (findTicket(currentTicket) == null) { // Show doesn't exist - send letter
                System.out.println("Purchase failed! Please check your letters.");
                displayShows();

                try (PrintWriter outFile = new PrintWriter(new FileWriter("letters.txt", true))) {
                    outFile.printf("""
                            %s
                            Dear %s,
                            
                            I’m sorry, but we couldn’t complete your ticket purchase.
                            Unfortunately, '%s' is not an available ticket at NEAT.
                            
                            Please check shows' messages and try again.
                            
                            Kind regards,
                            NEAT Ticket Office
                            
                            """, getTime(), currentMember.getName(), currentTicket.getName());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                currentTicket = findTicket(currentTicket);
                flag = false;
            }
        }

        System.out.print("Please input the count of tickets you want to buy >");
        try {
            int purchaseCount = input.nextInt();
            input.nextLine();
            if (purchaseCount <= 0) { // Prevent non-positive input
                System.out.println("Please enter a positive number!");
                return;
            }
            // Update ticket inventory (negative count)
            currentTicket.updateCount(-purchaseCount);
            // Update member's purchase records
            currentMember.purchase(currentTicket.getName(), purchaseCount);
            System.out.println("Purchase successfully!");
        } catch (InputMismatchException e) { // Invalid number input
            System.out.println("Please input valid number!");
        } catch (NotEnoughTicketsException e) { // Insufficient tickets - send letter
            System.out.println("Purchase failed! Please check your letters.");
            try (PrintWriter outFile = new PrintWriter(new FileWriter("letters.txt", true))) {
                outFile.printf("""
                        %s
                        Dear %s,
                        
                        I’m sorry, but we couldn’t complete your ticket purchase.
                        There are not enough tickets available for '%s' — only %d left.
                        
                        Please try again later or choose another option.
                        
                        Kind regards,
                        NEAT Ticket Office
                        
                        """, getTime(), currentMember.getName(), currentTicket.getName(), currentTicket.getCount());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Handles ticket cancellation process including validation and updates.
     */
    public void cancel() {
        checkName();

        if (currentMember == null)
            return;

        // Validate show name exists
        boolean flag = true;
        while (flag) {
            System.out.print("Please enter the show name you want to cancel >");
            String show = input.nextLine();
            currentTicket = new Ticket(show.trim());
            if (findTicket(currentTicket) == null) { // Show doesn't exist
                System.out.println("Please enter right show name!");
                displayMembers();
            } else {
                currentTicket = findTicket(currentTicket);
                flag = false;
            }
        }

        System.out.print("Please input the count of tickets you want to cancel >");
        try {
            int purchaseCount = input.nextInt();
            input.nextLine();
            if (purchaseCount <= 0) { // Prevent non-positive input
                System.out.println("Please enter a positive number!");
                return;
            }
            // Update ticket inventory (positive count)
            currentTicket.updateCount(purchaseCount);
            // Update member's purchase records (negative count)
            currentMember.purchase(currentTicket.getName(), -purchaseCount);
            System.out.println("Cancel successfully!");
        } catch (InputMismatchException e) { // Invalid number input
            System.out.println("Please input valid number!");
        } catch (NotEnoughTicketsException e) { // Insufficient tickets for cancellation
            System.out.println("Purchase failed! Please check your letters.");
        }
    }
}