package top.smallway.icantbeoncampus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import top.smallway.icantbeoncampus.net.Okhttp;

public class restartpassword extends AppCompatActivity {
    private EditText phone, code, new_password;
    //    @param phone 手机号
//    @param code 验证码
//    @param new_password 新密码
    private Button get_code, change;
    //    @param get_code 获取验证码
//    @param change 更改密码

    private final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                get_code.setEnabled(false);
            }
            Toast.makeText(restartpassword.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            if (msg.what==1){
                finish();
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restartpassword);
        initview();
        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_number = phone.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        try {
                            String res = Okhttp.getInstance().get_code(phone_number);
                            JSONObject jsonObject = JSON.parseObject(res);
                            if ((int) jsonObject.get("code") == 0) {
                                message.what = 0;
                                message.obj = "验证码发送成功";
                            } else {
                                message.what = 1;
                                message.obj = "验证码发送失败";
                            }
                            mHandler.sendMessage(message);
                            Log.d("TAG", res);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_number = phone.getText().toString();
                String get_code = code.getText().toString();
                String password = new_password.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        try {
                            String res = Okhttp.getInstance().change_password(phone_number, get_code, password);
                            Log.d("手机号", phone_number);
                            JSONObject jsonObject = JSON.parseObject(res);
                            if ((int) jsonObject.get("code") == 0) {
                                message.what = 1;
                                message.obj = "密码修改成功";
                            } else {
                                message.what = 2;
                                message.obj = "密码修改失败";
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
        phone = findViewById(R.id.phone);
        code = findViewById(R.id.code);
        new_password = findViewById(R.id.new_password);
        get_code = findViewById(R.id.get_code);
        change = findViewById(R.id.change);
    }
}