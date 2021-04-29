package com.sanleng.emergencystation.model;



import com.sanleng.emergencystation.bean.Stocks;

import java.util.List;

public interface StocksContract {
    void Success(List<Stocks.UniversalStockMaterialListBean> mList);
    void Failed();
}
