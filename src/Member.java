
public class Member implements Comparable<Member> {
    //For each member, the office should know their first name, surname
//
//and their purchased tickets together with the quantity of each type of ticket they have purchased.
//To avoid abuse of the discount, a member can hold at most three different types of ticket at a time
//(but can hold more than three tickets in total).
//We also assume that no two members share both their first name and their surname.
    //field
    private final String firstName;
    private final String surname;

    private String ticket;//?? 种类最多3种，但总数可以超过3张。
    private String count;//??
    private final int maxOfType = 3;//??

    Member(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }


    //method
    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, surname);

    }

    public boolean equals(Member m2) {
        return (this.firstName.equals(m2.surname) && this.surname.equals(m2.surname));
    }

    @Override //比较姓氏，姓氏相同比较名字
    public int compareTo(Member o) {
        if (!this.firstName.equals(o.firstName)) { //如果名不同，比较名即可
            return this.firstName.compareTo(o.firstName);
        } else //如果名相同，比较姓
            return this.surname.compareTo(o.surname);
    }
}
