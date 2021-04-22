package com.sanleng.emergencystation.bean;

import java.util.List;

public class Banners {

    /**
     * msg :
     * data : {"content":[{"number":1,"createTime":"2020-12-23 15:46:27","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/7bcf094595ac4d62bc2f1338c83c1a09.png","id":2},{"number":2,"createTime":"2020-12-23 15:52:17","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/62b01652028d49478926ae4f77de7801.png","id":5},{"number":3,"createTime":"2020-12-23 15:52:07","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/fd8e29a7d7b34580b4d8194506e90ee7.png","id":4},{"number":4,"createTime":"2020-12-23 16:02:47","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/ceaf3d024053421cba78c77736c2f471.png","id":6},{"number":5,"createTime":"2020-12-23 16:02:58","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/98c1d42039ec442f9041b9558504a787.png","id":7},{"number":6,"createTime":"2020-12-23 16:03:07","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/342be510fba5420ea7d4d3ba1a91a923.png","id":8}],"totalPages":1,"last":true,"totalElements":6,"first":true,"sort":null,"numberOfElements":6,"size":10,"number":0}
     * status : 0
     */

    private String msg;
    private DataBean data;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * content : [{"number":1,"createTime":"2020-12-23 15:46:27","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/7bcf094595ac4d62bc2f1338c83c1a09.png","id":2},{"number":2,"createTime":"2020-12-23 15:52:17","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/62b01652028d49478926ae4f77de7801.png","id":5},{"number":3,"createTime":"2020-12-23 15:52:07","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/fd8e29a7d7b34580b4d8194506e90ee7.png","id":4},{"number":4,"createTime":"2020-12-23 16:02:47","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/ceaf3d024053421cba78c77736c2f471.png","id":6},{"number":5,"createTime":"2020-12-23 16:02:58","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/98c1d42039ec442f9041b9558504a787.png","id":7},{"number":6,"createTime":"2020-12-23 16:03:07","carouselImage":"http://jssl.oss-cn-beijing.aliyuncs.com/342be510fba5420ea7d4d3ba1a91a923.png","id":8}]
         * totalPages : 1
         * last : true
         * totalElements : 6
         * first : true
         * sort : null
         * numberOfElements : 6
         * size : 10
         * number : 0
         */

        private int totalPages;
        private boolean last;
        private int totalElements;
        private boolean first;
        private Object sort;
        private int numberOfElements;
        private int size;
        private int number;
        private List<ContentBean> content;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * number : 1
             * createTime : 2020-12-23 15:46:27
             * carouselImage : http://jssl.oss-cn-beijing.aliyuncs.com/7bcf094595ac4d62bc2f1338c83c1a09.png
             * id : 2
             */

            private int number;
            private String createTime;
            private String carouselImage;
            private int id;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCarouselImage() {
                return carouselImage;
            }

            public void setCarouselImage(String carouselImage) {
                this.carouselImage = carouselImage;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
