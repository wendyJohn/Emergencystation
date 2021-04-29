package com.sanleng.emergencystation.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.sanleng.emergencystation.MyApplication;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.RecordsAdapter;
import com.sanleng.emergencystation.bean.Trace;
import com.sanleng.emergencystation.model.StoresContract;
import com.sanleng.emergencystation.presenter.Requests;
import com.sanleng.emergencystation.utils.MessageEvent;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.view.PopWins;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 出入库记录表
 *
 * @author Qiaoshi
 */
public class RecordsActivity extends BaseActivity implements View.OnClickListener, StoresContract, OnDateSetListener {

    private ImageView rebacks;
    private RecordsAdapter adapter;
    private ListView record_listview;
    private LinearLayout timeselection, operationselection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        loadData();
        initDate();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_records;
    }

    private void initview() {
        rebacks = findViewById(R.id.rebacks);
        record_listview = findViewById(R.id.record_listview);
        timeselection = findViewById(R.id.timeselection);
        operationselection = findViewById(R.id.operationselection);
        timeselection.setOnClickListener(this);
        operationselection.setOnClickListener(this);
        rebacks.setOnClickListener(this);
    }

    // 加载数据
    private void loadData() {
        Requests.GetStoreIo(RecordsActivity.this, getApplicationContext(), PreferenceUtils.getString(getApplicationContext(), "us_id"), "", "", "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.rebacks:
                finish();
                break;
            case R.id.timeselection:
                mDialogAll.show(RecordsActivity.this.getSupportFragmentManager(), "all");
                break;
            case R.id.operationselection:
                showPopFormBottom(v);
                break;
        }
    }

    //时间选择器
    TimePickerDialog mDialogAll;

    private void initDate() {
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(RecordsActivity.this)
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
        popWins.showAtLocation(findViewById(R.id.record_view), Gravity.CENTER, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_takea:
                    Requests.GetStoreIo(RecordsActivity.this, getApplicationContext(), PreferenceUtils.getString(getApplicationContext(), "us_id"), "", "", "");
                    popWins.dismiss();
                    break;
                case R.id.btn_takeb:
                    Requests.GetStoreIo(RecordsActivity.this, getApplicationContext(), PreferenceUtils.getString(getApplicationContext(), "us_id"), "2", "", "");
                    popWins.dismiss();
                    break;
                case R.id.btn_takec:
                    Requests.GetStoreIo(RecordsActivity.this, getApplicationContext(), PreferenceUtils.getString(getApplicationContext(), "us_id"), "1", "", "");
                    popWins.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onDateSet(com.jzxiang.pickerview.TimePickerDialog timePickerView, long millseconds) {
        String times = getDateToString(millseconds);
        Requests.GetStoreIo(RecordsActivity.this, getApplicationContext(), PreferenceUtils.getString(getApplicationContext(), "us_id"), "", times, "");
    }

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }


    @Override
    public void Success(List<Trace.PageBean.ListBean> mList) {
        try {
            adapter = new RecordsAdapter(RecordsActivity.this, mList);
            record_listview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Failed() {

    }
}
