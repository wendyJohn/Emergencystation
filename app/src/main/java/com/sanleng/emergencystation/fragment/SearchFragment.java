package com.sanleng.emergencystation.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.MaterialDesActivity;
import com.sanleng.emergencystation.adapter.StockAdapter;
import com.sanleng.emergencystation.bean.Stocks;
import com.sanleng.emergencystation.model.StocksContract;
import com.sanleng.emergencystation.presenter.Requests;
import com.sanleng.emergencystation.utils.CharacterParser;
import com.sanleng.emergencystation.utils.PinyinComparator;
import com.sanleng.emergencystation.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment implements StocksContract {
    private StockAdapter stockAdapter;
    private View view;
    private GridView stock_gridViews;
    private String Cabinetnumber;
    //汉字转换成拼音的类
    private CharacterParser characterParser;
    // 根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;
    private String CabinetName;
    private List<Stocks.UniversalStockMaterialListBean> lists;

    @SuppressLint("ValidFragment")
    public SearchFragment(String str) {
        super();
        this.Cabinetnumber = str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, null);
        initView();
        return view;
    }

    private void initView() {
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
        initData(CabinetName);
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        stock_gridViews = view.findViewById(R.id.stock_gridViews);
        lists = new ArrayList<>();
    }

    private void initData(String str) {
        try {
            if (str.equals("全部")) {
                Requests.getStocksCall(SearchFragment.this, getActivity(), PreferenceUtils.getString(getActivity(), "us_id"), "");
            } else if (str.equals("1号柜")) {
                Requests.getStocksCall(SearchFragment.this, getActivity(), PreferenceUtils.getString(getActivity(), "us_id"), PreferenceUtils.getString(getActivity(), CabinetName));
            } else if (str.equals("2号柜")) {
                Requests.getStocksCall(SearchFragment.this, getActivity(), PreferenceUtils.getString(getActivity(), "us_id"), PreferenceUtils.getString(getActivity(), CabinetName));
            } else if (str.equals("3号柜")) {
                Requests.getStocksCall(SearchFragment.this, getActivity(), PreferenceUtils.getString(getActivity(), "us_id"), PreferenceUtils.getString(getActivity(), CabinetName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<Stocks.UniversalStockMaterialListBean> filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = lists;
        } else {
            filterDateList.clear();
            for (Stocks.UniversalStockMaterialListBean names : lists) {
                String name = names.getGoodsType();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(names);
                }
            }
        }
        try {
            stockAdapter.updateListView(filterDateList);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    @Override
    public void Success(List<Stocks.UniversalStockMaterialListBean> mList) {
        try {
            lists.clear();
            lists = mList;
            stock_gridViews.setNumColumns(3);
            stockAdapter = new StockAdapter(getActivity(), lists);
            stock_gridViews.setAdapter(stockAdapter);
            stock_gridViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String ugrUscCode = lists.get(position).getUscId();
                    String ugiType = lists.get(position).getNodekey();
                    Intent intent=new Intent(getActivity(), MaterialDesActivity.class);
                    intent.putExtra("ugrUscCode",ugrUscCode);
                    intent.putExtra("ugiType",ugiType);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Failed() {

    }
}
