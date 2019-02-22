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

	// 初始化视图
	public void frameInit() {
		JFrame frame = new JFrame("滚动条及文本域示例");
		frame.setLayout(null);
		frame.setBounds(200, 200, 280, 250);

		getMessage = new JTextArea();
		getMessage.setBounds(5, 0, 250, 160);
		// getMessage.setText("文本域接收信息 ");
		getMessage.setEditable(false);// 不可编辑
		titledBorder=new TitledBorder("byteSize: "+mByteSize);
		getMessage.setBorder(titledBorder);// 设置标题
		getMessage.setLineWrap(true);// 设置自动换行
		getMessage.setWrapStyleWord(true);// 设置以单词为整体换行，(即不会将单词切割成两半)
		JScrollPane scrollPane = new JScrollPane(getMessage);// 添加滚动条
		scrollPane.setBounds(5, 0, 250, 160);

		sendMessage = new JTextField();
		sendMessage.setBounds(5, 170, 250, 30);
		// sendMessage.setText("按回车发送信息：输入的信息要够长，够多，才可以看到自动换行和滚动条，滚动条显示在末尾，即最新输入的一行中！");

		frame.add(scrollPane);
		frame.add(sendMessage);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myEvent();
		frame.setVisible(true);

	}

	// 开始接收线程
	private static void startMutilThreadServer() {

		try {
			ServerSocket serverSocket = new ServerSocket(12398);
			// 死循环
			/*
			 * while(true){ //System.out.println("MultiThreadServer~~~监听~~~");
			 * //accept方法会阻塞，直到有客户端与之建立连接 Socket socket = serverSocket.accept();
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
				// 每次添加信息时刷新文本区域，将滚动条显示在末尾，即显示最新一次输入
				getMessage.setCaretPosition(byteArrayToString(bf2, len)
						.length());
				System.out.println(byteArrayToStr(bf2, len));
				mByteSize+=len;
				getMessage.setBorder(new TitledBorder("byteSIze:"+mByteSize));// 设置标题

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
					// getMessage.append("\r\n信息：：" + msg);
					getMessage.append(msg);
					// 每次添加信息时刷新文本区域，将滚动条显示在末尾，即显示最新一次输入
					getMessage.setCaretPosition(getMessage.getText().length());
					sendMessage.setText(null);
				}
			}
		});
	}

	public void appendMsg(String msg) {

		getMessage.append(msg);
		// 每次添加信息时刷新文本区域，将滚动条显示在末尾，即显示最新一次输入
		getMessage.setCaretPosition(getMessage.getText().length());
		sendMessage.setText(null);
	}

}
