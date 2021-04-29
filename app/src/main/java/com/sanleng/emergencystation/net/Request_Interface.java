package com.sanleng.emergencystation.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.sanleng.emergencystation.bean.Articles;
import com.sanleng.emergencystation.bean.Banners;
import com.sanleng.emergencystation.bean.Cabinet;
import com.sanleng.emergencystation.bean.Cabinets;
import com.sanleng.emergencystation.bean.Events;
import com.sanleng.emergencystation.bean.Materialdetails;
import com.sanleng.emergencystation.bean.Records;
import com.sanleng.emergencystation.bean.Stocks;
import com.sanleng.emergencystation.bean.Trace;
import com.sanleng.emergencystation.bean.User;
import com.sanleng.emergencystation.bean.Users;
import com.sanleng.emergencystation.bean.Version;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Request_Interface {
    //登录
    @POST("/platform-basic/api/user/login")
    Call<User> getloginCalls(@Query("username") String username, @Query("passwd") String passwd);

    //获取用户信息
    @POST("/platform-basic/api/userapply/getUserByName")
    Call<Users> getUserCalls(@Query("name") String name);

    //获取版本号与下载链接
    @POST("/kspf/app/version/getApk?")
    Call<Version> getVersionCall(@Query("osType") String osType, @Query("platformkey") String platformkey);

    //教育培训
    @GET("/sl-common-plugin/api/commonArticle/getList")
    Call<Articles> getArticleCall(@Query("pageNum") String pageNum, @Query("pageSize") String pageSize);

    //获取banner图片
    @GET("/sl-common-plugin/api/commonBanner/getList")
    Call<Banners> getBannerCall();


    //获取事件记录
    @GET("/sl-universal-store-sys/api/universalStoreEvent/list")
    Call<Events> getStoreEvent(@Query("chevType") String chevType, @Query("startTime") String startTime, @Query("endTime") String endTime, @Query("page") String page, @Query("limit") String limit);

    //获取使用记录
    @GET("/sl-universal-store-sys/api/chemicalStoreIo/getList")
    Call<Trace> getStoreIo(@Query("chioChemicalStoreCode") String chioChemicalStoreCode
            , @Query("chioType") String chioType
            , @Query("startTime") String startTime
            , @Query("endTime") String endTime
            , @Query("page") String page
            , @Query("limit") String limit);

    //获取柜体信息
    @GET("/sl-universal-store-sys/api/universalStoreApp/getList")
    Call<Cabinet> getVersalStore(@Query("unitId") String unitId);

    //获取副柜体信息
    @GET("/sl-universal-store-sys/api/universal-store/getOne")
    Call<Cabinets> getOnes(@Query("id") String id);


    //获取库存信息
    @GET("/sl-universal-store-sys/api/universalStoreApp/stockMaterial")
    Call<Stocks> getStockMaterial(@Query("usId") String usId, @Query("uscId") String uscId);


    //获取物资详情
    @GET("/sl-universal-store-sys/api/universalStoreApp/getGoodsInfo")
    Call<Materialdetails> getGoodsInfo(@Query("ugrUscCode") String ugrUscCode
            , @Query("ugiType") String ugiType);


    //统一添加文件头
    @SuppressLint("NewApi")
    static OkHttpClient genericClient(final Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.e("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()//okhttp设置部分，此处还可再设置网络参数
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Authorization", PreferenceUtils.getString(context, "JWT"))
                                .addHeader("unitId", PreferenceUtils.getString(context, "unitcode"))
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return client;
    }
}
