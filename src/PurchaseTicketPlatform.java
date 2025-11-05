import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PurchaseTicketPlatform {
    public static final SortedLinkedList<Member> memberList = new SortedLinkedList<>();//成员列表
    public static final SortedLinkedList<Ticket> ticketList = new SortedLinkedList<>();//每个成员，票的列表
    private Member mem;//*这两个是不是在类里声明比较好？
    private Ticket tic;
    private final Scanner input = new Scanner(System.in); //要在类内初始化，但实际直到nextline/int()等才开始读取的

    //    你的程序应该从文件中读取已注册会员列表和可用票证列表。
//    输入文件的内容应采用以下格式：第一行包含一个整数，表示已注册会员的数量，
//    后面是会员信息（每个会员一行，包含其名字和姓氏）。

    public void loadList() {
        try {
            Scanner s = new Scanner(new File("input_data.txt"));
            //s.nextLine();
            int memberCount = s.nextInt();//第一行数字
            s.nextLine();//到姓名那一行
            for (int i = 0; i < memberCount; i++) {
                String line = s.nextLine();//读取会员名
                String[] str = line.split(" ");
                Member mem = new Member(str[0].trim(), str[1].trim());
                memberList.add(mem);
            }

            int showCount = s.nextInt();
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

    public void printMenu() {
        System.out.print("""
                ╭──────── Show & Member Management Menu ────────╮
                │ t: List all shows' messages                   │
                │ m: List all members' messages                 │
                │ b: Buy new tickets                            │
                │ c: Cancel tickets                             │
                ╰──────── Enter 'f' to quit the program ────────╯
                Please type your option and press Enter >""");
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        loadList();
        boolean flag = true;
        while (flag) try {
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
        } catch (PrintOnlyException e) {

            System.out.println(e.getMessage());
        }
    }

    //    t- 在屏幕上显示所有演出的信息，包括剩余门票数量和每张门票的价格。
    public void displayShows() {
        System.out.println("┌─────────────────────────────────────────────┬──────────────────┬─────────┐");
        System.out.printf("│%-45s│%-18s│%-9s│%n", "SHOW NAME", "TICKETS AVAILABLE", "PRICE(£)");
        System.out.println("├─────────────────────────────────────────────┼──────────────────┼─────────┤");
        for (Ticket t : ticketList)
            System.out.println(t.toString());
        System.out.println("└─────────────────────────────────────────────┴──────────────────┴─────────┘\n");
    }

    //    m- 在屏幕上显示所有会员的信息，包括他们持有每种票的数量、每种票的总价以及所有票的总价。
    public void displayMembers() {
        System.out.println("───────────────┬──────────────────────────────────────────────────");
        System.out.printf("%-15s│%-50s %n", "MEMBER NAME", "PURCHASE RECORD");
        for (Member m : memberList) {
            System.out.println(m.toString());
        }
        System.out.println("───────────────┴──────────────────────────────────────────────────");
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

    public Member findMember(Member mem) {
        for (Member m : memberList) {
            if (m.compareTo(mem) == 0)
                return m;//返回列表已有对象
            else if (m.compareTo(mem) > 0)
                return null;
        }
        return null;
    }

    public Ticket findTicket(Ticket tic) {
        for (Ticket t : ticketList) {
            if (t.compareTo(tic) == 0) {
                return t; // 返回列表中已有的对象
            } else if (t.compareTo(tic) > 0)
                return null;
        }
        return null;
    }

    public String getTime() {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return current.format(formatter);
    }

    public void checkName() {
        //Scanner s = new Scanner(System.in);
        System.out.print("Please enter your full name(split by space)>");
        //①检查会员是否列表
        try {
            String[] userName = input.nextLine().split(" ");
            mem = new Member(userName[0].trim(), userName[1].trim());
            if (findMember(mem) == null) {
                System.out.println("Name is not exist!");
                //return;
            } else
                mem = findMember(mem);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Input invalid format name!");
            //return;
        }
    }

    //    b- 当注册会员购买指定数量的指定票证并添加到其帐户时，更新存储的数据。
    //需要修改的数据如下:
    //Member类 map<String, Integer>，purchaseRecords，根据用户输入
    //ticketList里的某项Ticket
    //步骤如下：
    public void buy() {
        checkName();//①检查会员名是否存在列表
        //②检查门票是否在列表
        boolean flag = false;
        while (!flag) {
            System.out.print("Please enter the show name you want to buy >");
            String show = input.nextLine();
            tic = new Ticket(show.trim());
            if (findTicket(tic) == null) { //如果不存在 //写信1
                System.out.println("Purchase failed! Please check your letters.");
                displayShows();

                try (PrintWriter outFile = new PrintWriter(new FileWriter("letter.txt", true))) {
                    outFile.printf("""
                            %s
                            Dear %s,
                            
                            I’m sorry, but we couldn’t complete your ticket purchase.
                            Unfortunately, '%s' is not an available ticket at NEAT.
                            
                            Please check shows' messages and try again.
                            
                            Kind regards,
                            NEAT Ticket Office
                            
                            """, getTime(), mem.getName(), tic.getName());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                tic = findTicket(tic);
                flag = true;
            }
        }

        System.out.println("Please input the count of tickets you want to buy >");
        try {
            int purchaseCount = input.nextInt();
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
            //return;
        } catch (NotEnoughTicketsException e) { //捕捉该方法异常，防止票不足还售卖 //写信2
            System.out.println("Purchase failed! Please check your letters.");
            //PrintWriter outFile = null;
            try (PrintWriter outFile = new PrintWriter(new FileWriter("letter.txt", true))) {
                outFile.printf("""
                        %s
                        Dear %s,
                        
                        I’m sorry, but we couldn’t complete your ticket purchase.
                        There are not enough tickets available for '%s' — only %d left.
                        
                        Please try again later or choose another option.
                        
                        Kind regards,
                        NEAT Ticket Office
                        
                        """, getTime(), mem.getName(), tic.getName(), tic.getCount());
                //return;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (PrintOnlyException e) { //票超过限购种类
            System.out.println(e.getMessage());
        }

    }


    //    c- 当注册会员取消指定数量的指定票证并将其从其帐户中删除时，更新存储的数据。
    public void cancel() {
        checkName();
        //②检查门票是否在列表
        boolean flag = true;
        while (flag) {
            System.out.print("Please enter the show name you want to cancel >");
            String show = input.nextLine();
            tic = new Ticket(show.trim());
            if (findTicket(tic) == null) { //如果不存在
                System.out.println("Please enter right show name!");
                displayMembers();

            } else {
                tic = findTicket(tic);
                flag = false;
            }
        }

        System.out.println("Please input the count of tickets you want to cancel >");
        try {
            int purchaseCount = input.nextInt();
            if (purchaseCount <= 0) { //禁止输入非正数
                System.out.println("Please enter a positive number!");
                return;
            }
            //①更新库存(ticket)信息 传入值为正数，库存减
            tic.updateCount(purchaseCount);
            //②更新会员信息的hashmap 存名字，和对应票数量
            //mem.purchase(tic.getName(), purchaseCount);
            mem.purchase(tic.getName(), -purchaseCount);//能不能通过传入负值？ 可以
            System.out.println("Cancel successfully!");
        } catch (InputMismatchException e) {//输入非数字？
            System.out.println("Please input valid number!");
            //return;
        } catch (NotEnoughTicketsException e) { //捕捉该方法异常，防止票不足还售卖 //写信2
            System.out.println("Purchase failed! Please check your letters.");
        } catch (PrintOnlyException e) {
            System.out.println(e.getMessage());
        }

    }


}
