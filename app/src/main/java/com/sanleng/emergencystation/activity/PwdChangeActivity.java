package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.navisdk.ui.routeguide.mapmode.subview.P;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bigkoo.svprogresshud.SVProgressHUDAnimateUtil;
import com.jaeger.library.StatusBarUtil;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.net.NetCallBack;
import com.sanleng.emergencystation.net.RequestUtils;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class PwdChangeActivity extends AppCompatActivity implements OnClickListener {

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
        StatusBarUtil.setColor(PwdChangeActivity.this,R.color.translucency);
        initView();
        initListener();
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
        RequestParams params = new RequestParams();
        params.put("username", PreferenceUtils.getString(this, "EmergencyStation_username"));
        params.put("oldpassword", pwd);
        params.put("newpassword", newpwd);
        params.put("platformkey", "app_firecontrol_owner");

        RequestUtils.ClientPost(URLs.PasswordModification, params, new NetCallBack() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null || result.length() == 0) {
                    return;
                }
                System.out.println("数据请求成功" + result);
                try {
                    JSONObject jsonobject = new JSONObject(result);
                    String msg = jsonobject.getString("msg");
                    if (msg.equals("密码修改成功,请重新登录")) {
                        new SVProgressHUD(PwdChangeActivity.this).showSuccessWithStatus("密码修改成功");
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                // 清空sharepre中的用户名和密码
                                PreferenceUtils.setString(PwdChangeActivity.this, "EmergencyStation_username", "");
                                PreferenceUtils.setString(PwdChangeActivity.this, "EmergencyStation_password", "");
                                Intent loginOutIntent = new Intent(PwdChangeActivity.this, LoginActivity.class);
                                loginOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(loginOutIntent);
                                finish();
                            }
                        }, 1000);
                    }
                    if (msg.equals("旧密码不正确")) {
                        new SVProgressHUD(PwdChangeActivity.this).showErrorWithStatus("旧密码不正确");
                    }
                    if (msg.equals("用户不存在")) {
                        new SVProgressHUD(PwdChangeActivity.this).showErrorWithStatus("用户不存在");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(Throwable arg0) {
                new SVProgressHUD(PwdChangeActivity.this).showErrorWithStatus("服务器请求异常");
            }
        });


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
