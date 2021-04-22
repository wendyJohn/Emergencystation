package com.sanleng.emergencystation.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.RecordsAdapter;
import com.sanleng.emergencystation.bean.Records;

import java.util.ArrayList;
import java.util.List;


public class RecordPopupWindow extends PopupWindow {
    private static final String TAG = "PhotoPopupWindow";
    private View mView; // PopupWindow 菜单布局
    private Context mContext; // 上下文参数
    private View.OnClickListener mSelectListener; // 时间选择
    private View.OnClickListener mCaptureListener; // 操作选择
    private View.OnClickListener mCancleListener; // 取消操作
    private ListView record_listview;
    private RecordsAdapter adapter;


    public RecordPopupWindow(Activity context, View.OnClickListener selectListener, View.OnClickListener captureListener, View.OnClickListener cancleListener) {
        super(context);
        this.mContext = context;
        this.mSelectListener = selectListener;
        this.mCaptureListener = captureListener;
        this.mCancleListener = cancleListener;
        init();
        initData();
    }

    /**
     * 设置布局以及点击事件
     */
    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        mView = inflater.inflate(R.layout.pops_item, null);
        LinearLayout operationselection = (LinearLayout) mView.findViewById(R.id.operationselection);
        LinearLayout timeselection = (LinearLayout) mView.findViewById(R.id.timeselection);
        LinearLayout record_cancle = (LinearLayout) mView.findViewById(R.id.record_cancle);
        ImageView record_canimages = (ImageView) mView.findViewById(R.id.record_canimages);
        record_listview = (ListView) mView.findViewById(R.id.record_listview);


        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(mContext, R.anim.down_anim);
        record_canimages.startAnimation(animationSet);

        timeselection.setOnClickListener(mSelectListener);
        operationselection.setOnClickListener(mCaptureListener);
        record_cancle.setOnClickListener(mCancleListener);

        // 导入布局
        this.setContentView(mView);
        // 设置动画效果
        this.setAnimationStyle(R.style.popwindow_anim_style);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置可触
        this.setFocusable(false);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
//        mView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = mView.findViewById(R.id.ll_pop).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });
    }

    //加载数据
    private void initData() {
        List<Records> mList = new ArrayList<>();
        Records bean1 = new Records();
        bean1.setName("测试员");
        bean1.setTiem("2021/4/6 12:22:32");
        bean1.setState("存");
        bean1.setType("灭火器");

        Records bean2 = new Records();
        bean2.setName("测试员");
        bean2.setTiem("2021/4/6 12:22:32");
        bean2.setState("取");
        bean2.setType("灭火器");

        Records bean3 = new Records();
        bean3.setName("测试员");
        bean3.setTiem("2021/4/6 12:22:32");
        bean3.setState("存");
        bean3.setType("灭火器");

        Records bean4 = new Records();
        bean4.setName("测试员");
        bean4.setTiem("2021/4/6 12:22:32");
        bean4.setState("取");
        bean4.setType("灭火器");

        Records bean5 = new Records();
        bean5.setName("测试员");
        bean5.setTiem("2021/4/6 12:22:32");
        bean5.setState("存");
        bean5.setType("灭火器");

        Records bean6 = new Records();
        bean6.setName("测试员");
        bean6.setTiem("2021/4/6 12:22:32");
        bean6.setState("取");
        bean6.setType("灭火器");

        Records bean7 = new Records();
        bean7.setName("测试员");
        bean7.setTiem("2021/4/6 12:22:32");
        bean7.setState("存");
        bean7.setType("灭火器");

        Records bean8 = new Records();
        bean8.setName("测试员");
        bean8.setTiem("2021/4/6 12:22:32");
        bean8.setState("取");
        bean8.setType("灭火器");

        Records bean9 = new Records();
        bean9.setName("测试员");
        bean9.setTiem("2021/4/6 12:22:32");
        bean9.setState("存");
        bean9.setType("灭火器");

        Records bean10 = new Records();
        bean10.setName("测试员");
        bean10.setTiem("2021/4/6 12:22:32");
        bean10.setState("取");
        bean10.setType("灭火器");

        mList.add(bean1);
        mList.add(bean2);
        mList.add(bean3);
        mList.add(bean4);
        mList.add(bean5);
        mList.add(bean6);
        mList.add(bean7);
        mList.add(bean8);
        mList.add(bean9);
        mList.add(bean10);

        adapter=new RecordsAdapter(mContext,mList);
        record_listview.setAdapter(adapter);

    }


}
