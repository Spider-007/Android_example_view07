package com.example.android_example_view07;

//callBack HttpUrlConnection reponse
public interface HttpCallBackListener {

    void onFinish(String mReponse);

    void onError(String mException);

}
