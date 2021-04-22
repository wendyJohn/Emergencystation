package com.sanleng.emergencystation.utils;


/**
 * author: qiaoshi
 **/
public class MessageEvent {
    private int TAG;
    private String message;
    public static final int EXAM_Refreshs = 0x124695; // 刷新
    public static final int EXAM_load = 0x135255;  //加载


    public MessageEvent(int TAG) {
        this.TAG = TAG;
    }

    public MessageEvent(int TAG, String message) {
        this.TAG = TAG;
        this.message = message;
    }

    public int getTAG() {
        return TAG;
    }

    public void setTAG(int TAG) {
        this.TAG = TAG;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
