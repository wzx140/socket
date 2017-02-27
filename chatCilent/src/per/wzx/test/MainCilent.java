package per.wzx.test;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by wzx on 17-2-25.
 */
public class MainCilent {
    public static void main(String[] args) {
        System.out.println("请输入服务器的地址和端口");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] strs = str.split(" ");
        try {
            Socket socket = new Socket(strs[0], Integer.parseInt(strs[1]));
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MainFrame frame = new MainFrame("cilent", socket);
                    frame.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
