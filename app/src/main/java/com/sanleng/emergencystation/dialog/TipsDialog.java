package com.sanleng.emergencystation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sanleng.emergencystation.R;

/**
 * 提示
 * @author qiaoshi
 */
public class TipsDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView notice;
    private TextView cancle;
    private TextView msg;
    private Handler mHandler;
    private String str;

    public TipsDialog(Context context, String str, Handler mHandler) {
        super(context);
        this.context = context;
        this.str = str;
        this.mHandler = mHandler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        this.setContentView(R.layout.tipsdialog);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
        notice =  findViewById(R.id.notice);
        cancle =  findViewById(R.id.cancle);
        msg =  findViewById(R.id.msg);
        msg.setText(str);
        notice.setOnClickListener(this);
        cancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            //继续
            case R.id.notice:
                Message mymsg = new Message();
                mymsg.what = 22260;
                mHandler.sendMessage(mymsg);
                dismiss();
                break;
            // 取消
            case R.id.cancle:
                Message msg = new Message();
                msg.what = 22261;
                mHandler.sendMessage(msg);
                dismiss();
                break;
            default:
                break;
        }
    }

}
