import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Send {
    public static void main(String arg[]) {
        sendMulticast();
    }

    private static void sendMulticast() {
        InetAddress group;
        String message = "test";// 用于发送的字符串
        try {
            group = InetAddress.getByName("224.0.0.4");// 组播地址
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            return;
        } 
        int port = 4006; // 端口
        MulticastSocket mss = null;
        try {
            mss = new MulticastSocket(port); // 1.创建一个用于发送和接收的MulticastSocket组播套接字对象
            mss.joinGroup(group); // 3.使用组播套接字joinGroup(),将其加入到一个组播
            DatagramPacket dp = new DatagramPacket(message.getBytes(),
                    message.length(), group, port);
            mss.send(dp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendBroadcast() {
        System.out.println("send");
        // 广播的实现 :由客户端发出广播，服务器端接收
        String host = "255.255.255.255";// 广播地址
        int port = 9999;// 广播的目的端口
        String message = "test";// 用于发送的字符串
        try {
            InetAddress adds = InetAddress.getByName(host);
            DatagramSocket ds = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(message.getBytes(),
                    message.length(), adds, port);
            ds.send(dp);
            ds.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
