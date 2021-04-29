package com.sanleng.emergencystation.bean;

import java.util.List;

public class Stocks {


    /**
     * universalStockMaterialList : [{"uscId":"ed9c76b584ef3f090c824c1bc00779ba","nodekey":"YS","goodsType":"钥匙","cabinetNumber":"1","actualStorageNumber":0,"maxStorageNumber":15,"storageType":"正常"}]
     * state : ok
     * statecode : SUCCESS
     * message : 操作成功
     */

    private String state;
    private String statecode;
    private String message;
    private List<UniversalStockMaterialListBean> universalStockMaterialList;

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

    public List<UniversalStockMaterialListBean> getUniversalStockMaterialList() {
        return universalStockMaterialList;
    }

    public void setUniversalStockMaterialList(List<UniversalStockMaterialListBean> universalStockMaterialList) {
        this.universalStockMaterialList = universalStockMaterialList;
    }

    public static class UniversalStockMaterialListBean {
        /**
         * uscId : ed9c76b584ef3f090c824c1bc00779ba
         * nodekey : YS
         * goodsType : 钥匙
         * cabinetNumber : 1
         * actualStorageNumber : 0
         * maxStorageNumber : 15
         * storageType : 正常
         */

        private String uscId;
        private String nodekey;
        private String goodsType;
        private String cabinetNumber;
        private int actualStorageNumber;
        private int maxStorageNumber;
        private String storageType;
        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUscId() {
            return uscId;
        }

        public void setUscId(String uscId) {
            this.uscId = uscId;
        }

        public String getNodekey() {
            return nodekey;
        }

        public void setNodekey(String nodekey) {
            this.nodekey = nodekey;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public String getCabinetNumber() {
            return cabinetNumber;
        }

        public void setCabinetNumber(String cabinetNumber) {
            this.cabinetNumber = cabinetNumber;
        }

        public int getActualStorageNumber() {
            return actualStorageNumber;
        }

        public void setActualStorageNumber(int actualStorageNumber) {
            this.actualStorageNumber = actualStorageNumber;
        }

        public int getMaxStorageNumber() {
            return maxStorageNumber;
        }

        public void setMaxStorageNumber(int maxStorageNumber) {
            this.maxStorageNumber = maxStorageNumber;
        }

        public String getStorageType() {
            return storageType;
        }

        public void setStorageType(String storageType) {
            this.storageType = storageType;
        }
    }
}
