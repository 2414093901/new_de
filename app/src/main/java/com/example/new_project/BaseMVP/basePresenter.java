package com.example.new_project.BaseMVP;

public class basePresenter<V> {
    protected V baseView;
    protected com.example.new_project.BaseMVP.baseModel baseModel;

    public basePresenter(){
        baseModel = new baseModel();
    }

    public void getBind(V baseView){
        this.baseView = baseView;
    }

    public void setNoBind(){
        baseView = null;
    }
}
