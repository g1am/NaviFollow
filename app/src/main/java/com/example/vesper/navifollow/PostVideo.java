package com.example.vesper.navifollow;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vesper.navifollow.postvideo.OkHttpUtil;
import com.example.vesper.navifollow.postvideo.ProgressListener;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PostVideo extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getName();
    private ProgressBar post_progress;
    private TextView post_text;
    ///////////////////////
    private EditText username;
    private   EditText password;
    public static String tablename;
    ///////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_video);
///////////////////
        username = findViewById(R.id.start);
        password = findViewById(R.id.end);

        /////////////////////////
        post_progress = (ProgressBar) findViewById(R.id.post_progress);
        post_text = (TextView) findViewById(R.id.post_text);

        findViewById(R.id.ok_post_file).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        tablename=username.getText().toString()+password.getText().toString();
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/mahc/video/0/1.3gp");
        String postUrl = "http://192.168.166.105:8080/upload";
        OkHttpUtil.postFile(postUrl, new ProgressListener() {
            @Override
            public void onProgress(long currentBytes, long contentLength, boolean done) {
                Log.i(TAG, "currentBytes==" + currentBytes + "==contentLength==" + contentLength + "==done==" + done);
                int progress = (int) (currentBytes * 100 / contentLength);
                post_progress.setProgress(progress);
                post_text.setText(progress + "%");
            }
        }, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "whataaa");
                if (response != null) {
                    Log.i(TAG, "whataaabbbbbb");
                    String result = response.body().string();
                    Log.i(TAG, "result===" + result);
                    Toast.makeText(PostVideo.this, result, Toast.LENGTH_SHORT);
                }
            }
        }, file);

    }
}

