package cao.mine.service.sendsocket;

import cao.mine.Listen.SocketListener;
import cao.mine.Listen.SocketTemp;
import cao.mine.init.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by 10441 on 2016/10/13.
 */
public class SendSocket {
    private JSONObject sendMsg;
    private Socket socket;
    private ExecutorService executorService;//软件全局线程池
    private int outTime;

    public SendSocket(Context context,JSONObject sendMsg,int outTime){
        this.socket=context.getSocket();
        this.sendMsg=sendMsg;this.executorService = context.getThreadPool();
        this.executorService = context.getThreadPool();
        this.outTime=outTime;
    }


    public JSONObject getResult(){
        String msg=null;
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(sendMsg);
            printWriter.flush();
            SocketTemp temp=new SocketTemp();
            temp.setSocket(socket);
            temp.setTime(this.outTime);
            temp.setLock(true);
            executorService.execute(new SocketListener(temp));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            msg= in.readLine();
            temp.setLock(false); //执行readline完成后，释放锁，子线程关闭
            if (msg == null) {
                System.out.println("服务器返回空，可能断开socket连接");
                socket.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("出现异常，连接可能已断开");
        }
        return JSON.parseObject(msg);
    }
}