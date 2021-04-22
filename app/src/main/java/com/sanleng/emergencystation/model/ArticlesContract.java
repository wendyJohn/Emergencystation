package com.sanleng.emergencystation.model;


import com.sanleng.emergencystation.bean.Articles;

import java.util.List;

public interface ArticlesContract {
    void ArSuccess(List<Articles.DataBean.ContentBean> mList);
    void Failed();
    void TimeOut();
}
