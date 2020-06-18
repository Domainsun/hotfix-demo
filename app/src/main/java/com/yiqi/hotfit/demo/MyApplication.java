package com.yiqi.hotfit.demo;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(MyApplication.this, "初始化成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                Toast.makeText(MyApplication.this, "初始化失败", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}

