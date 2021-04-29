package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.Trace;
import com.sanleng.emergencystation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TraceListAdapter extends BaseAdapter {
    private Context context;
    private List<Trace.PageBean.ListBean> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;

    public TraceListAdapter(Context context, List<Trace.PageBean.ListBean> traceList) {
        this.context = context;
        this.traceList = traceList;
    }

    @Override
    public int getCount() {
        return traceList.size();
    }

    @Override
    public Trace.PageBean.ListBean getItem(int position) {
        return traceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Trace.PageBean.ListBean trace = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trace, parent, false);
            holder.tvAcceptTime = (TextView) convertView.findViewById(R.id.tvAcceptTime);
            holder.tv_names = (TextView) convertView.findViewById(R.id.tv_names);
            holder.tv_states = (TextView) convertView.findViewById(R.id.tv_states);
            holder.tv_types = (TextView) convertView.findViewById(R.id.tv_types);
            holder.tv_cabinet = (TextView) convertView.findViewById(R.id.tv_cabinet);
            holder.tvTopLine = (TextView) convertView.findViewById(R.id.tvTopLine);
            holder.tvDot = (TextView) convertView.findViewById(R.id.tvDot);
            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
        }

        if (traceList.get(position).getChio_type() == 1) {
            holder.tv_states.setText("取");
            holder.tv_states.setTextColor(context.getResources().getColor(R.color.record_on));
            holder.tv_states.setBackground(context.getResources().getDrawable(R.drawable.trace_on));
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else if (traceList.get(position).getChio_type() == 2) {
            holder.tv_states.setText("存");
            holder.tv_states.setTextColor(context.getResources().getColor(R.color.record_in));
            holder.tv_states.setBackground(context.getResources().getDrawable(R.drawable.trace_in));
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        holder.tvAcceptTime.setText(Utils.getFormatTime(traceList.get(position).getChio_operation_time()));
        holder.tv_names.setText(traceList.get(position).getName());
        holder.tv_types.setText(traceList.get(position).getNames());
        holder.tv_cabinet.setText(traceList.get(position).getUs_name());

        return convertView;
    }

    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    static class ViewHolder {
        public TextView tvAcceptTime, tv_names, tv_states, tv_types, tv_cabinet;
        public TextView tvTopLine, tvDot;
    }
}
