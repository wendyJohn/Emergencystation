package com.sanleng.emergencystation.utils;

public interface UpdatePresenter {
    void UpdateSuccess(String version,String path);
    void UpdateFailed();
}
