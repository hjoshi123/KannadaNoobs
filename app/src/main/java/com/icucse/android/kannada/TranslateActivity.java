package com.icucse.android.kannada;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class TranslateActivity extends AppCompatActivity {
    private WebView webView;
    private static final String url = "https://translate.google.com/?sl=kn&q=%E0%B2%B8%E0%B3%81%E0%B2%B8%E0%B3%8D%E0%B2%B5%E0%B2%BE%E0%B2%97%E0%B2%A4#kn/en/%E0%B2%B8%E0%B3%81%E0%B2%B8%E0%B3%8D%E0%B2%B5%E0%B2%BE%E0%B2%97%E0%B2%A4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        if(hasInternetConnection()){
            webView = (WebView) findViewById(R.id.webview);

            webView.setWebViewClient(new MyWebViewClient());
            webView.loadUrl(url);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Connection not established",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private boolean hasInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info!=null && info.isConnected();
    }
}
