package com.sanleng.emergencystation.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
//    private WebView webView;
//    /** 视频全屏参数 */
//    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//    private View customView;
//    private FrameLayout fullscreenContainer;
//    private WebChromeClient.CustomViewCallback customViewCallback;
        FullVideoView video;
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


//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//        webView = findViewById(R.id.webview);
//        initWebView();
        video= findViewById(R.id.video);
        setVideo();
    }


    /**
     * 设置视频参数
     */
    private void setVideo() {
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);//隐藏进度条
        video.setMediaController(mediaController);
        video.setVideoURI(Uri.parse("http://hls.open.ys7.com/openlive/ff814ef5eacf479c8ee045a24fbc21a7.m3u8"));
        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
    }


    /** 展示网页界面 **/
//    public void initWebView() {
//        WebChromeClient wvcc = new WebChromeClient();
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true); // 关键点
//        webSettings.setAllowFileAccess(true); // 允许访问文件
//        webSettings.setSupportZoom(true); // 支持缩放
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
//        webView.setWebChromeClient(wvcc);
//        WebViewClient wvc = new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webView.loadUrl(url);
//                return true;
//            }
//        };
//        webView.setWebViewClient(wvc);
//        webView.setWebChromeClient(new WebChromeClient() {
//            /*** 视频播放相关的方法 **/
//            @Override
//            public View getVideoLoadingProgressView() {
//                FrameLayout frameLayout = new FrameLayout(MonitorActivity.this);
//                frameLayout.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
//                return frameLayout;
//            }
//            @Override
//            public void onShowCustomView(View view, CustomViewCallback callback) {
//                showCustomView(view, callback);
//            }
//            @Override
//            public void onHideCustomView() {
//                hideCustomView();
//            }
//        });
//        // 加载Web地址
//        webView.loadUrl("http://hls.open.ys7.com/openlive/ff814ef5eacf479c8ee045a24fbc21a7.hd.m3u8");
//    }

    /**
     * 视频播放全屏
     **/
//    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
//        // if a view already exists then immediately terminate the new one
//        if (customView != null) {
//            callback.onCustomViewHidden();
//            return;
//        }
//
//        MonitorActivity.this.getWindow().getDecorView();
//        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
//        fullscreenContainer = new FullscreenHolder(MonitorActivity.this);
//        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
//        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
//        customView = view;
//        setStatusBarVisibility(false);
//        customViewCallback = callback;
//    }

    /**
     * 隐藏视频全屏
     */
//    private void hideCustomView() {
//        if (customView == null) {
//            return;
//        }
//
//        setStatusBarVisibility(true);
//        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
//        decor.removeView(fullscreenContainer);
//        fullscreenContainer = null;
//        customView = null;
//        customViewCallback.onCustomViewHidden();
//        webView.setVisibility(View.VISIBLE);
//    }



    /**
     * 全屏容器界面
     */
//    static class FullscreenHolder extends FrameLayout {
//
//        public FullscreenHolder(Context ctx) {
//            super(ctx);
//            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
//        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent evt) {
//            return true;
//        }
//    }
//
//    private void setStatusBarVisibility(boolean visible) {
//        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
//                if (customView != null) {
//                    hideCustomView();
//                } else if (webView.canGoBack()) {
//                    webView.goBack();
//                } else {
//                    finish();
//                }
//                return true;
//            default:
//                return super.onKeyUp(keyCode, event);
//        }
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        webView.reload();
//    }
}
