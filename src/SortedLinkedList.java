import java.util.ListIterator;
import java.util.LinkedList;
import java.lang.Comparable;

public class SortedLinkedList<E extends Comparable<? super E>> extends LinkedList<E> {

    @Override
    public boolean add(E e) {
        ListIterator<E> it = this.listIterator();//获取 有序链表的迭代器。这个迭代器的类型是:Iterator<E>
//        if (this.size() == 0) {
//            it.add(e);
//            return true; //第一个元素
//        }
        while (it.hasNext()) {
            E next = it.next();
            if (e.compareTo(next) < 0) {
                it.previous();
                it.add(e);
                return true;
            }
        }
        it.add(e); //插入第一个元素，或插入到末尾
        return true;
    }

    public static void main(String[] args) {
        SortedLinkedList<String> list = new SortedLinkedList<>();
        list.add("Bob");
        list.add("Sam");
        list.add("Amy");
        for (String s : list) {
            System.out.println(s);
        }

    }


}


