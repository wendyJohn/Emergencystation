package com.sanleng.emergencystation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanleng.emergencystation.R;


public class E_StationDialog extends Dialog {
    Context context;
    private TextView name;
    private TextView address;
    private TextView eliminate;
    private TextView walknavigation;
    private TextView drivenavigation;

    private String names;
    private String addresses;

    private android.view.View.OnClickListener clickListener2;
    private ImageView canle;

    public E_StationDialog(Context context,String names ,String addresses,android.view.View.OnClickListener clickListener2) {
        super(context);
        this.context = context;
        this.names = names;
        this.addresses = addresses;
        this.clickListener2 = clickListener2;
    }

    public E_StationDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        this.setContentView(R.layout.e_stationdialog);
        // this.setCancelable(false);// 设置点击屏幕Dialog不消失

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        name.setText("名称：" + names);
        address.setText("地址：" + addresses);
        eliminate = findViewById(R.id.eliminate);
        walknavigation = findViewById(R.id.walknavigation);
        drivenavigation = findViewById(R.id.drivenavigation);
        canle = findViewById(R.id.canles);

        eliminate.setOnClickListener(clickListener2);
        walknavigation.setOnClickListener(clickListener2);
        drivenavigation.setOnClickListener(clickListener2);
        canle.setOnClickListener(clickListener2);
    }

}
