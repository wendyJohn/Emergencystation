package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.CabinetsAdapter;
import com.sanleng.emergencystation.bean.Cabinet;
import com.sanleng.emergencystation.dialog.TipsDialog;
import com.sanleng.emergencystation.model.CabinetContract;
import com.sanleng.emergencystation.mqtt.MyMqttClient;
import com.sanleng.emergencystation.presenter.Requests;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.utils.Utils;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//远程控制；
public class RemoteControlActivity extends BaseActivity implements View.OnClickListener, CabinetContract, CompoundButton.OnCheckedChangeListener {

    private ImageView backs;
    private ListView cabinets_list;
    private CabinetsAdapter cabinetsAdapter;
    private LinearLayout lina, linb, linc, lind;
    private CheckBox checkboxa, checkboxb, checkboxc, checkboxd;
    private List<String> checkedList = new ArrayList<>();
    private TextView open_doors;
    private String mac;
    private TipsDialog tipsDialog;
    private String checkedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();//初始化界面；
        initData();
        MqttClient();//连接Mqtt协议；
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_remotecontrol;
    }

    //初始化
    private void initView() {
        open_doors = findViewById(R.id.open_doors);

        lina = findViewById(R.id.lina);
        linb = findViewById(R.id.linb);
        linc = findViewById(R.id.linc);
        lind = findViewById(R.id.lind);

        checkboxa = findViewById(R.id.checkboxa);
        checkboxb = findViewById(R.id.checkboxb);
        checkboxc = findViewById(R.id.checkboxc);
        checkboxd = findViewById(R.id.checkboxd);

        backs = findViewById(R.id.backs);
        cabinets_list = findViewById(R.id.cabinets_list);
        backs.setOnClickListener(this);
        open_doors.setOnClickListener(this);
        // 绑定事件
        checkboxa.setOnCheckedChangeListener(this);
        checkboxb.setOnCheckedChangeListener(this);
        checkboxc.setOnCheckedChangeListener(this);
        checkboxd.setOnCheckedChangeListener(this);
    }

    //加载数据
    private void initData() {
        Requests.getVersalStores(RemoteControlActivity.this, getApplicationContext());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.backs:
                finish();
                break;
            //远程开门
            case R.id.open_doors:
                try {
                    Collections.sort(checkedList);
                    checkedValues = Utils.listToString(checkedList);
                    if (checkedValues == null || "".equals(checkedValues)) {
                        new SVProgressHUD(RemoteControlActivity.this).showInfoWithStatus("请选择相应要开的柜门");
                    } else {
                        tipsDialog = new TipsDialog(RemoteControlActivity.this, "是否确定远程开门", m_Handler);
                        tipsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        tipsDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void Success(List<Cabinet.MapListBean> mList) {
        try {
            mac = mList.get(0).getUsMacAddress();
            setCabinets(mList.get(0).getUscUsCode());

            cabinetsAdapter = new CabinetsAdapter(RemoteControlActivity.this, mList);
            cabinets_list.setAdapter(cabinetsAdapter);
            cabinets_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cabinetsAdapter.changeSelected(position);//刷新
                    mac = mList.get(position).getUsMacAddress();
                    setCabinets(mList.get(position).getUscUsCode());
                }
            });


            cabinets_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cabinetsAdapter.changeSelected(position);//刷新
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Failed() {

    }

    //设置副柜的数量
    private void setCabinets(int str) {
        checkboxa.setChecked(false);
        checkboxb.setChecked(false);
        checkboxc.setChecked(false);
        checkboxd.setChecked(false);
        //未选中删除ABC
        Iterator<String> iterator = checkedList.iterator();
        while (iterator.hasNext()) {
            String value = iterator.next();
            if (value.equals("A") || value.equals("B") || value.equals("C") || value.equals("D")) {
                iterator.remove();
            }
        }
        if (str == 1) {
            lina.setVisibility(View.VISIBLE);
            linb.setVisibility(View.GONE);
            linc.setVisibility(View.GONE);
            lind.setVisibility(View.GONE);
        }
        if (str == 2) {
            lina.setVisibility(View.VISIBLE);
            linb.setVisibility(View.VISIBLE);
            linc.setVisibility(View.GONE);
            lind.setVisibility(View.GONE);
        }
        if (str == 3) {
            lina.setVisibility(View.VISIBLE);
            linb.setVisibility(View.VISIBLE);
            linc.setVisibility(View.VISIBLE);
            lind.setVisibility(View.GONE);
        }
        if (str == 4) {
            lina.setVisibility(View.VISIBLE);
            linb.setVisibility(View.VISIBLE);
            linc.setVisibility(View.VISIBLE);
            lind.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 复选框选中与不选中的事件
     */
    @Override
    public void onCheckedChanged(CompoundButton checkBox, boolean checked) {
        // TODO Auto-generated method stub
        switch (checkBox.getId()) {
            case R.id.checkboxa:
                if (checked) {// 选中
                    checkedList.add("A");
                } else {
                    //未选中删除A
                    Iterator<String> iterator = checkedList.iterator();
                    while (iterator.hasNext()) {
                        String value = iterator.next();
                        if (value.equals("A")) {
                            iterator.remove();
                        }
                    }
                }
                break;
            case R.id.checkboxb:
                if (checked) {// 选中
                    checkedList.add("B");
                } else {
                    //未选中删除A
                    Iterator<String> iterator = checkedList.iterator();
                    while (iterator.hasNext()) {
                        String value = iterator.next();
                        if (value.equals("B")) {
                            iterator.remove();
                        }
                    }
                }
                break;
            case R.id.checkboxc:
                if (checked) {// 选中
                    checkedList.add("C");
                } else {
                    //未选中删除A
                    Iterator<String> iterator = checkedList.iterator();
                    while (iterator.hasNext()) {
                        String value = iterator.next();
                        if (value.equals("C")) {
                            iterator.remove();
                        }
                    }
                }
                break;
            case R.id.checkboxd:
                if (checked) {// 选中
                    checkedList.add("D");
                } else {
                    //未选中删除A
                    Iterator<String> iterator = checkedList.iterator();
                    while (iterator.hasNext()) {
                        String value = iterator.next();
                        if (value.equals("D")) {
                            iterator.remove();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    //连接MQTT
    public void MqttClient() {
        MyMqttClient.sharedCenter().setConnect();//连接MQTT
        MyMqttClient.sharedCenter().setOnServerDisConnectedCallback(new MyMqttClient.OnServerDisConnectedCallback() {
            @Override
            public void callback(Throwable e) {
            }
        });

        //MQTT接收数据
        MyMqttClient.sharedCenter().setOnServerReadStringCallback(new MyMqttClient.OnServerReadStringCallback() {
            @Override
            public void callback(String Topic, MqttMessage Msg, byte[] MsgByte) {
            }
        });

        //连接上服务器
        MyMqttClient.sharedCenter().setOnServerConnectedCallback(new MyMqttClient.OnServerConnectedCallback() {
            @Override
            public void callback() {
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler m_Handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                //确认开门
                case 0x4521260:
                    try {
                        JSONObject object=new JSONObject();
                        object.put("control",checkedValues);
                        object.put("userIds",PreferenceUtils.getString(context, "ids"));
                        MyMqttClient.sharedCenter().setSendData("iotcontrol/" + mac, object.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        MyMqttClient.sharedCenter().setDisConnect();//断开连接
        super.onDestroy();
    }
}
