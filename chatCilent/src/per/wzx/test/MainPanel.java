package per.wzx.test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 * Created by wzx on 17-2-25.
 */
public class MainPanel extends JPanel implements Runnable{
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private JScrollPane pane;
    private Socket socket;
    private String temp;
    private BufferedReader reader;
    private BufferedWriter writer;

    public MainPanel(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(),"utf-8")
            );
            writer=new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(),"utf-8")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        initGUI();
        initListener();
    }

    public void initGUI() {
        pane= new JScrollPane();

        textField = new JTextField("Hellow");
        textField.setColumns(10);

        button = new JButton("发送");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(pane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(button)
                                .addGap(15))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(pane, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button))
                                .addContainerGap(17, Short.MAX_VALUE))
        );

        textArea = new JTextArea();
        pane.setViewportView(textArea);
        setLayout(groupLayout);
    }

    public void initListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write(getTextField());
                    writer.newLine();
                    writer.flush();
                    addArea(getTextField());
                    textField.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    @Override
    public void run() {
        try {
            while ((temp = reader.readLine()) != null) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        addArea(temp);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addArea(String str) {
        textArea.append(str+"\r\n");
    }

    public String getTextField() {
        return textField.getText();
    }


}
