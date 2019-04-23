package com.example.new_project.BaseMVP;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

public  class getPermissions {


    public getPermissions(){

    }

        //获取权限
        public static void getMyPermission(Activity activity, String[] permissions) {
        if (EasyPermissions.hasPermissions(activity, permissions)) {
            //已经打开权限
            Toast.makeText(activity, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(activity, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }

    //激活相机操作
    public static Uri goCamera(Activity activity, File cameraSavePath, Uri uri) {
        Log.e("222", "cameraSavePath:"+cameraSavePath+"uri:"+uri);
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileprovider
            uri = FileProvider.getUriForFile(activity, "com.example.new_project.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, 1);
        return uri;
    }


    //激活相册操作
    public static void goPhotoAlbum(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, 2);
    }
}
