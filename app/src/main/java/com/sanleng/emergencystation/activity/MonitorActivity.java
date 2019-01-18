package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.view.FullVideoView;

/**
 * 视频监控
 */
public class MonitorActivity extends AppCompatActivity {
    private RelativeLayout r_back;
    private FullVideoView videoa;
    private FullVideoView videob;
    private boolean fullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoractivity);
        r_back = findViewById(R.id.r_back);
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
    }

    /**
     * 设置视频参数
     */
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
    }
}
