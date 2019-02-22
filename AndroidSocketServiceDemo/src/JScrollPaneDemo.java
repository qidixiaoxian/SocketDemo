import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class JScrollPaneDemo {
	public JTextField sendMessage;
	public static JTextArea getMessage;
	public static TitledBorder titledBorder;
	public static int mByteSize=0;

	public static void main(String[] args) {
		new JScrollPaneDemo().frameInit();
		startMutilThreadServer();
	}

	// ��ʼ����ͼ
	public void frameInit() {
		JFrame frame = new JFrame("���������ı���ʾ��");
		frame.setLayout(null);
		frame.setBounds(200, 200, 280, 250);

		getMessage = new JTextArea();
		getMessage.setBounds(5, 0, 250, 160);
		// getMessage.setText("�ı��������Ϣ ");
		getMessage.setEditable(false);// ���ɱ༭
		titledBorder=new TitledBorder("byteSize: "+mByteSize);
		getMessage.setBorder(titledBorder);// ���ñ���
		getMessage.setLineWrap(true);// �����Զ�����
		getMessage.setWrapStyleWord(true);// �����Ե���Ϊ���廻�У�(�����Ὣ�����и������)
		JScrollPane scrollPane = new JScrollPane(getMessage);// ��ӹ�����
		scrollPane.setBounds(5, 0, 250, 160);

		sendMessage = new JTextField();
		sendMessage.setBounds(5, 170, 250, 30);
		// sendMessage.setText("���س�������Ϣ���������ϢҪ���������࣬�ſ��Կ����Զ����к͹���������������ʾ��ĩβ�������������һ���У�");

		frame.add(scrollPane);
		frame.add(sendMessage);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myEvent();
		frame.setVisible(true);

	}

	// ��ʼ�����߳�
	private static void startMutilThreadServer() {

		try {
			ServerSocket serverSocket = new ServerSocket(12398);
			// ��ѭ��
			/*
			 * while(true){ //System.out.println("MultiThreadServer~~~����~~~");
			 * //accept������������ֱ���пͻ�����֮�������� Socket socket = serverSocket.accept();
			 * ServerThread serverThread = new ServerThread(socket);
			 * serverThread.start(); }
			 */
			Socket socket = serverSocket.accept();
			InputStream mInputStream = socket.getInputStream();
			byte[] bf2 = new byte[1024];
			int len = 0;
			while (true) {
				len = mInputStream.read(bf2, 0, bf2.length);
				getMessage.append(byteArrayToString(bf2, len));
				// ÿ�������Ϣʱˢ���ı����򣬽���������ʾ��ĩβ������ʾ����һ������
				getMessage.setCaretPosition(byteArrayToString(bf2, len)
						.length());
				System.out.println(byteArrayToStr(bf2, len));
				mByteSize+=len;
				getMessage.setBorder(new TitledBorder("byteSIze:"+mByteSize));// ���ñ���

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String byteArrayToString(byte[] bytes, int len) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < len; i++) {
			stringBuffer.append(bytes[i] + " ");
		}
		return stringBuffer.toString();
	}

	public static String byteArrayToStr(byte[] bytes, int len) {
		byte[] temp = new byte[len];
		for (int i = 0; i < len; i++) {
			temp[i] = bytes[i];
		}
		return new String(temp,StandardCharsets.UTF_8);
	}

	public void myEvent() {
		sendMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String msg = sendMessage.getText();
					// getMessage.append("\r\n��Ϣ����" + msg);
					getMessage.append(msg);
					// ÿ�������Ϣʱˢ���ı����򣬽���������ʾ��ĩβ������ʾ����һ������
					getMessage.setCaretPosition(getMessage.getText().length());
					sendMessage.setText(null);
				}
			}
		});
	}

	public void appendMsg(String msg) {

		getMessage.append(msg);
		// ÿ�������Ϣʱˢ���ı����򣬽���������ʾ��ĩβ������ʾ����һ������
		getMessage.setCaretPosition(getMessage.getText().length());
		sendMessage.setText(null);
	}

}
