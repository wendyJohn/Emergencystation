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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.FaceRegistrationActivity;
import com.sanleng.emergencystation.activity.LoginActivity;
import com.sanleng.emergencystation.activity.PwdChangeActivity;
import com.sanleng.emergencystation.data.Version_mag;
import com.sanleng.emergencystation.dialog.CustomDialog;
import com.sanleng.emergencystation.presenter.UpdateRequest;
import com.sanleng.emergencystation.service.UpdateService;
import com.sanleng.emergencystation.utils.DataCleanManager;
import com.sanleng.emergencystation.utils.ImageDown;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.utils.UpdatePresenter;
import com.sanleng.emergencystation.utils.Utils;

import java.io.File;

/**
 * @author qiaoshi
 */
public class MineFragment extends BaseFragment implements OnClickListener, UpdatePresenter {
    private View view;
    private RelativeLayout changepassword, scavengingcaching, faceregistration, versionupdate;
    private TextView tv_user_headname, tv_mobile, item_search_addb, login_out;
    private ImageView iv_userhead;
    private DataCleanManager dm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment, null);
        findView();
        initListener();
        return view;
    }

    private void findView() {
        tv_mobile = view.findViewById(R.id.tv_mobile);
        login_out = view.findViewById(R.id.login_out);
        changepassword = view.findViewById(R.id.changepassword);
        scavengingcaching = view.findViewById(R.id.scavengingcaching);
        faceregistration = view.findViewById(R.id.faceregistration);
        versionupdate = view.findViewById(R.id.versionupdate);
        tv_user_headname = view.findViewById(R.id.tv_user_headname);
        item_search_addb = view.findViewById(R.id.item_search_addb);
        tv_user_headname.setText(PreferenceUtils.getString(getActivity(), "agentName"));
        tv_mobile.setText(Utils.hidePhoneNum(PreferenceUtils.getString(getActivity(), "mobile")));
        // 头像
        iv_userhead = view.findViewById(R.id.iv_userhead);
        // 头像设置
        try {
            if (PreferenceUtils.getString(getActivity(), "usericon").equals("no_portrait")) {
                return;
            } else {
                ImageDown downImage = new ImageDown("http://" + PreferenceUtils.getString(getActivity(), "usericon"));
                downImage.loadImage(new ImageDown.ImageCallBack() {
                    @Override
                    public void getDrawable(Drawable drawable) {
                        iv_userhead.setImageDrawable(drawable);
                    }
                });
            }
            //缓存大小
            item_search_addb.setText(dm.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void initListener() {
        login_out.setOnClickListener(this);
        changepassword.setOnClickListener(this);
        scavengingcaching.setOnClickListener(this);
        faceregistration.setOnClickListener(this);
        versionupdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 个人信息
            case R.id.rl_userhead:
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
            // 人脸注册
            case R.id.faceregistration:
                Intent faceRegistrationIntent = new Intent(getActivity(), FaceRegistrationActivity.class);
                startActivity(faceRegistrationIntent);
                break;
            // 版本更新
            case R.id.versionupdate:
//                UpdateRequest.GetUpdate(MineFragment.this, getActivity(), "os_android", Version_mag.platformkey);
                break;
            case R.id.login_out:
                // 清空sharepre中的用户名和密码
                PreferenceUtils.setString(getActivity(), "EmergencyStation_usernames", "");
                Intent loginOutIntent = new Intent(getActivity(), LoginActivity.class);
                loginOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginOutIntent);
                getActivity().finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
    }

    @Override
    public void UpdateSuccess(String version, final String path, String appDescribe) {
        int versions = Integer.parseInt(version);
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
                    i.putExtra("apkurl", path);
                    getActivity().startService(i);
                    new SVProgressHUD(getActivity()).showWithStatus("版本正在更新...");
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
}
