import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainProgram {
    public static SortedLinkedList<Member> memberList = new SortedLinkedList<>();//成员列表
    public static SortedLinkedList<Ticket> ticketList = new SortedLinkedList<>();//每个成员，票的列表
    public static int memberCount;
    public static int showCount;

    //    你的程序应该从文件中读取已注册会员列表和可用票证列表。
//    输入文件的内容应采用以下格式：第一行包含一个整数，表示已注册会员的数量，
//    后面是会员信息（每个会员一行，包含其名字和姓氏）。
    public static void loadList() {
        try {
            Scanner s = new Scanner(new File("input_data.txt"));
            //s.nextLine();
            memberCount = s.nextInt();//第一行数字
            s.nextLine();//到姓名那一行
            for (int i = 0; i < memberCount; i++) {
                String line = s.nextLine();//读取会员名
                String[] str = line.split(" ");
                Member mem = new Member(str[0].trim(), str[1].trim());
                memberList.add(mem);
            }

            showCount = s.nextInt();
            s.nextLine();//!
            for (int i = 0; i < showCount; i++) {//一次循环读三行
                try {
                    Ticket tic = new Ticket(
                            s.nextLine(),
                            Integer.parseInt(s.nextLine()),
                            Double.parseDouble(s.nextLine())
                    );
                    ticketList.add(tic);
                } catch (NumberFormatException e) {
                    System.out.println("转换int/double时失败");
                }


            }

        } catch (FileNotFoundException e) {
            System.out.println("The file can not find!");
        }
    }

    //    f- 完成程序的运行。


    public static void printMenu() {
        System.out.println("""
                ┌──────── Show & Member Management Menu ────────┐
                │ t: List all shows' messages                   │
                │ m: List all members' messages                 │
                │ b: Buy new tickets                            │
                │ c: Cancel tickets                             │
                └──────── Enter 'f' to quit the program ────────┘
                Please type your option and press Enter >
                """);
    }

    public static void acceptOption() {
        Scanner input = new Scanner(System.in);
        loadList();
        boolean flag = true;
        while (flag) {
            printMenu();
            String line = input.nextLine();
            if (line.isEmpty()) {//check empty
                System.out.println("Input can not be empty!");
                continue;
            }
            if (line.length() != 1) {//check length
                System.out.println("Please type valid option!");
                continue;
            }
            char ch = line.charAt(0);
            switch (ch) {
                case 'f':
                    System.out.println("You have exited.");
                    flag = false;
                    break;
                case 't':
                    displayShows();
                    break;
                case 'm':
                    displayMembers();
                    break;
                case 'b':
                    buy();
                    break;
                case 'c':
                    cancel();
                    break;
                default:
                    System.out.println("Please type valid option!");
                    break;
            }
        }
    }

    //    t- 在屏幕上显示所有演出的信息，包括剩余门票数量和每张门票的价格。
    public static void displayShows() {
        System.out.println("_____________________________________________________________________________");
        System.out.printf("%-50s|%-20s|%-20s %n", "SHOW NAME", "TICKETS AVAILABLE", "PRICE");
        for (Ticket t : ticketList)
            System.out.println(t.toString());
        System.out.println("_____________________________________________________________________________\n");
    }

    //    m- 在屏幕上显示所有会员的信息，包括他们持有每种票的数量、每种票的总价以及所有票的总价。
    public static void displayMembers() {
        for (Member m : memberList) {
            System.out.println(m.toString());
        }
        System.out.println();//空行
    }

    //    b- 当注册会员购买指定数量的指定票证并添加到其帐户时，更新存储的数据。
    //需要修改的数据如下:
    //Member类 map<String, Integer>，purchaseTickets，根据用户输入
    //ticketList里的某项Ticket
    //步骤如下：
    public static void buy() {
        System.out.println("Please enter your full name(split by space)>");
        Scanner s = new Scanner(System.in);
        String[] userName = s.nextLine().split(" ");

        try {
            Member mem = new Member(userName[0], userName[1]);
        } catch (Exception e) {
            System.out.println("Input invalid format name!");
            return; //需要重新操作
        }


        //检查名字是否存在, 不存在则return
        if (!isMemberExist(userName[0], userName[1])) {
            System.out.println("Name is not exist!");
            return;
        }

        int i = 0;
        for (Ticket t : ticketList) {//向用户展示演出名字
            System.out.println(i + ":" + t.getName());
            i++;
        }

//        System.out.println("Please input the number of show. ");
//        int num = s.nextInt();//演出编号，也是ticketList所对应的序号
//        ticketList.get(i).getCount();

    }


    public static boolean isMemberExist(String fName, String lName) {
        for (Member m : MainProgram.memberList) {
            if (fName.equals(m.getFirstName()) && lName.equals(m.getSurname()))
                return true;
        }
        return false;
    }


    //    c- 当注册会员取消指定数量的指定票证并将其从其帐户中删除时，更新存储的数据。
    public static void cancel() {

    }


    public static void main(String[] args) {
        //printMenu();
        acceptOption();


    }

}
