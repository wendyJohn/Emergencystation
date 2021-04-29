package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文章列表数据
 *
 * @author Qiaoshi
 */
public class ArticleActivity extends BaseActivity {
    private ListView listView;
    private ArticleAdapter articleAdapter;
    private ProgressDialog dialog;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
    private int pageNo = 1;// 设置pageNo的初始化值为1，即默认获取的是第一页的数据。
    private int allpage;
    private boolean is_divPage;// 是否进行分页操作
    private boolean finish = true;// 是否加载完成;
    private RelativeLayout r_back;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.listview);
        r_back = (RelativeLayout) findViewById(R.id.r_back);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_article;
    }
}
