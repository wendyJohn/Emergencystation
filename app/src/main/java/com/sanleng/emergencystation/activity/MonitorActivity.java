package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.view.FullVideoView;

/**
 * 视频监控
 */
public class MonitorActivity extends AppCompatActivity {
    private RelativeLayout r_back;
    private RelativeLayout ryout;
    private FullVideoView videoa;
    private FullVideoView videob;
    private boolean fullscreen;
    private int orientation;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoractivity);
        StatusBarUtil.setColor(MonitorActivity.this,R.color.translucency);
        r_back = findViewById(R.id.r_back);
        ryout= findViewById(R.id.ryout);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        videoa = findViewById(R.id.videoa);
        videob = findViewById(R.id.videob);
        setVideoa();
        setVideob();
    }

    /**
     * 设置视频参数
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setVideoa() {
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);//隐藏进度条
        videoa.setMediaController(mediaController);
        videoa.setVideoURI(Uri.parse("http://hls.open.ys7.com/openlive/ff814ef5eacf479c8ee045a24fbc21a7.m3u8"));
        videoa.start();
        videoa.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });

        videoa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setOratation("VIDEO_A");
                return false;
            }
        });
    }


    /**
     * 保存 旋转 之前的 数据
     *
     * @param outState
     */
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        int sec = outState.getInt("time");
        videoa.seekTo(sec);
        super.onRestoreInstanceState(outState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int sec = videoa.getCurrentPosition();
        outState.putInt("time", sec);
        super.onSaveInstanceState(outState);
    }


    /**
     * 横竖屏
     */
    public void setOratation(String str) {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if (width > height) {
            ryout.setVisibility(View.VISIBLE);
            orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;  //竖屏
            if (str.equals("VIDEO_A")) {
                //待调试
                setVideob();
                videob.setVisibility(View.VISIBLE);
            }
            if (str.equals("VIDEO_B")) {
                //待调试
                setVideoa();
                videoa.setVisibility(View.VISIBLE);
            }
        } else {
            ryout.setVisibility(View.GONE);
            orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横屏
            if (str.equals("VIDEO_A")) {
                videob.setVisibility(View.GONE);
            }
            if (str.equals("VIDEO_B")) {
                videoa.setVisibility(View.GONE);
            }
        }
        this.setRequestedOrientation(orientation);
    }

    /**
     * 设置视频参数
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setVideob() {
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);//隐藏进度条
        videob.setMediaController(mediaController);
        videob.setVideoURI(Uri.parse("http://hls.open.ys7.com/openlive/0914d875ae71473ca7dad4edd27690e8.m3u8"));
        videob.start();
        videob.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });

        videob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setOratation("VIDEO_B");
                return false;
            }
        });
    }
}