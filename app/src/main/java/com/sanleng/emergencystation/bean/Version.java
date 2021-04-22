package com.sanleng.emergencystation.bean;

public class Version {

    /**
     * msg : 获取成功
     * code : 0
     * data : {"ids":"e4a13f1cdaf24da58859fff6bf8df9d3","platformType":"app_firecontrol_supervise","osType":"os_android","appName":"智慧消防监管端","appVersion":"V3.0.1（300011）","appDescribe":"1.啊啊啊啊","downloadNumbers":0,"pubState":"1","pubDate":"2018-02-01 09:49:24","pubDateString":null,"downloadUrl":"/RootFile/Apk/20180201/f3749e1c869541fd94ba6a775608b7d7.apk","size":null,"istop":"1"}
     */

    private String msg;
    private String code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ids : e4a13f1cdaf24da58859fff6bf8df9d3
         * platformType : app_firecontrol_supervise
         * osType : os_android
         * appName : 智慧消防监管端
         * appVersion : V3.0.1（300011）
         * appDescribe : 1.啊啊啊啊
         * downloadNumbers : 0
         * pubState : 1
         * pubDate : 2018-02-01 09:49:24
         * pubDateString : null
         * downloadUrl : /RootFile/Apk/20180201/f3749e1c869541fd94ba6a775608b7d7.apk
         * size : null
         * istop : 1
         */

        private String ids;
        private String platformType;
        private String osType;
        private String appName;
        private String appVersion;
        private String appDescribe;
        private int downloadNumbers;
        private String pubState;
        private String pubDate;
        private Object pubDateString;
        private String downloadUrl;
        private Object size;
        private String istop;

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public String getPlatformType() {
            return platformType;
        }

        public void setPlatformType(String platformType) {
            this.platformType = platformType;
        }

        public String getOsType() {
            return osType;
        }

        public void setOsType(String osType) {
            this.osType = osType;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getAppDescribe() {
            return appDescribe;
        }

        public void setAppDescribe(String appDescribe) {
            this.appDescribe = appDescribe;
        }

        public int getDownloadNumbers() {
            return downloadNumbers;
        }

        public void setDownloadNumbers(int downloadNumbers) {
            this.downloadNumbers = downloadNumbers;
        }

        public String getPubState() {
            return pubState;
        }

        public void setPubState(String pubState) {
            this.pubState = pubState;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public Object getPubDateString() {
            return pubDateString;
        }

        public void setPubDateString(Object pubDateString) {
            this.pubDateString = pubDateString;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public Object getSize() {
            return size;
        }

        public void setSize(Object size) {
            this.size = size;
        }

        public String getIstop() {
            return istop;
        }

        public void setIstop(String istop) {
            this.istop = istop;
        }
    }
}
