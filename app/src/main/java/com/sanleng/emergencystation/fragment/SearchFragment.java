package com.sanleng.emergencystation.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


import com.sanleng.emergencystation.MyApplication;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.StockAdapter;
import com.sanleng.emergencystation.bean.MaterialBean;
import com.sanleng.emergencystation.utils.CharacterParser;
import com.sanleng.emergencystation.utils.MessageEvent;
import com.sanleng.emergencystation.utils.PinyinComparator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment implements View.OnClickListener {
    private StockAdapter stockAdapter;
    private View view;
    private GridView stock_gridViews;
    private TextView text_tip;
    private String Cabinetnumber;
    //汉字转换成拼音的类
    private CharacterParser characterParser;
    // 根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;
    private String CabinetName;

    public SearchFragment(String str) {
        super();
        this.Cabinetnumber = str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, null);
        initView();
        initData();
        return view;
    }

    private void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (Cabinetnumber.equals("一")) {
            CabinetName = "全部";
        }
        if (Cabinetnumber.equals("二")) {
            CabinetName = "1号柜";
        }
        if (Cabinetnumber.equals("三")) {
            CabinetName = "2号柜";
        }
        if (Cabinetnumber.equals("四")) {
            CabinetName = "3号柜";
        }
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        stock_gridViews = view.findViewById(R.id.stock_gridViews);
        text_tip = view.findViewById(R.id.text_tip);

    }

    private void initData() {
        try {
            ArrayList<MaterialBean> one_list = new ArrayList<>();
            MaterialBean beana = new MaterialBean();
            beana.setName("灭火器");
            beana.setNumber("2");
            beana.setLack("2");
            beana.setState("正常");

            MaterialBean beanb = new MaterialBean();
            beanb.setName("灭火器");
            beanb.setNumber("1");
            beanb.setLack("2");
            beanb.setState("短缺");

            MaterialBean beanc = new MaterialBean();
            beanc.setName("灭火器");
            beanc.setNumber("1");
            beanc.setLack("2");
            beanc.setState("短缺");

            MaterialBean beand = new MaterialBean();
            beand.setName("灭火器");
            beand.setNumber("2");
            beand.setLack("2");
            beand.setState("正常");


            MaterialBean beane = new MaterialBean();
            beane.setName("灭火器");
            beane.setNumber("2");
            beane.setLack("2");
            beane.setState("正常");


            MaterialBean beanf = new MaterialBean();
            beanf.setName("灭火器");
            beanf.setNumber("0");
            beanf.setLack("2");
            beanf.setState("短缺");

            MaterialBean beang = new MaterialBean();
            beang.setName("灭火器");
            beang.setNumber("0");
            beang.setLack("2");
            beang.setState("短缺");


            MaterialBean beanh = new MaterialBean();
            beanh.setName("灭火器");
            beanh.setNumber("0");
            beanh.setLack("2");
            beanh.setState("短缺");

            MaterialBean beanj = new MaterialBean();
            beanj.setName("灭火器");
            beanj.setNumber("0");
            beanj.setLack("2");
            beanj.setState("短缺");


            MaterialBean beank = new MaterialBean();
            beank.setName("灭火器");
            beank.setNumber("0");
            beank.setLack("2");
            beank.setState("短缺");

            MaterialBean beanl = new MaterialBean();
            beanl.setName("灭火器");
            beanl.setNumber("0");
            beanl.setLack("2");
            beanl.setState("短缺");

            MaterialBean beanq = new MaterialBean();
            beanq.setName("灭火器");
            beanq.setNumber("0");
            beanq.setLack("2");
            beanq.setState("短缺");


            one_list.add(beana);
            one_list.add(beanb);
            one_list.add(beanc);
            one_list.add(beand);
            one_list.add(beane);
            one_list.add(beanf);
            one_list.add(beang);
            one_list.add(beanh);
            one_list.add(beanj);
            one_list.add(beank);
            one_list.add(beanl);
            one_list.add(beanq);

            stock_gridViews.setNumColumns(3);
            stockAdapter = new StockAdapter(getActivity(), one_list);
            stock_gridViews.setAdapter(stockAdapter);
            stock_gridViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    MaterDetsDialog detsDialog=new MaterDetsDialog(getActivity());
//                    detsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                    detsDialog.show();
                }
            });

//            StockData.Stock(getActivity(), SearchFragment.this, "2", CabinetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void StokSuccess(String state, List<DangerousChemicals> stocklist) {
//        mylist = stocklist;
//        if (state.equals("1")) {
//            text_tip.setText(Cabinetnumber + "号柜外暂无危化品");
//            stockAdapter = new StockAdapter(getActivity(), stocklist, "柜外");
//            fraglistview.setAdapter(stockAdapter);
//            fraglistview.setEmptyView(text_tip);//设置当ListView为空的时候显示text_tip "柜外暂无数据"
//            inventory_out.setText("柜外(" + stocklist.size() + ")");
//        } else {
//            text_tip.setText(Cabinetnumber + "号柜内暂无危化品");
//            stockAdapter = new StockAdapter(getActivity(), stocklist, "柜内");
//            fraglistview.setAdapter(stockAdapter);
//            fraglistview.setEmptyView(text_tip);//设置当ListView为空的时候显示text_tip "柜内暂无数据"
//            inventory_in.setText("柜内(" + stocklist.size() + ")");
//        }
//        fraglistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String epc = ((DangerousChemicals) stockAdapter.getItem(position)).getRfid();
//                String name = ((DangerousChemicals) stockAdapter.getItem(position)).getName();
//                String equation = ((DangerousChemicals) stockAdapter.getItem(position)).getEquation();
//                String balancedata = ((DangerousChemicals) stockAdapter.getItem(position)).getBalancedata();
//                String describe = ((DangerousChemicals) stockAdapter.getItem(position)).getDescribe();
//                String cas = ((DangerousChemicals) stockAdapter.getItem(position)).getCas();
//                DetailsDialog detailsDialog = new DetailsDialog(getActivity(), epc, name, equation, balancedata, describe, cas);
//                detailsDialog.show();
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    /**
     * 接收EventBus返回数据
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backData(MessageEvent messageEvent) {
        switch (messageEvent.getTAG()) {
        }
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
//        List<DangerousChemicals> filterDateList = new ArrayList<>();
//        if (TextUtils.isEmpty(filterStr)) {
//            filterDateList = mylist;
//        } else {
//            filterDateList.clear();
//            for (DangerousChemicals names : mylist) {
//                String name = names.getName();
//                if (name.indexOf(filterStr.toString()) != -1
//                        || characterParser.getSelling(name).startsWith(filterStr.toString())) {
//                    filterDateList.add(names);
//                }
//            }
//        }
//        try {
//            stockAdapter.updateListView(filterDateList);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }
}
