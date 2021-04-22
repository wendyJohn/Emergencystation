package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.MaterialAdapter;
import com.sanleng.emergencystation.bean.ArchitectureBean;
import com.sanleng.emergencystation.dialog.MaterialDetailsDialog;
import com.sanleng.emergencystation.dialog.PromptDialog;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 物资列表
 *
 * @author Qiaoshi
 */
public class MaterialActivity extends BaseActivity {

    private RelativeLayout r_back;
    private ListView materiallistview;
    private MaterialAdapter materialAdapter;
    private int pageNo = 1;// 设置pageNo的初始化值为1，即默认获取的是第一页的数据。
    private int allpage;
    private List<ArchitectureBean> allList;// 存放所有数据AlarmBean的list集合
    private List<ArchitectureBean> onelist;// 存放一页数据实体类的Bean
    // 加载提示
    private PromptDialog promptDialog;
    private boolean is_divPage;// 是否进行分页操作
    private boolean finish = true;// 是否加载完成;
    private String ids;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        this.setContentView(R.layout.materialactivity);
        initview();
        loadData(pageNo);
    }

	@Override
	protected int getLayoutRes() {
		return R.layout.materialactivity;
	}

	private void initview() {
        Intent intent = getIntent();
        ids = intent.getStringExtra("ids");
        // 创建对象
        promptDialog = new PromptDialog(this);
        // 设置自定义属性
        promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(2000);
        materialAdapter = new MaterialAdapter();
        allList = new ArrayList<ArchitectureBean>();
        r_back = (RelativeLayout) findViewById(R.id.r_back);
        r_back.setOnClickListener(new MyOnClickListener(0));
    }

    // 加载数据
    private void loadData(int page) {



    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message message) {
            final Bundle data = message.getData();
            switch (message.what) {
                // 开门
                case 456456:
                    int selIndex = data.getInt("selIndex");

                    break;


                default:
                    break;
            }
        }
    };

    /**
     * 头标点击监听
     */
    private class MyOnClickListener implements OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            switch (index) {
                case 0:
                    finish();
                    break;
            }

        }
    }

}
