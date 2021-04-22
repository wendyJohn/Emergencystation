package com.sanleng.emergencystation.model;



import com.sanleng.emergencystation.bean.Banners;

import java.util.List;

public interface BannersContract {
    void BanerSuccess(List<Banners.DataBean.ContentBean> mList);
    void Failed();
}
