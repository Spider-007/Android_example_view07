package com.example.android_example_view07;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSentRequestButton, mOpenWebViewButton, mGetOkHttpBtn, mPostOkHttpBtn;
    private Button mPullButton, mSaxButton, mJsonObjectButton, mGSONButton;
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
        mSentRequestButton.setOnClickListener(this);
        mOpenWebViewButton.setOnClickListener(this);
        mGetOkHttpBtn.setOnClickListener(this);
        mPostOkHttpBtn.setOnClickListener(this);
        mTextView = findViewById(R.id.mTv);
        mWebView = findViewById(R.id.mWebViewId);

        //parse
        mPullButton = findViewById(R.id.pullBtn);
        mSaxButton = findViewById(R.id.saxBtn);
        mJsonObjectButton = findViewById(R.id.jsonObjectBtn);
        mGSONButton = findViewById(R.id.GSONBtn);
        mPullButton.setOnClickListener(this);
        mSaxButton.setOnClickListener(this);
        mJsonObjectButton.setOnClickListener(this);
        mGSONButton.setOnClickListener(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendRequestBtn:
                //HttpsUrlConnection request step:
                String mAddress = "http://10.0.2.2/get_data.json";
                HttpUtils.handlerOkHttpResonse(mAddress, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("YJH", "onFailure: " + e
                                .getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        showReponse(response.body().string());
                    }
                });
              /*  HttpUtils.handleHttpUrlConnectionRquest(mAddress, new HttpCallBackListener() {
                    @Override
                    public void onFinish(String mReponse) {
                        showReponse(mReponse);
                    }

                    @Override
                    public void onError(String mException) {
                        Log.e("YJH", "onFailure: " + mException);
                    }
                });*/
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
            case R.id.pullBtn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient mOkHttpClientPost = new OkHttpClient();
                        Request mRquestPost = new Request.Builder()
                                .url("http://10.0.2.2/get_data.xml")
                                .build();
                        try {
                            Response mPostResponse = mOkHttpClientPost.newCall(mRquestPost).execute();
                            String toString = mPostResponse.body().string();
                            pullParse(toString);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                break;
            case R.id.saxBtn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient mOkHttpClient = new OkHttpClient();
                        Request mRequest = new Request.Builder()
                                .url("http://10.0.2.2/get_data.xml")
                                .build();
                        try {
                            Response response = mOkHttpClient.newCall(mRequest).execute();
                            String string = response.body().string();
                            saxParse(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                break;
            case R.id.jsonObjectBtn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient mOkHttpClient = new OkHttpClient();
                        Request mRequest = new Request.Builder().url("http://10.0.2.2/get_data.json").build();
                        try {
                            Response response = mOkHttpClient.newCall(mRequest).execute();
                            String string = response.body().string();
                            parseJSONWithJsonObject(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.GSONBtn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient mOkHttpClient = new OkHttpClient();
                        Request mRquest = new Request.Builder()
                                .url("http://10.0.2.2/get_data.json").build();
                        try {
                            Response mResponse = mOkHttpClient.newCall(mRquest).execute();
                            Gson mGson = new Gson();
                            List<Book> books = mGson.fromJson(mResponse.body().string(), new TypeToken<List<Book>>() {
                            }.getType());
                            for (int i = 0; i < books.size(); i++) {
                                Log.e("SpiderLine", "run->id :" + books.get(i).getId());
                                Log.e("SpiderLine", "run: name:" + books.get(i).getName());
                                Log.e("SpiderLine", "run: version:" + books.get(i).getVersion());
                            }
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

    private void pullParse(String mString) {
        try {
            XmlPullParserFactory mXmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser mXmlPullParser = mXmlPullParserFactory.newPullParser();
            mXmlPullParser.setInput(new StringReader(mString));
            int eventType = mXmlPullParser.getEventType();
            String id = null;
            String name = null;
            String version = null;
            //Warning ->while
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = mXmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = mXmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = mXmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = mXmlPullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Log.e("Spiderline", "pullParse:->id-: " + id);
                            Log.e("Spiderline", "pullParse:->name" + name);
                            Log.e("Spiderline", "pullParse:->version" + version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = mXmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //sax Parse
    private void saxParse(String mString) {
        SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();
        try {
            XMLReader mXmlreader = mSAXParserFactory.newSAXParser().getXMLReader();
            SaxParseHandler mSaxParseHandler = new SaxParseHandler();
            mXmlreader.setContentHandler(mSaxParseHandler);
            mXmlreader.parse(new InputSource(new StringReader(mString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //parse Json with JSONObject
    private void parseJSONWithJsonObject(String mString) {
        try {
            JSONArray mJSONArray = new JSONArray(mString);
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject object = mJSONArray.getJSONObject(i);
                String id = object.getString("id");
                String name = object.getString("name");
                String version = object.getString("version");
                Log.e("SpiderLine", "parseJSONWithJsonObject: ->id:" + id);
                Log.e("SpiderLine", "parseJSONWithJsonObject: ->name:" + name);
                Log.e("SpiderLine", "parseJSONWithJsonObject: ->version:" + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
