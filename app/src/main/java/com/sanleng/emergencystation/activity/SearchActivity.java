package com.sanleng.emergencystation.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.PagersAdapter;
import com.sanleng.emergencystation.dialog.RecordPopupWindow;
import com.sanleng.emergencystation.utils.MessageEvent;
import com.sanleng.emergencystation.view.PagerSlidingTab;
import com.sanleng.emergencystation.view.PopWins;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 搜索操作
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, OnDateSetListener {
    private ImageView backs;
    private PagerSlidingTab pagerSlidingTab;
    private ViewPager viewPager;
    private String Cabinetnumber;//当前几号柜
    private LinearLayout usagerecord;
    private RecordPopupWindow mRecordPopupWindow;

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
        if (!EventBus.getDefault().isRegistered(this)) { EventBus.getDefault().register(this); }
        backs = findViewById(R.id.backs);
        backs.setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);
        pagerSlidingTab = findViewById(R.id.pagerSlidingTab);
        viewPager.setAdapter(new PagersAdapter(getSupportFragmentManager(), "4"));
        pagerSlidingTab.setViewPager(viewPager);/*绑定pagerSlidingTab和ViewPager*/
        usagerecord = findViewById(R.id.usagerecord);
        usagerecord.setOnClickListener(this);

    }

    //加载数据
    private void initData() {
        try {
            initDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            case R.id.backs:
                finish();
                break;
            case R.id.usagerecord:
                usagerecord.setVisibility(View.GONE);
                mRecordPopupWindow = new RecordPopupWindow(SearchActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialogAll.show(SearchActivity.this.getSupportFragmentManager(), "all");
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopFormBottom(v);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        usagerecord.setVisibility(View.VISIBLE);
                        mRecordPopupWindow.dismiss();
                    }
                });
                View rootView = LayoutInflater.from(SearchActivity.this).inflate(R.layout.fragment_search, null);
                mRecordPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    //时间选择器
    TimePickerDialog mDialogAll;
    private void initDate() {
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(SearchActivity.this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("请选择查询日期")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.gray))
                .setType(Type.ALL)
                .setWheelItemTextSize(16)
                .build();
    }


    //操作查询选择
    PopWins popWins;
    public void showPopFormBottom(View view) {
        popWins = new PopWins(this, onClickListener);
        popWins.showAtLocation(findViewById(R.id.search_view), Gravity.CENTER, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_takea:
                    System.out.println("btn_takea");
                    popWins.dismiss();
                    break;
                case R.id.btn_takeb:
                    System.out.println("btn_takeb");
                    popWins.dismiss();
                    break;
                case R.id.btn_takec:
                    System.out.println("btn_takec");
                    popWins.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDateSet(com.jzxiang.pickerview.TimePickerDialog timePickerView, long millseconds) {

    }
}
