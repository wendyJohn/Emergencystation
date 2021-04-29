package com.sanleng.emergencystation.model;


import com.sanleng.emergencystation.bean.Trace;

import java.util.List;
public interface StoresContract {
    void Success(List<Trace.PageBean.ListBean> mList);
    void Failed();
}
