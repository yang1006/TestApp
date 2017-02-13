package yll.self.testapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by yll on 2016/6/8.
 */
public class WebviewTestActivity extends Activity{

    WebView webView;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_test);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Object() {
            //用于跳转用户登录页面事件。
            @JavascriptInterface
            public void toastMessage(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WebviewTestActivity.this, "这是本地的toast "+message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, "myObj");
        webView.loadUrl("file:///android_asset/111.html");
        Button btn_click_js = (Button) findViewById(R.id.btn_click_js);
        btn_click_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:funFromjs()");
            }
        });
    }


}
