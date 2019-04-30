package com.sanleng.emergencystation.zxing.activiry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.zxing.Result;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.dialog.OutofStockDialog;
import com.sanleng.emergencystation.dialog.ReportLossDialog;
import com.sanleng.emergencystation.dialog.TipsDialog;
import com.sanleng.emergencystation.dialog.WarehousingDialog;
import com.sanleng.emergencystation.net.NetCallBack;
import com.sanleng.emergencystation.net.RequestUtils;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.zxing.bean.ZxingConfig;
import com.sanleng.emergencystation.zxing.camera.CameraManager;
import com.sanleng.emergencystation.zxing.common.Constant;
import com.sanleng.emergencystation.zxing.decode.DecodeImgCallback;
import com.sanleng.emergencystation.zxing.decode.DecodeImgThread;
import com.sanleng.emergencystation.zxing.decode.ImageUtil;
import com.sanleng.emergencystation.zxing.view.ViewfinderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    public ZxingConfig config;
    private SurfaceView previewView;
    private ViewfinderView viewfinderView;
    private AppCompatImageView flashLightIv;
    private TextView flashLightTv;
    private AppCompatImageView backIv;
    private LinearLayoutCompat flashLightLayout;
    private LinearLayoutCompat bottomLayout;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private com.sanleng.emergencystation.zxing.activiry.BeepManager beepManager;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private SurfaceHolder surfaceHolder;
    private TextView text_title;
    private String  title;

    //类型
    private String mode;
    private String ids = null;
    private String stationName = null;
    private String stationId = null;
    private String storageLocation = null;
    private String reason = null;


    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }
        /*先获取配置信息*/
        try {
            config = (ZxingConfig) getIntent().getExtras().get(Constant.INTENT_ZXING_CONFIG);
        } catch (Exception e) {
        }
        if (config == null) {
            config = new ZxingConfig();
        }
        setContentView(R.layout.activity_captures);
        initView();
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        beepManager.setPlayBeep(config.isPlayBeep());
        beepManager.setVibrate(config.isShake());
    }


    private void initView() {
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        title= intent.getStringExtra("title");
        text_title = findViewById(R.id.text_title);
        text_title.setText(title);
        previewView = findViewById(R.id.preview_view);
        previewView.setOnClickListener(this);

        viewfinderView = findViewById(R.id.viewfinder_view);
        viewfinderView.setZxingConfig(config);


        backIv = findViewById(R.id.backIv);
        backIv.setOnClickListener(this);

        flashLightIv = findViewById(R.id.flashLightIv);
        flashLightTv = findViewById(R.id.flashLightTv);

        flashLightLayout = findViewById(R.id.flashLightLayout);
        flashLightLayout.setOnClickListener(this);
        bottomLayout = findViewById(R.id.bottomLayout);


        switchVisibility(bottomLayout, config.isShowbottomLayout());
        switchVisibility(flashLightLayout, config.isShowFlashLight());


        /*有闪光灯就显示手电筒按钮  否则不显示*/
        if (isSupportCameraLedFlash(getPackageManager())) {
            flashLightLayout.setVisibility(View.VISIBLE);
        } else {
            flashLightLayout.setVisibility(View.GONE);
        }

    }


    /**
     * @param pm
     * @return 是否有闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param flashState 切换闪光灯图片
     */
    public void switchFlashImg(int flashState) {

        if (flashState == Constant.FLASH_OPEN) {
            flashLightIv.setImageResource(R.drawable.ic_open);
            flashLightTv.setText("关闭闪光灯");
        } else {
            flashLightIv.setImageResource(R.drawable.ic_close);
            flashLightTv.setText("打开闪光灯");
        }

    }

    /**
     * @param rawResult 返回的扫描结果
     */
    public void handleDecode(Result rawResult) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        String resultString = rawResult.getText();
        if (resultString.equals("")) {
            Toast.makeText(CaptureActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
        } else {
            if (mode.equals("Warehousing")) {
                // 入库
                WarehousingDialog warehousingDialog = new WarehousingDialog(CaptureActivity.this,
                        resultString, mHandler);
                warehousingDialog.show();
            }

            if (mode.equals("OutOfStock")) {
                // 出库
                OutofStockDialog outofStockDialog = new OutofStockDialog(CaptureActivity.this, resultString,
                        mHandler);
                outofStockDialog.show();
            }
            if (mode.equals("Reportloss")) {
                // 报损
                ReportLossDialog reportLossDialog = new ReportLossDialog(CaptureActivity.this, resultString,
                        mHandler);
                reportLossDialog.show();
            }
        }


    }


    private void switchVisibility(View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication(), config);

        viewfinderView.setCameraManager(cameraManager);
        handler = null;

        surfaceHolder = previewView.getHolder();
        if (hasSurface) {

            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫一扫");
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void onPause() {

        Log.i("CaptureActivity", "onPause");
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();

        if (!hasSurface) {

            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
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

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.flashLightLayout) {
            /*切换闪光灯*/
            cameraManager.switchFlashLight(handler);
        } else if (id == R.id.backIv) {
            finish();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_IMAGE && resultCode == RESULT_OK) {
            String path = ImageUtil.getImageAbsolutePath(this, data.getData());

            new DecodeImgThread(path, new DecodeImgCallback() {
                @Override
                public void onImageDecodeSuccess(Result result) {
                    handleDecode(result);
                }

                @Override
                public void onImageDecodeFailed() {
                    Toast.makeText(CaptureActivity.this, "抱歉，解析失败,换个图片试试.", Toast.LENGTH_SHORT).show();
                }
            }).run();


        }
    }


    @SuppressLint("HandlerLeak")
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
                //继续
                case 22260:
                    try {
                        continuePreview();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                case 22261:
                    try {
                        finish();
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
            params.put("agentName", PreferenceUtils.getString(CaptureActivity.this, "agentName"));
            params.put("agentId", PreferenceUtils.getString(CaptureActivity.this, "ids"));
            params.put("stationName", stationName);
            params.put("stationId", stationId);
            params.put("storageLocation", storageLocation);
            params.put("state", "emergencystation_in");
            params.put("username", PreferenceUtils.getString(CaptureActivity.this, "EmergencyStation_username"));
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
                    try {
                        JSONObject jSONObject = new JSONObject(result);
                        String message = jSONObject.getString("state");
                        if (message.equals("ok")) {
                            TipsDialog tipsDialog = new TipsDialog(CaptureActivity.this, "入库成功,是否继续？", mHandler);
                            tipsDialog.show();
                        } else {
                            new SVProgressHUD(CaptureActivity.this).showErrorWithStatus("信息错误，入库失败");
                            continuePreview();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                new SVProgressHUD(CaptureActivity.this).showSuccessWithStatus("入库成功");
//                continuePreview();
                }

                @Override
                public void onMyFailure(Throwable arg0) {
                    new SVProgressHUD(CaptureActivity.this).showErrorWithStatus("入库失败");

                }
            });
    }

    // 提交出库数据信息
    private void OutOfStock() {
        if(stationName==null||"".equals(stationName)){
            new SVProgressHUD(CaptureActivity.this).showErrorWithStatus("信息错误，出库失败");
            continuePreview();
        }else {
            RequestParams params = new RequestParams();
            params.put("ids", ids);
            params.put("agentName", PreferenceUtils.getString(CaptureActivity.this, "agentName"));
            params.put("agentId", PreferenceUtils.getString(CaptureActivity.this, "ids"));
            params.put("stationName", stationName);
            params.put("stationId", stationId);
            params.put("storageLocation", storageLocation);
            params.put("state", "emergencystation_out");
            params.put("username", PreferenceUtils.getString(CaptureActivity.this, "EmergencyStation_username"));
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
                    try {
                        JSONObject jSONObject = new JSONObject(result);
                        String message = jSONObject.getString("msg");
                        if (message.equals("修改成功")) {
                            TipsDialog tipsDialog = new TipsDialog(CaptureActivity.this, "出库成功,是否继续？", mHandler);
                            tipsDialog.show();
                        } else {
                            new SVProgressHUD(CaptureActivity.this).showErrorWithStatus("信息错误，出库失败");
                            continuePreview();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onMyFailure(Throwable arg0) {
                    new SVProgressHUD(CaptureActivity.this).showErrorWithStatus("出库失败");
                }
            });
        }
    }

    // 提交报损数据信息
    private void ReportLoss() {
        if(stationName==null||"".equals(stationName)){
            new SVProgressHUD(CaptureActivity.this).showErrorWithStatus("信息错误，报损失败");
            continuePreview();
        }else {
            RequestParams params = new RequestParams();
            params.put("ids", ids);
            params.put("agentName", PreferenceUtils.getString(CaptureActivity.this, "agentName"));
            params.put("agentId", PreferenceUtils.getString(CaptureActivity.this, "ids"));
            params.put("stationName", stationName);
            params.put("stationId", stationId);
            params.put("storageLocation", storageLocation);
            params.put("state", "emergencystation_break");
            params.put("reason", reason);

            params.put("username", PreferenceUtils.getString(CaptureActivity.this, "EmergencyStation_username"));
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

                    try {
                        JSONObject jSONObject = new JSONObject(result);
                        String message = jSONObject.getString("msg");
                        if (message.equals("修改成功")) {
                            TipsDialog tipsDialog = new TipsDialog(CaptureActivity.this, "报损成功,是否继续？", mHandler);
                            tipsDialog.show();
                        } else {
                            new SVProgressHUD(CaptureActivity.this).showErrorWithStatus("信息错误，报损失败");
                            continuePreview();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                new SVProgressHUD(CaptureActivity.this).showSuccessWithStatus("报损成功");

                }

                @Override
                public void onMyFailure(Throwable arg0) {
                    new SVProgressHUD(CaptureActivity.this).showErrorWithStatus("报损失败");
                }
            });
        }
    }

    //重复扫码
    private void continuePreview() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);
        if (handler != null) {
            handler.restartPreviewAndDecode();
        }
    }
}
