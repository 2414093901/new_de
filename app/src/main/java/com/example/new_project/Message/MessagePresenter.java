package com.example.new_project.Message;
import com.example.new_project.BaseMVP.baseModel;
import com.example.new_project.BaseMVP.basePresenter;
import com.loopj.android.http.RequestParams;

public class MessagePresenter extends basePresenter<MessageView> {

    public void Message(String url, RequestParams requestParams){
        baseModel.post(url, requestParams, new baseModel.IHttpRequestListener() {
            @Override
            public void IOnSuccess(String resouces) {
                baseView.Message(resouces);
            }

            @Override
            public void IOnFailure(String resouces) {
                baseView.Failure(resouces);
            }
        });
    }
}
