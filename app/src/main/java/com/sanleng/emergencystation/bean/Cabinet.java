package com.sanleng.emergencystation.bean;

import java.util.List;

public class Cabinet {

    /**
     * mapList : [{"ustPic1Url":"http://jssl.oss-cn-beijing.aliyuncs.com/9fa58b58cb4e4395a9ce42f7914c3613.jpg","usMacAddress":"464709F64DE9","uscUsCode":1,"usName":"1号钥匙柜","usId":"2f5c6c5296b7a3d8df3b085e394ef990"},{"ustPic1Url":"http://jssl.oss-cn-beijing.aliyuncs.com/7ebf6d4ab4854116ab7fbefdfc1e991c.jpg","usMacAddress":"96F50941B993","uscUsCode":3,"usName":"1号应急柜","usId":"bea343090501ada11ec1916e533423e1"}]
     * state : ok
     * statecode : SUCCESS
     * message : 操作成功
     */

    private String state;
    private String statecode;
    private String message;
    private List<MapListBean> mapList;

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

    public List<MapListBean> getMapList() {
        return mapList;
    }

    public void setMapList(List<MapListBean> mapList) {
        this.mapList = mapList;
    }

    public static class MapListBean {
        /**
         * ustPic1Url : http://jssl.oss-cn-beijing.aliyuncs.com/9fa58b58cb4e4395a9ce42f7914c3613.jpg
         * usMacAddress : 464709F64DE9
         * uscUsCode : 1
         * usName : 1号钥匙柜
         * usId : 2f5c6c5296b7a3d8df3b085e394ef990
         */

        private String ustPic1Url;
        private String usMacAddress;
        private int uscUsCode;
        private String usName;
        private String usId;

        public String getUstPic1Url() {
            return ustPic1Url;
        }

        public void setUstPic1Url(String ustPic1Url) {
            this.ustPic1Url = ustPic1Url;
        }

        public String getUsMacAddress() {
            return usMacAddress;
        }

        public void setUsMacAddress(String usMacAddress) {
            this.usMacAddress = usMacAddress;
        }

        public int getUscUsCode() {
            return uscUsCode;
        }

        public void setUscUsCode(int uscUsCode) {
            this.uscUsCode = uscUsCode;
        }

        public String getUsName() {
            return usName;
        }

        public void setUsName(String usName) {
            this.usName = usName;
        }

        public String getUsId() {
            return usId;
        }

        public void setUsId(String usId) {
            this.usId = usId;
        }
    }
}
