package com.example.new_project.Register;

import com.example.new_project.BaseMVP.baseModel;
import com.example.new_project.BaseMVP.basePresenter;
import com.loopj.android.http.RequestParams;

public class RegisterPresenter extends basePresenter<RegisterView> {

    public void Register(String url, RequestParams requestParams){
        baseModel.post(url, requestParams, new baseModel.IHttpRequestListener() {
            @Override
            public void IOnSuccess(String resouces) {
                baseView.Register(resouces);
            }

            @Override
            public void IOnFailure(String resouces) {
                baseView.Failure(resouces);
            }
        });
    }

}
