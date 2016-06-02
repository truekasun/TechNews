package me.kasun.technews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

public class NewsDetailActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_news_detail);
        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");
        setTitle(title);
        mWebView = (WebView) findViewById(R.id.detail_web_view);
        mWebView.setWebViewClient(new NewsDetailWebViewClient(this));
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.loadUrl(url);
    }
}
