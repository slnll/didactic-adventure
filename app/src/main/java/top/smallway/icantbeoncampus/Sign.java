package top.smallway.icantbeoncampus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import top.smallway.icantbeoncampus.net.Okhttp;


public class Sign extends AppCompatActivity {
    private TextView id, logid, title, type, time, status;
    private Button schoolGPS;
    private String url = null;
    private String jwsession = MainActivity.get_JWSESSION();
    private String message;
    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Toast.makeText(Sign.this, String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initview();
        Intent intent = getIntent();
        String V_id = intent.getStringExtra("id");
        String V_logid = intent.getStringExtra("logId");
        String V_title = intent.getStringExtra("title");
        String V_time = intent.getStringExtra("time");
        String V_type = intent.getStringExtra("type");
        String V_status = intent.getStringExtra("status");
        String V_mode = intent.getStringExtra("mode");
        String V_schoolId = intent.getStringExtra("schoolId");
        String V_latitude = intent.getStringExtra("latitude");
        String V_longitude = intent.getStringExtra("longitude");
        if (V_mode.equals("1")) {
            url = "https://gw.wozaixiaoyuan.com/sign/mobile/receive/doSignByArea";
        } else {
            url = "https://gw.wozaixiaoyuan.com/sign/mobile/receive/doSignByLocation";
        }

        id.setText("signId:" + V_logid);
        logid.setText("id:" + V_id);
        title.setText(V_title);
        type.setText(V_type);
        time.setText(V_time);
        if (V_status.equals("已签到")) {
            status.setBackgroundResource(R.color.gree);
        } else {
            status.setBackgroundResource(R.color.red);
        }
        status.setText(V_status);

        schoolGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message message = new Message();
                            String res = Okhttp.getInstance().Areasign(url, V_id, V_schoolId, V_logid, V_latitude, V_longitude, jwsession);
                            JSONObject jsonObject = JSONObject.parseObject(res);
                            if (jsonObject.get("code").toString().equals("0")){
                                message.what=0;
                                message.obj="签到成功，请勿重复签到！";
                            }else {
                                message.what = (int) jsonObject.get("code");
                                message.obj = jsonObject.get("message");
                            }
                            mHandler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


    }

    private void initview() {
        id = findViewById(R.id.id);
        logid = findViewById(R.id.logid);
        title = findViewById(R.id.S_title);
        type = findViewById(R.id.S_type);
        time = findViewById(R.id.S_time);
        status = findViewById(R.id.S_status);
        schoolGPS = findViewById(R.id.schoolGPS);
    }


}