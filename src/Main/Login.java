package Main;

import cn.hutool.http.HttpRequest;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    public static String IPAddr;
    public static String IPUrl;
    static String result;

    private static void initComponent() {
        JFrame frame = new JFrame("资产管理系统");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        //加载图片
        ImageIcon icon=new ImageIcon(Login.class.getResource("/Resources/loginBackground.jpg"));
        //将图片放入label中
        JLabel picLabel=new JLabel(icon);
        //设置label的大小
        picLabel.setBounds(0,0,800,210);

        JLabel userLabel = new JLabel("用户名");
        userLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        userLabel.setBounds(300,220,80,30);

        JTextField userText = new JTextField(20);
        userText.setFont(new Font("Dialog", Font.BOLD, 20));
        userText.setBounds(390,220,165,30);

        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        passwordLabel.setBounds(300,260,80,30);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setEchoChar('*');
        passwordText.setFont(new Font("Dialog", Font.BOLD, 20));
        passwordText.setBounds(390,260,165,30);

        JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        ipLabel.setBounds(300,300,80,30);

        JTextField ipText = new JTextField(20);
        ipText.setText("10.36.16.113");
        ipText.setFont(new Font("Dialog", Font.BOLD, 20));
        ipText.setBounds(390,300,165,30);

        JLabel portLabel = new JLabel("端口");
        portLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        portLabel.setBounds(300,340,80,30);

        JTextField portText = new JTextField(20);
        portText.setText("8181");
        portText.setFont(new Font("Dialog", Font.BOLD, 20));
        portText.setBounds(390,340,165,30);

        JButton loginButton = new JButton("登录");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.setBackground(new Color(76, 152, 236));
        loginButton.setForeground(Color.white);
        loginButton.setBounds(300, 380, 250, 30);

        panel.add(picLabel);
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordText);
        panel.add(passwordLabel);
        panel.add(ipLabel);
        panel.add(ipText);
        panel.add(portLabel);
        panel.add(portText);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = String.valueOf(passwordText.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(panel,"请输入用户名或密码!");
            } else {
                IPUrl = "http://" + ipText.getText() + ":" + portText.getText() + "/";
                IPAddr = IPUrl + "login/ajax";
                System.out.println("loginUrl:     " + IPAddr);
                String requestStr = "{" + "\"username\":" + "\""+ username + "\"" + "," + "\"password\":" + "\"" + password + "\"" +"}";
                System.out.println("requestStr:   " + requestStr);
                try {
                    result = HttpRequest.post(IPAddr).body(requestStr).execute().body();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(panel, exception, "提示",JOptionPane.WARNING_MESSAGE);
                }
                System.out.println("info: " + result);
                JSONObject jsResult = JSONObject.fromObject(result);
                String status = jsResult.getString("status");
                String reason = jsResult.getString("reason");
                System.out.println("status:  " + status);
                if (status.equals("true")) {
                    System.out.println("......");
                    frame.dispose();
                    new MainPage();

                } else {
                    JOptionPane.showMessageDialog(panel, reason, "提示",JOptionPane.WARNING_MESSAGE);
                    userText.setText("");
                    passwordText.setText("");
                }
            }
        });
        frame.setVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        initComponent();
    }
}
