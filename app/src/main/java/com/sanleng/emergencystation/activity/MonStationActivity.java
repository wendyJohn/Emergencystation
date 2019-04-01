package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
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
 *应急站
 * @author 作者 : Qiaoshi
 *
 */
public class MonStationActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	private MyGridView itemGrid;
	private FunctionAdapter adapter;
	private RelativeLayout r_back;

	@SuppressLint("ResourceAsColor")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monstationactivity);
		initView();
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.monstationactivity;
	}

	// 初始化
	private void initView() {
		r_back =  findViewById(R.id.r_back);
		itemGrid = findViewById(R.id.item_grid);
		adapter = new FunctionAdapter(MonStationActivity.this, R.array.myfunction_name_items,
				R.array.myfunction_icon_items);
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
//			case 0:// 物资管理
//				Intent intent_materialmanagement = new Intent(MonStationActivity.this, MaterialManagementActivity.class);
//				startActivity(intent_materialmanagement);
//				break;
			case 0://入库
				AndPermission.with(this)
						.permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
						.onGranted(new Action() {
							@Override
							public void onAction(List<String> permissions) {
								Intent intent_Warehousing = new Intent(MonStationActivity.this, CaptureActivity.class);
								ZxingConfig config = new ZxingConfig();
								config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
								intent_Warehousing.putExtra(Constant.INTENT_ZXING_CONFIG, config);
								intent_Warehousing.putExtra("mode", "Warehousing");
								intent_Warehousing.putExtra("title", "入库");
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
								Toast.makeText(MonStationActivity.this, "没有权限无法扫描", Toast.LENGTH_LONG).show();
							}
						}).start();
				break;
			case 1:// 出库
				AndPermission.with(this)
						.permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
						.onGranted(new Action() {
							@Override
							public void onAction(List<String> permissions) {
								Intent intent_OutOfStock = new Intent(MonStationActivity.this, CaptureActivity.class);
								ZxingConfig config = new ZxingConfig();
								config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
								intent_OutOfStock.putExtra(Constant.INTENT_ZXING_CONFIG, config);
								intent_OutOfStock.putExtra("mode", "OutOfStock");
								intent_OutOfStock.putExtra("title", "出库");
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
								Toast.makeText(MonStationActivity.this, "没有权限无法扫描", Toast.LENGTH_LONG).show();
							}
						}).start();
				break;
            case 2://报损
                AndPermission.with(this)
                        .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent_Reportloss = new Intent(MonStationActivity.this, CaptureActivity.class);
                                ZxingConfig config = new ZxingConfig();
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                intent_Reportloss.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                intent_Reportloss.putExtra("mode", "Reportloss");
								intent_Reportloss.putExtra("title", "报损");
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
                                Toast.makeText(MonStationActivity.this, "没有权限无法扫描", Toast.LENGTH_LONG).show();
                            }
                        }).start();
                break;
            case 3:// 视频监控
                Intent intent_videosurveillance = new Intent(MonStationActivity.this, EmergencyStationActivity.class);
                intent_videosurveillance.putExtra("mode", "视频监控");
                startActivity(intent_videosurveillance);
                break;
			case 4:// 应急开门
				Intent intent_emergencyStation = new Intent(MonStationActivity.this, EmergencyStationActivity.class);
				intent_emergencyStation.putExtra("mode", "应急开门");
				startActivity(intent_emergencyStation);
				break;
			case 5:// 物资查询
				Intent intent_emergencystation = new Intent(MonStationActivity.this, EmergencyStationActivity.class);
				intent_emergencystation.putExtra("mode", "物资查询");
				startActivity(intent_emergencystation);
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
