import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainProgram {

    //    你的程序应该从文件中读取已注册会员列表和可用票证列表。
//    输入文件的内容应采用以下格式：第一行包含一个整数，表示已注册会员的数量，
//    后面是会员信息（每个会员一行，包含其名字和姓氏）。
    public static boolean loadList() {
        try {
            Scanner s = new Scanner(new File("input_data.txt"));
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("The file can not find!");
            return false;
        }
    }

    //    f- 完成程序的运行。
//    t- 在屏幕上显示所有演出的信息，包括剩余门票数量和每张门票的价格。
//    m- 在屏幕上显示所有会员的信息，包括他们持有每种票的数量、每种票的总价以及所有票的总价。
//    b- 当注册会员购买指定数量的指定票证并添加到其帐户时，更新存储的数据。
//    c- 当注册会员取消指定数量的指定票证并将其从其帐户中删除时，更新存储的数据。
    public static void printMenu() {
        System.out.println("""
                -----------Show & Member Management Menu----------
                t - List all shows' messages
                m - List all members' messages
                b - Buy new tickets
                c - Cancel tickets
                ----------Enter 'f' to quit the program-----------
                Please type your option and press Enter >
                """);
    }

    public static void acceptOption() {
        Scanner input = new Scanner(System.in);
        loadList();
        boolean flag = true;
        while (flag) {
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
                    listShows();
                    break;
                case 'm':
                    listMembers();
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


    public static void listShows() {

    }

    public static void listMembers() {

    }

    public static void buy() {

    }

    public static void cancel() {

    }


    public static void main(String[] args) {
        printMenu();
        acceptOption();


    }

}
