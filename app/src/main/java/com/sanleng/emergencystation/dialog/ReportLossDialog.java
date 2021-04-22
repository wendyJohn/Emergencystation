package com.sanleng.emergencystation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 报损提示
 *
 * @author qiaoshi
 */
public class ReportLossDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView notice;
    private TextView cancle;
    private Handler mHandler;
    private String ids;

    private TextView name;
    private TextView number;
    private TextView specification;
    private TextView model;
    private EditText info_editText;

    private String stationName = null;
    private String stationId = null;
    private String stationAddress = null;
    private String storageLocation = null;

    public ReportLossDialog(Context context, String ids, Handler mHandler) {
        super(context);
        this.context = context;
        this.ids = ids;
        this.mHandler = mHandler;
    }

    public ReportLossDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        this.setContentView(R.layout.reportlossdialog);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
        notice = (TextView) findViewById(R.id.notice);
        cancle = (TextView) findViewById(R.id.cancle);

        name = (TextView) findViewById(R.id.name);
        number = (TextView) findViewById(R.id.number);
        specification = (TextView) findViewById(R.id.specification);
        model = (TextView) findViewById(R.id.model);
        info_editText = (EditText) findViewById(R.id.info_editText);

        notice.setOnClickListener(this);
        cancle.setOnClickListener(this);
        MaterialDetails();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            // 确认报损
            case R.id.notice:
                if ("".equals(info_editText.getText().toString().trim())
                        || info_editText.getText().toString().trim() == null) {
                    Toast.makeText(context, "报损说明不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Message mymsg = new Message();
                    Bundle data = new Bundle();
                    data.putString("ids", ids);
                    data.putString("stationAddress", stationAddress);
                    data.putString("stationId", stationId);
                    data.putString("stationName", stationName);
                    data.putString("storageLocation",storageLocation);
                    data.putString("reason", info_editText.getText().toString().trim());
                    mymsg.setData(data);
                    mymsg.what = 35268;
                    mHandler.sendMessage(mymsg);
                    dismiss();
                }
                break;
            // 取消报损
            case R.id.cancle:
                Message msg = new Message();
                msg.what = 35269;
                mHandler.sendMessage(msg);
                dismiss();
                break;
            default:
                break;
        }
    }

    // 获取物资详情
    private void MaterialDetails() {

    }
}
