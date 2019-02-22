import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiThreadServer {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12306);
            //死循环
            while(true){
                System.out.println("MultiThreadServer~~~监听~~~");
                //accept方法会阻塞，直到有客户端与之建立连接
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
