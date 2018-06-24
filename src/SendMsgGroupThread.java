import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * 该类为发送群聊消息使用的类
 */
public class SendMsgGroupThread extends Thread {
    private String msg;
    private OutputStream os;
    private Object obj = new Object();

    public SendMsgGroupThread(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        synchronized (obj) {
            //从集合中拿出储存的socket连接
            for (Map.Entry<String, Socket> entry : Main.users.entrySet()) {
                try {
                    os = entry.getValue().getOutputStream();
                    os.write(msg.getBytes());
                } catch (IOException e) {
                    System.out.println("用户【" + "@@" + "】下线");
                    Main.users.remove(entry.getKey());
                }
            }
        }
    }
}
