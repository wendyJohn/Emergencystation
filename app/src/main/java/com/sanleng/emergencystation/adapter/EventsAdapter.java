package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.Events;
import com.sanleng.emergencystation.bean.MaterialBean;
import com.sanleng.emergencystation.utils.Utils;

import java.text.ParseException;
import java.util.List;


/**
 * 事件记录适配器
 * @author QiaoShi
 */
public class EventsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Events.PageBean.ListBean> mList;

    public EventsAdapter(Context mContext, List<Events.PageBean.ListBean> mList) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.mList = mList;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.events_item, null);
            holder = new Holder();
            holder.tv_times = convertView.findViewById(R.id.tv_times);
            holder.tv_names = convertView.findViewById(R.id.tv_names);
            holder.tv_type = convertView.findViewById(R.id.tv_type);
            holder.tv_persons = convertView.findViewById(R.id.tv_persons);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.tv_names.setText(mList.get(position).getChev_ch_name()+"\n"+"("+mList.get(position).getChev_chc_name()+")");
        holder.tv_persons.setText("负责人:"+mList.get(position).getChev_user());
        try {
            holder.tv_times.setText(Utils.getFormatTime(mList.get(position).getChev_time()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(mList.get(position).getChev_type()==0){
            holder.tv_type.setText("超时关门");
        }
        if(mList.get(position).getChev_type()==1){
            holder.tv_type.setText("物资短缺");
        }
        if(mList.get(position).getChev_type()==2){
            holder.tv_type.setText("物资过期");
        }
        if(mList.get(position).getChev_type()==3){
            holder.tv_type.setText("温度过高");
        }
        if(mList.get(position).getChev_type()==4){
            holder.tv_type.setText("湿度过高");
        }
        return convertView;
    }


    class Holder {
        TextView tv_times, tv_names,tv_type, tv_persons;
    }
}
