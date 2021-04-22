package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.Records;

import java.util.List;


/**
 * 物资记录适配器
 *
 * @author QiaoShi
 */
public class RecordsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Records> mList;

    public RecordsAdapter(Context mContext, List<Records> mList) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.records_item, null);
            holder = new Holder();
            holder.record_times = convertView.findViewById(R.id.record_times);
            holder.record_users = convertView.findViewById(R.id.record_users);
            holder.record_states = convertView.findViewById(R.id.record_states);
            holder.record_types = convertView.findViewById(R.id.record_types);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.record_times.setText(mList.get(position).getTiem());
        holder.record_users.setText(mList.get(position).getName());
        if (mList.get(position).getState().equals("存")) {
            holder.record_states.setText(mList.get(position).getState());
            holder.record_states.setTextColor(mContext.getResources().getColor(R.color.record_in));
            holder.record_states.setBackground(mContext.getResources().getDrawable(R.drawable.records_in));
        } else {
            holder.record_states.setText(mList.get(position).getState());
            holder.record_states.setTextColor(mContext.getResources().getColor(R.color.record_on));
            holder.record_states.setBackground(mContext.getResources().getDrawable(R.drawable.records_on));
        }
        holder.record_types.setText(mList.get(position).getType());
        return convertView;
    }


    class Holder {
        TextView record_times, record_users, record_states, record_types;
    }
}
