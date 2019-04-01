package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sanleng.emergencystation.R;

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
}