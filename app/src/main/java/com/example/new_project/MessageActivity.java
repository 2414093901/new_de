package com.example.new_project;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.new_project.Utility.HttpClick;
import com.example.new_project.Utility.Http_url;
import com.example.new_project.Utility.img_compress;
import com.example.new_project.base.send_base;
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
import pub.devrel.easypermissions.EasyPermissions;

public class MessageActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

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
    private LinearLayout ly_root;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri

    String msgtitle1,msgcontent1,msgphone;
    RequestParams requestParams = new RequestParams() ;
    ArrayList<String> path = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);
//        setTitleName();
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
                    HttpClick.getInstance().post(Http_url.URL_send, requestParams, new HttpClick.IHttpRequestListener() {
                        @Override
                        public void IOnSuccess(String resouces) {
                            send_base send_base = JSON.parseObject(resouces, send_base.class);
                            if (send_base.getCode() == 200){
                                Log.e("555","成功");
                                setResult(3);
                                finish();
//                                startActivity(new Intent().setClass(MessageActivity.this,LandingActivity.class));
                            }else {
                                Log.e("555","发布失败"+resouces);
                            }
                        }

                        @Override
                        public void IOnFailure(String resouces) {
                            Log.e("555","发布失败"+resouces);
                        }
                    });
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
            case R.id.msg_img2:
                //图片2
                break;
            case R.id.msg_img3:
                //图片3
                break;
        }
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

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }


    //激活相机操作
    private void goCamera() {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileprovider
            uri = FileProvider.getUriForFile(MessageActivity.this, "com.example.new_project.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        MessageActivity.this.startActivityForResult(intent, 1);
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
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
            Log.d("拍照返回图片路径:", photoPath);
                file = new File(photoPath);
            Log.d("debug","file size: "+file.length());

            Picasso.with(MessageActivity.this).load(file).into(msgImg1);

            Bitmap bmp = BitmapFactory.decodeFile(photoPath);
            img_compress.compressImageToFile(bmp,file,20);
            Log.d("debug","file size: "+file.length());

        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            File[] file = new File[pathList.size()];
            for(int i=0;i<file.length;i++){
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
            try {
                Picasso.with(MessageActivity.this).load(new File(pathList.get(1))).into(msgImg2);
            } catch (Exception e) {
                Picasso.with(MessageActivity.this).load(R.mipmap.fanhui).into(msgImg2);
            }
            path.addAll(pathList);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        startActivity(new Intent().setClass(this,LandingActivity.class));
//    }

//    @Override
//    protected int getChildLayoutId() {
//        return R.layout.activity_msg;
//    }
//
//    @Override
//    protected String getMyTitle() {
//        return "发布消息";
//    }
}


