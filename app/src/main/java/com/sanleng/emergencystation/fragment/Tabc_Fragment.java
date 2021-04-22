package com.sanleng.emergencystation.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.LoginActivity;
import com.sanleng.emergencystation.activity.PwdChangeActivity;
import com.sanleng.emergencystation.data.Version_mag;
import com.sanleng.emergencystation.dialog.CustomDialog;
import com.sanleng.emergencystation.presenter.UpdateRequest;
import com.sanleng.emergencystation.service.UpdateService;
import com.sanleng.emergencystation.utils.DataCleanManager;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.utils.UpdatePresenter;

import java.io.File;

/**
 * 首页
 */
public class Tabc_Fragment extends BaseFragment implements View.OnClickListener, UpdatePresenter {
    private View view;
    private TextView login_out;
    public static final String ACTION_IMGHEAD_PORTRAIT = "image_head";

    private RelativeLayout changepassword;
    private RelativeLayout scavengingcaching;
    private RelativeLayout versionupdate;

    private TextView tv_user_headname;
    private ImageView iv_userhead;
    private File cameraFile;
    private TextView item_search_addb;

    private DataCleanManager dm;

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
        versionupdate = (RelativeLayout) view.findViewById(R.id.versionupdate);

        tv_user_headname = (TextView) view.findViewById(R.id.tv_user_headname);
        item_search_addb = (TextView) view.findViewById(R.id.item_search_addb);

        try {
            //缓存大小
            item_search_addb.setText(dm.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String agentName = PreferenceUtils.getString(getActivity(), "agentName");
        tv_user_headname.setText(agentName);

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
        versionupdate.setOnClickListener(this);
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
                startActivity(new Intent(getActivity().getApplicationContext(), PwdChangeActivity.class));
                break;
            // 清除缓存
            case R.id.scavengingcaching:
                //清理缓存
                dm.clearAllCache(getActivity());
                new SVProgressHUD(getActivity()).showSuccessWithStatus("清理成功");
                //缓存大小
                item_search_addb.setText("0.0");
                break;

            // 版本更新
            case R.id.versionupdate:
//                UpdateRequest.GetUpdate(Tabc_Fragment.this, getActivity(), "os_android", Version_mag.platformkey);
                break;

            case R.id.login_out:
                // 清空sharepre中的用户名和密码
                PreferenceUtils.setString(getActivity(), "EmergencyStation_usernames", "");
                PreferenceUtils.setString(getActivity(), "EmergencyStation_passwords", "");
                Intent loginOutIntent = new Intent(getActivity(), LoginActivity.class);
                loginOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginOutIntent);
                getActivity().finish();
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


    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext().getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    @Override
    public void UpdateSuccess(String version, final String path, String appDescribe) {
//        int versions=Integer.parseInt(version);
        int versions = 1;
        if (versions > getLocalVersion(getActivity())) {
            // 是否更新
            CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
            String messageitems = "更新内容如下：" + appDescribe;
            builder.setMessage(messageitems);
            builder.setTitle("检测到新的版本信息");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent i = new Intent(getActivity(), UpdateService.class);
                    i.putExtra("apkurl", "https://slyj.slicity.com" + path);
                    getActivity().startService(i);
                    new SVProgressHUD(getActivity()).showWithStatus("版本正在更新...");
                }
            });
            builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else {
            new SVProgressHUD(getActivity()).showInfoWithStatus("当前版本：" + getLocalVersionName(getActivity()) + "\n已是最新版本");
        }
    }

    @Override
    public void UpdateFailed() {
        new SVProgressHUD(getActivity()).showErrorWithStatus("更新失败");
    }
}
