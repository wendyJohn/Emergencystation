package com.sanleng.emergencystation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.EmergencystationAdapter;
import com.sanleng.emergencystation.bean.ArchitectureBean;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MonStationDialog extends Dialog {

	Context context;
	private TextView message;
	private ListView keylistview;
	private Handler handler;

	private EmergencystationAdapter emergencystationAdapter;
	private int pageNo = 1;// 设置pageNo的初始化值为1，即默认获取的是第一页的数据。
	private int allpage;
	private List<ArchitectureBean> allList;// 存放所有数据AlarmBean的list集合
	private List<ArchitectureBean> onelist;// 存放一页数据实体类的Bean
	private boolean is_divPage;// 是否进行分页操作
	private boolean finish = true;// 是否加载完成;

	public MonStationDialog(Context context, Handler handler) {
		super(context);
		this.context = context;
		this.handler = handler;
		loadData(pageNo);
	}

	public MonStationDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.setContentView(R.layout.monstationdialog);
		this.setCancelable(true);// 设置点击屏幕Dialog不消失
		message = (TextView) findViewById(R.id.con_tilte);
		message.setText("站点列表");
		emergencystationAdapter = new EmergencystationAdapter();
		allList = new ArrayList<ArchitectureBean>();
	}

	// 加载数据
	private void loadData(int page) {}

}
