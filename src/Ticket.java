public class Ticket implements Comparable<Ticket> {
    //field
    private final String name;
    private int count;//余票
    private final double price;

    //constructor
    public Ticket(String name) {//这里需要写两个构造函数，因为用户输入演出名时，不行
        this.name = name;
        count = 0;
        price = 0;
    }

    public Ticket(String name, int count, double price) {
        this.name = name;
        this.count = count;
        this.price = price;
    }

    //method
    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("│%-45s│%-18d│%5.2f    │", name, count, price);
    }

    @Override //比较票名
    public int compareTo(Ticket o) {
        return this.name.compareTo(o.name);
    }

    public void updateCount(int i) { //可正可负 购买-1，退票+1
        if (count + i < 0)
            throw new NotEnoughTicketsException();//当余票为负数时抛出异常
        count += i; //没异常时才更新
    }
}
