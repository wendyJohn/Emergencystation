package com.sanleng.emergencystation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.sanleng.emergencystation.R;

//远程控制；
public class RemoteControlActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backs;
    private GridView gridViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();//初始化界面；
        initData();

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_remotecontrol;
    }

    //初始化
    private void initView() {
        backs = findViewById(R.id.backs);
        backs.setOnClickListener(this);

    }

    //加载数据
    private void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.backs:
                finish();
                break;

        }
    }

}
