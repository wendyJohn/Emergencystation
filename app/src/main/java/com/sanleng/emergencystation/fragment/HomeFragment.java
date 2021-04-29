package com.sanleng.emergencystation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.sanleng.emergencystation.MyApplication;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.EventsActivity;
import com.sanleng.emergencystation.activity.RecordsActivity;
import com.sanleng.emergencystation.activity.RemoteControlActivity;
import com.sanleng.emergencystation.activity.SearchActivity;
import com.sanleng.emergencystation.adapter.TraceListAdapter;
import com.sanleng.emergencystation.bean.Banners;
import com.sanleng.emergencystation.bean.Trace;
import com.sanleng.emergencystation.dialog.CabinetsPopup;
import com.sanleng.emergencystation.model.BannersContract;
import com.sanleng.emergencystation.model.StoresContract;
import com.sanleng.emergencystation.presenter.Requests;
import com.sanleng.emergencystation.utils.MessageEvent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 物资首页
 */
public class HomeFragment extends BaseFragment implements BannersContract, OnBannerListener, View.OnClickListener, StoresContract {
    private View view;
    private LinearLayout functiona, functionb, functionc;
    private Banner mBanner;
    private ArrayList<String> imagePath;
    private ArrayList<String> imageTitle;

    private ListView lvTrace;
    private List<Trace.PageBean.ListBean> traceLists = new ArrayList<>();
    private TraceListAdapter adapter;
    private CabinetsPopup cabinetsPopup;
    private TextView tv_mores;
    private String operation_type;

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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        lvTrace = view.findViewById(R.id.lvtrace);
        mBanner = view.findViewById(R.id.banner);
        functiona = view.findViewById(R.id.functiona);
        functionb = view.findViewById(R.id.functionb);
        functionc = view.findViewById(R.id.functionc);
        tv_mores = view.findViewById(R.id.tv_mores);
        functiona.setOnClickListener(this);
        functionb.setOnClickListener(this);
        functionc.setOnClickListener(this);
        tv_mores.setOnClickListener(this);
    }

    //设置Banner
    private void setBanner() {
        Requests.getBannerCall(HomeFragment.this, getActivity());
    }


    private void initData() {
        Requests.GetStoreIo(HomeFragment.this, getActivity(), "", "", "", "");
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
    public void Success(List<Trace.PageBean.ListBean> mList) {
        try {
            traceLists.clear();
            traceLists = mList;
            adapter = new TraceListAdapter(getActivity(), traceLists);
            lvTrace.setAdapter(adapter);
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

    /**
     * 接收EventBus返回数据
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backData(MessageEvent messageEvent) {
        switch (messageEvent.getTAG()) {
            case MyApplication.REQUEST_CODE_ASK_Cabinets:
                if (operation_type.equals("stock")) {//查看物资库存
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                } else {
                    //查看更多记录
                    startActivity(new Intent(getActivity(), RecordsActivity.class));
                }
                break;
            case MyApplication.REQUEST_NOCabinets:
              new SVProgressHUD(getActivity()).showInfoWithStatus("暂无副柜信息");
                break;

        }
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
                addCabinets();
                operation_type = "stock";
                break;
            //事件记录
            case R.id.functionc:
                startActivity(new Intent(getActivity(), EventsActivity.class));
                break;
            //更多使用记录
            case R.id.tv_mores:
                addCabinets();
                operation_type = "record";
                break;

        }
    }


    //加载柜体信息
    private void addCabinets() {
        cabinetsPopup = new CabinetsPopup(getActivity());
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        cabinetsPopup.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
}
