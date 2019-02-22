import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String args[]) {

	     
        try {
            // Ϊ�˿����̣��ҾͰ����еĴ��붼����main��������,Ҳû�в�׽�쳣��ֱ���׳�ȥ�ˡ�ʵ�ʿ����в���ȡ��
            // 1.�½�ServerSocket���󣬴���ָ���˿ڵ�����
            ServerSocket serverSocket = new ServerSocket(12308);
            System.out.println("����˼�����ʼ��~~~~");
            // 2.���м���
            Socket socket = serverSocket.accept();// ��ʼ����9999�˿ڣ������յ����׽��ֵ����ӡ�
            // 3.�õ����������ͻ��˷��͵���Ϣ�������
            InputStream is = socket.getInputStream();
            // 4.��������
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufReader = new BufferedReader(reader);
            String s = null;
            StringBuffer sb = new StringBuffer();
            while ((s = bufReader.readLine()) != null) {
                sb.append(s);
            }
            System.out.println("��������" + sb.toString());
            // �ر�������
            socket.shutdownInput();

            OutputStream os = socket.getOutputStream();
            os.write(("���Ƿ����,�ͻ��˷����ҵ����ݾ��ǣ�"+sb.toString()).getBytes());
            os.flush();
            // �ر������
            socket.shutdownOutput();
            os.close();

            // �ر�IO��Դ
            bufReader.close();
            reader.close();
            is.close();

            socket.close();// �ر�socket
            serverSocket.close();// �ر�ServerSocket

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
}
