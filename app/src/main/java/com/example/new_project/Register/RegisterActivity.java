package com.example.new_project.Register;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.new_project.Utility.InfoDialog_paizhao;
import com.example.new_project.Utility.getPhotoFromPhotoAlbum;
import com.example.new_project.Utility.img_compress;
import com.example.new_project.Base.Resgister_base;
import com.example.new_project.BaseMVP.BaseAppCompatActivity;
import com.example.new_project.BaseMVP.getPermissions;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.new_project.Land.LandActivity;

public class RegisterActivity extends BaseAppCompatActivity<RegisterView, RegisterPresenter> implements RegisterView{

    @BindView(R.id.zc_sjh)
    EditText zcSjh;
    @BindView(R.id.zc_mm)
    EditText zcMm;

    @BindView(R.id.zc_name)
    EditText zcName;
    @BindView(R.id.zc_img)
    ImageView zcImg;
    @BindView(R.id.zc_toxiang)
    Button zcToxiang;
    @BindView(R.id.zc_zc)
    Button zcZc;

    private String sjh;
    private String mima;
    private String name;
    File file;

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri

    String photoPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setTitleName();
    }


    @Override
    protected RegisterView getBindView() {
        return this;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int getChildLayoutId() {
        return R.layout.activity_zc;
    }

    @Override
    protected String getMyTitle() {
        return "注册";
    }

    @Override
    public void Register(String res) {
        Resgister_base resgister_base = JSON.parseObject(res, Resgister_base.class);
        if (resgister_base.getCode() == 200) {
            Intent intent = new Intent();
            intent.putExtra("photoPath",photoPath);
            intent.putExtra("phone",sjh);
            Log.e("222", "sjh:" + sjh + "photoPath:" + photoPath);
            intent.setClass(RegisterActivity.this, LandActivity.class);
            startActivity(intent);
        } else {
            Log.e("222", "失败: " + resgister_base.getCode() + resgister_base.getMsg());
            Toast.makeText(RegisterActivity.this, "注册失败" + res, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Failure(String res) {
        Toast.makeText(RegisterActivity.this,"注册失败"+res,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                Log.e("222", "dsasd"+uri);
                photoPath = uri.getEncodedPath();
            }
            file = new File(photoPath);
            Log.e("222", "拍照返回图片路径:"+photoPath);
            Log.d("222","file size: "+file.length());
            Picasso.with(RegisterActivity.this).load(file).into(zcImg);
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            file = new File(photoPath);
            Log.e("222", "拍照返回图片路径:"+photoPath);
            Log.d("222","file size: "+file.length());
            Picasso.with(RegisterActivity.this).load(file).into(zcImg);
            //图片压缩
            Bitmap bmp = BitmapFactory.decodeFile(photoPath);
            img_compress.compressImageToFile(bmp,file,20);
            Log.d("222","file size: "+file.length());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.zc_zc,R.id.zc_img})
    public void OnClick(View view)  {
        switch (view.getId()) {
            case R.id.zc_img:
                getPermissions.getMyPermission(RegisterActivity.this,permissions);
                InfoDialog_paizhao infoDialog = new InfoDialog_paizhao.Builder(RegisterActivity.this)
                        .setButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               uri = getPermissions.goCamera(RegisterActivity.this,cameraSavePath,uri);
                            }
                        })
                        .setButton2(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getPermissions.goPhotoAlbum(RegisterActivity.this);
                            }
                        })
                        .setButton3(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(RegisterActivity.this, "NO_OK Clicked.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                infoDialog.show();
                infoDialog.setWondow(infoDialog,getWindowManager());
                break;

            case R.id.zc_zc:
                sjh =  zcSjh.getText().toString();
                mima = zcMm.getText().toString();
                name = zcName.getText().toString();
                if (sjh.length()<6 || mima.length()<6 || name==null){
                    Toast.makeText(this,"账号或密码少于6位,或者名字为null",Toast.LENGTH_SHORT).show();
                }else {
                    RequestParams requestParams = new RequestParams();//相当于Map
                    requestParams.put("uphone",sjh);
                    requestParams.put("upassword",mima);
                    requestParams.put("uname",name);
                    try {
                        requestParams.put("imageFile",file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    basepresenter.Register(Http_url.URL_reg,requestParams);
                }
                break;
        }
    }

}
