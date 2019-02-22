import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class ServerThread extends Thread {


    private Socket socket;

    //�ڹ����еõ�Ҫ�����Ự��socket
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        super.run();
        InputStreamReader reader = null;
        BufferedReader bufReader = null;
        OutputStream os = null; 
        try {
            reader = new InputStreamReader(socket.getInputStream());
            bufReader = new BufferedReader(reader);
            String s = null;
            StringBuffer sb = new StringBuffer();
            while((s = bufReader.readLine()) != null){
                sb.append(s);
            }
            System.out.println("��������"+sb.toString());
        
            JScrollPaneDemo.getMessage.append(sb.toString());
			// ÿ�������Ϣʱˢ���ı����򣬽���������ʾ��ĩβ������ʾ����һ������
            JScrollPaneDemo.getMessage.setCaretPosition(sb.toString().length());
        
            
            //�ر�������
            socket.shutdownInput();
            
            //���ظ��ͻ�������
            os = socket.getOutputStream();
            os.write(("���Ƿ����,�ͻ��˷����ҵ����ݾ��ǣ�"+sb.toString()).getBytes());
            
            os.flush();
            socket.shutdownOutput();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally{//�ر�IO��Դ
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if(bufReader != null){
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
         
          
    }

}
