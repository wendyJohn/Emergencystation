package com.sanleng.emergencystation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.EventsActivity;
import com.sanleng.emergencystation.activity.RemoteControlActivity;
import com.sanleng.emergencystation.activity.SearchActivity;
import com.sanleng.emergencystation.adapter.TraceListAdapter;
import com.sanleng.emergencystation.bean.Banners;
import com.sanleng.emergencystation.bean.Trace;
import com.sanleng.emergencystation.model.BannersContract;
import com.sanleng.emergencystation.presenter.Requests;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 物资首页
 */
public class HomeFragment extends BaseFragment implements BannersContract, OnBannerListener, View.OnClickListener {
    private View view;
    private LinearLayout functiona, functionb, functionc;
    private Banner mBanner;
    private ArrayList<String> imagePath;
    private ArrayList<String> imageTitle;

    private ListView lvTrace;
    private List<Trace> traceList = new ArrayList<>();
    private TraceListAdapter adapter;


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
        setBanner();//加载Banner;
        initData();//加载数据;
    }

    //初始化
    public void initView() {
        lvTrace = view.findViewById(R.id.lvtrace);
        mBanner = view.findViewById(R.id.banner);
        functiona = view.findViewById(R.id.functiona);
        functionb = view.findViewById(R.id.functionb);
        functionc = view.findViewById(R.id.functionc);
        functiona.setOnClickListener(this);
        functionb.setOnClickListener(this);
        functionc.setOnClickListener(this);
    }

    //设置Banner
    private void setBanner() {
        Requests.getBannerCall(HomeFragment.this, getActivity());
    }


    private void initData() {
        // 模拟一些假的数据
        traceList.add(new Trace("2021-04-22 17:48:00", "测试员", "取出", "灭火器", "应急柜"));
        traceList.add(new Trace("2021-04-22 14:13:00", "测试员", "存放", "钥匙", "钥匙柜"));
        traceList.add(new Trace("2021-04-22 13:01:04", "测试员", "取出", "灭火器", "应急柜"));
        traceList.add(new Trace("2021-04-22 12:19:47", "测试员", "存放", "灭火器", "应急柜"));
        traceList.add(new Trace("2021-04-22 11:12:44", "测试员", "取出", "钥匙", "钥匙柜"));
        traceList.add(new Trace("2021-04-22 03:12:12", "测试员", "存放", "灭火器", "应急柜"));
        traceList.add(new Trace("2021-04-22 21:06:46", "测试员", "取出", "灭火器", "应急柜"));
        traceList.add(new Trace("2021-04-22 18:59:41", "测试员", "存放", "灭火器", "应急柜"));
        traceList.add(new Trace("2021-04-22 18:35:32", "测试员", "取出", "灭火器", "应急柜"));
        adapter = new TraceListAdapter(getActivity(), traceList);
        lvTrace.setAdapter(adapter);
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
            mBanner.setDelayTime(5000);
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
