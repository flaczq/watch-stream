package com.flaq.apps.watchsteam.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.flaq.apps.watchsteam.R;

public class StreamActivity extends AppCompatActivity {

    private Intent intent;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        webView = (WebView) findViewById(R.id.webView);

        intent = getIntent();

        playStream();
    }

    private void playStream() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setSupportZoom(true);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Toast.makeText(getApplicationContext(), "Wait", Toast.LENGTH_LONG).show();
            }
        });

        String stream = getString(R.string.url_stream_1) + intent.getStringExtra("stream") + getString(R.string.url_stream_2);
        webView.loadUrl(stream);
    }

}
