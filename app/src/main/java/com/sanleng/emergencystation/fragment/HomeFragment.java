package com.sanleng.emergencystation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.mapframework.nirvana.Utils;
import com.bumptech.glide.Glide;
import com.sanleng.emergencystation.MyApplication;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.EventsActivity;
import com.sanleng.emergencystation.activity.RemoteControlActivity;
import com.sanleng.emergencystation.activity.SearchActivity;
import com.sanleng.emergencystation.bean.Banners;
import com.sanleng.emergencystation.model.BannersContract;
import com.sanleng.emergencystation.mqtt.MyMqttClient;
import com.sanleng.emergencystation.presenter.Requests;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 物资首页
 */
public class HomeFragment extends BaseFragment implements BannersContract, OnBannerListener, View.OnClickListener {
    private View view;
    private LinearLayout functiona,functionb,functionc;
    private Banner mBanner;
    private ArrayList<String> imagePath;
    private ArrayList<String> imageTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        view = getView();
        initView();
        MqttClient();//连接Mqtt协议；
        setBanner();//加载Banner;
    }

    //初始化
    public void initView() {
        mBanner = view.findViewById(R.id.banner);
        functiona = view.findViewById(R.id.functiona);
        functionb= view.findViewById(R.id.functionb);
        functionc= view.findViewById(R.id.functionc);
        functiona.setOnClickListener(this);
        functionb.setOnClickListener(this);
        functionc.setOnClickListener(this);
    }

    //设置Banner
    private void setBanner() {
        Requests.getBannerCall(HomeFragment.this, getActivity());
    }

    @Override
    public void BanerSuccess(List<Banners.DataBean.ContentBean> mList) {
        try {
            imagePath = new ArrayList<>();
            imageTitle = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                imagePath.add(mList.get(i).getCarouselImage());
                imageTitle.add("");
            }
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            mBanner.setImageLoader(new MyLoader());
            mBanner.setBannerAnimation(Transformer.Default);
            mBanner.setBannerTitles(imageTitle);
            mBanner.setDelayTime(3000);
            mBanner.isAutoPlay(true);
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setImages(imagePath)
                    .setOnBannerListener(this)
                    .start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Failed() {

    }

    @Override
    public void OnBannerClick(int position) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //远程开门
            case R.id.functiona:
                startActivity(new Intent(getActivity(), RemoteControlActivity.class));
                break;
            //物资库存
            case R.id.functionb:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            //事件记录
            case R.id.functionc:
                startActivity(new Intent(getActivity(), EventsActivity.class));
                break;

        }
    }


    //连接MQTT
    public void MqttClient() {
        MyMqttClient.sharedCenter().setConnect();//连接MQTT
        MyMqttClient.sharedCenter().setOnServerDisConnectedCallback(new MyMqttClient.OnServerDisConnectedCallback() {
            @Override
            public void callback(Throwable e) {
            }
        });

        //MQTT接收数据
        MyMqttClient.sharedCenter().setOnServerReadStringCallback(new MyMqttClient.OnServerReadStringCallback() {
            @Override
            public void callback(String Topic, MqttMessage Msg, byte[] MsgByte) {
            }
        });

        //连接上服务器
        MyMqttClient.sharedCenter().setOnServerConnectedCallback(new MyMqttClient.OnServerConnectedCallback() {
            @Override
            public void callback() {
            }
        });
    }

    /**
     * 网络加载图片
     * 使用了Glide图片加载框架
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load((String) path)
                    .into(imageView);
        }
    }
}
