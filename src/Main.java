import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类负责启动线程
 * 无限循环检查是否有socket连接
 */

public class Main {
    //储存连接的socket对象
    public static Map<String, Socket> users = new HashMap();

    public static void main(String[] args) {
        try {
            //启动服务器
            ServerSocket ss = new ServerSocket(6666);
            while (true) {
                Socket s = ss.accept();
                new Thread(new ReceiveMsg(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
