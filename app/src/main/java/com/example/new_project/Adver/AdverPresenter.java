package com.example.new_project.Adver;
import com.example.new_project.BaseMVP.baseModel;
import com.example.new_project.BaseMVP.basePresenter;
import com.loopj.android.http.RequestParams;

public class AdverPresenter extends basePresenter<AdverView> {
    public void Adver(String url, RequestParams requestParams){
        baseModel.post(url, requestParams, new baseModel.IHttpRequestListener() {
            @Override
            public void IOnSuccess(String resouces) {
                baseView.Adver(resouces);
            }

            @Override
            public void IOnFailure(String resouces) {
                baseView.Failure(resouces);
            }
        });
    }
}
