package com.sanleng.emergencystation.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.FunctionAdapter;
import com.sanleng.emergencystation.view.MyGridView;
import com.sanleng.emergencystation.zxing.activiry.CaptureActivity;
import com.sanleng.emergencystation.zxing.bean.ZxingConfig;
import com.sanleng.emergencystation.zxing.common.Constant;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * 物质管理操作功能模块界面
 *
 * @author 作者 : Qiaoshi
 *
 */
public class MaterialManagementActivity extends Activity implements OnClickListener, OnItemClickListener {

	private MyGridView itemGrid;
	private FunctionAdapter adapter;
	private RelativeLayout r_back;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.materialmanagementactivity);
		initView();
	}

	// 初始化
	private void initView() {
		r_back = findViewById(R.id.r_back);
		itemGrid = findViewById(R.id.item_grid);
		adapter = new FunctionAdapter(MaterialManagementActivity.this, R.array.material_management_item,
				R.array.material_management_icon_item);
		itemGrid.setAdapter(adapter);
		itemGrid.setOnItemClickListener(this);
		r_back.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
		switch (position) {
			case 0:// 入库
				AndPermission.with(this)
						.permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
						.onGranted(new Action() {
							@Override
							public void onAction(List<String> permissions) {
								Intent intent_Warehousing = new Intent(MaterialManagementActivity.this, CaptureActivity.class);
								ZxingConfig config = new ZxingConfig();
								config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
								intent_Warehousing.putExtra(Constant.INTENT_ZXING_CONFIG, config);
								intent_Warehousing.putExtra("mode", "Warehousing");
								startActivity(intent_Warehousing);
							}
						})
						.onDenied(new Action() {
							@Override
							public void onAction(List<String> permissions) {
								Uri packageURI = Uri.parse("package:" + getPackageName());
								Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
								Toast.makeText(MaterialManagementActivity.this, "没有权限无法扫描", Toast.LENGTH_LONG).show();
							}
						}).start();
				break;
			case 1:// 出库
				AndPermission.with(this)
						.permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
						.onGranted(new Action() {
							@Override
							public void onAction(List<String> permissions) {
								Intent intent_OutOfStock = new Intent(MaterialManagementActivity.this, CaptureActivity.class);
								ZxingConfig config = new ZxingConfig();
								config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
								intent_OutOfStock.putExtra(Constant.INTENT_ZXING_CONFIG, config);
								intent_OutOfStock.putExtra("mode", "OutOfStock");
								startActivity(intent_OutOfStock);
							}
						})
						.onDenied(new Action() {
							@Override
							public void onAction(List<String> permissions) {
								Uri packageURI = Uri.parse("package:" + getPackageName());
								Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
								Toast.makeText(MaterialManagementActivity.this, "没有权限无法扫描", Toast.LENGTH_LONG).show();
							}
						}).start();
				break;
			case 2:// 物资查询
				Intent intent_emergencystation = new Intent(MaterialManagementActivity.this, EmergencyStationActivity.class);
				intent_emergencystation.putExtra("mode", "物资查询");
				startActivity(intent_emergencystation);
				break;
			case 3:// 报损
				AndPermission.with(this)
						.permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
						.onGranted(new Action() {
							@Override
							public void onAction(List<String> permissions) {
								Intent intent_Reportloss = new Intent(MaterialManagementActivity.this, CaptureActivity.class);
								ZxingConfig config = new ZxingConfig();
								config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
								intent_Reportloss.putExtra(Constant.INTENT_ZXING_CONFIG, config);
								intent_Reportloss.putExtra("mode", "Reportloss");
								startActivity(intent_Reportloss);
							}
						})
						.onDenied(new Action() {
							@Override
							public void onAction(List<String> permissions) {
								Uri packageURI = Uri.parse("package:" + getPackageName());
								Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
								Toast.makeText(MaterialManagementActivity.this, "没有权限无法扫描", Toast.LENGTH_LONG).show();
							}
						}).start();
				break;
			case 4:// 盘点

				break;
			case 5:// 站点详情
				Intent intent_emergencystations = new Intent(MaterialManagementActivity.this, EmergencyStationActivity.class);
				intent_emergencystations.putExtra("mode", "站点详情");
				startActivity(intent_emergencystations);
				break;

			case 6:// 维护

				break;
			default:
				break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.r_back:
				finish();
				break;
			default:
				break;
		}
	}
}
