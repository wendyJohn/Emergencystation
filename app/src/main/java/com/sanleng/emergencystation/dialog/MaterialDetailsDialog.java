package com.sanleng.emergencystation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 物资详情
 *
 * @author qiaoshi
 *
 */
public class MaterialDetailsDialog extends Dialog implements View.OnClickListener {

	private Context context;
	private TextView notice;
	private String ids;
	private TextView name;
	private TextView number;
	private TextView specification;
	private TextView model;

	private TextView stationName;
	private TextView effective;
	private TextView stationAddress;
	private TextView storageLocation;

	public MaterialDetailsDialog(Context context, String ids) {
		super(context);
		this.context = context;
		this.ids = ids;
	}

	public MaterialDetailsDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.setContentView(R.layout.materialdetailsdialog);
		this.setCancelable(false);// 设置点击屏幕Dialog不消失
		notice = (TextView) findViewById(R.id.notice);

		name = (TextView) findViewById(R.id.name);
		number = (TextView) findViewById(R.id.number);
		specification = (TextView) findViewById(R.id.specification);
		model = (TextView) findViewById(R.id.model);

		stationName = (TextView) findViewById(R.id.stationName);
		effective = (TextView) findViewById(R.id.effective);
		stationAddress = (TextView) findViewById(R.id.stationAddress);
		storageLocation = (TextView) findViewById(R.id.storageLocation);

		notice.setOnClickListener(this);
		MaterialDetails();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			// 知道了
			case R.id.notice:
				dismiss();
				break;

			default:
				break;
		}
	}

	// 获取物资详情
	private void MaterialDetails(){

	}

}
