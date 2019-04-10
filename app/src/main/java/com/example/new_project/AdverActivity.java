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

public class AdverActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{


    @BindView(R.id.title1)
    EditText title1;
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

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri

    String msgphone;
    Intent intent;
    ArrayList<String> path = new ArrayList<>();
    File file;
    RequestParams requestParams = new RequestParams() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);
//        setTitleName();
        intent = getIntent();
        msgphone = intent.getStringExtra("msgphone");
        title1.setVisibility(View.GONE);
        content1.setVisibility(View.GONE);
        msgImg1.setVisibility(View.VISIBLE);
        msgImg2.setVisibility(View.VISIBLE);
        msgImg3.setVisibility(View.VISIBLE);
    }




    @OnClick({R.id.msg_img1,R.id.msg_img2,R.id.msg_img3,R.id.msg_btn1,R.id.title1,R.id.content1})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.msg_btn1:
                //点击发布
                    requestParams.put("uphone",msgphone);
                    HttpClick.getInstance().post(Http_url.URL_sendMore, requestParams, new HttpClick.IHttpRequestListener() {
                        @Override
                        public void IOnSuccess(String resouces) {
                            send_base send_base = JSON.parseObject(resouces, send_base.class);
                            if (send_base.getCode() == 200){
                                Log.e("555","成功");
                                setResult(4);
                                finish();
//                                startActivity(new Intent().setClass(AdverActivity.this,LandingActivity.class));
                            }else {
                                Log.e("555","发布失败"+resouces);
                            }
                        }

                        @Override
                        public void IOnFailure(String resouces) {
                            Log.e("555","发布失败"+resouces);
                        }
                    });

                break;
            case R.id.msg_img1:
                //图片1
                msgImg2.setVisibility(View.VISIBLE);
                MultiImageSelector.create()
                        .showCamera(false) // 是否显示相机. 默认为显示
                        .count(3) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .multi() // 多选模式, 默认模式;
                        .origin(path) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(AdverActivity.this, 2);
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
            Picasso.with(AdverActivity.this).load(file).into(msgImg1);
            //压缩
            Bitmap bmp = BitmapFactory.decodeFile(photoPath);
            img_compress.compressImageToFile(bmp,file,20);
        } else if (requestCode == 2 && resultCode == RESULT_OK  && data!=null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Log.e("555","pathList:"+pathList);

            File[] file = new File[pathList.size()];
            for(int i=0;i<file.length;i++){
                file[i] = new File(pathList.get(i));
                Log.e("555","size1:"+file[i].length());
                Bitmap bmp = BitmapFactory.decodeFile(pathList.get(i));
                img_compress.compressImageToFile(bmp,file[i],20);
                Log.e("555","size2:"+file[i].length());
            }
            try {
                requestParams.put("iimgs", file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                Picasso.with(AdverActivity.this).load(new File(pathList.get(0))).into(msgImg1);
            } catch (Exception e) {
                Picasso.with(AdverActivity.this).load(R.mipmap.xinlang).into(msgImg1);
            }
            try {
                Picasso.with(AdverActivity.this).load(new File(pathList.get(1))).into(msgImg2);
            } catch (Exception e) {
                Picasso.with(AdverActivity.this).load(R.mipmap.fanhui).into(msgImg2);
            }

            path.addAll(pathList);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
            uri = FileProvider.getUriForFile(AdverActivity.this, "com.example.new_project.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        AdverActivity.this.startActivityForResult(intent, 1);
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }






//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        startActivity(new Intent().setClass(this,LandingActivity.class));
//    }



//@Override
//protected int getChildLayoutId() {
//    return R.layout.activity_msg;
//}
//
//    @Override
//    protected String getMyTitle() {
//        return "发布广告";
//    }

}
