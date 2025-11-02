import java.util.HashMap;

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

    //?? 种类最多3种，但总数可以超过3张。
    public HashMap<String, Integer> purchaseRecords = new HashMap<>();//表演名字对应数量。在这初始化。
    //private final int maxOfType = 3;//??

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
        //String map;
        StringBuilder sMap = new StringBuilder(); //拼接字符串
        if (purchaseRecords.isEmpty())
            sMap.append("(No purchases yet)"); //暂无购买记录
        else { //有买票记录时
            purchaseRecords.forEach((key, value) -> { //lambda表达式
                //map=map+key+":"+value;
                sMap.append(key).append(":").append(value).append(" ");
            });
        }
        return String.format("%-15s│%-50s", firstName + " " + surname, sMap.toString());
    }

    @Override //比较姓氏，姓氏相同比较名字
    public int compareTo(Member o) {
        if (!this.surname.equals(o.surname)) { //如果姓不同，比较姓即可
            return this.surname.compareTo(o.surname);
        } else //如果姓相同，比较名
            return this.firstName.compareTo(o.firstName);
    }

    public void purchase(String name, int count) {
        if (!purchaseRecords.containsKey(name)) //首次购买该种
        {
            if (purchaseRecords.size() == 3) //限制购买种类
                throw new PurchaseLimitException();
            purchaseRecords.put(name, count);
        } else { //非首次购买该种
            purchaseRecords.put(name, purchaseRecords.get(name) + count);//根据演出名查找hashmap对应数量，再做更新
        }

    }

}
