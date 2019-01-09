package com.sanleng.emergencystation.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.net.NetCallBack;
import com.sanleng.emergencystation.net.RequestUtils;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.view.MarqueeViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab1Fragment extends Fragment {
    private View view;
    private MarqueeViews marqueeviews;
    private List<String> info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.taba_fragment, null);
        initView();
        addNews(1);
        return view;
    }

    private void initView(){
        marqueeviews = (MarqueeViews) view.findViewById(R.id.marqueeviews);
//                Intent intent=new Intent(getActivity(),EmergencyRescueActivity.class);
//                startActivity(intent);

    }


    //获取信息
    private void addNews(int page) {
        info = new ArrayList<>();
        info.add("南京工程学院附近应急柜物资缺少");
        info.add("南京工程学院附近危化品柜急需处理");
        marqueeviews.startWithList(info);
//        RequestParams params = new RequestParams();
//        params.put("event_no", "142");
//        params.put("pageNum", page + "");
//        params.put("pageSize", "10");
//        params.put("unit_code", PreferenceUtils.getString(getActivity(), "unitcode"));
//        params.put("username", PreferenceUtils.getString(getActivity(), "MobileFig_username"));
//        params.put("platformkey", "app_firecontrol_owner");
//
//        RequestUtils.ClientPost(URLs.Police_URL, params, new NetCallBack() {
//            @Override
//            public void onStart() {
//                super.onStart();
//            }
//
//            @Override
//            public void onMySuccess(String result) {
//                if (result == null || result.length() == 0) {
//                    return;
//                }
//                System.out.println("报警数据请求成功" + result);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    String msg = jsonObject.getString("msg");
//                    if (msg.equals("获取成功")) {
//                        String data = jsonObject.getString("data");
//                        JSONObject objects = new JSONObject(data);
//                        String listsize = objects.getString("total");
//                        String list = objects.getString("list");
//                        JSONArray array = new JSONArray(list);
//                        JSONObject object;
//                        for (int i = 0; i < array.length(); i++) {
//                            object = (JSONObject) array.get(i);
//                            String unit_name = object.getString("unit_name");
//                            String build_name = object.getString("build_name");
//                            String position = object.getString("position");
//                            String str = unit_name + build_name + position;
//                            info.add(str);
//                        }
//                        // 在代码里设置自己的动画
//                        marqueeviews.startWithList(info);
//                    }
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onMyFailure(Throwable arg0) {
//
//            }
//        });
    }
}
