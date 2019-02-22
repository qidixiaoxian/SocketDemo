package com.qdxx.socektdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
    }

    //初始化视图
    private void initView() {
        DemoActivity.this.findViewById(R.id.btn_tcp_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoActivity.this.startActivity(new Intent(DemoActivity.this,
                        TcpDemoActivity.class));
                DemoActivity.this.finish();
            }
        });
        DemoActivity.this.findViewById(R.id.btn_udp_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoActivity.this.startActivity(new Intent(DemoActivity.this,
                        UdpDemoActivity.class));
                DemoActivity.this.finish();
            }
        });


    }
}
