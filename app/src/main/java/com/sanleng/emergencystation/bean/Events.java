package com.sanleng.emergencystation.bean;

import java.util.List;

public class Events {

    /**
     * state : ok
     * page : {"totalCount":181,"pageSize":1,"totalPage":181,"currPage":1,"nextPage":2,"list":[{"chev_type":4,"chev_user":"管理员","chev_ch_name":"应急柜","chev_time":"2021-04-22T01:17:05.000+0000","chev_unit_code":"f73b6f5aa62f4af3a07e0cb3a0bc166a","chev_ids":"78ada55ae35ad20692d24eaf53a4c7cb","chev_chc_name":"1号柜"}]}
     * statecode : PAGE_DATA_SUCCESS
     * message : 分页数据获取成功
     */

    private String state;
    private PageBean page;
    private String statecode;
    private String message;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
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

    public static class PageBean {
        /**
         * totalCount : 181
         * pageSize : 1
         * totalPage : 181
         * currPage : 1
         * nextPage : 2
         * list : [{"chev_type":4,"chev_user":"管理员","chev_ch_name":"应急柜","chev_time":"2021-04-22T01:17:05.000+0000","chev_unit_code":"f73b6f5aa62f4af3a07e0cb3a0bc166a","chev_ids":"78ada55ae35ad20692d24eaf53a4c7cb","chev_chc_name":"1号柜"}]
         */

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private int nextPage;
        private List<ListBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * chev_type : 4
             * chev_user : 管理员
             * chev_ch_name : 应急柜
             * chev_time : 2021-04-22T01:17:05.000+0000
             * chev_unit_code : f73b6f5aa62f4af3a07e0cb3a0bc166a
             * chev_ids : 78ada55ae35ad20692d24eaf53a4c7cb
             * chev_chc_name : 1号柜
             */

            private int chev_type;
            private String chev_user;
            private String chev_ch_name;
            private String chev_time;
            private String chev_unit_code;
            private String chev_ids;
            private String chev_chc_name;

            public int getChev_type() {
                return chev_type;
            }

            public void setChev_type(int chev_type) {
                this.chev_type = chev_type;
            }

            public String getChev_user() {
                return chev_user;
            }

            public void setChev_user(String chev_user) {
                this.chev_user = chev_user;
            }

            public String getChev_ch_name() {
                return chev_ch_name;
            }

            public void setChev_ch_name(String chev_ch_name) {
                this.chev_ch_name = chev_ch_name;
            }

            public String getChev_time() {
                return chev_time;
            }

            public void setChev_time(String chev_time) {
                this.chev_time = chev_time;
            }

            public String getChev_unit_code() {
                return chev_unit_code;
            }

            public void setChev_unit_code(String chev_unit_code) {
                this.chev_unit_code = chev_unit_code;
            }

            public String getChev_ids() {
                return chev_ids;
            }

            public void setChev_ids(String chev_ids) {
                this.chev_ids = chev_ids;
            }

            public String getChev_chc_name() {
                return chev_chc_name;
            }

            public void setChev_chc_name(String chev_chc_name) {
                this.chev_chc_name = chev_chc_name;
            }
        }
    }
}
