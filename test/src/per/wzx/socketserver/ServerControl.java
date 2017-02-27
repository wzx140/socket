package per.wzx.socketserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wzx on 17-2-25.
 */
public class ServerControl implements Runnable {
    private Socketer socketer = null;
    private BufferedReader reader = null;
    private String tempStr = null;
    private Integer i;

    public ServerControl(Socketer socketer, Integer i) {
        this.socketer = socketer;
        this.i = i;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    socketer.getSocket(i).getInputStream(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out:while ((tempStr = reader.readLine()) != null) {
                if (tempStr.equals(".close")) {
                    socketer.closeSocket(i);
                    socketer.sendInfo("用户"+i+"已下线");
                    break out;
                } else {
                    socketer.sendMessage(tempStr, i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}