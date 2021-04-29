package com.sanleng.emergencystation.bean;

import java.util.List;

public class Materialdetails {


    /**
     * goodInfo : [{"ugi_id":"886bbf44d39e382f4e26dab19fa0dfee","ugr_usage_state":3,"ugi_unit":"个","ugi_pic1_url":"http://jssl.oss-cn-beijing.aliyuncs.com/e79e07530bb5410398d35cc32c3e21f6.jpg","ugi_pic2_url":"","ugi_usage":"use2","ugi_type":"xfc1","ugr_status":2,"ugr_unit_code":"f73b6f5aa62f4af3a07e0cb3a0bc166a","ugr_rfid":"10100002","ugr_batch":"批次1","ugi_guide_url":"http://jssl.oss-cn-beijing.aliyuncs.com/8c939235e1cd4883841a7d0511110ba9.mp4","ugi_specification":"23","names":"消防铲1","ugr_us_code":"bea343090501ada11ec1916e533423e1","ugi_name":"消防铲2号","ugi_description":"desc3","ugi_pic3_url":"","ugr_antenna":"1","ugr_ugi_code":"886bbf44d39e382f4e26dab19fa0dfee","ugr_usc_code":"099aac0cfc5d2245256efcf6e7eb4cd2","ugi_factory":"测试","ugr_id":"38f6c0f15dc444e6fb7b8c2bf4ce8e1f","ugi_type_code":"xfc002"}]
     * state : ok
     * statecode : SUCCESS
     * message : 操作成功
     */

    private String state;
    private String statecode;
    private String message;
    private List<GoodInfoBean> goodInfo;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GoodInfoBean> getGoodInfo() {
        return goodInfo;
    }

    public void setGoodInfo(List<GoodInfoBean> goodInfo) {
        this.goodInfo = goodInfo;
    }

    public static class GoodInfoBean {
        /**
         * ugi_id : 886bbf44d39e382f4e26dab19fa0dfee
         * ugr_usage_state : 3
         * ugi_unit : 个
         * ugi_pic1_url : http://jssl.oss-cn-beijing.aliyuncs.com/e79e07530bb5410398d35cc32c3e21f6.jpg
         * ugi_pic2_url :
         * ugi_usage : use2
         * ugi_type : xfc1
         * ugr_status : 2
         * ugr_unit_code : f73b6f5aa62f4af3a07e0cb3a0bc166a
         * ugr_rfid : 10100002
         * ugr_batch : 批次1
         * ugi_guide_url : http://jssl.oss-cn-beijing.aliyuncs.com/8c939235e1cd4883841a7d0511110ba9.mp4
         * ugi_specification : 23
         * names : 消防铲1
         * ugr_us_code : bea343090501ada11ec1916e533423e1
         * ugi_name : 消防铲2号
         * ugi_description : desc3
         * ugi_pic3_url :
         * ugr_antenna : 1
         * ugr_ugi_code : 886bbf44d39e382f4e26dab19fa0dfee
         * ugr_usc_code : 099aac0cfc5d2245256efcf6e7eb4cd2
         * ugi_factory : 测试
         * ugr_id : 38f6c0f15dc444e6fb7b8c2bf4ce8e1f
         * ugi_type_code : xfc002
         */

        private String ugi_id;
        private int ugr_usage_state;
        private String ugi_unit;
        private String ugi_pic1_url;
        private String ugi_pic2_url;
        private String ugi_usage;
        private String ugi_type;
        private int ugr_status;
        private String ugr_unit_code;
        private String ugr_rfid;
        private String ugr_batch;
        private String ugi_guide_url;
        private String ugi_specification;
        private String names;
        private String ugr_us_code;
        private String ugi_name;
        private String ugi_description;
        private String ugi_pic3_url;
        private String ugr_antenna;
        private String ugr_ugi_code;
        private String ugr_usc_code;
        private String ugi_factory;
        private String ugr_id;
        private String ugi_type_code;
        private String ugr_leave_time;
        private String ugr_durable_years;

        public String getUgr_durable_years() {
            return ugr_durable_years;
        }

        public void setUgr_durable_years(String ugr_durable_years) {
            this.ugr_durable_years = ugr_durable_years;
        }

        public String getUgi_id() {
            return ugi_id;
        }

