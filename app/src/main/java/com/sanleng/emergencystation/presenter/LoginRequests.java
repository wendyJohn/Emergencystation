package com.sanleng.emergencystation.presenter;

import android.content.Context;

import com.sanleng.emergencystation.bean.User;
import com.sanleng.emergencystation.bean.Users;
import com.sanleng.emergencystation.model.LoginModel;
import com.sanleng.emergencystation.net.Request_Interface;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRequests {
    //登录
    public static void GetLogin(final LoginModel loginModel, final Context context, final String username, final String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<User> call = request_Interface.getloginCalls(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    String msg = response.body().getStatus();
                    if (msg.equals("0")) {
                        String jwt = response.body().getJwt();
                        PreferenceUtils.setString(context, "JWT", jwt);
                        // 存入数据库中（登录名称和密码）
                        PreferenceUtils.setString(context, "EmergencyStation_username", username);
                        //存入数据库中（登录名称和密码用来判断是否需要重新登录问题）
                        PreferenceUtils.setString(context, "EmergencyStation_username", username);
                        loginModel.LoginSuccess("登录成功");
                        LoginRequests.GetUser(context,username);
                    } else {
                        loginModel.LoginFailed();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loginModel.LoginFailed();
            }
        });

    }


    //获取用户信息
    public static void GetUser(Context context, final String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Users> call = request_Interface.getUserCalls(username);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                String unitcode = response.body().getData().getUnitcode();
                String agentName = response.body().getData().getName();
                String mobile = response.body().getData().getMobile();

                String ids = response.body().getData().getId();
                String duty = response.body().getData().getDuty();
//               //绑定唯一标识
                JPushInterface.setAlias(context, 1, ids);
                // 存入数据库中（登录名称和密码）
                PreferenceUtils.setString(context, "EmergencyStation_username", username);
                //存入数据库中（登录名称和密码用来判断是否需要重新登录问题）
                PreferenceUtils.setString(context, "EmergencyStation_usernames", username);
                // 单位ID
                PreferenceUtils.setString(context, "unitcode", unitcode);
                // 人员名称
                PreferenceUtils.setString(context, "agentName", agentName);
                PreferenceUtils.setString(context, "ids", ids);
                PreferenceUtils.setString(context, "duty", duty);
                PreferenceUtils.setString(context, "mobile", mobile);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
            }
        });
    }

}