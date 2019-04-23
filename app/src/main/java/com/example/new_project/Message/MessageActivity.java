package com.example.new_project.Message;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.new_project.R;
import com.example.new_project.Utility.Http_url;
import com.example.new_project.Base.send_base;
import com.example.new_project.BaseMVP.BaseAppCompatActivity;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

public class MessageActivity extends BaseAppCompatActivity<MessageView,MessagePresenter> implements MessageView {
    @BindView(R.id.title1)
    EditText msgTitle1;
    @BindView(R.id.content1)
    EditText content1;
    @BindView(R.id.msg_img1)
    ImageView msgImg1;
    @BindView(R.id.msg_img2)
    ImageView msgImg2;
    @BindView(R.id.msg_img3)
    ImageView msgImg3;
    @BindView(R.id.msg_btn1)
    Button msgBtn1;

    File file;

//    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri

    String msgtitle1,msgcontent1,msgphone;
    RequestParams requestParams = new RequestParams() ;
    ArrayList<String> path = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitleName();
        Intent intent = getIntent();
        msgphone = intent.getStringExtra("msgphone");
        msgImg1.setVisibility(View.VISIBLE);
        msgImg2.setVisibility(View.VISIBLE);
        msgImg3.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.msg_img1,R.id.msg_img2,R.id.msg_img3,R.id.msg_btn1,R.id.title1,R.id.content1})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.msg_btn1:
                //点击发布
                msgtitle1 = msgTitle1.getText().toString();
                msgcontent1 = content1.getText().toString();
                if (msgtitle1 != null && msgcontent1 != null){
                    requestParams.put("mtitle",msgtitle1);
                    requestParams.put("mmsg",msgcontent1);
                    requestParams.put("uphone",msgphone);
                    basepresenter.Message(Http_url.URL_send,requestParams);
                }else {
                    Toast.makeText(MessageActivity.this, "标题或内容为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.msg_img1:
                //图片1
                msgImg2.setVisibility(View.VISIBLE);
                MultiImageSelector.create()
                        .showCamera(false) // 是否显示相机. 默认为显示
                        .count(3) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .multi() // 多选模式, 默认模式;
                        .origin(path) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(MessageActivity.this, 2);
                msgImg2.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            file = new File(photoPath);
            Picasso.with(MessageActivity.this).load(file).into(msgImg1);

        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            path.clear();
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            File[] file = new File[pathList.size()];
            for(int i=0;i<file.length;i++){
                Log.e("tag",pathList.get(i));
                file[i] = new File(pathList.get(i));
            }
            try {
                requestParams.put("mimg", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Picasso.with(MessageActivity.this).load(new File(pathList.get(0))).into(msgImg1);
            } catch (Exception e) {
                Picasso.with(MessageActivity.this).load(R.mipmap.xinlang).into(msgImg1);
            }
            path.addAll(pathList);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected MessageView getBindView() {
        return this;
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.activity_msg;
    }

    @Override
    protected String getMyTitle() {
        return "发布信息";
    }


    @Override
    public void Message(String res) {
        send_base send_base = JSON.parseObject(res, send_base.class);
        if (send_base.getCode() == 200) {
            Log.e("555", "成功");
            setResult(3);
            finish();
//            startActivity(new Intent().setClass(MessageActivity.this, LandActivity.class));
        } else {
            Log.e("tag",res);
            Toast.makeText(MessageActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void Failure(String res) {
        Toast.makeText(MessageActivity.this, "发布失败"+res, Toast.LENGTH_SHORT).show();
    }


}
