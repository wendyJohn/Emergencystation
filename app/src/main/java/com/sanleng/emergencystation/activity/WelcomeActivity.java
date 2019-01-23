package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.sanleng.emergencystation.R;

/**
 * 引导界面
 * @author Qiaoshi
 * @date 创建时间：2019年01月07日
 */

public class WelcomeActivity extends AppCompatActivity {
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.welcome_activity);
        StatusBarUtil.setColor(WelcomeActivity.this,R.color.translucency);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);

    }
}