package Main;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fazecast.jSerialComm.SerialPort;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import uhf.AsyncSocketState;
import uhf.MultiLableCallBack;
import uhf.Reader;
import uhf.Types;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class MainPage extends JPanel {

    Login login;
    MainPage main;

    //获取当前可用串口
    SerialPort[] portList;
    ArrayList<String> portNameList;
    //选择的串口
    String ComName = "";
    static Boolean writeSuccFlag = false;
    static String flag = "0";
    public static Reader ReaderControllor;
    List<AsyncSocketState> clients = null;           //客户端信息
    public AsyncSocketState currentclient;    //当前操作客户端对象
    String IPUrl1 = login.IPUrl;
    int power = 30;
    static String depIDText;
    static String AssertNumberText;
    static int pageIndexText;
    static int pageSizeText;
    //放置数据的集合
    private List<NotCard> notCardList = new ArrayList<NotCard>();
    private Object[][] data = null;
    private Object[] columnNames = new Object[]{"资产编号", "EPC编号", "资产名称", "资产分类", "使用部门名称",
            "使用部门编号", "使用人名称", "使用人编号", "管理人名称", "管理人编号", "存放地名称", "存放地编号"};
    // 表格所有行数据
    Object[][] rowData = {
            {"张三", 80, 80, 80, 240, 1, 1, 1, 1, 1, 1, 1},
            {"John", 70, 80, 90, 240, 1, 1, 1, 1, 1, 1, 1},
            {"Sue", 70, 70, 70, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70164636, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 7167370, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 7071177, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Joe", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1}
    };
    Object[][] rowData1 = {
            {"张三", 80, 80, 80, 240, 1, 1, 1, 1, 1, 1, 1},
            {"John", 70, 80, 90, 240, 1, 1, 1, 1, 1, 1, 1},
            {"Sue", 70, 70, 70, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 70164636, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Jane", 80, 7167370, 60, 210, 1, 1, 1, 1, 1, 1, 1},
            {"Joe", 80, 70, 60, 210, 1, 1, 1, 1, 1, 1, 1}
    };

    JComboBox cmb = new JComboBox();    //创建JComboBox
    JComboBox cmb1 = new JComboBox();    //创建JComboBox
    JButton connButton = new JButton("打开");
    JButton setRadioButton = new JButton("设置");
    JButton readButton = new JButton("读");
    JButton writeButton = new JButton("写");
    JButton queryButton = new JButton("查询");
    JButton sendCardButton = new JButton("发卡");
    JTextField epcText = new JTextField();    //EPC
    JTextField assetNumber = new JTextField();    //资产编号
    JTextField assetName = new JTextField();    //资产名称
    JTextField department = new JTextField();    //部门
    JTextField manufacturer = new JTextField();    //制造商
    JTextField storageLocation = new JTextField();    //存放地
    JTextField user = new JTextField();    //使用人
    //查询条件
    JTextField depID = new JTextField();    //使用部门编号
    JTextField assetNumber2 = new JTextField();    //资产编号
    JTextField pageIndex = new JTextField();    //页码
    JTextField pageSize = new JTextField();    //每页数目
    JTextField userID = new JTextField();    //使用人编号
    JTextField adminID = new JTextField();    //管理人编号
    JTextField locationID = new JTextField();    //存放地点编号
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel label10;
    TableModel tableModel;
    JTable table;

    public static void main(String[] args)
    {
        JFrame frame=new JFrame("资产管理客户端");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainPage(), BorderLayout.CENTER);
        frame.setSize(1900,1350);
//        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);   //全屏
        frame.setVisible(true);
    }

    public MainPage() {
        super(new GridLayout(1,1));

        JFrame frame=new JFrame("资产管理客户端");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1900,1350);

        ReaderControllor = new Reader(new MultiLableCallBack() {
            @Override
            public void method(String data) {
                writeSuccFlag = false;
                //划分字符串
                String[] result = (data + "," + flag).split("\\,");
                String responseCode = result[2];
                System.out.println("responseCode:  " + responseCode);
                byte type = Conversion.toBytes(result[1])[0];
                switch (type) {
                    case Types.READ_TAGS_RESPOND:
                        System.out.println("READ_TAGS--------------");
                        epcText.setText(result[3]);
                        String EPCText = result[3];
                        String queryEPCUrl = IPUrl1 + "api/assetInfo/epcs";
                        String EPCInfo = HttpRequest.post(queryEPCUrl).body(JSONUtil.toJsonStr(new String[]{EPCText})).header("Accept", "*/*").execute().body();
                        System.out.println("EPCInfo:    " + EPCInfo);
                        JSONObject j = JSONObject.fromObject(EPCInfo);
                        JSONArray records = j.getJSONArray("records");
                        if (!records.isEmpty()) {
                            JSONObject key1 = (JSONObject) records.get(0);
                            assetName.setText(key1.getString("assetName"));
                            assetNumber.setText(key1.getString("assetNumber"));
                            department.setText(key1.getString("department"));
                            manufacturer.setText(key1.getString("manufacturer"));
                            storageLocation.setText(key1.getString("storageLocation"));
                            user.setText(key1.getString("user"));
                        } else {
                            assetName.setText("");
                            assetNumber.setText("");
                            department.setText("");
                            manufacturer.setText("");
                            storageLocation.setText("");
                            user.setText("");
                            JOptionPane.showMessageDialog(tabbedPane, "没有查到相关数据", "提示",JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case Types.WRITE_TAGS_RESPOND:
                        System.out.println("WRITE_TAGS-------------");
                        if ("1".equals(responseCode)) {
                            writeSuccFlag = true;
                            System.out.println("writeSuccFlag:  " + writeSuccFlag);
                        } else {
                            writeSuccFlag = false;
                            System.out.println("writeSuccFlag:  " + writeSuccFlag);
                        }
                        break;
                }
            }
            @Override
            public void ReaderNotice(String s) {}
        });

        ImageIcon icon1= new ImageIcon(MainPage.class.getResource("/Resources/set.png"));
        icon1.setImage(icon1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        JComponent panel1=settingPanel();
        tabbedPane.addTab("系统设置", icon1, panel1,"设置");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2=sendCardPage();
        ImageIcon icon2= new ImageIcon(MainPage.class.getResource("/Resources/card.png"));
        icon2.setImage(icon2.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        tabbedPane.addTab("发卡", icon2, panel2,"发卡");
        tabbedPane.revalidate();
        tabbedPane.setMnemonicAt(1,KeyEvent.VK_2);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JComponent settingPanel() {
        GetCom();
        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label1=new JLabel("RFID资产管理软件  系统设置");
        label1.setFont(new Font("Dialog", Font.PLAIN, 30));
        label1.setOpaque(true);
        label1.setBackground(new Color(76, 152, 236));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.WHITE);
        panel.add(label1, BorderLayout.NORTH);

        JPanel panel1=new JPanel();
        panel1.setLayout(null);

        JLabel label2=new JLabel("通信设置");
        label2.setFont(new Font("Dialog", Font.PLAIN, 25));
        label2.setBounds(40, 30, 150, 25);
        panel1.add(label2);

        JLabel label3=new JLabel("端口号: ");
        label3.setFont(new Font("Dialog", Font.PLAIN, 20));
        label3.setBounds(120, 80, 150, 20);
        panel1.add(label3);

        cmb.setBounds(230, 80, 150, 30);
        cmb.setFont(new Font("Dialog", Font.PLAIN, 20));
        for (int i = 0; i < portNameList.size(); i++) {
            cmb.addItem(portNameList.get(i));
        }
        panel1.add(cmb);

        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("Dialog", Font.BOLD, 20));
        refreshButton.setBounds(420, 80, 100, 30);
        refreshButton.setBackground(new Color(76, 152, 236));
        refreshButton.setForeground(Color.white);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmb.removeAllItems();
                GetCom();
                for (int i = 0; i < portNameList.size(); i++) {
                    cmb.addItem(portNameList.get(i));
                }
            }
        });
        panel1.add(refreshButton);

        connButton.setFont(new Font("Dialog", Font.BOLD, 20));
        connButton.setBounds(560, 80, 100, 30);
        connButton.setBackground(new Color(76, 152, 236));
        connButton.setForeground(Color.white);
        connButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectCom();
            }
        });
        panel1.add(connButton);

        JLabel label4=new JLabel("发射功率: ");
        label4.setFont(new Font("Dialog", Font.PLAIN, 20));
        label4.setBounds(120, 175, 150, 20);
        panel.add(label4);

        cmb1.setBounds(230, 130, 150, 30);
        cmb1.setFont(new Font("Dialog", Font.PLAIN, 20));
        List<String> radioList;
        radioList = Arrays.asList("5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30");
        Collections.reverse(radioList);
        for (int i = 0; i < radioList.size(); i++) {
            cmb1.addItem(radioList.get(i));
        }
        cmb1.insertItemAt("10", 0);
        panel1.add(cmb1);

        setRadioButton.setFont(new Font("Dialog", Font.BOLD, 20));
        setRadioButton.setBounds(420, 130, 100, 30);
        setRadioButton.setBackground(new Color(76, 152, 236));
        setRadioButton.setForeground(Color.white);
        setRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPower();
            }
        });
        panel1.add(setRadioButton);

        JLabel label5=new JLabel("读/写标签");
        label5.setFont(new Font("Dialog", Font.PLAIN, 25));
        label5.setBounds(40, 250, 150, 25);
        panel1.add(label5);

        readButton.setFont(new Font("Dialog", Font.BOLD, 20));
        readButton.setBounds(230, 300, 100, 30);
        readButton.setBackground(new Color(76, 152, 236));
        readButton.setForeground(Color.white);
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readCard();
            }
        });
        panel1.add(readButton);

        writeButton.setFont(new Font("Dialog", Font.BOLD, 20));
        writeButton.setBounds(420, 300, 100, 30);
        writeButton.setBackground(new Color(76, 152, 236));
        writeButton.setForeground(Color.white);
        writeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String writedata = epcText.getText();
                writeCard(writedata);
            }
        });
        panel1.add(writeButton);

        JLabel label6=new JLabel("EPC号: ");
        label6.setFont(new Font("Dialog", Font.PLAIN, 20));
        label6.setBounds(120, 390, 150, 20);
        panel.add(label6);
        epcText.setFont(new Font("Dialog", Font.PLAIN, 20));
        epcText.setBounds(230,390,290,30);
        panel.add(epcText);

        JLabel label7=new JLabel("资产编号: ");
        label7.setFont(new Font("Dialog", Font.PLAIN, 20));
        label7.setBounds(120, 440, 150, 20);
        panel.add(label7);
        assetNumber.setFont(new Font("Dialog", Font.PLAIN, 20));
        assetNumber.setBounds(230,440,145,30);
        panel.add(assetNumber);

        JLabel label8=new JLabel("资产名称: ");
        label8.setFont(new Font("Dialog", Font.PLAIN, 20));
        label8.setBounds(420, 440, 150, 20);
        panel.add(label8);
        assetName.setFont(new Font("Dialog", Font.PLAIN, 20));
        assetName.setBounds(530,440,145,30);
        panel.add(assetName);

        JLabel label9=new JLabel("部门: ");
        label9.setFont(new Font("Dialog", Font.PLAIN, 20));
        label9.setBounds(720, 440, 150, 20);
        panel.add(label9);
        department.setFont(new Font("Dialog", Font.PLAIN, 20));
        department.setBounds(810,440,145,30);
        panel.add(department);

        JLabel label10=new JLabel("制造商: ");
        label10.setFont(new Font("Dialog", Font.PLAIN, 20));
        label10.setBounds(120, 490, 150, 20);
        panel.add(label10);
        manufacturer.setFont(new Font("Dialog", Font.PLAIN, 20));
        manufacturer.setBounds(230,490,145,30);
        panel.add(manufacturer);

        JLabel label11=new JLabel("存放地点: ");
        label11.setFont(new Font("Dialog", Font.PLAIN, 20));
        label11.setBounds(420, 490, 150, 20);
        panel.add(label11);
        storageLocation.setFont(new Font("Dialog", Font.PLAIN, 20));
        storageLocation.setBounds(530,490,145,30);
        panel.add(storageLocation);

        JLabel label12=new JLabel("使用人: ");
        label12.setFont(new Font("Dialog", Font.PLAIN, 20));
        label12.setBounds(720, 490, 150, 20);
        panel.add(label12);
        user.setFont(new Font("Dialog", Font.PLAIN, 20));
        user.setBounds(810,490,145,30);
        panel.add(user);

        panel.add(panel1, BorderLayout.CENTER);
        return panel;
    }

    /**
     * 获取当前所有可用串口(Linux)
     * */
    public void GetCom()
    {
        portNameList = new ArrayList<>();
        portList = SerialPort.getCommPorts();
        for(int i = 0;i<portList.length;i++)
        {
            String name = portList[i].getSystemPortName();
            System.out.println("portName:  " + name);
            portNameList.add(name);
            System.out.println(name);
        }
    }

    /**
     * 打开or关闭 串口
     * */
    public void connectCom() {
//        if (null == cmb.getSelectedItem()) {
//            JOptionPane.showMessageDialog(tabbedPane, "串口打开失败，请先选择端口", "提示",JOptionPane.WARNING_MESSAGE);
//        } else {
        ComName = cmb.getSelectedItem().toString();
        System.out.println("COMName:    " + ComName);
        ReaderControllor.IsLinuxSerial = true;
        boolean result = ReaderControllor.ComStart(ComName, 115200);
        System.out.println("open Com or not:    " + result);
        if (connButton.getText().equals("打开")) {
            if (result == true) {
                connButton.setText("关闭");
                clients = ReaderControllor.GetClientInfo();
                System.out.println("clients sizes:   " + clients.size());
                if (clients.size() == 1) currentclient = clients.get(0);// 只有一台连接的时候直接默认选择这一台
                for(int i = 0;i<clients.size();i++)
                {
                    ReaderControllor.GetMACDev(clients.get(i));
                    ReaderControllor.GetWorkMode(clients.get(i));
                }

            } else {
                JOptionPane.showMessageDialog(this, "串口打开失败，请检查是否有其他程序使用串口", "提示",JOptionPane.WARNING_MESSAGE);
            }
        } else {
            Boolean closePortResult =  ReaderControllor.SerialPortClose();
            System.out.println("closePortResult:  " + closePortResult);
            connButton.setText("打开");
        }
//        }
    }

    /**
     * 设置发卡器发射功率
     * */
    public void setPower() {
        power = Integer.parseInt(cmb1.getSelectedItem().toString());
//        System.out.println("clients size:  " + clients.size());
        if (null == clients) {
            JOptionPane.showMessageDialog(this, "设置功率失败，请检查是否打开串口", "提示",JOptionPane.WARNING_MESSAGE);
        } else {
            currentclient = clients.get(0);// 只有一台连接的时候直接默认选择这一台
            ReaderControllor.SetPower(currentclient,(byte)power,(byte)power);
            JOptionPane.showMessageDialog(this, "发射功率设置成功", "提示",JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * 读卡
     * */
    public void readCard() {
        byte bank = (byte)0x01;
        int startadd = Integer.parseInt("2");
        int readlen = Integer.parseInt("6");
        byte fliter = (byte)0x00;                                                //不过滤时无效
        String fliterdata = "";
        String pwd = "00000000";
        if (null == clients) {
            JOptionPane.showMessageDialog(this, "请检查是否连接发卡器！", "提示",JOptionPane.WARNING_MESSAGE);
        } else {
            currentclient = clients.get(0);// 只有一台连接的时候直接默认选择这一台
            String readCardResult = ReaderControllor.ReadTags(currentclient, pwd, fliter, fliterdata, bank, startadd, readlen);
            if (readCardResult.equals("0")) {
                System.out.println("发送读取标签指令成功！");
            } else {
                System.out.println("发送读取标签指令失败！");
                JOptionPane.showMessageDialog(tabbedPane, "发送读取标签指令失败！", "提示",JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * 写卡（EPC号）
     * */
    public void writeCard(String writedata) {
        byte bank = (byte)0x01;
        int startadd = Integer.parseInt("2");
        int datalen = Integer.parseInt("6");
        byte fliter = (byte)0x00;                                                //不过滤时无效
        String fliterdata = "";
        String pwd = "00000000";
        if (null == clients) {
            JOptionPane.showMessageDialog(this, "请检查是否连接发卡器！", "提示",JOptionPane.WARNING_MESSAGE);
        } else {
            currentclient = clients.get(0);// 只有一台连接的时候直接默认选择这一台
            String readCardResult = ReaderControllor.WriteTags(currentclient, pwd, fliter, fliterdata, bank, startadd, datalen,writedata);
            if (readCardResult.equals("0")) {
                System.out.println("发送写入标签指令成功！");
            } else {
                System.out.println("发送写入标签指令失败！");
                JOptionPane.showMessageDialog(tabbedPane, "发送写入标签指令失败！", "提示",JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public JComponent sendCardPage() {

        JPanel panel1=new JPanel();
        panel1.setLayout(null);

        JLabel label1=new JLabel("RFID资产管理软件  发卡");
        label1.setFont(new Font("Dialog", Font.PLAIN, 30));
        label1.setBounds(0,0,1900,40);
        label1.setOpaque(true);
        label1.setBackground(new Color(76, 152, 236));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.WHITE);
        panel1.add(label1);

        JLabel label2=new JLabel("使用部门编号:");
        label2.setFont(new Font("Dialog", Font.PLAIN, 20));
        label2.setBounds(40, 80, 150, 20);
        panel1.add(label2);
        depID.setFont(new Font("Dialog", Font.PLAIN, 20));
        depID.setBounds(180,80,60,25);
        panel1.add(depID);

        JLabel label3=new JLabel("资产编号:");
        label3.setFont(new Font("Dialog", Font.PLAIN, 20));
        label3.setBounds(260, 80, 150, 20);
        panel1.add(label3);
        assetNumber2.setFont(new Font("Dialog", Font.PLAIN, 20));
        assetNumber2.setBounds(360,80,100,25);
        panel1.add(assetNumber2);

        JLabel label4=new JLabel("页码:");
        label4.setFont(new Font("Dialog", Font.PLAIN, 20));
        label4.setBounds(480, 80, 150, 20);
        panel1.add(label4);
        pageIndex.setFont(new Font("Dialog", Font.PLAIN, 20));
        pageIndex.setBounds(540,80,60,25);
        pageIndex.setText("1");
        panel1.add(pageIndex);

        JLabel label5=new JLabel("每页数目:");
        label5.setFont(new Font("Dialog", Font.PLAIN, 20));
        label5.setBounds(620, 80, 150, 20);
        panel1.add(label5);
        pageSize.setFont(new Font("Dialog", Font.PLAIN, 20));
        pageSize.setBounds(720,80,60,25);
        pageSize.setText("15");
        panel1.add(pageSize);

        JLabel label6=new JLabel("使用人编号:");
        label6.setFont(new Font("Dialog", Font.PLAIN, 20));
        label6.setBounds(800, 80, 150, 20);
        panel1.add(label6);
        userID.setFont(new Font("Dialog", Font.PLAIN, 20));
        userID.setBounds(920,80,60,25);
        panel1.add(userID);

        JLabel label7=new JLabel("管理人编号:");
        label7.setFont(new Font("Dialog", Font.PLAIN, 20));
        label7.setBounds(1000, 80, 150, 20);
        panel1.add(label7);
        adminID.setFont(new Font("Dialog", Font.PLAIN, 20));
        adminID.setBounds(1120,80,60,25);
        panel1.add(adminID);

        JLabel label8=new JLabel("存放地点编号:");
        label8.setFont(new Font("Dialog", Font.PLAIN, 20));
        label8.setBounds(1200, 80, 150, 20);
        panel1.add(label8);
        locationID.setFont(new Font("Dialog", Font.PLAIN, 20));
        locationID.setBounds(1340,80,60,25);
        panel1.add(locationID);

        queryButton.setFont(new Font("Dialog", Font.BOLD, 20));
        queryButton.setBounds(1450, 80, 100, 30);
        queryButton.setBackground(new Color(76, 152, 236));
        queryButton.setForeground(Color.white);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data = queryNotCard();
                System.out.println("data:  " + data);
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                tableModel.setDataVector(data, columnNames);
//                tableModel.setDataVector(rowData1, columnNames);
                label10.setText(String.valueOf(table.getRowCount()));
                panel1.revalidate();
            }
        });
        panel1.add(queryButton);

        data = queryNotCard();
        table = showTable(data);
//        table = showTable(rowData);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40,120,1800,700);
        panel1.add(scrollPane);

        JLabel label9=new JLabel("总条数:");
        label9.setFont(new Font("Dialog", Font.PLAIN, 20));
        label9.setBounds(40, 850, 150, 20);
        panel1.add(label9);
        label10=new JLabel("0");
        label10.setFont(new Font("Dialog", Font.PLAIN, 20));
        label10.setBounds(140, 850, 150, 20);
        label10.setText(String.valueOf(table.getRowCount()));
        panel1.add(label10);

        sendCardButton.setFont(new Font("Dialog", Font.BOLD, 20));
        sendCardButton.setBounds(1600, 80, 100, 30);
        sendCardButton.setBackground(new Color(76, 152, 236));
        sendCardButton.setForeground(Color.white);
        sendCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (null == clients) {
                    JOptionPane.showMessageDialog(tabbedPane, "未连接发卡器！", "提示",JOptionPane.WARNING_MESSAGE);
                } else {
                    if (null == table.getValueAt(table.getSelectedRow(), 1)) {
                        JOptionPane.showMessageDialog(tabbedPane, "请选择一条发卡资产数据！", "提示",JOptionPane.WARNING_MESSAGE);
                    } else {
                        String writedata = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
                        System.out.println("writedata:   " + writedata);
                        writeCard(writedata);
                        try {
                            Thread.currentThread().sleep(1000);
                            System.out.println("writeSuccFlag:  " + writeSuccFlag);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        if (writeSuccFlag) {
                            System.out.println("绑卡成功！");
                            String responseUrl =  IPUrl1 + "/api/card/asset/notify?epc=" + String.valueOf(table.getValueAt(table.getSelectedRow(), 1));  //第二版接口
                            System.out.println("responseUrl:   " + responseUrl);
                            String responseInfo = HttpRequest.post(responseUrl).header("Accept", "*/*").execute().body();
                            JSONObject responseObj = JSONObject.fromObject(responseInfo);
                            String status = responseObj.getString("status");
                            System.out.println("status:   " + status);
                            if (status.equals("true")) {
                                System.out.println("更新发卡信息成功！");
                                JOptionPane.showMessageDialog(tabbedPane, "更新发卡信息成功！", "提示",JOptionPane.WARNING_MESSAGE);
                                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                                tableModel.removeRow(table.getSelectedRow());
                                tableModel.fireTableDataChanged();
                            } else {
                                JOptionPane.showMessageDialog(tabbedPane, "更新发卡信息失败！", "提示",JOptionPane.WARNING_MESSAGE);
                            }
                            writeSuccFlag = false;
                        } else {
                            System.out.println("更新发卡信息失败！");
                            JOptionPane.showMessageDialog(tabbedPane, "更新发卡信息失败！", "提示",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });
        panel1.add(sendCardButton);

        return panel1;
    }

    /**
     * 调后台接口，获取未发卡资产数据，将数据读取到表格
     */
    private List<NotCard> getNotCards(int pageIndex, int pageSize) {
        List<NotCard> notCardList = new ArrayList<NotCard>();
        if (null == IPUrl1) {
            JOptionPane.showMessageDialog(tabbedPane, "IP未设置，请于登录界面设置IP！", "提示",JOptionPane.WARNING_MESSAGE);
        } else {
            String url = IPUrl1 + "api/ussued/asset/list?page=" + pageIndex + "&rows=" + pageSize;  //第二版接口
            depIDText = depID.getText();
            if (StringUtils.isNotBlank(depIDText)) {
                url = url + "&useOrgId=" + depIDText;
            }
            AssertNumberText = assetNumber2.getText();
            if (StringUtils.isNotBlank(AssertNumberText)) {
                url = url + "&assetNumber=" + AssertNumberText;
            }
            String userIDText = userID.getText();
            if (StringUtils.isNotBlank(userIDText)) {
                url = url + "&userEmployeeId=" + userIDText;
            }
            String adminIDText = adminID.getText();
            if (StringUtils.isNotBlank(adminIDText)) {
                url = url + "&administratorId=" + adminIDText;
            }
            String locationIDText = locationID.getText();
            if (StringUtils.isNotBlank(locationIDText)) {
                url = url + "&locationId" + locationIDText;
            }
            System.out.println("url:     " + url);
            String info = HttpUtil.get(url);
            System.out.println("info:    " + info);
            try {
                JSONObject j = JSONObject.fromObject(info);
                JSONArray records = j.getJSONArray("records");
                System.out.println("data size:  " + records.size());
                for (int i = 0; i< records.size(); i++) {
                    //多个查询条件
                    JSONObject key1 = (JSONObject)records.get(i);
                    NotCard notCard = new NotCard();
                    notCard.setID(key1.getString("id"));
                    notCard.setAssetNum(key1.getString("assetNumber"));
                    notCard.setEPCCode(key1.getString("rfidNumber"));
                    notCard.setAssetName(key1.getString("assetName"));
                    notCard.setAssetType(key1.getString("categoryId"));
                    notCard.setDepartment(key1.getString("usingDepartmentName"));
                    notCard.setDepartmentId((key1.getString("usingDepartmentId")));
                    notCard.setLiablePerson(key1.getString("usingEmployeeName"));
                    notCard.setLiablePersonId(key1.getString("usingEmployeeId"));
                    notCard.setAdminEmployeeName(key1.getString("adminEmployeeName"));
                    notCard.setAdminEmployeeId((key1.getString("adminEmployeeId")));
                    notCard.setStorageLocationName(key1.getString("storageLocationName"));
                    notCard.setStorageLocationId(key1.getString("storageLocationId"));
                    notCardList.add(notCard);
                }
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(tabbedPane, "获取未发卡数据失败，response null", "提示",JOptionPane.WARNING_MESSAGE);
            }
        }
        return notCardList;
    }

    public Object[][] queryNotCard() {
        pageIndexText = Integer.parseInt(pageIndex.getText());
        pageSizeText = Integer.parseInt(pageSize.getText());
        notCardList = getNotCards(pageIndexText, pageSizeText);
        data = new Object[notCardList.size()][columnNames.length];
        for (int i = 0; i < notCardList.size(); i++) {
            for (int j = 0; j < columnNames.length; j++) {
                data[i][0] = notCardList.get(i).getAssetNum();
                data[i][1] = notCardList.get(i).getEPCCode();
                data[i][2] = notCardList.get(i).getAssetName();
                data[i][3] = notCardList.get(i).getAssetType();
                data[i][4] = notCardList.get(i).getDepartment();
                data[i][5] = notCardList.get(i).getDepartmentId();
                data[i][6] = notCardList.get(i).getLiablePerson();
                data[i][7] = notCardList.get(i).getLiablePersonId();
                data[i][8] = notCardList.get(i).getAdminEmployeeName();
                data[i][9] = notCardList.get(i).getAdminEmployeeId();
                data[i][10] = notCardList.get(i).getStorageLocationName();
                data[i][11] = notCardList.get(i).getStorageLocationId();
            }
        }
        return data;
    }

    public JTable showTable(Object[][] data) {
        // 创建 表格模型，指定 所有行数据 和 表头
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        // 使用 表格模型 创建 行排序器（TableRowSorter 实现了 RowSorter）
        RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(tableModel);
        // 给 表格 设置 行排序器
        table.setRowSorter(rowSorter);
        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 20));      // 字体样式
        table.setSelectionForeground(Color.BLACK);      // 选中后字体颜色
        table.setSelectionBackground(new Color(76, 152, 236));     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色
        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 20));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.BLACK);                // 设置表头名称字体颜色
        // 设置行高
        table.setRowHeight(30);
        // 第二列列宽设置为40
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getSelectionModel();
        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(1800, 700));
        return table;
    }

}
