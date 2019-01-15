package com.sanleng.emergencystation.net;

/**
 * @author qiaoshi
 */
public class URLs {

    //云平台地址
    //public static String HOST_IP = "47.100.192.169";
    //public static String HOST_PORT = "8080";

    //调试地址
    public static String HOST_IP = "10.101.80.113";
    public static String HOST_PORT = "8080";

    public static String HOST = "http://" + HOST_IP + ":" + HOST_PORT;
    // 登陆
    public static String BULOGIN_URL = HOST + "/kspf/app/user/login";
    // 文章视频
    public static String Article_URL = HOST + "/kspf/app/publicityedu/list?page=";
    // 文章详情
    public static String ArticleLtem_URL = HOST + "/kspf/app/publicityedu/info";
    // 附近一公里的应急站
    public static String NearbyEmergencyStation_URL = HOST + "/kspf/app/station/distance";
    // 应急站列表
    public static String EmergencyStation_URL = HOST + "/kspf/app/station/list";
    // 应急开锁
    public static String ORDER_BASE_URL = "http://10.101.208.157:8091/emergencystation";// Order消息发送,心跳包uri
    // 物资入库
    public static String Warehousing_URL = HOST + "/kspf/app/station/state";
    // 物资出库
    public static String Outofstock_URL = HOST + "/kspf/app/station/state";
    // 物资列表
    public static String Material_URL = HOST + "/kspf/app/station/materiallist";
    // 物资详情
    public static String MaterialDetails_URL = HOST + "/kspf/app/station/detail";
    // 密码修改
    public static String PasswordModification = HOST + "/kspf/app/user/appPassChange";

}
