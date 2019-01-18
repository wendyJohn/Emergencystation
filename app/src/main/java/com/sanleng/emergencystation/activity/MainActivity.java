package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.BottomAdapter;
import com.sanleng.emergencystation.fragment.Taba_Fragment;
import com.sanleng.emergencystation.fragment.Tabb_Fragment;
import com.sanleng.emergencystation.fragment.Tabc_Fragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager mVp;
    private BottomNavigationView mBv;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private static final String BROADCAST_PERMISSION_DISC = "com.permissions.MYE_BROADCAST";
    private static final String BROADCAST_ACTION_DISC = "com.permissions.mye_broadcast";
    private Receivers receivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //数据填充
        setupViewPager(mVp);
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
}
