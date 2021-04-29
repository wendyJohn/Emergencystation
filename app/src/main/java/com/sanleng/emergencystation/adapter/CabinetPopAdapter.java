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
import com.sanleng.emergencystation.bean.Cabinet;
import com.sanleng.emergencystation.utils.ImageDown;
import com.sanleng.emergencystation.utils.ImageDown.ImageCallBack;

import java.util.List;

/**
 * 柜体数据适配器
 *
 * @author QiaoShi
 */
public class CabinetPopAdapter extends BaseAdapter {
    private Context context;
    private List<Cabinet.MapListBean> list;
    int mSelect = 0;   //选中项


    public CabinetPopAdapter(Context context, List<Cabinet.MapListBean> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.cabinetspop_item, parent, false);
            holder.cabinet_name = convertView.findViewById(R.id.cabinet_name);
            holder.lin_cabinet = convertView.findViewById(R.id.lin_cabinet);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cabinet_name.setText(list.get(position).getUsName());

        return convertView;
    }

    class ViewHolder {
        TextView cabinet_name;
        LinearLayout lin_cabinet;
    }

    public void changeSelected(int positon) { //刷新方法
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }
}
