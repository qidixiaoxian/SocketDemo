import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiThreadServer {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12306);
            //��ѭ��
            while(true){
                System.out.println("MultiThreadServer~~~����~~~");
                //accept������������ֱ���пͻ�����֮��������
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
