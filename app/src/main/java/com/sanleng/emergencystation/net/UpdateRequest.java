package com.sanleng.emergencystation.net;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.utils.UpdatePresenter;

import org.json.JSONException;
import org.json.JSONObject;


public class UpdateRequest {

    //获取版本号与下载链接
    public static void GetUpdate(final UpdatePresenter updatePresenter, final Context context, final String  osType, final String platformkey) {
        RequestParams params = new RequestParams();
        params.put("osType", osType);
        params.put("platformkey", platformkey);
        RequestUtils.ClientPost(URLs.VerSion_URL, params, new NetCallBack() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null || result.length() == 0) {
                    return;
                }
                System.out.println("更新数据请求成功" + result);
                try {
                    JSONObject object=new JSONObject(result);
                    String data=object.getString("data");
                    JSONObject objects=new JSONObject(data);
                    String appVersion=objects.getString("appVersion");
                    String downloadUrl=objects.getString("downloadUrl");
                    updatePresenter.UpdateSuccess(appVersion,downloadUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(Throwable arg0) {
                updatePresenter.UpdateFailed();
            }
        });





    }
}