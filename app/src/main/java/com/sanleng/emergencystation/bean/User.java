package com.sanleng.emergencystation.bean;

public class User {
    /**
     * jwt : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzbCIsImF1dGgiOiIwMTcyYzAwMTg0YTQ0YmNmZTAwMzcyYzAwMGE4MDAwMiwwMTc2YjE5OTE1NzRkYThiNjYxNjc2NjA5YmZmMDAwNyIsIlVTRVJfSUQiOiIwMTZmNDVmMGY0MWYzZjg3NDE0MDZmNDA0MTYxMDAzOCIsIlVTRVJfSURFTlRJVFkiOiJ1bml0X3R5cGVfYWRtaW5fb3duZXIiLCJVU0VSX1VOSVRDT0RFIjoiMjJkMWE3MjQ4ZWUwNDlkZTgyMzcwNGM0ZWI1ZTk4MWIiLCJleHAiOjE2MTA1MjU4OTF9.sbQn714oSeUsrMT51K8XiaF3za3klgap8FQUpg2Xzk47gNAtjvXNsDsAYxX5uz80ZKN651PNlZD1iO5nW0PQDg
     * status : 0
     */
    private String jwt;
    private String status;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
