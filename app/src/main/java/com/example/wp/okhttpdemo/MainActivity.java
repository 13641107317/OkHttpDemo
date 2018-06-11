package com.example.wp.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                request();
            }
        }).start();
    }

    public void reques() {
        //同步
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url("http://www.baidu.com").get().build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            Log.i(TAG, "reques: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void request() {
        //异步
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url("http://www.baidu.com").get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功
                Log.i(TAG, "onResponse: " + response.body().string());
            }
        });
    }
}
