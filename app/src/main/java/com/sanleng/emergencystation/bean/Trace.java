package com.sanleng.emergencystation.bean;

public class Trace {

    /**
     * 时间
     */
    private String acceptTime;
    private String tv_names, tv_states, tv_types, tv_cabinet;

    public Trace() {
    }

    public Trace(String acceptTime, String tv_names, String tv_states, String tv_types, String tv_cabinet) {
        this.acceptTime = acceptTime;
        this.tv_names = tv_names;
        this.tv_states = tv_states;
        this.tv_types = tv_types;
        this.tv_cabinet = tv_cabinet;
    }


    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getTv_names() {
        return tv_names;
    }

    public void setTv_names(String tv_names) {
        this.tv_names = tv_names;
    }

    public String getTv_states() {
        return tv_states;
    }

    public void setTv_states(String tv_states) {
        this.tv_states = tv_states;
    }

    public String getTv_types() {
        return tv_types;
    }

    public void setTv_types(String tv_types) {
        this.tv_types = tv_types;
    }

    public String getTv_cabinet() {
        return tv_cabinet;
    }

    public void setTv_cabinet(String tv_cabinet) {
        this.tv_cabinet = tv_cabinet;
    }
}
