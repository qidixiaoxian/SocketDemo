package com.qdxx.socektdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class TcpDemoActivity extends AppCompatActivity {

    private EditText mEtIpAddress;
    private EditText mEtServerPort;
    private Button mBtnConnectServer;
    private EditText mEtSendMsg;
    private Button mBtnSendMsg;
    private Button mBtnTest;
    private boolean mIsConnected;
    private OutputStream mOs;
    private InputStream mIs;
    private Button mBtnDisConnectServer;
    private Socket mSocket;
    private StringBuffer mStringBuffer;
    private TextView mTvMsg;
    private TextView mTvByteSize;
    private int mByteSize;
    private ScrollView mScrollMsg;
    private Button mBtnClearMsg;
    private Button mBtnStop;
    private boolean mIsStop;
    private Random random;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_demo);
        initView();
    }

    //初始化视图
    private void initView() {
        mEtIpAddress = TcpDemoActivity.this.findViewById(R.id.et_ip_address);
        mEtServerPort = TcpDemoActivity.this.findViewById(R.id.et_server_port);
        mBtnConnectServer = TcpDemoActivity.this.findViewById(R.id.btn_connect_server);
        mBtnDisConnectServer = TcpDemoActivity.this.findViewById(R.id.btn_dis_connect_server);
        mEtSendMsg = TcpDemoActivity.this.findViewById(R.id.et_send_msg);
        mBtnSendMsg = TcpDemoActivity.this.findViewById(R.id.btn_send_msg);
        mBtnTest = TcpDemoActivity.this.findViewById(R.id.btn_test);
        mTvByteSize = TcpDemoActivity.this.findViewById(R.id.tv_byte_size);
        mTvMsg = TcpDemoActivity.this.findViewById(R.id.tv_msg);
        mScrollMsg = TcpDemoActivity.this.findViewById(R.id.scroll_msg);
        mBtnClearMsg = TcpDemoActivity.this.findViewById(R.id.btn_clear_msg);
        mBtnStop = TcpDemoActivity.this.findViewById(R.id.btn_stop);
        mBtnConnectServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectServer();
            }
        });

        mBtnDisConnectServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectServer();
            }
        });
        mBtnClearMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMsg();
            }
        });
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
        mIsConnected = false;
        mBtnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });
        mStringBuffer = new StringBuffer();
        mByteSize = 0;
        mIsStop = false;
        random = new Random();


    }


    //socket连服务器
    private void connectServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(mEtIpAddress.getText().toString().trim(),
                            Integer.parseInt(mEtServerPort.getText().toString().trim()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TcpDemoActivity.this, "链接成功", Toast.LENGTH_SHORT).show();
                            mBtnDisConnectServer.setVisibility(View.VISIBLE);
                        }
                    });
                    mOs = mSocket.getOutputStream();
                    mIs = mSocket.getInputStream();

                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TcpDemoActivity.this, "链接失败", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        }).start();


    }

    //断开服务器
    private void disconnectServer() {
        try {
            mIs.close();
            mOs.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发送信息
    private void sendMsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mOs.write(mEtSendMsg.getText().toString().getBytes());
                    mOs.flush();
                    mByteSize += mEtSendMsg.getText().toString().getBytes().length;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvMsg.setText(mTvMsg.getText() + byteArrayToString(mEtSendMsg.getText().toString().getBytes(), mEtSendMsg.getText().toString().getBytes().length));
                            mTvByteSize.setText("bytes: " + mByteSize);
                            mScrollMsg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    mScrollMsg.post(new Runnable() {
                                        public void run() {
                                            mScrollMsg.fullScroll(View.FOCUS_DOWN);
                                        }
                                    });
                                }
                            });
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    //清空消息
    private void clearMsg() {
        mTvMsg.setText("");
        mByteSize = 0;
        mTvByteSize.setText("byteSize: " + 0);
    }

    //测试
    private void test() {
       /* while (!mIsStop) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final byte[] b = new byte[8];
                        random.nextBytes(b);
                        mOs.write(b);
                        mOs.flush();
                        mByteSize += 8;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvMsg.setText(mTvMsg.getText() + byteArrayToString(b, 8));
                                mTvByteSize.setText("bytes: " + mByteSize);
                                *//*mScrollMsg.getViewTreeObserver().addOnGlobalLayoutListener
                                (new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        mScrollMsg.post(new Runnable() {
                                            public void run() {
                                                mScrollMsg.fullScroll(View.FOCUS_DOWN);
                                            }
                                        });
                                    }
                                });*//*
                            }
                        });


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }*/
        mHandler = new Handler();
        Runnable r = new Runnable() {

            @Override
            public void run() {
                try {
                    final byte[] b = new byte[8];
                    random.nextBytes(b);
                    mOs.write(b);
                    mOs.flush();
                    mByteSize += 8;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvMsg.setText(mTvMsg.getText() + byteArrayToString(b, 8));
                            mTvByteSize.setText("bytes: " +  mByteSize);
                            mScrollMsg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    mScrollMsg.post(new Runnable() {
                                        public void run() {
                                            mScrollMsg.fullScroll(View.FOCUS_DOWN);
                                        }
                                    });
                                }
                            });
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }


                //每隔1s循环执行run方法

                    mHandler.postDelayed(this, 50);
            }
        };


        mHandler.postDelayed(r, 50);//延时100毫秒


    }

    //停止测试
    private void stop() {
        mIsStop = true;
    }


    public String byteArrayToString(byte[] bytes, int len) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            stringBuffer.append(bytes[i] + " ");
        }
        return stringBuffer.toString();
    }


}
