package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.StationBean;

import java.util.List;

/**
 * 底部菜单数据适配器
 *
 * @author QiaoShi
 */
public class BottomMenuAdapter extends BaseAdapter {
    private List<StationBean> mList;
    private Context mContext;
    private Handler handler;
    private LayoutInflater inflater;
    private String name;
    private String address;
    private double distance;
    private String id;
    private String mac;

    private final int TYPE_COUNT = 3;
    private final int TYPE_ONE = 0;
    private final int TYPE_TWO = 1;
    private final int TYPE_THREE = 2;
    private int currentType;


    public BottomMenuAdapter(Context mContext, List<StationBean> mList, String name, String address, double distance, String id,String mac,Handler handler) {
        super();

        this.mContext = mContext;
        this.mList = mList;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.id = id;
        this.mac = mac;
        this.handler = handler;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        currentType = getItemViewType(position);
        if (currentType == TYPE_ONE) { //加载第一种布局
            ViewHolderOne viewHolderOne;
            //首先判断convertview==null
            if (convertView == null) {
                viewHolderOne = new ViewHolderOne();
                convertView = inflater.inflate(R.layout.list_station, null);
                viewHolderOne.dooropening = (TextView) convertView.findViewById(R.id.dooropening);
                viewHolderOne.warehousings = (TextView) convertView.findViewById(R.id.warehousings);
                viewHolderOne.outofstock = (TextView) convertView.findViewById(R.id.outofstock);
                viewHolderOne.reportloss = (TextView) convertView.findViewById(R.id.reportloss);
                convertView.setTag(viewHolderOne);
            } else {
                viewHolderOne = (ViewHolderOne) convertView.getTag();
            }
            //设置参数
            // 一键开门
            viewHolderOne.dooropening.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("mac", mac);
                    msg.setData(data);
                    msg.what = 5859590;
                    handler.sendMessage(msg);
                }
            });
            //还物资
            viewHolderOne.warehousings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.what = 5859591;
                    handler.sendMessage(msg);
                }
            });
            //取物质
            viewHolderOne.outofstock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.what = 5859592;
                    handler.sendMessage(msg);
                }
            });
            //报损
            viewHolderOne.reportloss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.what = 5859593;
                    handler.sendMessage(msg);
                }
            });
            //设置参数
        }
        //加载第二种布局
        else if (currentType == TYPE_TWO) {
            ViewHolderTwo viewholdertwo;
            //首先判断convertview==null
            if (convertView == null) {
                viewholdertwo = new ViewHolderTwo();
                convertView = inflater.inflate(R.layout.material_item, null);
                viewholdertwo.name = (TextView) convertView.findViewById(R.id.name);
                viewholdertwo.address = (TextView) convertView.findViewById(R.id.address);
                viewholdertwo.icon = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(viewholdertwo);
            } else {
                viewholdertwo = (ViewHolderTwo) convertView.getTag();
            }
            //设置参数
            viewholdertwo.name.setText(mList.get(position).getName());
            viewholdertwo.address.setText(mList.get(position).getNumber());
            String image_type=mList.get(position).getImage_type();
            if(image_type.equals("1")){
                viewholdertwo.icon.setBackground(mContext.getResources().getDrawable(R.drawable.earthquake));
            }
            if(image_type.equals("2")){
                viewholdertwo.icon.setBackground(mContext.getResources().getDrawable(R.drawable.medicalcare));
            }
        }
        return convertView;
    }

    //为每种布局定义自己的ViewHolder
    public class ViewHolderOne {
        TextView dooropening;
        TextView warehousings;
        TextView outofstock;
        TextView reportloss;
    }

    public class ViewHolderTwo {
        TextView name;
        TextView address;
        ImageView icon;
    }


    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        StationBean bean = mList.get(position);
        int type = bean.getType();
        switch (type) {
            case TYPE_ONE:
                return TYPE_ONE;
            case TYPE_TWO:
                return TYPE_TWO;
            case TYPE_THREE:
                return TYPE_THREE;
            default:
                return -1;
        }
    }

}
