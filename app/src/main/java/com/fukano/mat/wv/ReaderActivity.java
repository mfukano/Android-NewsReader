package com.fukano.mat.wv;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by matfukano on 5/13/15.
 */
public class ReaderActivity extends Activity {

    static final public String WEBPAGE_NOTHING = "about:blank";

    private String suspendUrl = null;

    WebView myWebView;
    String site;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        site = intent.getStringExtra("site");
        setContentView(R.layout.webview);
        myWebView = (WebView) findViewById(R.id.webView1);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(site);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(site)) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site,
            // so launch another Activity that handles URLs
            /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;*/
            view.loadUrl(url);
            return true;
        }
    }

    /*
     *  Changes back button press action -- if there's history, move back a page.
     *  If not, exit the actiity & return to main.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        myWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myWebView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}