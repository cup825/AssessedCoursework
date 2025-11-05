import java.util.ListIterator;
import java.util.LinkedList;

public class SortedLinkedList<E extends Comparable<? super E>> extends LinkedList<E> {

    @Override
    public boolean add(E e) {
        ListIterator<E> it = this.listIterator();//获取 有序链表的迭代器。这个迭代器的类型是:Iterator<E>
        while (it.hasNext()) {
            E next = it.next();
            if (e.compareTo(next) <= 0) {
                it.previous();
                it.add(e);
                return true;
            }
        }
        it.add(e); //插入第一个元素，或插入到末尾
        return true;
    }
}


