package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.dialog.PromptDialog;
import com.sanleng.emergencystation.model.LoginModel;
import com.sanleng.emergencystation.presenter.LoginRequests;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.utils.RSAUtils;
import com.sanleng.emergencystation.utils.StringUtils;

/**
 * 登陆界面
 * 2020/10/10
 *
 * @author qiaoshi
 */
public class LoginActivity extends BaseActivity implements OnClickListener, LoginModel {
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
    private SystemBarTintManager tintManager;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
//        initWindow();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.login_activity;
    }


    private void initView() {
        // 创建对象
        promptDialog = new PromptDialog(this);
        // 设置自定义属性
        promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(2000);
        login_btn = findViewById(R.id.login_btn);
        login_number = findViewById(R.id.login_number);
        login_password = findViewById(R.id.login_password);
        login_questions = findViewById(R.id.login_questions);
        scrollviewRootLayout = findViewById(R.id.scrollviewRootLayout);
        login_btn.setOnClickListener(this);
        login_password.setOnClickListener(this);
        whether_contact = findViewById(R.id.whether_contact);
        whether_contact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    PreferenceUtils.setInt(LoginActivity.this, "state", 1);
                } else {
                    PreferenceUtils.setInt(LoginActivity.this, "state", 0);
                }
            }
        });
    }

    private void initWindow() {//初始化，将状态栏和标题栏设为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(LoginActivity.this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.login_btn));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        login_number.setText(PreferenceUtils.getString(this, "EmergencyStation_username"));
        int state = PreferenceUtils.getInt(LoginActivity.this, "state");
        if (state == 1) {
            // 记住上次登录的信息
            lastAccount = PreferenceUtils.getString(this, "EmergencyStation_usernames");
            if (!StringUtils.isEmpty(lastAccount)) {
                login_number.setText(lastAccount);
                login_password.setText(lastPwd);
                Intent intent_pwdchange = new Intent(LoginActivity.this, MainTabActivity.class);
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
                String psaa= RSAUtils.encryptByPublic(password);
                if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
                    promptDialog.showLoading("正在登录...");
                    LoginRequests.GetLogin(LoginActivity.this, getApplicationContext(), userName, psaa);
                } else {
                    Toast.makeText(LoginActivity.this, "输入的用户和密码不能为空", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void LoginSuccess(String msg) {
        if (msg.equals("登录成功")) {
            promptDialog.showSuccess("登录成功");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    // 等待1000毫秒后销毁此页面，并提示登陆成功
                    Intent intent_pwdchange = new Intent(LoginActivity.this, MainTabActivity.class);
                    startActivity(intent_pwdchange);
                    finish();
                }
            }, 1000);
        } else {
            promptDialog.showError(msg);
        }
    }

    @Override
    public void LoginFailed() {
        promptDialog.showError("登录失败");
    }
}
