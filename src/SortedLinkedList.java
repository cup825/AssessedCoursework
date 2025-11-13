import java.util.ListIterator;
import java.util.LinkedList;

/**
 * A sorted linked list implementation that maintains elements in natural order.
 * Extends LinkedList and overrides add method to maintain sorted order.
 *
 * @param <E> the type of elements in this list, which must implement Comparable
 * @author Ziyue Ren
 * @version 1.0
 * @see LinkedList
 * @since 2025
 */
public class SortedLinkedList<E extends Comparable<? super E>> extends LinkedList<E> {
    /**
     * Adds an element while maintaining sorted order.
     *
     * @param e the element to add
     * @return true (as specified by Collection.add)
     */
    @Override
    public boolean add(E e) {
        ListIterator<E> it = this.listIterator(); // Get iterator for sorted list
        while (it.hasNext()) {
            E next = it.next();
            if (e.compareTo(next) <= 0) {
                it.previous();
                it.add(e);
                return true;
            }
        }
        it.add(e); // Insert as first element or at end
        return true;
    }
}