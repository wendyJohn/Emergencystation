package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.jaeger.library.StatusBarUtil;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.MaterialAdapter;
import com.sanleng.emergencystation.bean.ArchitectureBean;
import com.sanleng.emergencystation.dialog.MaterialDetailsDialog;
import com.sanleng.emergencystation.dialog.PromptDialog;
import com.sanleng.emergencystation.net.NetCallBack;
import com.sanleng.emergencystation.net.RequestUtils;
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
public class MaterialActivity extends Activity {

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
        StatusBarUtil.setColor(MaterialActivity.this,R.color.translucency);
        initview();
        loadData(pageNo);
    }

    private void initview() {
        Intent intent = getIntent();
        ids = intent.getStringExtra("ids");
        // 创建对象
        promptDialog = new PromptDialog(this);
        // 设置自定义属性
        promptDialog.getDefaultBuilder().touchAble(true).round(3).loadingDuration(2000);
//        materialAdapter = new MaterialAdapter();
        allList = new ArrayList<ArchitectureBean>();
        r_back = (RelativeLayout) findViewById(R.id.r_back);
        r_back.setOnClickListener(new MyOnClickListener(0));
    }

    // 加载数据
    private void loadData(int page) {
        onelist = new ArrayList<ArchitectureBean>();
        ArchitectureBean beana = new ArchitectureBean();
        beana.setName("简易呼吸器" + "  数量：1");
        beana.setAddress("1号应急箱");
        beana.setId("a");
        beana.setType("A");
        beana.setMac("54C9DFF77EA4");
        onelist.add(beana);

        ArchitectureBean beanb = new ArchitectureBean();
        beanb.setName("头盔" + "  数量：2");
        beanb.setAddress("2号应急箱");
        beanb.setId("b");
        beanb.setType("B");
        beanb.setMac("54C9DFF77EA4");
        onelist.add(beanb);

        ArchitectureBean beanc = new ArchitectureBean();
        beanc.setName("消防服" + "  数量：2");
        beanc.setAddress("2号应急箱");
        beanc.setId("c");
        beanc.setType("B");
        beanc.setMac("54C9DFF77EA4");
        onelist.add(beanc);

        ArchitectureBean beand = new ArchitectureBean();
        beand.setName("手套" + "  数量：2");
        beand.setAddress("2号应急箱");
        beand.setId("d");
        beand.setType("B");
        beand.setMac("54C9DFF77EA4");
        onelist.add(beand);

        ArchitectureBean beane = new ArchitectureBean();
        beane.setName("安全绳" + "  数量：2");
        beane.setAddress("2号应急箱");
        beane.setId("e");
        beane.setType("B");
        beane.setMac("54C9DFF77EA4");
        onelist.add(beanc);

        ArchitectureBean beanf = new ArchitectureBean();
        beanf.setName("腰帶" + "  数量：1");
        beanf.setAddress("2号应急箱");
        beanf.setId("f");
        beanf.setType("B");
        beanf.setMac("54C9DFF77EA4");
        onelist.add(beanf);

        ArchitectureBean beang = new ArchitectureBean();
        beang.setName("胶鞋" + "  数量：2");
        beang.setAddress("2号应急箱");
        beang.setId("g");
        beang.setType("B");
        beang.setMac("54C9DFF77EA4");
        onelist.add(beang);

        ArchitectureBean beanc1 = new ArchitectureBean();
        beanc1.setName("水带" + "  数量：2");
        beanc1.setAddress("3号应急箱");
        beanc1.setId("h");
        beanc1.setType("C");
        beanc1.setMac("54C9DFF77EA4");
        onelist.add(beanc1);

        ArchitectureBean beanc2 = new ArchitectureBean();
        beanc2.setName("消防枪头" + "  数量：2");
        beanc2.setAddress("3号应急箱");
        beanc2.setId("i");
        beanc2.setType("C");
        beanc2.setMac("54C9DFF77EA4");
        onelist.add(beanc2);

        ArchitectureBean beanc3 = new ArchitectureBean();
        beanc3.setName("水带接头" + "  数量：4");
        beanc3.setAddress("3号应急箱");
        beanc3.setId("j");
        beanc3.setType("C");
        beanc3.setMac("54C9DFF77EA4");
        onelist.add(beanc3);

        ArchitectureBean beand1 = new ArchitectureBean();
        beand1.setName("折叠担架" + "  数量：1");
        beand1.setAddress("4号应急箱");
        beand1.setId("k");
        beand1.setType("D");
        beand1.setMac("54C9DFF77EA4");
        onelist.add(beand1);

        ArchitectureBean beane1 = new ArchitectureBean();
        beane1.setName("干粉灭火器" + "  数量：2");
        beane1.setAddress("5号应急箱");
        beane1.setId("l");
        beane1.setType("E");
        beane1.setMac("54C9DFF77EA4");
        onelist.add(beane1);

        ArchitectureBean beane2 = new ArchitectureBean();
        beane2.setName("逃生应急箱" + "  数量：1");
        beane2.setAddress("5号应急箱");
        beane2.setId("m");
        beane2.setType("E");
        beane2.setMac("54C9DFF77EA4");
        onelist.add(beane2);

        ArchitectureBean beanf1 = new ArchitectureBean();
        beanf1.setName("消防桶" + "  数量：1");
        beanf1.setAddress("6号应急箱");
        beanf1.setId("n");
        beanf1.setType("F");
        beanf1.setMac("54C9DFF77EA4");
        onelist.add(beanf1);

        ArchitectureBean beanf2 = new ArchitectureBean();
        beanf2.setName("扩音喇叭" + "  数量：2");
        beanf2.setAddress("6号应急箱");
        beanf2.setId("o");
        beanf2.setType("F");
        beanf2.setMac("54C9DFF77EA4");
        onelist.add(beanf2);

        ArchitectureBean beang1 = new ArchitectureBean();
        beang1.setName("轮椅" + "  数量：1");
        beang1.setAddress("7号应急箱");
        beang1.setId("p");
        beang1.setType("G");
        beang1.setMac("54C9DFF77EA4");
        onelist.add(beang1);

        materiallistview = (ListView) findViewById(R.id.materiallistview);
        materialAdapter = new MaterialAdapter(MaterialActivity.this,onelist,mHandler);
        materiallistview.setAdapter(materialAdapter);


//		onelist = new ArrayList<ArchitectureBean>();
//		RequestParams params = new RequestParams();
//		params.put("stationId", ids);
//		params.put("pageNum", page + "");
//		params.put("pageSize", "10");
//		params.put("unit_code", PreferenceUtils.getString(MaterialActivity.this, "unitcode"));
//		params.put("username", PreferenceUtils.getString(MaterialActivity.this, "EmergencyStation_username"));
//		params.put("platformkey", "app_firecontrol_owner");
//
//		RequestUtils.ClientPost(URLs.Material_URL, params, new NetCallBack() {
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
//				System.out.println("数据请求成功" + result);
//				try {
//					int length = 10;
//					int SIZE = 0;
//					JSONObject jsonObject = new JSONObject(result);
//					String msg = jsonObject.getString("msg");
//					if (msg.equals("获取成功")) {
//						String data = jsonObject.getString("data");
//						JSONObject objects = new JSONObject(data);
//						String listsize = objects.getString("total");
//						SIZE = Integer.parseInt(listsize);
//						String list = objects.getString("list");
//
//						JSONArray array = new JSONArray(list);
//						JSONObject object;
//						for (int i = 0; i < array.length(); i++) {
//							ArchitectureBean bean = new ArchitectureBean();
//							object = (JSONObject) array.get(i);
//							String name = object.getString("name");
//							String number = object.getString("number");
//							String ids = object.getString("ids");
//
//							bean.setName(name);
//							bean.setAddress(number);
//							bean.setId(ids);
//							onelist.add(bean);
//						}
//
//						if (SIZE % length == 0) {
//							allpage = SIZE / length;
//						} else {
//							allpage = SIZE / length + 1;
//						}
//
//						materiallistview = (ListView) findViewById(R.id.materiallistview);
//						allList.addAll(onelist);
//						materialAdapter.bindData(MaterialActivity.this, allList);
//						if (pageNo == 1) {
//							// 没有数据就提示暂无数据。
//							materiallistview.setEmptyView(findViewById(R.id.nodata));
//							materiallistview.setAdapter(materialAdapter);
//						}
//						materialAdapter.notifyDataSetChanged();
//						pageNo++;
//
//						finish = true;
//
//						materiallistview.setOnScrollListener(new OnScrollListener() {
//							@Override
//							public void onScrollStateChanged(AbsListView view, int scrollState) {
//								/**
//								 * 当分页操作is_divPage为true时、滑动停止时、且pageNo<=allpage（ 这里因为服务端有allpage页数据）时，加载更多数据。
//								 */
//								if (is_divPage && scrollState == OnScrollListener.SCROLL_STATE_IDLE && pageNo <= allpage
//										&& finish) {
//									finish = false;
//									loadData(pageNo);
//								} else if (pageNo > allpage && finish) {
//									finish = false;
//									// 如果pageNo>allpage则表示，服务端没有更多的数据可供加载了。
//									Toast.makeText(MaterialActivity.this, "加载完了！", Toast.LENGTH_SHORT).show();
//								}
//							}
//
//							// 当：第一个可见的item（firstVisibleItem）+可见的item的个数（visibleItemCount）=
//							// 所有的item总数的时候， is_divPage变为TRUE，这个时候才会加载数据。
//							@Override
//							public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
//												 int totalItemCount) {
//								is_divPage = (firstVisibleItem + visibleItemCount == totalItemCount);
//							}
//						});
//
//						materiallistview.setOnItemClickListener(new OnItemClickListener() {
//
//							@Override
//							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//								// TODO Auto-generated method stub
//								ArchitectureBean bean = allList.get(position);
//								String ids = bean.getId();
//								MaterialDetailsDialog materialDetailsDialog = new MaterialDetailsDialog(
//										MaterialActivity.this, ids);
//								materialDetailsDialog.show();
//							}
//						});
//
//					} else {
//						promptDialog.showError(msg);
//					}
//
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			@Override
//			public void onMyFailure(Throwable arg0) {
//				promptDialog.showError("加载失败");
//			}
//		});

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
