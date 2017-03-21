package com.apps.mobile.utn.adtd.geco;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageButton button_web_view_close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView browser = (WebView) findViewById(R.id.web_view);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setWebViewClient(new WebViewClient(){

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        button_web_view_close = (AppCompatImageButton) findViewById(R.id.button_web_view_close);
        button_web_view_close.setOnClickListener(this);

        String URL="";


        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        if(mBundle != null){
            URL = mBundle.getString("URL");
            browser.loadUrl(URL);
        }
        else
        {
            Toast.makeText(this,getString(R.string.error_loading_url),Toast.LENGTH_SHORT).show();
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_web_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }



    @Override
    public void onBackPressed() {

      finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_web_view_close:
                finish();
                break;
            default:
                break;
        }



    }
}
