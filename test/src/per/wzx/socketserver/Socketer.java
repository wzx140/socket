package per.wzx.socketserver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by wzx on 17-2-25.
 */
public class Socketer {
    private HashMap<Integer, Socket> sockets = null;
    private HashMap<Integer, BufferedWriter> writers = null;
    private ArrayList<Integer> liveCilents = null;


    public Socketer() {
        sockets = new HashMap<>();
        writers = new HashMap<>();
        liveCilents = new ArrayList<>();
    }

    public void addSocket(Socket socket, Integer i) throws IOException {
        sockets.put(i, socket);
        writers.put(i, new BufferedWriter(new OutputStreamWriter(
                getSocket(i).getOutputStream(), "utf-8")));
        liveCilents.add(i);
        System.out.println("用户" + i + "已连接");
        getWriter(i).write("您已连接");
        getWriter(i).newLine();
        getWriter(i).flush();
        sendInfo("用户" + i + "已连接");
    }


    public void sendMessage(String str, Integer cilent) {
        System.out.println("用户" + cilent + "发送 " + str + " /////" + new Date());
        try {
            for (Integer i : liveCilents) {
                if (!i.equals(cilent)) {
                    writers.get(i).write("用户" + cilent + "：");
                    writers.get(i).write(str);
                    writers.get(i).newLine();
                    writers.get(i).flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfo(String str) {
        try {
            for (Integer i : liveCilents) {
                    writers.get(i).write(str);
                    writers.get(i).newLine();
                    writers.get(i).flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket(int i) {
            for (int j = 0; j < liveCilents.size(); j++) {
                if (liveCilents.get(j).equals(i)) {
                    liveCilents.remove(j);
                }
            }
            sockets.remove(i);
            writers.remove(i);
            System.out.println("用户" + i + "已断开连接");
    }

    public Socket getSocket(Integer i) {
        return sockets.get(i);
    }

    public BufferedWriter getWriter(Integer i) {
        return writers.get(i);
    }

}
