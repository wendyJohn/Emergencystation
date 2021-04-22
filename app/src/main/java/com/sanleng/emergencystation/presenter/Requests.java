package com.sanleng.emergencystation.presenter;

import android.content.Context;

import com.sanleng.emergencystation.bean.Articles;
import com.sanleng.emergencystation.bean.Banners;
import com.sanleng.emergencystation.bean.Events;
import com.sanleng.emergencystation.model.ArticlesContract;
import com.sanleng.emergencystation.model.BannersContract;
import com.sanleng.emergencystation.model.EventsContract;
import com.sanleng.emergencystation.net.Request_Interface;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Requests {

    //教育培训
    public static void getArticleCall(final ArticlesContract articlesContract, final Context context, final String pageNum, final String pageSize) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Articles> call = request_Interface.getArticleCall(pageNum, pageSize);
        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                try {
                    if (response.body().getStatus().equals("0")) {
                        articlesContract.ArSuccess(response.body().getData().getContent());
                    } else {
                        articlesContract.Failed();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    articlesContract.TimeOut();
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                articlesContract.Failed();
            }
        });
    }


    //Banner
    public static void getBannerCall(final BannersContract bannersContract, final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Banners> call = request_Interface.getBannerCall();
        call.enqueue(new Callback<Banners>() {
            @Override
            public void onResponse(Call<Banners> call, Response<Banners> response) {
                try {
                    if (response.body().getStatus().equals("0")) {
                        bannersContract.BanerSuccess(response.body().getData().getContent());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Banners> call, Throwable t) {

            }
        });
    }


    //获取事件记录
    public static void getStoreEvent(final EventsContract eventsContract, final Context context, final String chevType, final String startTime, final String endTime) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Events> call = request_Interface.getStoreEvent(chevType, startTime, endTime);
        call.enqueue(new Callback<Events>() {
            @Override
            public void onResponse(Call<Events> call, Response<Events> response) {
                try {
                    if (response.body().getState().equals("ok")) {
                        eventsContract.Success(response.body().getPage().getList());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Events> call, Throwable t) {
            }
        });
    }


}
