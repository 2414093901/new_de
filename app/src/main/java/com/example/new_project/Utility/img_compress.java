package com.example.new_project.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class img_compress {
    //Bitmap bmp = BitmapFactory.decodeFile(pathList.get(i));//pathList.get(i)为String类型,图片的地址

    /**
     * 质量压缩:
     *
     * bmp 为String类型,图片的地址
     * file 要压缩的文件
     * options 为质量压缩的比例，0-100的返回  100代表不进行质量压缩，越靠近0，也就是值越小，质量压缩越厉害
     */

    public static void compressImageToFile(Bitmap bmp, File file,int options) {
        //options为质量压缩的比例，0-100的返回  100代表不进行质量压缩，越靠近0，也就是值越小，质量压缩越厉害
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        try {            //进行缓存
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 尺寸压缩:
     *
     * bmp 为String类型,图片的地址
     * file 要压缩的文件
     * ratio 为尺寸压缩的倍数，值越大，图片尺寸越小
     */
    public static void compressBitmapToFile(Bitmap bmp, File file,int ratio) {
        //ratio为尺寸压缩的倍数，值越大，图片尺寸越小

        // 压缩bitmap到对应的尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        // 将压缩后的bitmap绘制到画板
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 将压缩后的数据保存放在baos中，这里仍然可以继续使用质量压缩

        result.compress(Bitmap.CompressFormat.JPEG, 100, baos);        //进行缓存
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 采样率压缩:
     *
     * filePath 为String类型,图片的地址
     * file 要压缩的文件
     * inSmapleSize int类型,数值越高，图片像素越低
     */
    public static void compressBitmap(String filePath, File file,int inSmapleSize) {
        //数值越高，图片像素越低
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;////为true的时候不会真正加载图片，而是得到图片的宽高信息。
        // 采样率
        options.inSampleSize = inSmapleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
