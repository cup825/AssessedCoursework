import java.util.HashMap;
import java.util.Map;

public class Member implements Comparable<Member> {
    //field
    private final String firstName;
    private final String surname;
    private final HashMap<String, Integer> purchaseRecords = new HashMap<>();//表演名字对应数量。在这初始化。

    //method
    public Member(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }


//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getSurname() {
//        return surname;
//    }

    public String getName() {
        return firstName + " " + surname;
    }

    //@Override
    public String getMemberMessage(SortedLinkedList<Ticket> list) { //Member对象toString
        return "───────────────┼──────────────────────────────────────────────────\n" +
                String.format("%-15s│%-20s", firstName + " " + surname, recordToString(list));
    }


    public String recordToString(SortedLinkedList<Ticket> list) {
        StringBuilder res = new StringBuilder(); //拼接字符串
        double cost = 0.0, total = 0.0;
        if (purchaseRecords.isEmpty()) //暂无购买记录
            res.append("(No purchases yet)");
        else { //有买票记录时: 需要查询票的列表里对应单价，再*value
            int i = 0;

            //可以把以下一段作为方法提出来
            for (Map.Entry<String, Integer> entry : purchaseRecords.entrySet()) { //得到买票数量
                String key = entry.getKey();
                int value = entry.getValue();
                for (Ticket t : list) {//得到票单价
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

    @Override //比较姓氏，姓氏相同比较名字
    public int compareTo(Member o) {
        if (!this.surname.equals(o.surname)) { //如果姓不同，比较姓即可
            return this.surname.compareTo(o.surname);
        } else //如果姓相同，比较名
            return this.firstName.compareTo(o.firstName);
    }

    public void purchase(String name, int count) {
        //不需要处理==0，因为在主函数处理了(catch)
        if (!purchaseRecords.containsKey(name)) //首次购买该种
        {
            if (count < 0)//没有购买还取消了
            {
                throw new IllegalStateException("You have not purchased this type of ticket.");
            }
            if (purchaseRecords.size() == 3) //限制购买种类
                //throw new PurchaseLimitException();
                throw new IllegalStateException("Purchase failed! You cannot buy more than 3 ticket types.\n" +
                        "Remove an existing type or add more of a type you already own.");
            purchaseRecords.put(name, count);
        } else { //非首次购买该种
            if (count < 0 && -count > purchaseRecords.get(name)) {//如果取消 且 超过已有数量
                throw new IllegalStateException("You have not enough tickets.");
            }
            //这一句，如果count设置为负数，也可以作为取消的操作
            purchaseRecords.put(name, purchaseRecords.get(name) + count);//根据演出名查找hashmap对应数量，再做更新
        }

    }

}
