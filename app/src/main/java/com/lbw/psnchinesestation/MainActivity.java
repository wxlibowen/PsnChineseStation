package com.lbw.psnchinesestation;

import android.media.PlaybackParams;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView web;//网页控件
    private String URL = "http://psnine.com/";//网址
    private long exitTime = 0;//退出计时器
    private SwipeRefreshLayout swipe_refresh;//下拉刷新
    private Button btn_bai_du;
    private DrawerLayout dl;
    private boolean flag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        webSetting();

    }



    private void initView() {
        String brand = android.os.Build.BRAND;
       if (brand.equals("Xiaomi")){
           flag=true;
       }else {
           flag=false;
       }

        getSupportActionBar().hide();//去掉ActionBar
        web = findViewById(R.id.web_view);
        dl=findViewById(R.id.drawer_layout);
        btn_bai_du =findViewById(R.id.btn_baidu);
        btn_bai_du.setOnClickListener(this);

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持JavaScript用于登录
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//用于播放视频
        webSettings.setAppCacheEnabled(true);//开启缓存
        web.requestFocus();//使页面获得焦点
        web.setWebChromeClient(new WebChromeClient());//使用webView打开网页
        web.setWebViewClient(new WebViewClient());    //使用webView打开网页

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
    private void webSetting() {
        web.loadUrl(URL);//加载网页
    }
    

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Snackbar.make(web,"再次点击退出!",Snackbar.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_baidu:
               if (flag){
                   URL="http://wap.luohua99.net/view/26218.html";
               }else {
                   URL="https://www.baidu.com";
               }
                webSetting();
                dl.closeDrawer(GravityCompat.START);
                break;


        }
    }


}
