package com.example.new_project.BaseMVP;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new_project.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ZXY on 2019/3/15 10:23.
 * Class functions
 * *********************************************************
 * *    所有activity的父类
 * *********************************************************
 */

public abstract class BaseAppCompatActivity<V extends baseView,P extends basePresenter> extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    TextView tv_titleName;
    LinearLayout ll_titleChild;
    private ImageView iv_back;

    protected V baseview;
    protected P basepresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        initParsonView();//初始化title布局
        setChildLayoutId();//引入子activity的布局
        basepresenter = createPresenter();
        baseview = getBindView();
        //绑定View
        basepresenter.getBind(baseview);
    }

    protected abstract V getBindView();
    protected abstract P createPresenter();
    /**
     * 将子acitivy的布局xml加入到titleScroll中
     */
    protected void setChildLayoutId() {
        //引入子activity的布局
        View view = LayoutInflater.from(this).inflate(getChildLayoutId(), null);
LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        ll_titleChild.addView(view, 0);
        setTitleName();
    }

    /**
     * 抽象接口获取子activity的布局
     *
     * @return
     */
    protected abstract int getChildLayoutId();
    protected abstract String getMyTitle();
    /**
     * 初始化控件
     */
    protected void initParsonView() {
        tv_titleName = findViewById(R.id.title_name);
        ll_titleChild = findViewById(R.id.title_scroll);
        iv_back = findViewById(R.id.title_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置标题
     *
     * @param
     */
    public void setTitleName() {
        tv_titleName.setText(getMyTitle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑View
        basepresenter.setNoBind();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }
    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }

}
