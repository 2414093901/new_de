package com.example.new_project.Utility;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.new_project.R;

/**
 * Created by ZXY on 2019/3/15 10:23.
 * Class functions
 * *********************************************************
 * *    所有activity的父类
 * *********************************************************
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    TextView tv_titleName;
    LinearLayout ll_titleChild;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        initParsonView();//初始化title布局
        setChildLayoutId();//引入子activity的布局
    }

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
}
