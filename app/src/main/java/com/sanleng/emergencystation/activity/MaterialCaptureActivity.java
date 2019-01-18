package com.sanleng.emergencystation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.dialog.OutofStockDialog;
import com.sanleng.emergencystation.dialog.ReportLossDialog;
import com.sanleng.emergencystation.dialog.WarehousingDialog;
import com.sanleng.emergencystation.net.NetCallBack;
import com.sanleng.emergencystation.net.RequestUtils;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.zxing.camera.CameraManager;
import com.sanleng.emergencystation.zxing.decoding.CaptureActivityHandler;
import com.sanleng.emergencystation.zxing.decoding.InactivityTimer;
import com.sanleng.emergencystation.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * 物质扫码管理
 *
 * @author qiaoshi
 */
@SuppressLint("HandlerLeak")
public class MaterialCaptureActivity extends AppCompatActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private String mode;

    private String ids = null;
    private String stationName = null;
    private String stationId = null;
    private String storageLocation = null;
    private String reason = null;
    private RelativeLayout r_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        r_back = (RelativeLayout) findViewById(R.id.r_back);
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        r_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MaterialCaptureActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
        } else {
            if (mode.equals("Warehousing")) {
                // 入库
                WarehousingDialog warehousingDialog = new WarehousingDialog(MaterialCaptureActivity.this,
                        resultString, mHandler);
                warehousingDialog.show();
            }

            if (mode.equals("OutOfStock")) {
                // 出库
                OutofStockDialog outofStockDialog = new OutofStockDialog(MaterialCaptureActivity.this, resultString,
                        mHandler);
                outofStockDialog.show();
            }
            if (mode.equals("Reportloss")) {
                // 报损
                ReportLossDialog reportLossDialog = new ReportLossDialog(MaterialCaptureActivity.this, resultString,
                        mHandler);
                reportLossDialog.show();
            }
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(MaterialCaptureActivity.this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    @SuppressLint("MissingPermission")
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private void continuePreview() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);
        if (handler != null) {
            handler.restartPreviewAndDecode();
        }
    }

    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                // 确认入库
                case 25267:
                    try {
                        Bundle bundle = message.getData();
                        stationId = bundle.getString("stationId");
                        stationName = bundle.getString("stationName");
                        storageLocation = bundle.getString("storageLocation");
                        ids = bundle.getString("ids");
                        Warehousing();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                // 取消入库
                case 25266:
                    try {
                        continuePreview();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                // 确认出库
                case 35267:
                    try {
                        Bundle bundle = message.getData();
                        stationId = bundle.getString("stationId");
                        stationName = bundle.getString("stationName");
                        storageLocation = bundle.getString("storageLocation");
                        ids = bundle.getString("ids");
                        OutOfStock();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                // 取消出库
                case 35266:
                    try {
                        continuePreview();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                // 确认报损
                case 35268:
                    try {
                        Bundle bundle = message.getData();
                        stationId = bundle.getString("stationId");
                        stationName = bundle.getString("stationName");
                        storageLocation = bundle.getString("storageLocation");
                        reason = bundle.getString("reason");
                        ids = bundle.getString("ids");
                        ReportLoss();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                // 取消报损
                case 35269:

                    try {
                        continuePreview();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    // 提交入库数据信息
    private void Warehousing() {
        RequestParams params = new RequestParams();
        params.put("ids", ids);
        params.put("agentName", PreferenceUtils.getString(MaterialCaptureActivity.this, "agentName"));
		params.put("agentId", PreferenceUtils.getString(MaterialCaptureActivity.this, "ids"));
        params.put("stationName", stationName);
        params.put("stationId", stationId);
        params.put("storageLocation", storageLocation);
        System.out.println("=======================aaaaaaaaaaaa"+storageLocation);
//        params.put("stationAddress", stationAddress);
		params.put("state", "emergencystation_in");

        params.put("username", PreferenceUtils.getString(MaterialCaptureActivity.this, "EmergencyStation_username"));
        params.put("platformkey", "app_firecontrol_owner");

        RequestUtils.ClientPost(URLs.Warehousing_URL, params, new NetCallBack() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null || result.length() == 0) {
                    return;
                }
                System.out.println("数据请求成功" + result);
                new SVProgressHUD(MaterialCaptureActivity.this).showSuccessWithStatus("入库成功");
                continuePreview();
            }

            @Override
            public void onMyFailure(Throwable arg0) {
                new SVProgressHUD(MaterialCaptureActivity.this).showErrorWithStatus("入库失败");
            }
        });

    }

    // 提交出库数据信息
    private void OutOfStock() {
        RequestParams params = new RequestParams();
        params.put("ids", ids);
        params.put("agentName", PreferenceUtils.getString(MaterialCaptureActivity.this, "agentName"));
        params.put("agentId", PreferenceUtils.getString(MaterialCaptureActivity.this, "ids"));
        params.put("stationName", stationName);
        params.put("stationId", stationId);
        params.put("storageLocation", storageLocation);
//        params.put("stationAddress", stationAddress);
			params.put("state", "emergencystation_out");

        params.put("username", PreferenceUtils.getString(MaterialCaptureActivity.this, "EmergencyStation_username"));
        params.put("platformkey", "app_firecontrol_owner");

        RequestUtils.ClientPost(URLs.Outofstock_URL, params, new NetCallBack() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null || result.length() == 0) {
                    return;
                }
                System.out.println("数据请求成功" + result);
                new SVProgressHUD(MaterialCaptureActivity.this).showSuccessWithStatus("出库成功");
                continuePreview();
            }

            @Override
            public void onMyFailure(Throwable arg0) {
                new SVProgressHUD(MaterialCaptureActivity.this).showErrorWithStatus("出库失败");
            }
        });

    }

    // 提交报损数据信息
    private void ReportLoss() {
        RequestParams params = new RequestParams();
        params.put("ids", ids);
        params.put("agentName", PreferenceUtils.getString(MaterialCaptureActivity.this, "agentName"));
        params.put("agentId", PreferenceUtils.getString(MaterialCaptureActivity.this, "ids"));
        params.put("stationName", stationName);
        params.put("stationId", stationId);
        params.put("storageLocation", storageLocation);
//        params.put("stationAddress", stationAddress);
			params.put("state", "emergencystation_break");
        params.put("reason", reason);

        params.put("username", PreferenceUtils.getString(MaterialCaptureActivity.this, "EmergencyStation_username"));
        params.put("platformkey", "app_firecontrol_owner");

        RequestUtils.ClientPost(URLs.Warehousing_URL, params, new NetCallBack() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null || result.length() == 0) {
                    return;
                }
                System.out.println("数据请求成功" + result);
                new SVProgressHUD(MaterialCaptureActivity.this).showSuccessWithStatus("报损成功");
                continuePreview();
            }

            @Override
            public void onMyFailure(Throwable arg0) {
                new SVProgressHUD(MaterialCaptureActivity.this).showErrorWithStatus("报损失败");
            }
        });

    }
}