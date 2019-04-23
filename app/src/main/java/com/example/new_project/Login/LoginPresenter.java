package com.example.new_project.Login;

import android.util.Log;
import com.example.new_project.BaseMVP.baseModel;
import com.example.new_project.BaseMVP.basePresenter;
import com.loopj.android.http.RequestParams;

public class LoginPresenter extends basePresenter<LoginView> {

    private String TAG = getClass().getSimpleName();

    public void getLogin(String url,RequestParams requestParams){
        baseModel.get(url ,requestParams, new baseModel.IHttpRequestListener() {
            @Override
            public void IOnSuccess(String resouces) {
                baseView.Login(resouces);
                Log.e("mvp",TAG+resouces);
            }

            @Override
            public void IOnFailure(String resouces) {
                baseView.Failure(resouces);
            }
        });
    }


}
