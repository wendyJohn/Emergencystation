package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
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
import com.sanleng.emergencystation.fragment.Tab1Fragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVp;
    private BottomNavigationView mBv;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
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

        //禁止ViewPager滑动
        mVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
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
        adapter.addFragment(new Tab1Fragment());
        viewPager.setAdapter(adapter);
    }

}
