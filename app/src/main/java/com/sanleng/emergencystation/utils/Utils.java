package com.sanleng.emergencystation.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by litonghui on 2018/5/11.
 */

public class Utils {

    public static int NetWork(Context context) {
        int State = -1;
        //获取网络工具类
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络工作状态
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            //获取网络类型
            int type = networkInfo.getType();
            //判断网络类型是否为WIFI
            if (type == ConnectivityManager.TYPE_WIFI) {
                return 1;
            }
            //判断网络类型是否为移动网络
            else if (type == ConnectivityManager.TYPE_MOBILE) {
                return 0;
            }
            //如果都不是返回-1
            else {
                return -1;
            }
        }
        return State;
    }

    //判断网络是否连接
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.i("NetWorkState", "Unavailabel");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.i("NetWorkState", "Availabel");
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("FuncTcpServer", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }


    public static String[] stuctZMAFreqCountry = new String[]{
            "915.75", "915.25",
            "903.25", "926.75",
            "926.25", "904.25",
            "927.25", "920.25",
            "919.25", "909.25",
            "918.75", "917.75",
            "905.25", "904.75",
            "925.25", "921.75",
            "914.75", "906.75",
            "913.75", "922.25",
            "911.25", "911.75",
            "903.75", "908.75",
            "905.75", "912.25",
            "906.25", "917.25",
            "914.25", "907.25",
            "918.25", "916.25",
            "910.25", "910.75",
            "907.75", "924.75",
            "909.75", "919.75",
            "916.75", "913.25",
            "923.75", "908.25",
            "925.75", "912.75",
            "924.25", "921.25",
            "920.75", "922.75",
            "902.75", "923.25",
    };

    public static String nowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 得到字符串方式的时间
     *
     * @param time
     * @return 2015-01-01 11:11:11
     */
    public static String getFormatTime(String time) {
        String times="";
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            times = time.substring(0,19).replace("T"," ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    // 两次点击按钮之间的点击间隔不能少于2000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    //隐藏手机号码
    public static String hidePhoneNum(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }

    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }
}
