import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.*;

public class MainProgram {
    public static SortedLinkedList<Member> memberList = new SortedLinkedList<>();//成员列表
    public static SortedLinkedList<Ticket> ticketList = new SortedLinkedList<>();//每个成员，票的列表
    public static int memberCount;
    public static int showCount;
    public static Member mem;//*这两个是不是在类里声明比较好？
    public static Ticket tic;

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

    public static void printMenu() {
        System.out.print("""
                ╭──────── Show & Member Management Menu ────────╮
                │ t: List all shows' messages                   │
                │ m: List all members' messages                 │
                │ b: Buy new tickets                            │
                │ c: Cancel tickets                             │
                ╰──────── Enter 'f' to quit the program ────────╯
                Please type your option and press Enter >""");
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
            char ch = line.charAt(0); //*提取字符串第一位字符 ？是否有必要
            switch (ch) {
                case 'f':  //f- 完成程序的运行。
                    System.out.println("You have exited the program.");
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
        System.out.println("┌─────────────────────────────────────────────┬──────────────────┬─────────┐");
        System.out.printf("│%-45s│%-18s│%-9s│%n", "SHOW NAME", "TICKETS AVAILABLE", "PRICE(£)");
        System.out.println("├─────────────────────────────────────────────┼──────────────────┼─────────┤");
        for (Ticket t : ticketList)
            System.out.println(t.toString());
        System.out.println("└─────────────────────────────────────────────┴──────────────────┴─────────┘\n");
    }

    //    m- 在屏幕上显示所有会员的信息，包括他们持有每种票的数量、每种票的总价以及所有票的总价。
    public static void displayMembers() {
        System.out.println("_____________________________________________________________________________");
        System.out.printf("%-15s│%-50s %n", "MEMBER NAME", "PURCHASE RECORD");
        for (Member m : memberList) {
            System.out.println(m.toString());
        }
        System.out.println("_____________________________________________________________________________");
    }

    //*思考：先实例化Member，传入Member对象。还是传入名字，用返回值实例化。哪种更好？
    //返回一个列表对应的对象，方便后续使用
//    public static Member isMemberExist(String fName, String lName) { //原来写的布尔值，思考后改为Member
//        for (Member m : MainProgram.memberList) {
//            if (fName.equals(m.getFirstName()) && lName.equals(m.getSurname()))
//                return m;
//        }
//        return null;
//    }

    public static Member findMember(Member mem) {
        for (Member m : memberList) {
            if (m.compareTo(mem) == 0)
                return m;//返回列表已有对象
            else if (m.compareTo(mem) > 0)
                return null;
        }
        return null;
    }

    public static Ticket findTicket(Ticket tic) {
        for (Ticket t : ticketList) {
            if (t.compareTo(tic) == 0) {
                return t; // 返回列表中已有的对象
            } else if (t.compareTo(tic) > 0)
                return null;
        }
        return null;
    }

    //    b- 当注册会员购买指定数量的指定票证并添加到其帐户时，更新存储的数据。
    //需要修改的数据如下:
    //Member类 map<String, Integer>，purchaseRecords，根据用户输入
    //ticketList里的某项Ticket
    //步骤如下：
    public static void buy() {
        Scanner s = new Scanner(System.in);

        System.out.print("Please enter your full name(split by space)>");
        //①检查会员是否列表 能否通过两个检查操作，将mem和tic与其相应列表对应
        try {
            String[] userName = s.nextLine().split(" ");
            mem = new Member(userName[0].trim(), userName[1].trim());
            if (findMember(mem) == null) {
                System.out.println("Name is not exist!");
                return;
            } else
                mem = findMember(mem);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Input invalid format name!");
            return;//这里异常怎么还要手动return
        }

        //②检查门票是否在列表
        boolean flag = false;
        while (!flag) {
            System.out.print("Please enter the show name you want to buy >");
            String show = s.nextLine();
            tic = new Ticket(show.trim());
            if (findTicket(tic) == null) { //如果不存在
                System.out.println("Show is not exist!Try again.\n" +
                        "The list of show is as follows:");
                displayShows();
            } else {
                tic = findTicket(tic);
                flag = true;
            }
        }

        System.out.println("Please input the count of tickets you want to buy >");
        try {
            int purchaseCount = s.nextInt();
            if (purchaseCount <= 0) { //禁止输入非正数
                System.out.println("Please enter a positive number!");
                return;
            }
            //①更新库存(ticket)信息 传入值为负数
            tic.updateCount(-purchaseCount);
            //②更新会员信息的hashmap 存名字，和对应票数量
            mem.purchase(tic.getName(), purchaseCount);
            System.out.println("Purchase successfully!");
        } catch (InputMismatchException e) {//输入非数字？
            System.out.println("Please input valid number!");
            return;
        } catch (NotEnoughTicketsException e) { //捕捉该方法异常，防止票不足还售卖 //写信
            System.out.println("Purchase failed! Please check your letters.");
            PrintWriter outFile = null;
            try {
                outFile = new PrintWriter(new FileWriter("letter.txt", true));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            outFile.printf("""
                    %s
                    Dear %s,
                    
                    I’m sorry, but we couldn’t complete your ticket purchase.
                    There are not enough tickets available for '%s' — only %d left.
                    
                    Please try again later or choose another option.
                    
                    Kind regards,
                    NEAT Ticket Office
                    
                    """, getTime(), mem.getName(), tic.getName(), tic.getCount());
            outFile.close();
            return;
        } catch (PurchaseLimitException e) {
            System.out.println("Purchase failed! Please check your letters.");
            PrintWriter outFile = null;
            try {
                outFile = new PrintWriter(new FileWriter("letter.txt", true));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            outFile.printf("""
                    %s
                    Dear %s,
                    
                    I’m sorry, but we couldn’t complete your ticket purchase.
                    You cannot buy more than three different types of tickets.
                    
                    However, you can still buy more tickets for the shows you already have,
                    or cancel one of your existing ticket types before adding a new one.
                    
                    Please try again later or choose another option.
                    
                    Kind regards,
                    NEAT Ticket Office
                    
                    """, getTime(), mem.getName());
            outFile.close();
            return;
        }

    }

    public static void writeLetter(String name) throws FileNotFoundException {


    }

    public static String getTime() {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return current.format(formatter);
    }

    //    c- 当注册会员取消指定数量的指定票证并将其从其帐户中删除时，更新存储的数据。
    public static void cancel() {

    }


    public static void main(String[] args) {
        //printMenu();
        acceptOption();


    }

}
