package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.sanleng.emergencystation.MyApplication;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.BottomAdapter;
import com.sanleng.emergencystation.data.Version_mag;
import com.sanleng.emergencystation.dialog.CustomDialog;
import com.sanleng.emergencystation.fragment.Taba_Fragment;
import com.sanleng.emergencystation.fragment.Tabb_Fragment;
import com.sanleng.emergencystation.fragment.Tabc_Fragment;
import com.sanleng.emergencystation.net.UpdateRequest;
import com.sanleng.emergencystation.service.UpdateService;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.utils.UpdatePresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements UpdatePresenter {
    private ViewPager mVp;
    private BottomNavigationView mBv;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private static final String BROADCAST_PERMISSION_DISC = "com.permissions.MYE_BROADCAST";
    private static final String BROADCAST_ACTION_DISC = "com.permissions.mye_broadcast";
    private Receivers receivers;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //数据填充
        setupViewPager(mVp);
        checkPermission();//7.0以上添加存储与相机的权限
        //获取版本号与下载链接
        UpdateRequest.GetUpdate(MainActivity.this, getApplicationContext(), "os_android", Version_mag.platformkey);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    //初始化数据
    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        //注册广播
        receivers = new Receivers();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION_DISC); // 只有持有相同的action的接受者才能接收此广
        registerReceiver(receivers, intentFilter, BROADCAST_PERMISSION_DISC, null);

        mBv = (BottomNavigationView) findViewById(R.id.navigation);
        mBv.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mVp = (ViewPager) findViewById(R.id.vp);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBv.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

//        禁止ViewPager滑动
//        mVp.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mVp.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mVp.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    mVp.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new Taba_Fragment());
        adapter.addFragment(new Tabb_Fragment());
        adapter.addFragment(new Tabc_Fragment());
        viewPager.setAdapter(adapter);
    }


    @Override
    public void UpdateSuccess(String version, final String path,String appDescribe) {
//        int versions = Integer.parseInt(version);
        int versions = 3;
        if (versions > getLocalVersion(MainActivity.this)) {
            // 是否更新
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            String messageitems = "更新内容如下：" + appDescribe;
            builder.setMessage(messageitems);
            builder.setTitle("检测到新的版本信息");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent i = new Intent(MainActivity.this, UpdateService.class);
                    i.putExtra("apkurl", "https://slyj.slicity.com" + path);
                    startService(i);
                    new SVProgressHUD(MainActivity.this).showWithStatus("版本正在更新...");
                }
            });
            builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    @Override
    public void UpdateFailed() {
        new SVProgressHUD(MainActivity.this).showErrorWithStatus("更新失败");
    }

    public class Receivers extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BROADCAST_ACTION_DISC)) {
                System.out.println("收到； 121212121212121212");

            }

        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receivers);
        super.onDestroy();
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
