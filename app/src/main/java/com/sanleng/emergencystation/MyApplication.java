package com.sanleng.emergencystation;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.SDKInitializer;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends MultiDexApplication {
    private static Application instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this);
//        if (!"generic".equalsIgnoreCase(Build.BRAND)) {
//            SDKInitializer.initialize(getApplicationContext());
//        }
    }
}
