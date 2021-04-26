package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.Trace;

import java.util.ArrayList;
import java.util.List;

public class TraceListAdapter extends BaseAdapter {
    private Context context;
    private List<Trace> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;

    public TraceListAdapter(Context context, List<Trace> traceList) {
        this.context = context;
        this.traceList = traceList;
    }

    @Override
    public int getCount() {
        return traceList.size();
    }

    @Override
    public Trace getItem(int position) {
        return traceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Trace trace = getItem(position);
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

        if (trace.getTv_states().equals("取出")) {
            holder.tv_states.setText(trace.getTv_states());
            holder.tv_states.setTextColor(context.getResources().getColor(R.color.record_on));
            holder.tv_states.setBackground(context.getResources().getDrawable(R.drawable.trace_on));
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else if (trace.getTv_states().equals("存放")) {
            holder.tv_states.setText(trace.getTv_states());
            holder.tv_states.setTextColor(context.getResources().getColor(R.color.record_in));
            holder.tv_states.setBackground(context.getResources().getDrawable(R.drawable.trace_in));
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        holder.tvAcceptTime.setText(trace.getAcceptTime());
        holder.tv_names.setText(trace.getTv_names());
        holder.tv_types.setText(trace.getTv_types());
        holder.tv_cabinet.setText(trace.getTv_cabinet());

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
