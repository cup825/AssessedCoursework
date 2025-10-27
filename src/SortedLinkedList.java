import java.util.ListIterator;
import java.util.LinkedList;
import java.lang.Comparable;

public class SortedLinkedList<E extends Comparable<E>> extends LinkedList<E> {

    public boolean insert(E element) {
        ListIterator<E> it = this.listIterator();//获取 有序链表的迭代器。这个迭代器的类型是:Iterator<E>
//        if (this.size() == 0) {
//            it.add(element);
//            return true; //第一个元素
//        }
        while (it.hasNext()) {
            var next = it.next();
            if (element.compareTo(next) < 0) {
                it.previous();
                it.add(element);
                return true;
            }
        }
        it.add(element);
        return true;
    }

    static void main(String[] args) {
        SortedLinkedList<String> list = new SortedLinkedList<>();
        list.insert("Bob");
        list.insert("Sam");
        list.insert("Amy");
        for (String s : list) {
            IO.println(s);
        }

    }


}


