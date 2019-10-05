package com.example.android_example_view07;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSentRequestButton, mOpenWebViewButton, mGetOkHttpBtn, mPostOkHttpBtn;
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
        mGetOkHttpBtn = findViewById(R.id.getOkHttpBtn);
        mPostOkHttpBtn = findViewById(R.id.postOkHttpBtn);
        mTextView = findViewById(R.id.mTv);
        mWebView = findViewById(R.id.mWebViewId);
        mSentRequestButton.setOnClickListener(this);
        mOpenWebViewButton.setOnClickListener(this);
        mGetOkHttpBtn.setOnClickListener(this);
        mPostOkHttpBtn.setOnClickListener(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendRequestBtn:
                //HttpsUrlConnection request step:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BufferedReader mBufferedReader = null;
                        HttpURLConnection mHttpURLConnection = null;
                        try {
                            URL mUrl = new URL("http://www.baidu.com");
                            mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();

                            //setting Url


                            //user get
//                            mHttpURLConnection.setRequestMethod("GET");
                            //----userPost
                            mHttpURLConnection.setRequestMethod("POST");
                            DataOutputStream mDataOutPutStream = new DataOutputStream(mHttpURLConnection.getOutputStream());
                            mDataOutPutStream.writeBytes("username = admin & password = 123456");

                            mHttpURLConnection.setConnectTimeout(60000);
                            mHttpURLConnection.setReadTimeout(60000);

                            //get InputStream
                            InputStream mInputStream = mHttpURLConnection.getInputStream();
                            mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
                            StringBuffer mStringBuffer = new StringBuffer();
                            String mString;
                            while ((mString = mBufferedReader.readLine()) != null) {
                                mStringBuffer.append(mString);
                            }
                            showReponse(mStringBuffer.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (mBufferedReader != null) {
                                    mBufferedReader.close();
                                }
                                if (mHttpURLConnection != null) {
                                    mHttpURLConnection.disconnect();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.webViewBtn:

                //open the https://www.codecombat.cc/play
                mWebView.setVisibility(View.VISIBLE);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.setWebViewClient(new WebViewClient());
                mWebView.loadUrl("http://www.baidu.com");

                break;
            case R.id.getOkHttpBtn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient mOkHttpClient = new OkHttpClient();
                        Request mRquest = new Request.Builder()
                                .url("http://www.baidu.com")
                                .build();
                        try {
                            Response mResponse = mOkHttpClient.newCall(mRquest).execute();
                            String mString = mResponse.body().string();
                            showReponse(mString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.postOkHttpBtn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient mOkHttpClientPost = new OkHttpClient();
                        RequestBody mRequestBody = new FormBody.Builder()
                                .add("username", "admin")
                                .add("password", "123456")
                                .build();
                        Request mRquestPost = new Request.Builder()
                                .url("http://www.baidu.com")
                                .post(mRequestBody)
                                .build();
                        try {
                            Response mPostResponse = mOkHttpClientPost.newCall(mRquestPost).execute();
                            showReponse(mPostResponse.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                break;
            default:
                throw new NullPointerException("Don't found the Id");
        }

    }

    private void showReponse(final String mString) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.setVisibility(View.GONE);
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText(mString);
            }
        });
    }
}
