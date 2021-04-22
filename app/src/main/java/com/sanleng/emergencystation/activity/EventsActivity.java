package com.sanleng.emergencystation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.EventsAdapter;
import com.sanleng.emergencystation.bean.Events;
import com.sanleng.emergencystation.model.EventsContract;
import com.sanleng.emergencystation.presenter.Requests;

import java.util.ArrayList;
import java.util.List;

//事件记录；
public class EventsActivity extends BaseActivity implements View.OnClickListener, EventsContract {

    private ImageView event_backs;
    private ListView events_listview;
    private List<Events.PageBean.ListBean> mLists;
    private EventsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();//初始化界面；
        initData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_events;
    }

    //初始化
    private void initView() {
        event_backs = findViewById(R.id.event_backs);
        events_listview = findViewById(R.id.events_listview);
        event_backs.setOnClickListener(this);

    }

    //加载数据
    private void initData() {
        Requests.getStoreEvent(EventsActivity.this, getApplicationContext(), "", "", "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.event_backs:
                finish();
                break;

        }
    }

    @Override
    public void Success(List<Events.PageBean.ListBean> mList) {
        mLists = new ArrayList<>();
        mLists.clear();
        mLists = mList;

        adapter = new EventsAdapter(EventsActivity.this, mLists);
        events_listview.setAdapter(adapter);

    }

    @Override
    public void Failed() {

    }
}
