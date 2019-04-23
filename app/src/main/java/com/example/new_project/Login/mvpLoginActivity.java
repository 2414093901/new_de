package com.example.new_project.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.new_project.R;
import com.example.new_project.Utility.Http_url;
import com.example.new_project.Base.Login_base;
import com.example.new_project.BaseMVP.BaseAppCompatActivity;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.new_project.Land.LandActivity;
import com.example.new_project.Register.RegisterActivity;

public class mvpLoginActivity extends BaseAppCompatActivity<LoginView,LoginPresenter> implements LoginView{
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.main_sjhtx)
    ImageView mainSjhtx;
    @BindView(R.id.main_sjh)
    EditText mainSjh;
    @BindView(R.id.main_mmtx)
    ImageView mainMmtx;
    @BindView(R.id.main_mm)
    EditText mainMm;
    @BindView(R.id.main_dl)
    Button mainDl;
    @BindView(R.id.main_wjmm)
    Button mainWjmm;
    @BindView(R.id.main_zc)
    Button mainZc;

    private String sjh;
    private String mima;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected LoginView getBindView() {
        return this;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @OnClick({R.id.main_wjmm,R.id.main_zc,R.id.main_dl})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.main_dl:
                //登陆
                sjh =  mainSjh.getText().toString();
                mima =  mainMm.getText().toString();
                if (sjh.length()<6 || mima.length()<6){
                    Toast.makeText(mvpLoginActivity.this,"账号或密码少于6位",Toast.LENGTH_SHORT).show();
                    mainMm.setText("");
                }else {
                    RequestParams requestParams = new RequestParams();//相当于Map
                    requestParams.put("uphone",sjh);
                    requestParams.put("upassword",mima);
                    basepresenter.getLogin(Http_url.URL_log ,requestParams);
                }
                break;
            case R.id.main_zc:
                //注册
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected int getChildLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getMyTitle() {
        return "登陆";
    }



    @Override
    public void Login( String res) {
        Login_base login_base = JSON.parseObject(res, Login_base.class);
        if (login_base.getCode()==200) {
            String photoPath = login_base.getData().getUimg();
            String phone = login_base.getData().getUphone();
            Intent intent = new Intent();
            intent.putExtra("photoPath",photoPath);
            intent.putExtra("phone",phone);
            intent.setClass(mvpLoginActivity.this, LandActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(mvpLoginActivity.this,"登陆失败"+res,Toast.LENGTH_SHORT).show();
        }
        Log.e("mvp",TAG+":"+res+login_base);
    }

    @Override
    public void Failure(String res) {
        Toast.makeText(mvpLoginActivity.this,"登陆失败"+res,Toast.LENGTH_SHORT).show();
    }



}


