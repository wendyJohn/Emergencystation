package com.sanleng.emergencystation.bean;

public class Users {

    /**
     * msg : 获取成功
     * code : 0
     * data : {"id":"0174cd2a2ae64bcf543c74520db20002","name":"书记许守江","unitcode":"22d1a7248ee049de823704c4eb5e981b","username":"leader1","password":"","mobile":"15511111111","idcard":"","email":"","birthday":null,"address":"","usericon":null,"department":"7ff5c57c55e3f2e3c167d914c20ae8dc","type":"unit_type_admin_regulators","duty":"总指挥长","isdelete":"0","roles":null}
     * state : ok
     */

    private String msg;
    private String code;
    private DataBean data;
    private String state;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class DataBean {
        /**
         * id : 0174cd2a2ae64bcf543c74520db20002
         * name : 书记许守江
         * unitcode : 22d1a7248ee049de823704c4eb5e981b
         * username : leader1
         * password :
         * mobile : 15511111111
         * idcard :
         * email :
         * birthday : null
         * address :
         * usericon : null
         * department : 7ff5c57c55e3f2e3c167d914c20ae8dc
         * type : unit_type_admin_regulators
         * duty : 总指挥长
         * isdelete : 0
         * roles : null
         */

        private String id;
        private String name;
        private String unitcode;
        private String username;
        private String password;
        private String mobile;
        private String idcard;
        private String email;
        private Object birthday;
        private String address;
        private Object usericon;
        private String department;
        private String type;
        private String duty;
        private String isdelete;
        private Object roles;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnitcode() {
            return unitcode;
        }

        public void setUnitcode(String unitcode) {
            this.unitcode = unitcode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getUsericon() {
            return usericon;
        }

        public void setUsericon(Object usericon) {
            this.usericon = usericon;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
        }

        public String getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        public Object getRoles() {
            return roles;
        }

        public void setRoles(Object roles) {
            this.roles = roles;
        }
    }
}
