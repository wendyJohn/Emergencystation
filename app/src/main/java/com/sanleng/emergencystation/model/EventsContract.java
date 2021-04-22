package com.sanleng.emergencystation.model;


import com.sanleng.emergencystation.bean.Events;

import java.util.List;

public interface EventsContract {
    void Success(List<Events.PageBean.ListBean> mList);
    void Failed();
}
