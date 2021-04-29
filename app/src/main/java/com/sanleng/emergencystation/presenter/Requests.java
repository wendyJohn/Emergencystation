package com.sanleng.emergencystation.presenter;

import android.content.Context;

import com.sanleng.emergencystation.MyApplication;
import com.sanleng.emergencystation.adapter.TraceListAdapter;
import com.sanleng.emergencystation.bean.Articles;
import com.sanleng.emergencystation.bean.Banners;
import com.sanleng.emergencystation.bean.Cabinet;
import com.sanleng.emergencystation.bean.Cabinets;
import com.sanleng.emergencystation.bean.Events;
import com.sanleng.emergencystation.bean.Materialdetails;
import com.sanleng.emergencystation.bean.Records;
import com.sanleng.emergencystation.bean.Stocks;
import com.sanleng.emergencystation.bean.Trace;
import com.sanleng.emergencystation.model.ArticlesContract;
import com.sanleng.emergencystation.model.BannersContract;
import com.sanleng.emergencystation.model.CabinetContract;
import com.sanleng.emergencystation.model.EventsContract;
import com.sanleng.emergencystation.model.MaterDetsContract;
import com.sanleng.emergencystation.model.StocksContract;
import com.sanleng.emergencystation.model.StoresContract;
import com.sanleng.emergencystation.net.Request_Interface;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.MessageEvent;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;

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
        Call<Events> call = request_Interface.getStoreEvent(chevType, startTime, endTime, "0", "50");
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

    //获取柜体信息
    public static void getVersalStores(final CabinetContract cabinetContract, final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Cabinet> call = request_Interface.getVersalStore(PreferenceUtils.getString(context, "unitcode"));
        call.enqueue(new Callback<Cabinet>() {
            @Override
            public void onResponse(Call<Cabinet> call, Response<Cabinet> response) {
                try {
                    if (response.body().getState().equals("ok")) {
                        cabinetContract.Success(response.body().getMapList());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Cabinet> call, Throwable t) {
            }
        });
    }

    //获取副柜柜体信息
    public static void getOnes(final Context context, String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Cabinets> call = request_Interface.getOnes(id);
        call.enqueue(new Callback<Cabinets>() {
            @Override
            public void onResponse(Call<Cabinets> call, Response<Cabinets> response) {
                try {
                    if (response.body().getState().equals("ok")) {
                        for (int i = 0; i < response.body().getCabinetRecordList().size(); i++) {
                            PreferenceUtils.setString(context, response.body().getCabinetRecordList().get(i).getUscName(), response.body().getCabinetRecordList().get(i).getUscId());
                        }
                        if (response.body().getCabinetRecordList().size() > 0) {
                            PreferenceUtils.setString(context, "number", response.body().getCabinetRecordList().size() + "");
                            MessageEvent messageEvent = new MessageEvent(MyApplication.REQUEST_CODE_ASK_Cabinets);
                            EventBus.getDefault().post(messageEvent);
                        }else{
                            MessageEvent messageEvent = new MessageEvent(MyApplication.REQUEST_NOCabinets);
                            EventBus.getDefault().post(messageEvent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Cabinets> call, Throwable t) {
            }
        });
    }

    //获取使用记录
    public static void GetStoreIo(final StoresContract storesContract, final Context context, final String chioChemicalStoreCode, final String chioType, final String startTime, final String endTime) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Trace> call = request_Interface.getStoreIo(chioChemicalStoreCode, chioType, startTime, endTime, "", "");
        call.enqueue(new Callback<Trace>() {
            @Override
            public void onResponse(Call<Trace> call, Response<Trace> response) {
                try {
                    storesContract.Success(response.body().getPage().getList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Trace> call, Throwable t) {
            }
        });
    }


    //获取库存信息
    public static void getStocksCall(final StocksContract stocksContract, final Context context, final String usId, final String uscId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Stocks> call = request_Interface.getStockMaterial(usId, uscId);
        call.enqueue(new Callback<Stocks>() {
            @Override
            public void onResponse(Call<Stocks> call, Response<Stocks> response) {
                try {
                    if (response.body().getState().equals("ok")) {
                        stocksContract.Success(response.body().getUniversalStockMaterialList());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Stocks> call, Throwable t) {

            }
        });
    }

    //获取物资详情
    public static void GetGoodsInfos(final MaterDetsContract materDetsContract, final Context context, final String ugrUscCode, final String ugiType) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.HOST) // 设置 网络请求 Url
                .client(Request_Interface.genericClient(context))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        Request_Interface request_Interface = retrofit.create(Request_Interface.class);
        //对 发送请求 进行封装
        Call<Materialdetails> call = request_Interface.getGoodsInfo(ugrUscCode, ugiType);
        call.enqueue(new Callback<Materialdetails>() {
            @Override
            public void onResponse(Call<Materialdetails> call, Response<Materialdetails> response) {
                try {
                    if (response.body().getState().equals("ok")) {
                        materDetsContract.Success(response.body().getGoodInfo());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Materialdetails> call, Throwable t) {
            }
        });
    }

}
