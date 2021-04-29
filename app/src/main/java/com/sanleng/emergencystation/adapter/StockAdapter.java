package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.Stocks;
import com.sanleng.emergencystation.utils.ImageDown;

import java.util.List;


/**
 * 物资库存适配器
 *
 * @author QiaoShi
 */
public class StockAdapter extends BaseAdapter {

    private Context mContext;
    private List<Stocks.UniversalStockMaterialListBean> mList;

    public StockAdapter(Context mContext, List<Stocks.UniversalStockMaterialListBean> mList) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.mList = mList;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<Stocks.UniversalStockMaterialListBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.stock_item, null);
            holder = new Holder();
            holder.stock_names = convertView.findViewById(R.id.stock_names);
            holder.stock_numbera = convertView.findViewById(R.id.stock_numbera);
            holder.stock_numberb = convertView.findViewById(R.id.stock_numberb);
            holder.stock_state = convertView.findViewById(R.id.stock_state);
            holder.stock_lins = convertView.findViewById(R.id.stock_lins);
            holder.stock_images = convertView.findViewById(R.id.stock_images);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.stock_names.setText(mList.get(position).getGoodsType());
        if (mList.get(position).getStorageType().equals("正常")) {
            holder.stock_state.setText("正常");
            holder.stock_numbera.setText(mList.get(position).getActualStorageNumber()+"");
            holder.stock_numberb.setText("/"+mList.get(position).getMaxStorageNumber()+"");
            holder.stock_numbera.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.stock_numberb.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.stock_lins.setBackground(mContext.getResources().getDrawable(R.drawable.stock_in));
        } else {
            holder.stock_state.setText("短缺");
            holder.stock_numbera.setText(mList.get(position).getActualStorageNumber()+"");
            holder.stock_numberb.setText("/"+mList.get(position).getMaxStorageNumber()+"");
            holder.stock_numbera.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.stock_numberb.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.stock_lins.setBackground(mContext.getResources().getDrawable(R.drawable.stock_on));
        }

        //接口回调的方法，完成图片的读取;
        ImageDown downImage = new ImageDown(mList.get(position).getImgUrl());
        downImage.loadImage(new ImageDown.ImageCallBack() {
            @Override
            public void getDrawable(Drawable drawable) {
                holder.stock_images.setImageDrawable(drawable);
            }
        });

        return convertView;
    }


    class Holder {
        TextView stock_names, stock_numbera,stock_numberb, stock_state;
        ImageView stock_images;
        LinearLayout stock_lins;
    }
}
