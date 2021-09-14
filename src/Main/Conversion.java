package Main;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Conversion {

	//Demo语言切换变量
    public static int EN = 0;
    public static int CN = 1;
    public static int Language = CN;
    
	public Conversion(int language)
	{
		Language = language;
		LoadDemoLanguage();
	}
	
	
    public static String createdate()
    {
    	Date date = new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss SSS");
        String createdate = sdf.format(date);
        return createdate;
    }
    
    public static String NowTime()
    {
    	Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String createdate = sdf.format(date);
        return createdate;
    }
       
    public static String AsciiToHex(String AsciiStr) 
	{
		byte Byt_Ascii[] = null;
		String StrHex = "";
		try {
			Byt_Ascii = AsciiStr.replace(" ","").getBytes("GBK");
			StrHex = byteArrayToHexString(Byt_Ascii);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//String转换为byte[] 
		return StrHex;
	}
	
	public static String HexToAscii(String HexStr)
	{
		byte[] Byt_Hex = toBytes(HexStr);
		String StrAscii = "";
		try {
			StrAscii = new String(Byt_Hex,"GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return StrAscii;
	}
	
	
	
	  /**
     * byte[]数组转换为16进制的字符串
     *
     * @param data 要转换的字节数组
     * @return 转换后的结果
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }
    
    
    
 	/**
     * 将16进制字符串转换为byte[]
     * 
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    } 
    
       
	public static String GetToString()
	{
		if(Language == CN)
		{
			return "获取";
		}
		else
		{
			return "Get";
		}
	}
	
	public static String SetToString()
	{
		if(Language == CN)
		{
			return "设置";
		}
		else
		{
			return "Set";
		}
	}
	
	public static String OkToString()
	{
		if(Language == CN)
		{
			return "成功";
		}
		else
		{
			return "Successful";
		}
	}
	
	public static String FailToString()
	{
		if(Language == CN)
		{
			return "失败";
		}
		else
		{
			return "Fail";
		}
	}
	
	public static String[] OpenSerialPortFailToString()
	{
		String[] result = new String[2];
		if(Language == CN)
		{
			result[0] = "串口打开失败";
			result[1] = "请检查是否有其他程序正在使用该串口";
		}
		else
		{
			result[0] = "Serial port failed to open";
			result[1] = "please check if other programs are using the serial port";
		}
		return result;
	}
    
	public static String[] OpenNetServerFailToString()
	{
		String[] result = new String[2];
		if(Language == CN)
		{
			result[0] = "服务器打开失败";
			result[1] = "请检查是否有其他程序正在使用该IP、端口以及确认电脑IPV4地址是否为此IP";
		}
		else
		{
			result[0] = "Server failed to open";
			result[1] = "Check to see if other programs are using the IP, port," + "<br/>" + "and verify that the computer's IPV4 address is this IP";
			
			result[1] = "<html>" + "Check to see if other programs are using the IP, port," + "<br>" + 
					    "and verify that the computer's IPV4 address is this IP"+ "</html>";
		}
		return result;
	}
	
	
	public static String[] PwdLenErrorString()
	{
		String[] result = new String[2];
		if(Language == CN)
		{
			result[0] = "密码长度错误";
			result[1] = "";
		}
		else
		{
			result[0] = "Pwd Len Error";
			result[1] = "";
		}
		return result;
	}
	
	
	
    

	public static String lanNetInfo = " 网络参数配置 ";
	public static String lanNetName = " 域名 ";
	public static String lan4G = " 4G参数配置 ";
	public static String lanwifi = " WiFi参数配置 ";
	public static String lanWorkMode = " 工作模式 ";
	public static String lanContrastEPC = " EPC对比参数 ";
	public static String lanTimingModeTime = " 定时模式时间参数 ";
	public static String lanMQTTConfig = " MQTT配置参数 ";
	public static String lanMQTTTheme = " MQTT主题 ";		
	public static String lanMacAndDev = " MAC地址和设备号 ";	
	public static String lanGpioOutState = " GPIO输出状态 ";
	public static String lanPower = " 功率 ";
	public static String lanArea = " 频率区域 ";
	public static String lanSingleAntTime = " 单通道天线工作时间及间隔时间 ";
	public static String lanMultiAntTime = " 多通道天线工作时间及间隔时间 ";
	public static String lanMultiWorkAnt = " 多通道工作天线 ";
	public static String lanFastID = " FastID参数 ";
	public static String lanEPCAndTID = " 同时读取EPC和TID ";  
	public static String lanTagfocus = " Tagfocus ";
	public static String lanReadTags = " 读取标签数据 ";
	public static String lanWriteTags = " 写入标签数据 ";
	public static String lanLockTags = " 锁定标签 ";
	public static String lanKillTags = " 销毁标签 ";
	public static String lanTemp = " 温度 ";
	public static String lanCarrier = " Carrier ";
	public static String lanRFLink = " RF链路 ";
	public static String lanDevConfig_24G = " 2.4G设备配置参数 ";
	public static String lanMoudleVersion = " 模块版本 ";
	public static String lanReadWriteOutTime = " 读写标签超时时间 ";
	
    
    public void LoadDemoLanguage()
    {
    	if(Language != CN)
		{
    		lanNetInfo = " Net Config ";
    		lanNetName = " NetName ";
    		lan4G = " 4G Config ";    		   		
    		lanwifi = " WiFi Config ";   	   		
    		lanWorkMode = " WorkMode ";
    		lanContrastEPC = " EPC comparison parameter ";
    		lanTimingModeTime = " TimingMode Time ";
    		lanMQTTConfig = " MQTT Config ";
    		lanMQTTTheme = " MQTT Theme ";
    		lanMacAndDev = " DevMAC And DevID ";  	
    		lanGpioOutState = " GPIO Out State ";   
    		lanPower = " Power ";
    		lanArea = " RF Area ";
    		lanSingleAntTime = " Single channel antenna working time and interval time ";
    		lanMultiAntTime = " Multi channel antenna working time and interval time ";
    		lanMultiWorkAnt = " Multi-channel working antenna ";
    		lanFastID = " FastID Config ";
    		lanEPCAndTID = " EPC And TID ";   
    		lanTagfocus = " Tagfocus ";
    		lanReadTags = " Read Tags Data ";
    		lanWriteTags = " Write Tags Data ";
    		lanLockTags = " Lock Tags ";
    		lanKillTags = " Kill Tags ";
    		lanTemp = " Temp ";
    		lanRFLink = " RF Link ";
    		lanDevConfig_24G = " 2.4G DevConfig ";   
    		lanMoudleVersion = " MoudleVersion ";
    		lanReadWriteOutTime = " ReadAndWriteTimeOut ";
		}
    }
    
    
    public static String GetState(String clientstate)
    {
    	String StrState = "";
    	if(Language == CN)
    	{
    		if(clientstate.equals("OK")) StrState = "在线";
    		else if(clientstate.equals("NG")) StrState = "离线";
    	}
    	else
    	{
    		StrState = clientstate;
    	}
    	return StrState;
    }
    
    
    public static String GetDemoVersion()
    {
    	//UHF_Demo[V2.1.2]-新增群读配置功能(读指定密码标签/群读User/读温度标签)，新增9200Rssi配置
    	//UHF_Demo[V2.1.3]-user起始地址可大于255
    	
    	return "UHF_Demo[V2.1.3]";
    }
    
    
    
    
    
}