        public void setUgi_id(String ugi_id) {
            this.ugi_id = ugi_id;
        }

        public int getUgr_usage_state() {
            return ugr_usage_state;
        }

        public void setUgr_usage_state(int ugr_usage_state) {
            this.ugr_usage_state = ugr_usage_state;
        }

        public String getUgi_unit() {
            return ugi_unit;
        }

        public void setUgi_unit(String ugi_unit) {
            this.ugi_unit = ugi_unit;
        }

        public String getUgi_pic1_url() {
            return ugi_pic1_url;
        }

        public void setUgi_pic1_url(String ugi_pic1_url) {
            this.ugi_pic1_url = ugi_pic1_url;
        }

        public String getUgi_pic2_url() {
            return ugi_pic2_url;
        }

        public void setUgi_pic2_url(String ugi_pic2_url) {
            this.ugi_pic2_url = ugi_pic2_url;
        }

        public String getUgi_usage() {
            return ugi_usage;
        }

        public void setUgi_usage(String ugi_usage) {
            this.ugi_usage = ugi_usage;
        }

        public String getUgi_type() {
            return ugi_type;
        }

        public void setUgi_type(String ugi_type) {
            this.ugi_type = ugi_type;
        }

        public int getUgr_status() {
            return ugr_status;
        }

        public void setUgr_status(int ugr_status) {
            this.ugr_status = ugr_status;
        }

        public String getUgr_unit_code() {
            return ugr_unit_code;
        }

        public void setUgr_unit_code(String ugr_unit_code) {
            this.ugr_unit_code = ugr_unit_code;
        }

        public String getUgr_rfid() {
            return ugr_rfid;
        }

        public void setUgr_rfid(String ugr_rfid) {
            this.ugr_rfid = ugr_rfid;
        }

        public String getUgr_batch() {
            return ugr_batch;
        }

        public void setUgr_batch(String ugr_batch) {
            this.ugr_batch = ugr_batch;
        }

        public String getUgi_guide_url() {
            return ugi_guide_url;
        }

        public void setUgi_guide_url(String ugi_guide_url) {
            this.ugi_guide_url = ugi_guide_url;
        }

        public String getUgi_specification() {
            return ugi_specification;
        }

        public void setUgi_specification(String ugi_specification) {
            this.ugi_specification = ugi_specification;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getUgr_us_code() {
            return ugr_us_code;
        }

        public void setUgr_us_code(String ugr_us_code) {
            this.ugr_us_code = ugr_us_code;
        }

        public String getUgi_name() {
            return ugi_name;
        }

        public void setUgi_name(String ugi_name) {
            this.ugi_name = ugi_name;
        }

        public String getUgi_description() {
            return ugi_description;
        }

        public void setUgi_description(String ugi_description) {
            this.ugi_description = ugi_description;
        }

        public String getUgi_pic3_url() {
            return ugi_pic3_url;
        }

        public void setUgi_pic3_url(String ugi_pic3_url) {
            this.ugi_pic3_url = ugi_pic3_url;
        }

        public String getUgr_antenna() {
            return ugr_antenna;
        }

        public void setUgr_antenna(String ugr_antenna) {
            this.ugr_antenna = ugr_antenna;
        }

        public String getUgr_ugi_code() {
            return ugr_ugi_code;
        }

        public void setUgr_ugi_code(String ugr_ugi_code) {
            this.ugr_ugi_code = ugr_ugi_code;
        }

        public String getUgr_usc_code() {
            return ugr_usc_code;
        }

        public void setUgr_usc_code(String ugr_usc_code) {
            this.ugr_usc_code = ugr_usc_code;
        }

        public String getUgi_factory() {
            return ugi_factory;
        }

        public void setUgi_factory(String ugi_factory) {
            this.ugi_factory = ugi_factory;
        }

        public String getUgr_id() {
            return ugr_id;
        }

        public void setUgr_id(String ugr_id) {
            this.ugr_id = ugr_id;
        }

        public String getUgi_type_code() {
            return ugi_type_code;
        }

        public void setUgi_type_code(String ugi_type_code) {
            this.ugi_type_code = ugi_type_code;
        }

        public String getUgr_leave_time() {
            return ugr_leave_time;
        }

        public void setUgr_leave_time(String ugr_leave_time) {
            this.ugr_leave_time = ugr_leave_time;
        }
    }
}
