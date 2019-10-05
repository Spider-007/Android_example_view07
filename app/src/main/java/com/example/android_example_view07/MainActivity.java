package com.example.android_example_view07;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSentRequestButton, mOpenWebViewButton;
    private TextView mTextView;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mSentRequestButton = findViewById(R.id.sendRequestBtn);
        mOpenWebViewButton = findViewById(R.id.webViewBtn);
        mTextView = findViewById(R.id.mTv);
        mWebView = findViewById(R.id.mWebViewId);
        mSentRequestButton.setOnClickListener(this);
        mOpenWebViewButton.setOnClickListener(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendRequestBtn:
                //open the https://www.codecombat.cc/play
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.setWebViewClient(new WebViewClient());
                mWebView.loadUrl("https://www.codecombat.cc/play");
                break;
            case R.id.webViewBtn:
                //HttpsUrlConnection request step:

                break;
            default:
                throw new NullPointerException("Don't found the Id");
        }
    }
}
