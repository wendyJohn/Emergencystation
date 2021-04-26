package com.sanleng.emergencystation.model;


import com.sanleng.emergencystation.bean.Cabinet;
import com.sanleng.emergencystation.bean.Events;

import java.util.List;

public interface CabinetContract {
    void Success(List<Cabinet.MapListBean> mList);
    void Failed();
}
