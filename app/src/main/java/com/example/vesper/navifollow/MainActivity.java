package com.example.vesper.navifollow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.vesper.navifollow.camera.CameraActivity;
import com.example.vesper.navifollow.sensor.SensorUtil;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button navibutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SensorUtil.getInstance().printAllSensor(this); // 打印所有可用传感器
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        button=(Button)findViewById(R.id.main_button);
        navibutton=(Button)findViewById(R.id.navibutton);
        navibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CameraActivity2.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });
    }
}

