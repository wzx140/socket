package per.wzx.socketserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by wzx on 17-2-25.
 */
public class MainControl extends Thread{
private String hostIp;
private int port;

    public MainControl(String hostIp, int port) {
        this.hostIp = hostIp;
        this.port = port;
    }

    @Override
    public void run() {
        super.run();
        try {
            ServerSocket serverSocket = new ServerSocket(port, 10,
                    InetAddress.getByName(hostIp));
            Socketer socketer = new Socketer();
            int i=1;
            while (true) {
                socketer.addSocket(serverSocket.accept(),i);
                ServerControl control = new ServerControl(socketer,i);
                new Thread(control).start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
