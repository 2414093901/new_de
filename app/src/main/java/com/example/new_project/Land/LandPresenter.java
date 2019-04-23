package com.example.new_project.Land;
import com.example.new_project.BaseMVP.baseModel;
import com.example.new_project.BaseMVP.basePresenter;
import com.loopj.android.http.RequestParams;

public class LandPresenter extends basePresenter<LandView> {

    public void Land(String url, RequestParams requestParams){
        baseModel.get(url, requestParams, new baseModel.IHttpRequestListener() {
            @Override
            public void IOnSuccess(String resouces) {
                baseView.Land(resouces);
            }

            @Override
            public void IOnFailure(String resouces) {
                baseView.Failure(resouces);
            }
        });
    }
}
