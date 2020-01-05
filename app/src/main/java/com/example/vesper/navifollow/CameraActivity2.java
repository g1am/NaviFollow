package com.example.vesper.navifollow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CameraActivity2 extends AppCompatActivity {

    private Button button;
    //////////////////////////////////
    private EditText start;
    private   EditText end;
    public static String tablename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SensorUtil.getInstance().printAllSensor(this); // 打印所有可用传感器

        setContentView(R.layout.activity_camera2);
        /////////////////////////////////
        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        ////////////////////////////////////
        initViews();
    }

    public void onclickMethod(View v){
        tablename=start.getText().toString()+end.getText().toString();
        // tablename="test";
        String postUrl = "http://192.168.166.105:5000/confirm";
        SendMessage(postUrl,tablename);
    }

    private void initViews(){
        button=(Button)findViewById(R.id.main_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CameraActivity3.class));
            }
        });
    }

    private void SendMessage(String url, String tablename) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("tablename", tablename);
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CameraActivity2.this, "服务器错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (res.equals("0")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CameraActivity2.this, "该路线暂时不存在", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CameraActivity2.this, "请进行导航", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                });
            }
        });
    }
}
