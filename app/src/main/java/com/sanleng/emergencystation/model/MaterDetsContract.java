package com.sanleng.emergencystation.model;



import com.sanleng.emergencystation.bean.Materialdetails;

import java.util.List;

public interface MaterDetsContract {
    void Success(List<Materialdetails.GoodInfoBean> mList);
    void Failed();
}
