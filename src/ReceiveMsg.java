import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 接收消息线程
 * 每连接一个socket就开启一个线程
 * 该线程在后台一直运行，直到socket断开连接
 */
public class ReceiveMsg extends Thread {
    private String clientName;
    private Socket s;

    public ReceiveMsg(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        while (true) {
            byte[] msg = new byte[1024];
            try {
                //获取客户端发送过来的消息
                InputStream is = s.getInputStream();
                int len = is.read(msg);
                String str = new String(msg, 0, len);

                //登陆时客户端发送过来的用户名
                if (str.contains("~!~@@login")) {
                    String[] str1 = str.split("~!~@@login");
                    String userName = str1[1];
                    Main.users.put(userName, s);
                    this.clientName = userName;
                    System.out.println("用户【" + clientName + "】登陆");
                }
                //判断是单聊还是群聊，然后调用对应方法
                if (str.contains("~!~!@@qunliao")) {
                    new SendMsgGroupThread(str).start();
                } else if (str.contains("~!~!@@danliao")) {
                    String[] str1 = str.split("~!~!@@danliao");
                    new SendMsgSingal(str1[1], str, s).send();
                }
            } catch (IOException e) {
                System.out.println("用户【" + clientName + "】下线");
                Main.users.remove(s);
                Thread.currentThread().stop();
            }
        }

    }
}
