package com.sanleng.emergencystation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.PagersAdapter;
import com.sanleng.emergencystation.utils.MessageEvent;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.view.PagerSlidingTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 搜索操作
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backs;
    private PagerSlidingTab pagerSlidingTab;
    private ViewPager viewPager;
    private LinearLayout usagerecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    //初始化
    private void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        backs = findViewById(R.id.searbacks);
        backs.setOnClickListener(this);
        viewPager = findViewById(R.id.viewPager);
        pagerSlidingTab = findViewById(R.id.pagerSlidingTab);
        if (PreferenceUtils.getString(context, "number").equals("1")) {
            pagerSlidingTab.setVisibility(View.GONE);
        }
        viewPager.setAdapter(new PagersAdapter(getSupportFragmentManager(), PreferenceUtils.getString(context, "number")));
        pagerSlidingTab.setViewPager(viewPager);/*绑定pagerSlidingTab和ViewPager*/
        usagerecord = findViewById(R.id.usagerecord);
        usagerecord.setOnClickListener(this);
    }

    //加载数据
    private void initData() {

    }


    /**
     * 接收EventBus返回数据
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backData(MessageEvent messageEvent) {
        switch (messageEvent.getTAG()) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回首页
            case R.id.searbacks:
                finish();
                break;
            case R.id.usagerecord:
                //查看更多记录
                startActivity(new Intent(SearchActivity.this, RecordsActivity.class));
                break;
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }


}
