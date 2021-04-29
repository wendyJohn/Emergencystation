package com.sanleng.emergencystation.bean;

import java.util.List;

public class Records {

    /**
     * state : ok
     * page : {"totalCount":1,"pageSize":10,"totalPage":1,"currPage":1,"nextPage":-1,"list":[{"usc_name":"1号柜","names":"钥匙","chio_type":1,"ugi_name":"1号房钥匙","chio_batch_number":"202104260005","us_name":"1号钥匙柜","name":"应急柜","chio_rfid":"10100006","chio_operation_time":"2021-04-26T02:28:39.000+0000"}]}
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
         * totalCount : 1
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * nextPage : -1
         * list : [{"usc_name":"1号柜","names":"钥匙","chio_type":1,"ugi_name":"1号房钥匙","chio_batch_number":"202104260005","us_name":"1号钥匙柜","name":"应急柜","chio_rfid":"10100006","chio_operation_time":"2021-04-26T02:28:39.000+0000"}]
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
             * usc_name : 1号柜
             * names : 钥匙
             * chio_type : 1
             * ugi_name : 1号房钥匙
             * chio_batch_number : 202104260005
             * us_name : 1号钥匙柜
             * name : 应急柜
             * chio_rfid : 10100006
             * chio_operation_time : 2021-04-26T02:28:39.000+0000
             */

            private String usc_name;
            private String names;
            private int chio_type;
            private String ugi_name;
            private String chio_batch_number;
            private String us_name;
            private String name;
            private String chio_rfid;
            private String chio_operation_time;

            public String getUsc_name() {
                return usc_name;
            }

            public void setUsc_name(String usc_name) {
                this.usc_name = usc_name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public int getChio_type() {
                return chio_type;
            }

            public void setChio_type(int chio_type) {
                this.chio_type = chio_type;
            }

            public String getUgi_name() {
                return ugi_name;
            }

            public void setUgi_name(String ugi_name) {
                this.ugi_name = ugi_name;
            }

            public String getChio_batch_number() {
                return chio_batch_number;
            }

            public void setChio_batch_number(String chio_batch_number) {
                this.chio_batch_number = chio_batch_number;
            }

            public String getUs_name() {
                return us_name;
            }

            public void setUs_name(String us_name) {
                this.us_name = us_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getChio_rfid() {
                return chio_rfid;
            }

            public void setChio_rfid(String chio_rfid) {
                this.chio_rfid = chio_rfid;
            }

            public String getChio_operation_time() {
                return chio_operation_time;
            }

            public void setChio_operation_time(String chio_operation_time) {
                this.chio_operation_time = chio_operation_time;
            }
        }
    }
}
