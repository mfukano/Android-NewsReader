package com.fukano.mat.wv;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by matfukano on 5/13/15.
 */
public class ReaderActivity extends Activity {

    WebView myWebView;  // public webView for access across methods
    String site;        // variable holder for site string

/*
 * Passes info by using intents, sets up web settings,
 * and applies settings on webview all upon activity creation.
 */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        site = intent.getStringExtra("site");
        setContentView(R.layout.webview);
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        try {
            // load the url
            myWebView.loadUrl(site);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Webview client that detects whether the user is remaining on the
     * page or visiting a new page, at which point it'll open a browser.
     * There's a special exception case for The Guardian, which was a
     * particular use case based on the URL.
     */
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(site)) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            else if(Uri.parse(url).getHost().equals("www.theguardian.com")){
                // Use case for The Guardian
                return false;
            }
            // Otherwise, the link is not for a page on my site,
            // so launch another Activity that handles URLs
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        }
    }

    // Share button logic
    public void sharePage(View v) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, myWebView.getUrl());
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, "Share link to..."));
    }

    /*
     *  Changes back button press action -- if there's history, move back a page.
     *  If not, exit the activity & return to main.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(myWebView != null){
            // Check if the key event was the Back button and if there's history
            if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
                myWebView.goBack();
                return true;
            }
            // If it wasn't the Back key or there's no web page history, bubble up to the default
            // system behavior (probably exit the activity)
        }
        else{
            Log.e(MainActivity.class.getSimpleName(), "myWebView is null : won't do anything");
        }
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