package top.smallway.icantbeoncampus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SignTable extends AppCompatActivity {
    private String url = "https://gw.wozaixiaoyuan.com/sign/mobile/receive/getMySignLogs";
    private String type, status, mode,longitude,latitude;
    private Person 数据;
    private RecyclerView recyclerView;
    private final Handler mHandler = new Handler(Looper.myLooper()) {

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            super.dispatchMessage(msg);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(msg.obj));
            JSONArray jsonArray = JSONArray.parseArray(String.valueOf(jsonObject.get("data")));
            List<Person> personList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("signTitle");
                if (obj.getString("mode").equals("1")) {
                    type = "校区签到\n(小tips：签到位置必须在学校)";
                    mode = obj.getString("mode");
                } else {
                    type = "定位签到\n(小tips：定位签到可以签到在任意位置，所以作者没写签到在学校的方法)";
                    mode = obj.getString("mode");
                }
                String time = obj.getString("signContext").trim();
                String schoolId = obj.getString("schoolId");
                String id = obj.getString("id");
                String logId = obj.getString("signId");
                if (obj.getString("type").equals("1")) {
                    status = "已签到";

                } else {
                    status = "未签到";
                }

                JSONArray jsonArray1 = obj.getJSONArray("areaList");
                if (jsonArray1 == null || jsonArray1.size() == 0) {
                    Log.e("TAG", "JSONArray is empty.");
                    latitude=null;
                    longitude=null;
                    // 或者返回一个默认的JSONArray对象
                }else {
                    JSONObject jsonObject1= (JSONObject) jsonArray1.get(0);
                    latitude=jsonObject1.getString("latitude");
                    longitude=jsonObject1.getString("longitude");
                }


                数据 = new Person(title, type, time, status, id, logId, schoolId, mode, latitude, longitude);
                personList.add(数据);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SignTable.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                MyAdapter myAdapter = new MyAdapter(personList);
                recyclerView.addItemDecoration(new SpaceItemDecoration(5));
                recyclerView.setAdapter(myAdapter);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_table);
        initview();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    String res = get_signTable(url);
                    Log.d("TAG", res);
                    message.obj = res;
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initview() {
        recyclerView = findViewById(R.id.sign_table);
    }


    private String get_signTable(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("page", "1")
                .addQueryParameter("size", "10");
//        转换成String url
        String Url = urlBuilder.build().toString();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(Url)
                .addHeader("JWSESSION", MainActivity.get_JWSESSION())
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}