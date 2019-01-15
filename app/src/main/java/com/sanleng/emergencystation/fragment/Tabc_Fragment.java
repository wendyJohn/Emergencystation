package com.sanleng.emergencystation.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.LoginActivity;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import java.io.File;

/**
 * 首页
 */
public class Tabc_Fragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private TextView login_out;
    public static final String ACTION_IMGHEAD_PORTRAIT = "image_head";

    private RelativeLayout changepassword;
    private RelativeLayout scavengingcaching;
    private RelativeLayout dataupdate;
    private RelativeLayout versionupdate;
    private RelativeLayout aboutus;

    private TextView tv_user_headname;
    private ImageView iv_userhead;
    private File cameraFile;
    private TextView item_search_addb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment, null);
        try {
            findView();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        initListener();
        return view;
    }

    private void findView() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_IMGHEAD_PORTRAIT);
        // 头像设置
        try {
            getActivity().registerReceiver(imgHeadportrait, filter);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        login_out = (TextView) view.findViewById(R.id.login_out);

        changepassword = (RelativeLayout) view.findViewById(R.id.changepassword);
        scavengingcaching = (RelativeLayout) view.findViewById(R.id.scavengingcaching);
        dataupdate = (RelativeLayout) view.findViewById(R.id.dataupdate);
        versionupdate = (RelativeLayout) view.findViewById(R.id.versionupdate);
        aboutus = (RelativeLayout) view.findViewById(R.id.aboutus);

        tv_user_headname = (TextView) view.findViewById(R.id.tv_user_headname);
        item_search_addb = (TextView) view.findViewById(R.id.item_search_addb);

        String uesrname = PreferenceUtils.getString(getActivity(), "EmergencyStation_username");
        tv_user_headname.setText(uesrname);

        // 头像
        iv_userhead = (ImageView) view.findViewById(R.id.iv_userhead);
        cameraFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg");
        if (!cameraFile.exists()) {
            cameraFile.getParentFile().mkdir();
            iv_userhead.setImageResource(R.drawable.ic_person);
        } else {
            iv_userhead.setImageBitmap(BitmapFactory.decodeFile(cameraFile.getAbsolutePath()));
        }
        iv_userhead.setOnClickListener(this);
    }

    private void initListener() {
        login_out.setOnClickListener(this);
        changepassword.setOnClickListener(this);
        scavengingcaching.setOnClickListener(this);
        dataupdate.setOnClickListener(this);
        versionupdate.setOnClickListener(this);
        aboutus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 个人信息
            case R.id.rl_userhead:
//			startActivity(new Intent(getActivity().getApplicationContext(), MyInfoActivity.class));
                break;
            // 修改密码
            case R.id.changepassword:
//                startActivity(new Intent(getActivity().getApplicationContext(), PwdChangeActivity.class));
                break;
            // 清除缓存
            case R.id.scavengingcaching:


                break;

            // 数据更新
            case R.id.dataupdate:

                break;

            // 版本更新
            case R.id.versionupdate:


                break;

            // 关于我们
            case R.id.aboutus:

                break;

            case R.id.login_out:
                // 清空sharepre中的用户名和密码
                PreferenceUtils.setString(getActivity(), "EmergencyStation_username", "");
                PreferenceUtils.setString(getActivity(), "EmergencyStation_password", "");
                Intent loginOutIntent = new Intent(getActivity(), LoginActivity.class);
                loginOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginOutIntent);
                getActivity().finish();
                break;
            // 头像点击跳转到个人信息界面
            case R.id.iv_userhead:
//			Intent intentiv_userhead = new Intent(getActivity(), MyInfoActivity.class);
//			startActivity(intentiv_userhead);
                break;
            default:
                break;
        }
    }

    // 广播接收器
    private final BroadcastReceiver imgHeadportrait = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            String action = intent.getAction();
            // 获取头像设置
            if (action.equals(ACTION_IMGHEAD_PORTRAIT)) {
                String imag_path = intent.getStringExtra("CameraFilePath");
                Bitmap bitmap = BitmapFactory.decodeFile(imag_path);
                iv_userhead.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        if (imgHeadportrait != null) {
            getActivity().unregisterReceiver(imgHeadportrait);
        }
        super.onDestroyView();
    }
}
