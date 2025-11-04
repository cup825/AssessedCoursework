import java.util.HashMap;
import java.util.Map;

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
    public HashMap<String, Integer> purchaseRecords = new HashMap<>();//表演名字对应数量。在这初始化。

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

    public String getName() {
        return firstName + " " + surname;
    }

    @Override
    public String toString() { //Member对象toString
        return "───────────────┼──────────────────────────────────────────────────\n" +
                String.format("%-15s│%-20s", firstName + " " + surname, recordToString());
    }


    public String recordToString() {//不用lambda表达式
        StringBuilder res = new StringBuilder(); //拼接字符串
        double cost = 0.0, total = 0.0;
        if (purchaseRecords.isEmpty())
            res.append("(No purchases yet)"); //暂无购买记录
        else { //有买票记录时: 需要查询票的列表里对应单价，再*value
            int i = 0;
            for (Map.Entry<String, Integer> entry : purchaseRecords.entrySet()) { //从循环1得到买票数量
                String key = entry.getKey();
                int value = entry.getValue();
                for (Ticket t : MainProgram.ticketList) {//从循环2得到票单价
                    if (t.getName().equals(key)) { //找到单价后break
                        cost = t.getPrice() * value;//花费=单价*数量
                        total += cost;
                        break;
                    }
                }
                String left = key + ":" + value;
                if (i == 0)
                    res.append(String.format("%-40s %8.2f£ %n", left, cost));
                else
                    res.append(String.format("%-15s│%-40s %8.2f£ %n", "", left, cost));
                i++;
            }

            res.append(String.format("%-15s│%-40s %8.2f£", "", "Total cost: ", total));//补空格
        }

        return res.toString();
    }

    //    public String recordToString() {
//        StringBuilder sMap = new StringBuilder(); //拼接字符串
//        double total=0.0;
//        if (purchaseRecords.isEmpty())
//            sMap.append("(No purchases yet)"); //暂无购买记录
//        else { //有买票记录时 需要查询票的列表里对应单价，再*value
//            purchaseRecords.forEach((key, value) -> { //lambda表达式
//                double cost = 0.0;
//                //拼接票名，数量，该票总花费
//                //花费：数量x价格 查询名字对应的价格,能不能写个名字价格map？
//                for (Ticket t : MainProgram.ticketList) {
//                    if (t.getName().equals(key)) { //找到单价后break
//                        cost = t.getPrice() * value;//花费=单价*数量
//                        break;
//                    }
//                }
//                total += cost;
//                sMap.append(key).append(":").append(value)
//                        .append("(").append(cost).append("£)\n");
//            });
//            sMap.append("Total cost: ").append(String.format("%.2f", total)).append("£");
//        }
//        return sMap.toString();
//    }

    @Override //比较姓氏，姓氏相同比较名字
    public int compareTo(Member o) {
        if (!this.surname.equals(o.surname)) { //如果姓不同，比较姓即可
            return this.surname.compareTo(o.surname);
        } else //如果姓相同，比较名
            return this.firstName.compareTo(o.firstName);
    }

    public void purchase(String name, int count) {
        if (count == 0) return;
        if (!purchaseRecords.containsKey(name)) //首次购买该种
        {
            if (count < 0)//没有购买还取消了
            {
                System.out.println("You have not purchased this type of ticket.");
                return;
            }
            if (purchaseRecords.size() == 3) //限制购买种类
                throw new PurchaseLimitException();
            purchaseRecords.put(name, count);
        } else { //非首次购买该种
            if (count < 0 && -count > purchaseRecords.get(name)) {//如果取消 且 超过已有数量
                System.out.println("You have not enough tickets.");
                return;
            }
            //这一句，如果count设置为负数，也可以作为取消的操作
            purchaseRecords.put(name, purchaseRecords.get(name) + count);//根据演出名查找hashmap对应数量，再做更新
        }

    }

}
