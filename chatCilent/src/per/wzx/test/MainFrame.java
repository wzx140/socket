package per.wzx.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by wzx on 17-2-25.
 */
public class MainFrame extends JFrame{
    private MainPanel panel;
    private Socket socket;

    public MainFrame(String title, Socket socket) throws HeadlessException {
        super(title);
        this.socket = socket;
        panel = new MainPanel(socket);
        initGUI();
        initListenner();
    }

    public void initGUI() {
        setLocationRelativeTo(null);
        setContentPane(panel);
        pack();
        setVisible(true);
    }

    public void start() {
        new Thread(panel).start();
    }
    public void initListenner() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                BufferedWriter writer=null;
                try {
                   writer= new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
                   writer.write(".close");
                   writer.newLine();
                   writer.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }finally {
                    System.exit(0);
                    if (writer != null) {
                        try {
                            writer.close();
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }


            }
        });
    }
}
