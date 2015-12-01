import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Receiver {
    public static  void main(String arg[]){
        receiveMulticast();
    }
    
    
    public static void receiveMulticast(){
        InetAddress group;
        try {
            group = InetAddress.getByName("224.0.0.4");
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            return ;
        } // 组播地址  
        int port = 4006; // 端口  
        MulticastSocket msr = null;  
        try {  
            msr = new MulticastSocket(port); // 1.创建一个用于发送和接收的MulticastSocket组播套接字对象  
            msr.joinGroup(group); // 3.使用组播套接字joinGroup(),将其加入到一个组播
            byte[] buffer = new byte[8192];  
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length); // 2.创建一个指定缓冲区大小及组播地址和端口的DatagramPacket组播数据包对象  
            msr.receive(dp); // 4.使用组播套接字的receive（）方法，将组播数据包对象放入其中，接收组播数据包  
            String s = new String(dp.getData(), 0, dp.getLength()); // 5.解码组播数据包提取信息，并依据得到的信息作出响应  
            System.out.println("receive:"+s);  
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("receive end");
    }

    private static void receiveBroadcast() {
        System.out.println("receive");
        int port = 9999;//开启监听的端口
        DatagramSocket ds = null;
        DatagramPacket dp = null;
        byte[] buf = new byte[1024];//存储发来的消息
        StringBuffer sbuf = new StringBuffer();
        try {
            //绑定端口的
            ds = new DatagramSocket(port);
            dp = new DatagramPacket(buf, buf.length);
            System.out.println("监听广播端口打开：");
            ds.receive(dp);
            ds.close();
            int i;
            for(i=0;i<1024;i++){
                if(buf[i] == 0){
                    break;
                }
                sbuf.append((char) buf[i]);
            }           
            System.out.println("收到广播消息：" + sbuf.toString());
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
