package com.sanleng.emergencystation.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.bean.ArchitectureBean;
import com.sanleng.emergencystation.bean.StationBean;
import com.sanleng.emergencystation.dialog.InstructionsDialog;

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

//	public MaterialAdapter(Context mContext, List<ArchitectureBean> mList,Handler handler) {
//		super();
//		this.mContext = mContext;
//		this.mList = mList;
//		this.handler = handler;
//	}

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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.material_item, null);
			holder = new Holder();
			holder.name = convertView.findViewById(R.id.name);
			holder.address =convertView.findViewById(R.id.address);
			holder.icon =convertView.findViewById(R.id.icon);
			holder.instructions_yout =convertView.findViewById(R.id.instructions_yout);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.name.setText(mList.get(position).getName());
		holder.address.setText("位置："+ mList.get(position).getAddress()+" 号箱");

		String image_type=mList.get(position).getType();
		if(image_type.equals("ZJHXQ")||image_type.equals("HXQ")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.a));
		}
		 if(image_type.equals("XFTK")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.b));
		}
		 if(image_type.equals("XFF")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.c));
		}
		if(image_type.equals("ST")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.d));
		}
		if(image_type.equals("AQS")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.e));
		}
		 if(image_type.equals("YD")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.f));
		}
		 if(image_type.equals("JX")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.g));
		}
		 if(image_type.equals("SD")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.h));
		}
		 if(image_type.equals("XFQT")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.i));
		}
		 if(image_type.equals("SDJT")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.j));
		}
		 if(image_type.equals("ZDDJ")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.k));
		}
		 if(image_type.equals("MHQ")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.l));
		}
		 if(image_type.equals("TSYJX")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.m));
		}
		 if(image_type.equals("XFT")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.n));
		}
		 if(image_type.equals("KYLB")){
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.o));
		}
		 if(image_type.equals("LY")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.p));
		}
		if(image_type.equals("JJX")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.jjx));
		}
		if(image_type.equals("YYYQD")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.yyyqd));
		}
		if(image_type.equals("FDMJ")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.fdmj));
		}
		if(image_type.equals("MHT")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.mht));
		}
		if(image_type.equals("JTZ")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.jtz));
		}
		if(image_type.equals("FBSD")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.fbsd));
		}
		if(image_type.equals("XFJBS")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xfjbs));
		}
		if(image_type.equals("XFC")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xfc));
		}
		if(image_type.equals("XFF")) {
			holder.icon.setBackground(mContext.getResources().getDrawable(R.drawable.xff));
		}
		holder.instructions_yout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String image_type=mList.get(position).getId();
				InstructionsDialog instructionsDialog = new InstructionsDialog(mContext, image_type);
				instructionsDialog.show();
			}
		});

		return convertView;
	}

	class Holder {
		TextView name;
		TextView address;
		ImageView icon;
		LinearLayout instructions_yout;
	}
}
