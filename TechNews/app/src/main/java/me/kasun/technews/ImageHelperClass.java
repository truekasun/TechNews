package me.kasun.technews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;

/**
 * Created by kasun on 2016-05-29.
 */
public class ImageHelperClass extends AppCompatActivity {
    public static void loadPhoto(Context context, String url) {

        WebView mWebView = new WebView(context);
        mWebView.setLayoutParams(new RelativeLayout.LayoutParams(android.widget.TableRow.LayoutParams.MATCH_PARENT, android.widget.TableRow.LayoutParams.MATCH_PARENT));
        mWebView.loadUrl(url);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setBackgroundColor(Color.TRANSPARENT);

        Dialog builder = new Dialog(context);
        builder.setCancelable(true);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
        builder.addContentView(mWebView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }
}
