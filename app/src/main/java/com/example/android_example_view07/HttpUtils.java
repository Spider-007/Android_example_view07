package com.example.android_example_view07;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

//httpUrlConnection and OkHttp utils
public class HttpUtils {

    //httpUrlConnection
    public static void handleHttpUrlConnectionRquest(final String mAddress, final HttpCallBackListener mHttpCallBackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection mHttpURLConnection = null;
                try {
                    URL mURL = new URL(mAddress);
                    mHttpURLConnection = (HttpURLConnection) mURL.openConnection();

                    mHttpURLConnection.setRequestMethod("GET");
                    mHttpURLConnection.setConnectTimeout(8000);
                    mHttpURLConnection.setReadTimeout(8000);
                    mHttpURLConnection.setDoInput(true);
                    mHttpURLConnection.setDoOutput(true);

                    InputStream mInputStream = mHttpURLConnection.getInputStream();
                    BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
                    StringBuilder mStringBuilder = new StringBuilder();
                    String line;
                    while ((line = mBufferedReader.readLine()) != null) {
                        mStringBuilder.append(line);
                    }
                    if (mHttpCallBackListener != null)
                        mHttpCallBackListener.onFinish(mStringBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    if (mHttpCallBackListener != null)
                        mHttpCallBackListener.onError(e.getMessage());
                } finally {
                    if (mHttpURLConnection != null) {
                        mHttpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }


    //httpUrlConnection
    public static String handleHttpUrlConnectionResponse(String mAddress) {
        HttpURLConnection mHttpURLConnection = null;
        BufferedReader mBufferedReader = null;
        try {
            StringBuffer mStringBuffer = null;
            URL mURL = new URL(mAddress);
            mHttpURLConnection = (HttpURLConnection) mURL.openConnection();

            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection.setReadTimeout(6000);
            mHttpURLConnection.setConnectTimeout(6000);
            mHttpURLConnection.setDoInput(true);
            mHttpURLConnection.setDoOutput(true);

            InputStream mInputStream = mHttpURLConnection.getInputStream();
            mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
            mStringBuffer = new StringBuffer();
            String request;
            while ((request = mBufferedReader.readLine()) != null) {
                mStringBuffer.append(request);
            }
            return mStringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (mBufferedReader != null) {
                try {
                    mBufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mHttpURLConnection != null) {
                mHttpURLConnection.disconnect();
            }
        }
    }

    //okHttp
    public static void handlerOkHttpResonse(String mString, Callback callback) {
        OkHttpClient mOkhttp = new OkHttpClient();
        Request mRquest = new Request.Builder()
                .url(mString)
                .build();
        //Internally open a child thread to process the request
        mOkhttp.newCall(mRquest).enqueue(callback);
    }
}
