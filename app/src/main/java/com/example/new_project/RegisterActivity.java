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
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.new_project.Utility.BaseAppCompatActivity;
import com.example.new_project.Utility.HttpClick;
import com.example.new_project.Utility.Http_url;
import com.example.new_project.Utility.InfoDialog_paizhao;
import com.example.new_project.Utility.getPhotoFromPhotoAlbum;
import com.example.new_project.Utility.img_compress;
import com.example.new_project.base.Resgister_base;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class RegisterActivity extends BaseAppCompatActivity implements EasyPermissions.PermissionCallbacks {


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

    private LinearLayout ly_root;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_zc);
        //账号密码,名字,头像
        ButterKnife.bind(this);
        setTitleName();
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
            Picasso.with(RegisterActivity.this).load(file).into(zcImg);

            Bitmap bmp = BitmapFactory.decodeFile(photoPath);
            img_compress.compressImageToFile(bmp,file,20);

       }
       else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            file = new File(photoPath);
            Picasso.with(RegisterActivity.this).load(file).into(zcImg);

            Bitmap bmp = BitmapFactory.decodeFile(photoPath);
            img_compress.compressImageToFile(bmp,file,20);
            Log.d("debug","file size: "+file.length());
            Log.d("debug","file name"+file.getName());
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
            uri = FileProvider.getUriForFile(RegisterActivity.this, "com.example.new_project.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        RegisterActivity.this.startActivityForResult(intent, 1);
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @OnClick({R.id.zc_zc,R.id.zc_img})
    public void OnClick(View view)  {
        switch (view.getId()) {
            case R.id.zc_img:
//                takePhoto.onPickFromCapture(uri);

                        getPermission();
                        InfoDialog_paizhao infoDialog = new InfoDialog_paizhao.Builder(RegisterActivity.this)
                                .setButton(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        goCamera();

                                    }
                                })
                                .setButton2(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        goPhotoAlbum();
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
                        Window window = infoDialog.getWindow();
                        window.setGravity(Gravity.BOTTOM);
                        WindowManager.LayoutParams WP = infoDialog.getWindow().getAttributes();
                        WindowManager m = getWindowManager();
                        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                        WindowManager.LayoutParams p = infoDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                        p.width = d.getWidth(); //宽度设置为屏幕
                        infoDialog.getWindow().setAttributes(WP);
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
                    HttpClick.getInstance().post(Http_url.URL_reg, requestParams, new HttpClick.IHttpRequestListener() {
                        @Override
                        public void IOnSuccess(String resouces) {
                            Log.e("222", "IOnSuccess: " + resouces);
                            Resgister_base resgister_base = JSON.parseObject(resouces, Resgister_base.class);
                            if (resgister_base.getCode() == 200) {
                                Intent intent = new Intent();
                                Log.e("222", "成功: " + resgister_base.getCode());
                                intent.setClass(RegisterActivity.this, LandingActivity.class);
                                startActivity(intent);
                            }else {
                                Log.e("222", "失败: " + resgister_base.getCode()+resgister_base.getMsg());
                                Toast.makeText(RegisterActivity.this,"注册失败"+resouces,Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void IOnFailure(String resouces) {
                            Log.e("222", "IOnFailure: " + resouces);
                        }
                    });

                }
                break;
        }
    }

}
