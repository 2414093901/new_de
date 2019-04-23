package com.example.new_project.Land;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.new_project.R;
import com.example.new_project.Adapter.RecyclerView_adapter;
import com.example.new_project.Utility.Http_url;
import com.example.new_project.Base.msgbase;
import com.example.new_project.BaseMVP.BaseAppCompatActivity;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.new_project.Adver.AdverActivity;
import com.example.new_project.Message.MessageActivity;

public class LandActivity extends BaseAppCompatActivity<LandView,LandPresenter> implements LandView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.dl_recyclerView)
    RecyclerView dlRecyclerView;
    @BindView(R.id.dl_swipeRefreshLayout)
    SwipeRefreshLayout dlSwipeRefreshLayout;
    com.example.new_project.Adapter.RecyclerView_adapter RecyclerView_adapter;
    int Mypage = 0;
    int pageSize = 10;
    ArrayList<msgbase.DataBean.ListBean> arrayList = new ArrayList<>();

    Intent intent;
    String phone;
    String photoPath;
    @BindView(R.id.dl_btn)
    Button dlBtn;
    @BindView(R.id.dl_btn2)
    Button dlBtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        intent = getIntent();
        phone = intent.getStringExtra("phone");
        photoPath = intent.getStringExtra("photoPath");
        Log.e("222", "phone:" + phone + "photoPath:" + photoPath);
        initData();
        initView();
        initLoading();
    }

    private void initData() {
        RequestParams requestParams = new RequestParams();//相当于Map
        requestParams.put("page", ++Mypage);
        requestParams.put("pageSize", pageSize);
        basepresenter.Land(Http_url.URL_selectmsg,requestParams);
    }

    //布局管理器
    private void initView() {
        dlRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        2、将布局管理器设置进RecyclerView
        dlRecyclerView.setLayoutManager(linearLayoutManager);
        dlSwipeRefreshLayout.setOnRefreshListener(this);
        dlRecyclerView.addItemDecoration(new DividerItemDecoration(LandActivity.this, DividerItemDecoration.VERTICAL));
    }


    @OnClick({R.id.dl_btn, R.id.dl_btn2})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.dl_btn:
                //发布信息
                Intent intent = new Intent();
                intent.setClass(LandActivity.this, MessageActivity.class);
                intent.putExtra("msgphone", phone);
                startActivity(intent);
//                finish();
                break;
            case R.id.dl_btn2:
                //发布广告
                Intent intent2 = new Intent();
                intent2.setClass(LandActivity.this, AdverActivity.class);
                intent2.putExtra("msgphone", phone);
                startActivity(intent2);
//                finish();
                break;
        }
    }

    @Override
    protected LandView getBindView() {
        return this;
    }

    @Override
    protected LandPresenter createPresenter() {
        return new LandPresenter();
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.activity_dl;
    }

    @Override
    protected String getMyTitle() {
        return "登陆成功";
    }

    @Override
    public void Land(String res) {
        msgbase msgbase1 = JSON.parseObject(res, msgbase.class);
        if (msgbase1.getData().getList() != null) {
            if (Mypage==1){
                arrayList.clear();
            }
            arrayList.addAll(msgbase1.getData().getList());
        } else {
            handler.sendEmptyMessageDelayed(3, 2000);
        }

        if (RecyclerView_adapter != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("333","刷新了!");
                    RecyclerView_adapter.setArrayList(arrayList);
                    RecyclerView_adapter.notifyDataSetChanged();
                }
            });
        } else {
            RecyclerView_adapter = new RecyclerView_adapter(LandActivity.this, arrayList, photoPath, phone);
            dlRecyclerView.setAdapter(RecyclerView_adapter);
        }
    }

    @Override
    public void Failure(String res) {
        Toast.makeText(LandActivity.this,"获取失败"+res,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        Mypage=0;
        initData();
        Toast.makeText(LandActivity.this, "刷新", Toast.LENGTH_SHORT).show();
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    //加載更多
    private void initLoading() {
        dlRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPositon = layoutManager.findLastVisibleItemPosition();//获取屏幕显示的最后一个Item
                int ItemCount = layoutManager.getItemCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == ItemCount - 1
                        && RecyclerView_adapter.isLoading() != 2) {
                    RecyclerView_adapter.setLoading(2);
                    initData();
                    handler.sendEmptyMessageDelayed(2, 1000);
                }
            }
        });
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (RecyclerView_adapter == null) {
                        RecyclerView_adapter = new RecyclerView_adapter(LandActivity.this, arrayList, photoPath, phone);
                        dlRecyclerView.setAdapter(RecyclerView_adapter);
                    } else {
                        RecyclerView_adapter.setArrayList(arrayList);
                        RecyclerView_adapter.notifyDataSetChanged();//刷新所有数据
                    }
                    dlSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 2:
                    RecyclerView_adapter.setLoading(1);
                    if (RecyclerView_adapter != null) {
                        //将数据源重新设置到adapter里面去
                        RecyclerView_adapter.setArrayList(arrayList);
                        RecyclerView_adapter.notifyDataSetChanged();//刷新所有数据
                    }
                    break;
                case 3:
                    RecyclerView_adapter.setLoading(3);
                    break;
            }
        }
    };


}
