package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.dialog.PromptDialog;
import com.sanleng.emergencystation.net.NetCallBack;
import com.sanleng.emergencystation.net.RequestUtils;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * 登陆界面
 *
 * @author qiaoshi
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener{
    private EditText login_number;
    private EditText login_password;
    private Button login_btn;
    private RelativeLayout scrollviewRootLayout;
    private TextView login_questions;
    private String userName;
    private String password;
    private String lastAccount;
    private String lastPwd;
    private CheckBox whether_contact;
    private PromptDialog promptDialog;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.login_activity);
        StatusBarUtil.setColor(LoginActivity.this,R.color.translucency);
        initView();
    }

    private void initView() {
        // 创建对象
        promptDialog = new PromptDialog(this);
        // 设置自定义属性
        promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(2000);

        login_btn = (Button) findViewById(R.id.login_btn);
        login_number = (EditText) findViewById(R.id.login_number);
        login_password = (EditText) findViewById(R.id.login_password);
        login_questions = (TextView) findViewById(R.id.login_questions);
        login_btn.setOnClickListener(this);
        login_password.setOnClickListener(this);

        whether_contact = (CheckBox) findViewById(R.id.whether_contact);
        whether_contact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    PreferenceUtils.setInt(LoginActivity.this, "EmergencyStation_state", 1);
                } else {
                    PreferenceUtils.setInt(LoginActivity.this, "EmergencyStation_state", 0);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int state = PreferenceUtils.getInt(LoginActivity.this, "EmergencyStation_state");
        if (state == 1) {
            // 记住上次登录的信息
            lastAccount = PreferenceUtils.getString(this, "EmergencyStation_username");
            lastPwd = PreferenceUtils.getString(this, "EmergencyStation_password");
            if (!StringUtils.isEmpty(lastAccount) && !StringUtils.isEmpty(lastPwd)) {
                login_number.setText(lastAccount);
                login_password.setText(lastPwd);
                Intent intent_pwdchange = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent_pwdchange);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                userName = login_number.getText().toString().trim();
                password = login_password.getText().toString().trim();
                RequestParams params = new RequestParams();
                params.put("username", userName);
                params.put("password", password);
                params.put("platformkey", "app_firecontrol_owner");
                RequestUtils.ClientPost(URLs.BULOGIN_URL, params, new NetCallBack() {
                    @Override
                    public void onStart() {
                        promptDialog.showLoading("正在登录...");
                        super.onStart();
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null || result.length() == 0) {
                            return;
                        }

                        System.out.println("数据请求成功" + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String msg = jsonObject.getString("msg");

                            if (msg.equals("登录成功")) {
                                promptDialog.showSuccess("登录成功");
                                String data = jsonObject.getString("data");
                                JSONObject object = new JSONObject(data);
                                String unitcode = object.getString("unitcode");
                                String agentName = object.getString("name");
                                String ids = object.getString("ids");

                                //绑定唯一标识
                                JPushInterface.setAlias(LoginActivity.this, 1, unitcode);

                                // 存入数据库中（登录名称和密码）
                                PreferenceUtils.setString(LoginActivity.this, "EmergencyStation_username", userName);
                                PreferenceUtils.setString(LoginActivity.this, "EmergencyStation_password", password);
                                // 单位ID
                                PreferenceUtils.setString(LoginActivity.this, "unitcode", unitcode);
                                // 人员名称
                                PreferenceUtils.setString(LoginActivity.this, "agentName", agentName);
                                PreferenceUtils.setString(LoginActivity.this, "ids", ids);

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        // 等待2000毫秒后销毁此页面，并提示登陆成功
                                        Intent intent_pwdchange = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent_pwdchange);
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                promptDialog.showError(msg);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMyFailure(Throwable arg0) {
                        promptDialog.showError("登录失败");
                    }
                });

                break;
            default:
                break;
        }
    }
}
