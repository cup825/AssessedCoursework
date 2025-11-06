/**
 * Represents a ticket for a performance/show.
 * Contains information about the show name, available ticket count, and price.
 * Implements Comparable interface for natural ordering by ticket name.
 *
 * @author Ziyue Ren
 * @version 1.0
 */
public class Ticket implements Comparable<Ticket> {
    private final String name;
    private int count; // Remaining tickets
    private final double price;

    /**
     * Constructs a ticket with only name (for search purposes).
     *
     * @param name the show name
     */
    public Ticket(String name) {
        this.name = name;
        count = 0;
        price = 0;
    }

    /**
     * Constructs a ticket with full details.
     *
     * @param name the show name
     * @param count the available ticket count
     * @param price the ticket price
     */
    public Ticket(String name, int count, double price) {
        this.name = name;
        this.count = count;
        this.price = price;
    }

    /**
     * Returns the show name.
     *
     * @return the show name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the available ticket count.
     *
     * @return the ticket count
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns the ticket price.
     *
     * @return the ticket price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns formatted string representation of the ticket.
     *
     * @return formatted ticket string
     */
    @Override
    public String toString() {
        return String.format("│%-45s│%-18d│%5.2f    │", name, count, price);
    }

    /**
     * Compares tickets by show name.
     *
     * @param ticket the ticket to compare with
     * @return comparison result
     */
    @Override
    public int compareTo(Ticket ticket) {
        return this.name.compareTo(ticket.name);
    }

    /**
     * Updates the ticket count (positive for cancel, negative for purchase).
     *
     * @param i the count change amount
     * @throws NotEnoughTicketsException if count would become negative
     */
    public void updateCount(int i) {
        if (count + i < 0) {
            throw new NotEnoughTicketsException(); // Throw exception if count goes negative
        }
        count += i;
    }
}