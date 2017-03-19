package per.wzx.socketserver;

import java.util.Scanner;

/**
 * Created by wzx on 17-2-25.
 */
public class ServerMain {
    public static void main(String[] args) {
        System.out.println("请输入本机地址及端口");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] strs = str.split(" ");
        MainControl control = new MainControl(strs[0],Integer.parseInt(strs[1]));
        control.start();
    }
}
