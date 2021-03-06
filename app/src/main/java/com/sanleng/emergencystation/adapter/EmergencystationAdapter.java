package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.ArchitectureBean;

import java.util.List;

/**
 * 应急站数据适配器
 *
 * @author QiaoShi
 *
 */
public class EmergencystationAdapter extends BaseAdapter {

	private List<ArchitectureBean> mList;
	private Context mContext;

	/**
	 * bindData用来传递数据给适配器。
	 *
	 * @list
	 */
	public void bindData(Context mContext, List<ArchitectureBean> mList) {
		this.mList = mList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return mList.size();

	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.emergencystation_item, null);
			holder = new Holder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.name.setText(mList.get(position).getName());
		holder.address.setText(mList.get(position).getAddress());
		return convertView;
	}

	class Holder {
		TextView name;
		TextView address;
	}
}
