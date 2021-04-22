package com.sanleng.emergencystation.model;

public interface UpdateModel {
    void UpdateSuccess(String version, String path, String appDescribe);
    void UpdateFailed();
}
