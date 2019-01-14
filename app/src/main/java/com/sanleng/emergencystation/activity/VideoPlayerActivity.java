package com.sanleng.emergencystation.activity;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.adapter.VideoListAdapter;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * 视频
 */
public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "VideoPlayerActivity";
    private ListView mListView;
    private VideoListAdapter mAdapter;
    private SensorEventListener mSensorEventListener;
    private SensorManager mSensorManager;
    private RelativeLayout r_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        mListView = (ListView) findViewById(R.id.list_view);
        r_back = findViewById(R.id.r_back);
        r_back.setOnClickListener(this);
        mAdapter = new VideoListAdapter(this);
        mListView.setAdapter(mAdapter);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.r_back:
                finish();
                break;
        }
    }
}
