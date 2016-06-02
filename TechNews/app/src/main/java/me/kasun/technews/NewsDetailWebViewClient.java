package me.kasun.technews;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by kasun on 2016-05-29.
 */
public class NewsDetailWebViewClient extends WebViewClient {
    private Context context;

    public NewsDetailWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url!=null) {
            ImageHelperClass.loadPhoto(context, url);
            return true;
        }
        return false;
    }
}
