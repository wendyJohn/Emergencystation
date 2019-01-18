package com.sanleng.emergencystation.data;
import com.sanleng.emergencystation.bean.Video;
import java.util.ArrayList;
import java.util.List;

public class VideoData {
    public static List<Video> getVideoListData() {
        List<Video> videoList = new ArrayList<>();
        videoList.add(new Video("应急宣传",
                98000,
                "http://10.101.80.113:8080/RootFile/Platform/20181122/1542854342919.jpg" ,
                "http://10.101.80.113:8080/RootFile/Platform/20181114/1542178640266.mp4"));

        videoList.add(new Video("应急宣传",
                413000,
                "http://10.101.80.113:8080/RootFile/Platform/20181122/1542854162848.jpg",
                "http://10.101.80.113:8080/RootFile/Platform/20181114/1542178640266.mp4"));

        videoList.add(new Video("应急宣传",
                439000,
                "http://10.101.80.113:8080/RootFile/Platform/20181122/1542853670308.jpg",
                "http://10.101.80.113:8080/RootFile/Platform/20181114/1542178640266.mp4"));
        return videoList;
    }
}
