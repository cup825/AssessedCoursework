import java.util.HashMap;
import java.util.Map;

/**
 * Represents a member in the ticket management system.
 * Members can purchase tickets and maintain purchase records.
 * Implements Comparable interface for natural ordering by surname and first name.
 *
 * @author Ziyue Ren
 * @version 1.0
 * @see Ticket
 * @see SortedLinkedList
 */
public class Member implements Comparable<Member> {
    private final String firstName;
    private final String surName;
    private final HashMap<String, Integer> purchaseRecords = new HashMap<>(); // Show name to quantity mapping

    /**
     * Constructs a new Member with given first name and surname.
     *
     * @param firstName the member's first name
     * @param surName the member's surname
     */
    public Member(String firstName, String surName) {
        this.firstName = firstName;
        this.surName = surName;
    }

    /**
     * Returns the full name of the member.
     *
     * @return full name as "firstName surname"
     */
    public String getName() {
        return firstName + " " + surName;
    }

    /**
     * Generates formatted member information message.
     *
     * @param list the ticket list for price lookup
     * @return formatted member message string
     */
    public String getMemberMessage(SortedLinkedList<Ticket> list) {
        return "───────────────┼──────────────────────────────────────────────────\n" +
                String.format("%-15s│%-20s", firstName + " " + surName, recordToString(list));
    }

    /**
     * Converts purchase records to formatted string with cost calculations.
     *
     * @param list the ticket list for price lookup
     * @return formatted purchase record string
     */
    public String recordToString(SortedLinkedList<Ticket> list) {
        StringBuilder res = new StringBuilder(); // For string concatenation
        double cost = 0.0, total = 0.0;

        if (purchaseRecords.isEmpty()) { // No purchase records yet
            res.append("(No purchases yet)");
        } else { // Has purchase records: need to lookup ticket prices and calculate costs
            int i = 0;

            for (Map.Entry<String, Integer> entry : purchaseRecords.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                for (Ticket t : list) { // Find ticket price
                    if (t.getName().equals(key)) {
                        cost = t.getPrice() * value; // Cost = unit price × quantity
                        total += cost;
                        break;
                    }
                }

                String left = key + ":" + value;
                if (i == 0) {
                    res.append(String.format("%-40s %8.2f£ %n", left, cost));
                } else {
                    res.append(String.format("%-15s│%-40s %8.2f£ %n", "", left, cost));
                }
                i++;
            }

            res.append(String.format("%-15s│%-40s %8.2f£", "", "Total cost: ", total)); // Add padding
        }

        return res.toString();
    }

    /**
     * Compares members by surname, then by first name if surnames are equal.
     *
     * @param o the other member to compare with
     * @return negative, zero, or positive integer based on comparison
     */
    @Override
    public int compareTo(Member o) {
        if (!this.surName.equals(o.surName)) {
            return this.surName.compareTo(o.surName);
        } else {
            return this.firstName.compareTo(o.firstName);
        }
    }

    /**
     * Purchases or cancels tickets for a show.
     *
     * @param name the show name
     * @param count the number of tickets (positive for purchase, negative for cancel)
     * @throws IllegalStateException if purchase limits exceeded or insufficient tickets for cancel
     */
    public void purchase(String name, int count) {
        if (!purchaseRecords.containsKey(name)) { // First time purchasing this show type
            if (count < 0) { // Trying to cancel without existing purchase
                throw new IllegalStateException("You have not purchased this type of ticket.");
            }
            if (purchaseRecords.size() == 3) { // Limit of 3 show types
                throw new IllegalStateException("Purchase failed! You cannot buy more than 3 ticket types.\n" +
                        "Remove an existing type or add more of a type you already own.");
            }
            purchaseRecords.put(name, count);
        } else { // Already purchased this show type before
            if (count < 0 && -count > purchaseRecords.get(name)) { // Cancel amount exceeds owned quantity
                throw new IllegalStateException("You have not enough tickets.");
            }
            // Update quantity (negative count works as cancellation)
            purchaseRecords.put(name, purchaseRecords.get(name) + count);
        }
    }
}