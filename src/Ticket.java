public class Ticket implements Comparable<Ticket> {
    //field
    private final String name;
    private int count;//余票
    private final double price;

    //constructor
    Ticket() {//用不用写两个构造函数?
        name = "";
        count = 0;
        price = 0;
    }

    Ticket(String name, int count, double price) {
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
        return String.format("The number of %s of ticket available is: %d, the price is: %.2f .",
                getName(), getCount(), getPrice());
    }

    @Override //?需要干啥
    public int compareTo(Ticket o) {
        return 0;
    }
}
