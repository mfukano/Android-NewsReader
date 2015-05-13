package com.fukano.mat.wv;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by matfukano on 5/13/15.
 */
public class ReaderActivity extends Activity {

    WebView myWebView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView myWebView = (WebView) findViewById(R.id.webView1);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
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

        Method pause = null;
        // Resumes the webview.
        try {
            pause = WebView.class.getMethod("onPause");
        } catch (SecurityException e) {
            // Nothing
        } catch (NoSuchMethodException e) {
            // Nothing
        } if (pause != null) {
            try {
                pause.invoke(myWebView);
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e) {
            }
        } else {
            // No such method.  Stores the current URL.
            suspendUrl = myWebView.getUrl();
            // And loads a URL without any processing.
            myWebView.loadUrl(WEBPAGE_NOTHING);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        Method resume = null;
        // Resumes the webview.
        try {
            resume = WebView.class.getMethod("onResume");
        } catch (SecurityException e) {
            // Nothing
        } catch (NoSuchMethodException e) {
            // Nothing
        } if (resume != null) {
            try {
                resume.invoke(myWebView);
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e) {
            }
        } else {
            // No such method.  Restores the suspended URL.
            if (suspendUrl == null) {
                myWebView.loadUrl(WEBVIEW_HOME);
            } else {
                myWebView.loadUrl(suspendUrl);
            }
        }
    }

}