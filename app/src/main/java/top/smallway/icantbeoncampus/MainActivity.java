package top.smallway.icantbeoncampus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.smallway.icantbeoncampus.net.Okhttp;
import top.smallway.icantbeoncampus.net.SSLSocketClient;

public class MainActivity extends AppCompatActivity {
    private EditText username, password;
    private TextView github,blog;
    private Button login,code;
    private String url = "https://gw.wozaixiaoyuan.com/basicinfo/mobile/login/username";
    private static Request request;
    private static String JWSESSION;
    private final Handler mHandler = new Handler(Looper.myLooper()) {


        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SignTable.class);
                    startActivity(intent);
                    finish();
            }else {
                Toast.makeText(MainActivity.this, String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_A = username.getText().toString();
                String password_A = password.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        try {
                            Message message = new Message();
                            request = Okhttp.getInstance().login_(url,username_A,password_A);
                            Response response = client.newCall(request).execute();
                            JWSESSION=response.headers().get("JWSESSION");
                            JSONObject jsonObject= JSON.parseObject(response.body().string());
                            message.what= (int) jsonObject.get("code");
                            if (message.what==0){
                                message.obj=JWSESSION;
                            }else {
                                message.obj=jsonObject.get("message");
                            }
                            mHandler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
//        登录按钮按下后
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, restartpassword.class);
                startActivity(intent);
            }
        });
//        修改密码按钮按下后
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpUriToBrowser(MainActivity.this,"https://github.com/smallway233/I-can-t-be-on-school");
            }
        });
//        开源地址按下后
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpUriToBrowser(MainActivity.this,"https://www.smallway.top/");
            }
        });
//        博客地址按下后
    }

    private void initview() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        github=findViewById(R.id.github);
        blog=findViewById(R.id.blog);
        code=findViewById(R.id.code);
    }

    public static void jumpUriToBrowser(Context context, String url) {
        if (url.startsWith("www."))
            url = "http://" + url;
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            Toast.makeText(context, "网址错误", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        // 设置意图动作为打开浏览器
        intent.setAction(Intent.ACTION_VIEW);
        // 声明一个Uri
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        context.startActivity(intent);
    }
    public  static String get_JWSESSION(){
        return JWSESSION;
    }

    public static Request get_Request(){
        return request;
    }
}