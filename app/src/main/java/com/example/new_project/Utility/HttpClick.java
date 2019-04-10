package com.example.new_project.Utility;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXY on 2019/4/1 9:50.
 * Class functions
 * *********************************************************
 * *
 * *********************************************************
 */

public class HttpClick {

    private HttpClick(){
    }
    public static HttpClick  getInstance(){
        HttpClick httpClick = new HttpClick();
        return httpClick;
    }
    //调用网路请求
    public void  get(String url, RequestParams requestParams,final IHttpRequestListener iHttpRequestListener) {
        //1\创建对象
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        //2、创建参数
        asyncHttpClient.get(url, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                iHttpRequestListener.IOnFailure(responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                iHttpRequestListener.IOnSuccess(responseString);
            }
        });

    }

    //调用网路请求
    public void  post(String url, RequestParams requestParams,final IHttpRequestListener iHttpRequestListener) {
        //1\创建对象
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        //2、创建参数
        asyncHttpClient.post(url, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                iHttpRequestListener.IOnFailure(responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                iHttpRequestListener.IOnSuccess(responseString);
            }
        });

    }

    public interface IHttpRequestListener{
        void IOnSuccess(String resouces);//成功
        void IOnFailure(String resouces);//失败
    }

}
