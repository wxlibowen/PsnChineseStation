package com.lbw.psnchinesestation;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView web;//网页控件
    private String URL = "http://psnine.com/";//网址
    private long exitTime = 0;//退出计时器
    private SwipeRefreshLayout swipe_refresh;//下拉刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        getSupportActionBar().hide();//去掉ActionBar
        web = findViewById(R.id.web_view);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持JavaScript用于登录
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//用于播放视频
        webSettings.setAppCacheEnabled(true);//开启缓存
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//缓存策略
        web.requestFocus();//使页面获得焦点
        web.setWebChromeClient(new WebChromeClient());//使用webView打开网页
        web.setWebViewClient(new WebViewClient());    //使用webView打开网页
        web.loadUrl(URL);//加载网页
        swipe_refresh = findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeColors(getColor(R.color.statue_bar));
        //下拉刷新监听
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                web.reload();
                swipe_refresh.setRefreshing(false);//加载完毕去掉ProgressBar
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
        }
        return false;
    }
}
