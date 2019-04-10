package com.example.new_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.new_project.Utility.BaseAppCompatActivity;
import com.example.new_project.Utility.HttpClick;
import com.example.new_project.Utility.Http_url;
import com.example.new_project.base.msgbase;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingActivity extends BaseAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.dl_recyclerView)
    RecyclerView dlRecyclerView;
    @BindView(R.id.dl_swipeRefreshLayout)
    SwipeRefreshLayout dlSwipeRefreshLayout;
    RecyclerView_adapter RecyclerView_adapter;
    int Mypage = 1;
    ArrayList<msgbase.DataBean.ListBean> arrayList = new ArrayList<>();

    Intent intent;
    String phone;
    String photoPath;
    @BindView(R.id.dl_btn)
    Button dlBtn;
    @BindView(R.id.dl_btn2)
    Button dlBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_dl);
        ButterKnife.bind(this);
        intent = getIntent();
        phone = intent.getStringExtra("phone");
        photoPath = intent.getStringExtra("photoPath");
        Log.e("222", "phone:" + phone + "photoPath:" + photoPath);

        //SwipeRefreshLayout_滑动监听
        initView();
        initData();
        initLoading();
    }

    @OnClick({R.id.dl_btn, R.id.dl_btn2})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.dl_btn:
                //发布信息
                Intent intent = new Intent();
                intent.setClass(LandingActivity.this, MessageActivity.class);
                intent.putExtra("msgphone", phone);
                startActivity(intent);
//                finish();
                break;
            case R.id.dl_btn2:
                //发布广告
                Intent intent2 = new Intent();
                intent2.setClass(LandingActivity.this, AdverActivity.class);
                intent2.putExtra("msgphone", phone);
                startActivity(intent2);
//                finish();
                break;
        }
    }


    //布局管理器
    private void initView() {
        dlRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        2、将布局管理器设置进RecyclerView
        dlRecyclerView.setLayoutManager(linearLayoutManager);
        dlSwipeRefreshLayout.setOnRefreshListener(this);
        dlRecyclerView.addItemDecoration(new DividerItemDecoration(LandingActivity.this, DividerItemDecoration.VERTICAL));
    }

    //获取所有数据
    private void initData() {
        RequestParams requestParams = new RequestParams();//相当于Map
        requestParams.put("page", Mypage);
        Log.e("333", "Mypage:" + Mypage);
        requestParams.put("pageSize", 10);
        HttpClick.getInstance().get(Http_url.URL_selectmsg, requestParams, new HttpClick.IHttpRequestListener() {
            @Override
            public void IOnSuccess(String resouces) {
                Log.e("333", "IOnSuccess");
                msgbase msgbase1 = JSON.parseObject(resouces, msgbase.class);
                if (msgbase1.getData().getList() != null) {
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
                    RecyclerView_adapter = new RecyclerView_adapter(LandingActivity.this, arrayList, photoPath, phone);
                    dlRecyclerView.setAdapter(RecyclerView_adapter);
                }

            }

            @Override
            public void IOnFailure(String resouces) {
                Log.e("333", "IOnFailure" + resouces);
            }
        });
    }


    @Override
    protected int getChildLayoutId() {
        return R.layout.activity_dl;
    }

    @Override
    protected String getMyTitle() {
        return "登陆成功";
    }

    //刷新
    @Override
    public void onRefresh() {
        Mypage = 1;
        initData2();
        Toast.makeText(LandingActivity.this, "刷新", Toast.LENGTH_SHORT).show();
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    private void initLoading() {
        dlRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPositon = layoutManager.findLastVisibleItemPosition();//获取屏幕显示的最后一个Item
                int ItemCount = layoutManager.getItemCount();
                Log.e("111", "lastPositon: " + lastPositon);
                Log.e("111", "ItemCount: " + ItemCount);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == ItemCount - 1
                        && RecyclerView_adapter.isLoading() != 2) {
                    RecyclerView_adapter.setLoading(2);
                    Mypage++;
                    Log.e("333", "Mypage" + Mypage);
                    initData();
                    handler.sendEmptyMessageDelayed(2, 2000);
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
                        RecyclerView_adapter = new RecyclerView_adapter(LandingActivity.this, arrayList, photoPath, phone);
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


    //刷新获取所有数据
    private void initData2() {
        RequestParams requestParams = new RequestParams();//相当于Map
        requestParams.put("page", Mypage);
        Log.e("333", "Mypage:" + Mypage);
        requestParams.put("pageSize", 10);
        HttpClick.getInstance().get(Http_url.URL_selectmsg, requestParams, new HttpClick.IHttpRequestListener() {
            @Override
            public void IOnSuccess(String resouces) {
                Log.e("333", "IOnSuccess");
                msgbase msgbase1 = JSON.parseObject(resouces, msgbase.class);
                if (msgbase1.getData().getList() != null) {
                    arrayList.clear();
                    arrayList.addAll(msgbase1.getData().getList());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("333","刷新了!");
                            RecyclerView_adapter.setArrayList(arrayList);
                            RecyclerView_adapter.notifyDataSetChanged();
                        }
                    });
                }
//               else {
//                    RecyclerView_adapter = new RecyclerView_adapter(LandingActivity.this, arrayList, photoPath, phone);
//                    dlRecyclerView.setAdapter(RecyclerView_adapter);
//                }
            }

            @Override
            public void IOnFailure(String resouces) {
                Log.e("333", "IOnFailure" + resouces);
            }
        });
    }

}
