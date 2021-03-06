package cao.mine.jfame.Frame;

import cao.mine.init.Context;
import cao.mine.jfame.service.MainService;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class MainFrame {
    private JFrame jf;
    private JLabel serverPathLabel, clientPathLabel;
    public JTextField serverPathText, clientPathText;
    private JButton compareButton;
    public Context context;
    public MainService mainService;
    public CompareFrame compareFrame;
    public Boolean isCompareFrameCreated=false;

    public MainFrame(Context context) {
        this.jf = new JFrame("主界面 - 文件比对客户端");
        this.serverPathLabel = new JLabel("服务器路径:");
        this.clientPathLabel = new JLabel("客户端路径:");
        Object lastServerPath=context.getConfText("lastServerPath");
        this.serverPathText = new JTextField(lastServerPath==null?"":lastServerPath.toString());
        Object lastClientPath=context.getConfText("lastClientPath");
        this.clientPathText = new JTextField(lastClientPath==null?"":lastClientPath.toString());
        this.compareButton = new JButton("文件对比");

        this.context = context;
        mainService = new MainService();
    }

    private void init() {
        jf.setLayout(null);
        jf.add(serverPathLabel);
        jf.add(clientPathLabel);
        jf.add(serverPathText);
        jf.add(clientPathText);
        jf.add(compareButton);
        serverPathLabel.setBounds(20, 42, 100, 30);
        serverPathText.setBounds(120, 50, 300, 20);
        clientPathLabel.setBounds(20, 94, 100, 30);
        clientPathText.setBounds(120, 100, 300, 20);
        compareButton.setBounds(100, 150, 100, 30);
        compareButton.addActionListener(mainService.sendCompareRequest(this));


    }

    public void send() {
        mainService.doSome(this);
    }


    public void showme() {
        init();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setLocation((int)(screenSize.getWidth()/2)-225,(int)(screenSize.getHeight()/2)-200);
        jf.setVisible(true);
        jf.setSize(450, 400);
        jf.setResizable(false);
        jf.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    context.getSocket().close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                } finally {
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

}
