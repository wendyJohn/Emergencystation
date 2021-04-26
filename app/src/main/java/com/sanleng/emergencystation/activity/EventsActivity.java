package com.sanleng.emergencystation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.EventsAdapter;
import com.sanleng.emergencystation.bean.Events;
import com.sanleng.emergencystation.model.EventsContract;
import com.sanleng.emergencystation.presenter.Requests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//事件记录；
public class EventsActivity extends BaseActivity implements View.OnClickListener, EventsContract, OnDateSetListener {

    private ImageView event_backs;
    private ListView events_listview;
    private List<Events.PageBean.ListBean> mLists;
    private EventsAdapter adapter;
    private Spinner spinner;
    private TextView timea, timeb;
    private String timetype;
    private String timeas = "";
    private String timebs = "";
    private String chevType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();//初始化界面；
        initData(chevType,timeas, timebs);
        initDate();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_events;
    }

    //初始化
    private void initView() {
        event_backs = findViewById(R.id.event_backs);
        timea = findViewById(R.id.timea);
        timeb = findViewById(R.id.timeb);
        events_listview = findViewById(R.id.events_listview);
        spinner = findViewById(R.id.spinner);
        event_backs.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.events);
                if (languages[pos].equals("超时关门")) {
                    chevType = "0";
                    initData(chevType,timeas, timebs);
                }
                if (languages[pos].equals("物资短缺")) {
                    chevType = "1";
                    initData(chevType,timeas, timebs);
                }
                if (languages[pos].equals("物资过期")) {
                    chevType = "2";
                    initData(chevType,timeas, timebs);
                }
                if (languages[pos].equals("温度过高")) {
                    chevType = "3";
                    initData(chevType,timeas, timebs);
                }
                if (languages[pos].equals("湿度过高")) {
                    chevType = "4";
                    initData(chevType,timeas, timebs);
                }
                if (languages[pos].equals("全部")) {
                    chevType = "";
                    initData(chevType,timeas, timebs);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        timea.setOnClickListener(this);
        timeb.setOnClickListener(this);
    }

    //加载数据
    private void initData(String stra, String strb, String strc) {
        Requests.getStoreEvent(EventsActivity.this, getApplicationContext(), stra, strb, strc);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.event_backs:
                finish();
                break;
            case R.id.timea:
                timetype = "时间A";
                mDialogAll.show(EventsActivity.this.getSupportFragmentManager(), "all");
                break;
            case R.id.timeb:
                timetype = "时间B";
                mDialogAll.show(EventsActivity.this.getSupportFragmentManager(), "all");
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

    //时间选择器
    TimePickerDialog mDialogAll;
    private void initDate() {
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(EventsActivity.this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("请选择查询日期")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.gray))
                .setType(Type.ALL)
                .setWheelItemTextSize(16)
                .build();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (timetype.equals("时间A")) {
            timeas = getDateToString(millseconds);
            timea.setText(timeas);
        } else {
            timebs = getDateToString(millseconds);
            timeb.setText(timebs);
            initData(chevType, timeas, timebs);
        }
    }

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
