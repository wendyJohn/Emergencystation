package com.sanleng.emergencystation.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.MaterItemsAdapter;
import com.sanleng.emergencystation.bean.Materialdetails;
import com.sanleng.emergencystation.model.MaterDetsContract;
import com.sanleng.emergencystation.presenter.Requests;
import com.sanleng.emergencystation.utils.ImageDown;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.List;

/**
 * 物资详情
 *
 * @author Qiaoshi
 */
public class MaterialDesActivity extends BaseActivity implements OnClickListener, MaterDetsContract {

    private RelativeLayout r_back;
    private ListView masters_list;
    private String ugrUscCode;
    private String ugiType;
    private MaterItemsAdapter materItemsAdapter;
    private LinearLayout image_lins;
    private ImageView mater_images;
    private TextView tv_funa, tv_funb, tv_a, tv_b, tv_c, tv_d, tv_e, tv_f, tv_g, tv_h, tv_i, tv_j, tv_k, title;
    //视频播放地址
    private String urls;//视频地址；
    private String imageurls;//封面地址；
    private String ugiName;
    NiceVideoPlayer mNiceVideoPlayer;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        initview();
        loadData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.materialdesactivity;
    }

    private void initview() {
        r_back = findViewById(R.id.r_back);
        r_back.setOnClickListener(this);
        image_lins = findViewById(R.id.image_lins);
        tv_funa = findViewById(R.id.tv_funa);
        tv_funb = findViewById(R.id.tv_funb);
        tv_a = findViewById(R.id.tv_a);
        tv_b = findViewById(R.id.tv_b);
        tv_c = findViewById(R.id.tv_c);
        tv_d = findViewById(R.id.tv_d);
        tv_e = findViewById(R.id.tv_e);
        tv_f = findViewById(R.id.tv_f);
        tv_g = findViewById(R.id.tv_g);
        tv_h = findViewById(R.id.tv_h);
        tv_i = findViewById(R.id.tv_i);
        tv_j = findViewById(R.id.tv_j);
        tv_k = findViewById(R.id.tv_k);
        title = findViewById(R.id.title);
        masters_list = findViewById(R.id.masters_list);
        mater_images = findViewById(R.id.mater_images);
        tv_funa.setOnClickListener(this);
        tv_funb.setOnClickListener(this);
        mNiceVideoPlayer = findViewById(R.id.nice_video_player);
    }

    // 加载数据
    private void loadData() {
        Intent intent = getIntent();
        ugrUscCode = intent.getStringExtra("ugrUscCode");
        ugiType = intent.getStringExtra("ugiType");
        MaterialDetails(ugrUscCode, ugiType);//加载物资详情
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.r_back:
                finish();
                break;
            case R.id.tv_funa://图片
                try {
                    tv_funa.setTextColor(context.getResources().getColor(R.color.record_in));
                    tv_funb.setTextColor(context.getResources().getColor(R.color.gray_maters));
                    tv_funa.setBackground(context.getResources().getDrawable(R.drawable.materfuna));
                    tv_funb.setBackground(context.getResources().getDrawable(R.drawable.materfunb));
                    tv_funa.setTextSize(15);
                    tv_funb.setTextSize(12);
                    image_lins.setVisibility(View.VISIBLE);
                    mNiceVideoPlayer.setVisibility(View.GONE);
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_funb://视频
                try {
                    mNiceVideoPlayer.setVisibility(View.VISIBLE);
                    image_lins.setVisibility(View.GONE);
                    tv_funa.setTextColor(context.getResources().getColor(R.color.gray_maters));
                    tv_funb.setTextColor(context.getResources().getColor(R.color.record_in));
                    tv_funb.setBackground(context.getResources().getDrawable(R.drawable.materfuna));
                    tv_funa.setBackground(context.getResources().getDrawable(R.drawable.materfunb));
                    tv_funa.setTextSize(12);
                    tv_funb.setTextSize(15);
                    if (urls == null) {
                        new SVProgressHUD(MaterialDesActivity.this).showInfoWithStatus("暂无视频");
                    } else {
                        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
                        mNiceVideoPlayer.setUp(urls, null);
                        TxVideoPlayerController controller = new TxVideoPlayerController(context);
                        controller.setTitle(ugiName);
                        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.black_b).error(R.drawable.black_b);
                        Glide.with(context)
                                .load(imageurls)
                                .apply(requestOptions)
                                .into(controller.imageView());
                        mNiceVideoPlayer.setController(controller);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    // 获取物资详情
    private void MaterialDetails(String stra, String strb) {
        Requests.GetGoodsInfos(MaterialDesActivity.this, context, stra, strb);
    }

    @Override
    public void Success(List<Materialdetails.GoodInfoBean> mList) {
        try {
            addData(mList, 0);
            materItemsAdapter = new MaterItemsAdapter(context, mList);
            masters_list.setAdapter(materItemsAdapter);
            masters_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        materItemsAdapter.changeSelected(position);//刷新
                        addData(mList, position);
                        tv_funa.setTextColor(context.getResources().getColor(R.color.record_in));
                        tv_funb.setTextColor(context.getResources().getColor(R.color.gray_maters));
                        tv_funa.setBackground(context.getResources().getDrawable(R.drawable.materfuna));
                        tv_funb.setBackground(context.getResources().getDrawable(R.drawable.materfunb));
                        tv_funa.setTextSize(15);
                        tv_funb.setTextSize(12);
                        image_lins.setVisibility(View.VISIBLE);
                        mNiceVideoPlayer.setVisibility(View.GONE);
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Failed() {

    }

    //加载数据
    private void addData(List<Materialdetails.GoodInfoBean> mList, int positions) {
        try {
            String names = mList.get(positions).getNames();
            ugiName = mList.get(positions).getUgi_name();
            String ugrRfid = mList.get(positions).getUgr_rfid();
            String ugiSpecification = mList.get(positions).getUgi_specification();
            String ugiFactory = mList.get(positions).getUgi_factory();
            String ugrBatch = mList.get(positions).getUgr_batch();
            String ugrStatus = mList.get(positions).getUgr_status() + "";//1柜外，2柜内
            String ugrUsageState = mList.get(positions).getUgr_usage_state() + "";//使用状态，1从未使用，3已被使用，4报废
            String ugrLeaveTime = mList.get(positions).getUgr_leave_time();
            String ugrDurableYears = mList.get(positions).getUgr_durable_years();
            String ugiUsage = mList.get(positions).getUgi_usage();
            String ugiDescription = mList.get(positions).getUgi_description();
            urls = mList.get(positions).getUgi_guide_url();
            imageurls = mList.get(positions).getUgi_pic1_url();

            tv_a.setText(ugiName);
            tv_b.setText(ugrRfid);
            tv_c.setText(ugiSpecification);
            tv_d.setText(ugiFactory);
            tv_e.setText(ugrBatch);
            if (ugrStatus.equals("1")) {
                tv_f.setText("柜外");
            } else {
                tv_f.setText("柜内");
            }

            if (ugrUsageState.equals("1")) {
                tv_g.setText("从未使用");
            } else if (ugrUsageState.equals("3")) {
                tv_g.setText("已被使用");
            } else if (ugrUsageState.equals("4")) {
                tv_g.setText("报废");
            }
            tv_h.setText(ugrLeaveTime);
            tv_i.setText(ugrDurableYears);
            tv_j.setText(ugiUsage);
            tv_k.setText(ugiDescription);
            title.setText(names);
            //接口回调的方法，完成图片的读取;
            ImageDown downImage = new ImageDown(mList.get(positions).getUgi_pic1_url());
            downImage.loadImage(new ImageDown.ImageCallBack() {
                @Override
                public void getDrawable(Drawable drawable) {
                    mater_images.setImageDrawable(drawable);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
