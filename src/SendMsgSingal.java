import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 该类为单聊消息发送时使用的类
 */
public class SendMsgSingal {
    private String userName;
    private String msg;
    private Socket userSocket;

    public SendMsgSingal(String userName, String msg, Socket userSocket) {
        this.userName = userName;
        this.msg = msg;
        this.userSocket = userSocket;
    }

    public void send() throws IOException {
        //发送给单聊对象
        OutputStream os = Main.users.get(userName).getOutputStream();
        os.write(msg.getBytes());

        //发送给自己
        os = userSocket.getOutputStream();
        os.write(msg.getBytes());
    }
}
