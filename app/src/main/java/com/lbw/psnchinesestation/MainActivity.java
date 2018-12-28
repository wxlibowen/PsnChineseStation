package com.lbw.psnchinesestation;

import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.Objects;


/**
 * @author WXlib
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView web;
    private String url = "http://www.d7vg.com/";
    private long exitTime = 0;
    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout dl;
    private boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        webSetting();

    }


    private void initView() {
        String brand = android.os.Build.BRAND;
        flag = "Xiaomi".equals(brand);

        Objects.requireNonNull(getSupportActionBar()).hide();
        web = findViewById(R.id.web_view);
        dl = findViewById(R.id.drawer_layout);
        Button btnBaiDu = findViewById(R.id.btn_baidu);
        btnBaiDu.setOnClickListener(this);

        WebSettings webSettings = web.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setAppCacheEnabled(true);
        web.requestFocus();
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient());

        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(getColor(R.color.statue_bar));
        //下拉刷新监听
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                web.reload();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void webSetting() {
        web.loadUrl(url);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) {
                web.goBack();
            } else {
                int exitTime = 2000;
                if ((System.currentTimeMillis() - this.exitTime) > exitTime) {
                    Snackbar.make(web, "再次点击退出!", Snackbar.LENGTH_SHORT).show();
                    this.exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_baidu:
                if (flag) {
//                   url ="http://wap.luohua99.net/view/26218.html";
                    url = "https://www.baidu.com";
                } else {
                    url = "https://www.baidu.com";
                }
                webSetting();
                dl.closeDrawer(GravityCompat.START);
                break;
            default:
                break;

        }
    }


}
