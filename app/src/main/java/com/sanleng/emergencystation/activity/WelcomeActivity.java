package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;

import com.sanleng.emergencystation.MyApplication;
import com.sanleng.emergencystation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导界面
 * @author Qiaoshi
 * @date 创建时间：2019年01月07日
 */

public class WelcomeActivity extends BaseActivity {
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.welcome_activity);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.welcome_activity;
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionStrs = new ArrayList<>();
            int hasWriteSdcardPermission = ContextCompat.checkSelfPermission(WelcomeActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteSdcardPermission != PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            int hasCameraPermission = ContextCompat.checkSelfPermission(WelcomeActivity.this, android.Manifest.permission.CAMERA);
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(android.Manifest.permission.CAMERA);
            }
            String[] stringArray = permissionStrs.toArray(new String[0]);
            if (permissionStrs.size() > 0) {
                requestPermissions(stringArray, MyApplication.REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
    }
}