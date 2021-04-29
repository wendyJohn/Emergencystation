package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.data.Version_mag;
import com.sanleng.emergencystation.dialog.CustomDialog;
import com.sanleng.emergencystation.fragment.HomeFragment;
import com.sanleng.emergencystation.fragment.MineFragment;
import com.sanleng.emergencystation.fragment.TrainFragment;
import com.sanleng.emergencystation.model.UpdateModel;
import com.sanleng.emergencystation.presenter.UpdateRequest;
import com.sanleng.emergencystation.service.UpdateService;
import com.sanleng.emergencystation.utils.NotificationUtil;

public class MainTabActivity extends BaseActivity implements UpdateModel {
    private Receivers receivers;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private Fragment[] fragments;
    private HomeFragment homefragment;
    private TrainFragment trainFragment;
    private MineFragment minefragment;
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private int index;
    private int currentTabIndex;// 当前fragment的index
    private SystemBarTintManager tintManager;
    private static final String BROADCAST_PERMISSION_DISC = "com.permissions.MYE_BROADCAST";
    private static final String BROADCAST_ACTION_DISC = "com.permissions.mye_broadcast";


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();//初始化控件
        isNotifyEnabled();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_maintab;
    }


    //初始化控件
    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        //获取版本号与下载链接
//        UpdateRequest.GetUpdate(MainTabActivity.this, getApplicationContext(), "os_android", Version_mag.platformkey);
//        initWindow();
        homefragment = new HomeFragment();
        trainFragment = new TrainFragment();
        minefragment = new MineFragment();
        fragments = new Fragment[]{homefragment, trainFragment, minefragment};
        imagebuttons = new ImageView[3];
        imagebuttons[0] = findViewById(R.id.ib_homeview);
        imagebuttons[1] = findViewById(R.id.ib_alarm_list);
        imagebuttons[2] = findViewById(R.id.ib_mine_list);

        imagebuttons[0].setSelected(true);
        textviews = new TextView[3];
        textviews[0] = findViewById(R.id.tv_homeview);
        textviews[1] = findViewById(R.id.tv_alarm_list);
        textviews[2] = findViewById(R.id.tv_mine_list);

        textviews[0].setTextColor(MainTabActivity.this.getResources().getColor(R.color.text_blue));
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homefragment)
                .add(R.id.fragment_container, trainFragment)
                .add(R.id.fragment_container, minefragment)
                .hide(minefragment).hide(trainFragment).show(homefragment).commit();

        //注册广播
        receivers = new Receivers();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION_DISC); // 只有持有相同的action的接受者才能接收此广
        registerReceiver(receivers, intentFilter, BROADCAST_PERMISSION_DISC, null);
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_homeview:
                index = 0;
                break;
            case R.id.re_alarm_list:
                index = 1;
                try {
//                    trainFragment.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.re_mine_list:
                index = 2;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(MainTabActivity.this.getResources().getColor(R.color.gray));
        textviews[index].setTextColor(MainTabActivity.this.getResources().getColor(R.color.text_blue));
        currentTabIndex = index;
    }

    private void initWindow() {//初始化，将状态栏和标题栏设为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(MainTabActivity.this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    @Override
    public void UpdateSuccess(String version, final String path, String appDescribe) {
        int versions = Integer.parseInt(version);
        if (versions > getLocalVersion(MainTabActivity.this)) {
            // 是否更新
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            String messageitems = "更新内容如下：" + appDescribe;
            builder.setMessage(messageitems);
            builder.setTitle("检测到新的版本信息");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent i = new Intent(MainTabActivity.this, UpdateService.class);
                    i.putExtra("apkurl", "https://slyj.slicity.com" + path);
                    startService(i);
                    new SVProgressHUD(MainTabActivity.this).showWithStatus("版本正在更新...");
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }

    }


    @Override
    public void UpdateFailed() {
        new SVProgressHUD(MainTabActivity.this).showErrorWithStatus("更新失败");
    }

    // 收到报警广播处理，刷新界面
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
        super.onDestroy();
        unregisterReceiver(receivers);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (checkDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /** 判断是否是快速点击 */
    private static long lastClickTime;
    public static boolean checkDoubleClick() {
        //点击时间
        long clickTime = SystemClock.uptimeMillis();
        //如果当前点击间隔小于500毫秒
        if (lastClickTime >= clickTime - 500) {
            return true;
        }
        //记录上次点击时间
        lastClickTime = clickTime;
        return false;
    }

    //判断通知是否开启
    private void isNotifyEnabled() {
        if (NotificationUtil.isNotifyEnabled(MainTabActivity.this) == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("  通知设置提示").setMessage("  通知接收未打开,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent localIntent = new Intent();
                    //直接跳转到应用通知设置的代码：
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0及以上
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上到8.0以下
                        localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        localIntent.putExtra("app_package", getPackageName());
                        localIntent.putExtra("app_uid", getApplicationInfo().uid);
                    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {//4.4
                        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        localIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        localIntent.setData(Uri.parse("package:" + getPackageName()));
                    } else {
                        //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                        }
                    }
                    startActivity(localIntent);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            }).show();
        }
    }

}
