package com.qdxx.socektdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Button mBtnSnedMsg;
    private TextView mTvMsg;
    private EditText mEtSendMsg;
    private EditText mEtServerIp;
    private EditText mEtServerPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //初始化视图
    private void initView() {
        mBtnSnedMsg = MainActivity.this.findViewById(R.id.btn_send_msg);
        mTvMsg = MainActivity.this.findViewById(R.id.tv_msg);
        mEtSendMsg = MainActivity.this.findViewById(R.id.et_send_msg);
        mEtServerIp = MainActivity.this.findViewById(R.id.et_server_ip);
        mEtServerPort = MainActivity.this.findViewById(R.id.et_server_port);
        mBtnSnedMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });

    }

    //发送信息
    private void sendMsg() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //1.创建监听指定服务器地址以及指定服务器监听的端口号
//                    Socket socket = new Socket("111.111.11.11", 12306);//111.111.11
                    Socket socket = new Socket(mEtServerIp.getText().toString().trim(),
                            Integer.parseInt(mEtServerPort.getText().toString().trim()));
                    //111.111.11
                    // .11为我这个本机的IP地址，端口号为12306.
                    //2.拿到客户端的socket对象的输出流发送给服务器数据
                    OutputStream os = socket.getOutputStream();
                    //写入要发送给服务器的数据
                    os.write(mEtSendMsg.getText().toString().getBytes());
                    os.flush();
                    socket.shutdownOutput();
                    //拿到socket的输入流，这里存储的是服务器返回的数据
                    InputStream is = socket.getInputStream();
                    //解析服务器返回的数据
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader bufReader = new BufferedReader(reader);
                    String s = null;
                    final StringBuffer sb = new StringBuffer();
                    while ((s = bufReader.readLine()) != null) {
                        sb.append(s);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvMsg.setText(sb.toString());
                        }
                    });
                    //3、关闭IO资源（注：实际开发中需要放到finally中）
                    bufReader.close();
                    reader.close();
                    is.close();
                    os.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
