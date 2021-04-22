package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class PwdChangeActivity extends BaseActivity implements OnClickListener {

    private EditText originalpassword;// 原密码
    private EditText newpassword;// 新密码
    private EditText confirmnewpassword;// 重复新密码
    private Button btn_passwordmodification;// 确定
    private RelativeLayout task_ac_back;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordmodificationactivity);
        initView();
        initListener();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.passwordmodificationactivity;
    }

    private void initView() {
        originalpassword = (EditText) findViewById(R.id.originalpassword);
        newpassword = (EditText) findViewById(R.id.newpassword);
        confirmnewpassword = (EditText) findViewById(R.id.confirmnewpassword);
        btn_passwordmodification = (Button) findViewById(R.id.btn_passwordmodification);
        task_ac_back = (RelativeLayout) findViewById(R.id.task_ac_back);

    }

    private void initListener() {
        btn_passwordmodification.setOnClickListener(this);
        task_ac_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_passwordmodification:
                doChangePwd();
                break;
            case R.id.task_ac_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 发送修改密码请求
     */
    private void doChangePwd() {
        String pwd = originalpassword.getText().toString().trim();
        String newpwd = newpassword.getText().toString().trim();
        String renewpwd = confirmnewpassword.getText().toString().trim();
        if (isEquale(pwd, newpwd, renewpwd)) {
            return;
        }

    }

    /**
     * 检测密码是否一致，是否为空
     *
     * @param pwd
     * @param newpwd
     * @param renewpwd
     * @return true 不合法
     */
    private boolean isEquale(String pwd, String newpwd, String renewpwd) {
        boolean flag = false;
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(newpwd) || TextUtils.isEmpty(renewpwd)) {
            Toast.makeText(PwdChangeActivity.this, "信息输入不完整", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!newpwd.equals(renewpwd)) {
            Toast.makeText(PwdChangeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return true;
        }
        return flag;
    }
}
