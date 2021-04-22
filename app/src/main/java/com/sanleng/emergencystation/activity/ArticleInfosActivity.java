package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sanleng.emergencystation.R;

/**
 * 查看详情
 */
public class ArticleInfosActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout back_onlines;
    private SystemBarTintManager tintManager;
    private TextView titles;
    private WebView webView;
    private ImageView nodata;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
        initData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_articleinfos;
    }

    //初始化
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        initWindow();
        titles = findViewById(R.id.titles);
        nodata= findViewById(R.id.nodata);
        back_onlines = findViewById(R.id.back_onlines);
        //获得控件
        webView = findViewById(R.id.wv_webview);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        back_onlines.setOnClickListener(this);
    }


    private void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("articletitle");
        String article = intent.getStringExtra("article");
        if(article.length()>0){
            nodata.setVisibility(View.GONE);
        }else{
            nodata.setVisibility(View.VISIBLE);
        }
        titles.setText(title);
        webView.loadDataWithBaseURL(null,article,"text/html","utf-8",null);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void initWindow() {//初始化，将状态栏和标题栏设为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(ArticleInfosActivity.this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_onlines:
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


}
