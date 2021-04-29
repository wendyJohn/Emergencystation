package com.sanleng.emergencystation.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.sanleng.emergencystation.MyApplication;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.RemoteControlActivity;
import com.sanleng.emergencystation.adapter.CabinetPopAdapter;
import com.sanleng.emergencystation.adapter.CabinetsAdapter;
import com.sanleng.emergencystation.bean.Cabinet;
import com.sanleng.emergencystation.model.CabinetContract;
import com.sanleng.emergencystation.presenter.Requests;
import com.sanleng.emergencystation.utils.MessageEvent;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class CabinetsPopup extends PopupWindow implements CabinetContract {
    private static final String TAG = "CabinetsPopup";
    private View mView; // PopupWindow 菜单布局
    private Context mContext; // 上下文参数
    private ListView canetlistview;
    private CabinetPopAdapter adapter;
    private String type;

    public CabinetsPopup(Activity context) {
        super(context);
        this.mContext = context;
        init();
        initData();
    }

    /**
     * 设置布局以及点击事件
     */
    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        mView = inflater.inflate(R.layout.pops_item, null);
        canetlistview = mView.findViewById(R.id.canetlistview);
        // 导入布局
        this.setContentView(mView);
        // 设置动画效果
        this.setAnimationStyle(R.style.popwindow_anim_style);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mView.findViewById(R.id.ll_pops).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    //加载数据
    private void initData() {
        Requests.getVersalStores(CabinetsPopup.this, mContext);
    }


    @Override
    public void Success(List<Cabinet.MapListBean> mList) {
        try {
            adapter = new CabinetPopAdapter(mContext, mList);
            canetlistview.setAdapter(adapter);
            canetlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String us_id = mList.get(position).getUsId();
                    PreferenceUtils.setString(mContext, "us_id", us_id);
                    Requests.getOnes(mContext, us_id);
                    dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Failed() {

    }
}
