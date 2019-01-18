package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.ArchitectureBean;
import com.sanleng.emergencystation.bean.StationBean;

import java.util.List;

/**
 * 物资数据适配器
 *
 * @author QiaoShi
 *
 */
public class MaterialAdapter extends BaseAdapter {

	private List<ArchitectureBean> mList;
	private Context mContext;
	private Handler handler;


	public MaterialAdapter(Context mContext, List<ArchitectureBean> mList,Handler handler) {
		super();
		this.mContext = mContext;
		this.mList = mList;
		this.handler = handler;
	}

//	/**
//	 * bindData用来传递数据给适配器。
//	 *
//	 * @list
//	 */
//	public void bindData(Context mContext, List<ArchitectureBean> mList) {
//		this.mList = mList;
//		this.mContext = mContext;
//	}

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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.material_item, null);
			holder = new Holder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.icon =convertView.findViewById(R.id.icon);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.name.setText(mList.get(position).getName());
		holder.address.setText(mList.get(position).getAddress());

		String image_type=mList.get(position).getId();
		if(image_type.equals("a")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.a));
		}
		if(image_type.equals("b")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.b));
		}
		if(image_type.equals("c")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.c));
		}
		if(image_type.equals("d")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.d));
		}
		if(image_type.equals("e")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.e));
		}
		if(image_type.equals("f")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.f));
		}
		if(image_type.equals("g")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.g));
		}
		if(image_type.equals("h")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.h));
		}
		if(image_type.equals("i")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.i));
		}
		if(image_type.equals("j")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.j));
		}if(image_type.equals("k")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.k));
		}
		if(image_type.equals("l")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.l));
		}
		if(image_type.equals("m")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.m));
		}
		if(image_type.equals("n")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.n));
		}if(image_type.equals("o")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.o));
		}
		if(image_type.equals("p")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.p));
		}

		return convertView;
	}

	class Holder {
		TextView name;
		TextView address;
		ImageView icon;
	}
}
