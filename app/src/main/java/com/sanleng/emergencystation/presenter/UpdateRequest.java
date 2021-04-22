package com.sanleng.emergencystation.presenter;

import android.content.Context;

import com.sanleng.emergencystation.bean.Version;
import com.sanleng.emergencystation.model.UpdateModel;
import com.sanleng.emergencystation.net.Request_Interface;
import com.sanleng.emergencystation.net.URLs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateRequest {
    //获取版本号与下载链接
    public static void GetUpdate(final UpdateModel updateModel, final Context context, final String osType, final String platformkey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Version> call = request_Interface.getVersionCall(osType, platformkey);
        call.enqueue(new Callback<Version>() {
            @Override
            public void onResponse(Call<Version> call, Response<Version> response) {
                try{
                updateModel.UpdateSuccess(response.body().getData().getAppVersion(), response.body().getData().getDownloadUrl(), response.body().getData().getAppDescribe());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Version> call, Throwable t) {
                updateModel.UpdateFailed();
            }
        });


    }
}