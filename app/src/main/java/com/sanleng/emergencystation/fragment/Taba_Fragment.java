package com.sanleng.emergencystation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.ArticleActivity;
import com.sanleng.emergencystation.activity.EmergencyRescueActivity;
import com.sanleng.emergencystation.activity.VideoPlayerActivity;
import com.sanleng.emergencystation.utils.RotateCard;
import com.sanleng.emergencystation.view.MarqueeViews;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class Taba_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    private MarqueeViews marqueeviews;
    private List<String> info;
    private RotateCard card;
    private ArrayList<View> views;
    private ImageView img;

    private LinearLayout administration;//应急站管理
    private LinearLayout rescue;//应急救援
    private LinearLayout dangerous;//危化品柜
    private LinearLayout smallprogram;//应急小程序
    private LinearLayout article;//文章
    private LinearLayout video;//视频


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.taba_fragment, null);
        initView();
        listener();
        statistics();
        return view;
    }

    //初始化
    private void initView() {
        marqueeviews = view.findViewById(R.id.marqueeviews);
        card = view.findViewById(R.id.card);
        administration = view.findViewById(R.id.administration);//应急站管理
        rescue = view.findViewById(R.id.rescue);//应急救援
        dangerous = view.findViewById(R.id.dangerous);//危化品柜
        smallprogram = view.findViewById(R.id.smallprogram);//应急小程序
        article = view.findViewById(R.id.article);//文章
        video = view.findViewById(R.id.video);//视频

    }

    private void listener() {
        administration.setOnClickListener(this);
        rescue.setOnClickListener(this);
        dangerous.setOnClickListener(this);
        smallprogram.setOnClickListener(this);
        article.setOnClickListener(this);
        video.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        addNews(1);
        super.onResume();
    }

    //绘制特效菜单
    private void statistics() {
        views = new ArrayList<View>();
        img = new ImageView(getActivity());
        img.setImageResource(R.drawable.statistics_a);
        views.add(img);

        img = new ImageView(getActivity());
        img.setImageResource(R.drawable.statistics_b);
        views.add(img);

        img = new ImageView(getActivity());
        img.setImageResource(R.drawable.statistics_c);
        views.add(img);

        img = new ImageView(getActivity());
        img.setImageResource(R.drawable.statistics_d);
        views.add(img);

        card.commitViews(views, 200);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //应急站管理
            case R.id.administration:

                break;
            //应急救援
            case R.id.rescue:
                Intent intent = new Intent(getActivity(), EmergencyRescueActivity.class);
                startActivity(intent);
                break;
            //危化品柜
            case R.id.dangerous:

                break;
            //应急小程序
            case R.id.smallprogram:

                break;
            //文章
            case R.id.article:
                Intent intent_article = new Intent(getActivity(), ArticleActivity.class);
                startActivity(intent_article);
                break;
            //视频
            case R.id.video:
                Intent intent_videoplayer = new Intent(getActivity(), VideoPlayerActivity.class);
                startActivity(intent_videoplayer);
                break;
        }
    }
}
