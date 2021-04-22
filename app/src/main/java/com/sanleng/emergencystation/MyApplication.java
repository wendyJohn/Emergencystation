package com.sanleng.emergencystation;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.sanleng.emergencystation.utils.GetMac;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends MultiDexApplication {
    private static Application instance;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 0x1231232;
    private static Handler mainHandler;
    private static Context context = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this);

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "e5794d4bf0", true, strategy);
        achieveMac();
        mainHandler = new Handler();
    }

    public static Context getAppContext() {
        return instance;
    }
    public static String MAC = "";
    public static String getMac() {
        return MAC;
    }

    /**
     * 获取本地mac地址
     * 初始化socket
     */
    public void achieveMac() {
        MAC = GetMac.getMacAddress().replaceAll(":", "");
    }
    public static Context getContext() {
        return context;
    }
    public static Handler getHandler(){
        return mainHandler;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
