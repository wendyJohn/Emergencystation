package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.net.URLs;

import java.util.ArrayList;
import java.util.List;

/**
 * 应急开锁
 *
 * @author qiaoshi
 *
 */
public class EmergencyUnlockingActivity extends BaseActivity implements OnClickListener {
	private String mac;
	private Button btn_one;
	private Button btn_two;
	private Button btn_three;
	private Button btn_four;
	private Button btn_five;
	private Button btn_six;
	private Button btn_seven;

	private Button btn_onekey;
	private RelativeLayout r_back;
	private List<String> list = new ArrayList<>();
	private String str;
	private int i = 0;// 次数

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		this.setContentView(R.layout.open_door);
		initview();
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.open_door;
	}

	private void initview() {
		Intent intent = getIntent();
		mac = intent.getStringExtra("mac");
		btn_one = (Button) findViewById(R.id.btn_one);
		btn_two = (Button) findViewById(R.id.btn_two);
		btn_three = (Button) findViewById(R.id.btn_three);
		btn_four = (Button) findViewById(R.id.btn_four);
		btn_five = (Button) findViewById(R.id.btn_five);
		btn_six = (Button) findViewById(R.id.btn_six);
		btn_seven = (Button) findViewById(R.id.btn_seven);
		btn_onekey = (Button) findViewById(R.id.btn_onekey);
		btn_one.setOnClickListener(this);
		btn_two.setOnClickListener(this);
		btn_three.setOnClickListener(this);
		btn_four.setOnClickListener(this);
		btn_five.setOnClickListener(this);
		btn_six.setOnClickListener(this);
		btn_seven.setOnClickListener(this);
		btn_onekey.setOnClickListener(this);
		r_back = (RelativeLayout) findViewById(R.id.r_back);
		r_back.setOnClickListener(this);
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
		list.add("G");
	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//			// 一号开锁
//			case R.id.btn_one:
////				Unlock("A", mac);
//				break;
//			// 二号开锁
//			case R.id.btn_two:
//				Unlock("B", mac);
//				break;
//			// 三号开锁
//			case R.id.btn_three:
//				Unlock("C", mac);
//				break;
//			// 四号开锁
//			case R.id.btn_four:
//				Unlock("D", mac);
//				break;
//			// 五号开锁
//			case R.id.btn_five:
//				Unlock("E", mac);
//				break;
//			// 六号开锁
//			case R.id.btn_six:
//				Unlock("F", mac);
//				break;
//			// 七号开锁
//			case R.id.btn_seven:
//				Unlock("G", mac);
//				break;
//			// 一键开启
//			case R.id.btn_onekey:
//				i = 0;
//				handler.postDelayed(runnable, 2000);// 每两秒执行一次runnable.
//				break;
//			// 返回
//			case R.id.r_back:
//				finish();
//				break;
//			default:
//				break;
//		}
//	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (i == 7) {
				handler.removeCallbacks(runnable);
			} else {
				str = list.get(i).toString();
				i++;
//				Unlock(str, mac);
				// 要做的事情
				handler.postDelayed(this, 2000);
			}

		}
	};

	// 开锁方式
//	private void Unlock(final String position, final String mac) {
//		RequestUtils.ClientPost(URLs.ORDER_BASE_URL + "/" + position + "/" + mac, null, new NetCallBack() {
//			@Override
//			public void onStart() {
//				super.onStart();
//			}
//
//			@Override
//			public void onMySuccess(String result) {
//				if (result == null || result.length() == 0) {
//					return;
//				}
//			}
//			@Override
//			public void onMyFailure(Throwable arg0) {
//
//			}
//		});
//	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(runnable);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

	}
}
