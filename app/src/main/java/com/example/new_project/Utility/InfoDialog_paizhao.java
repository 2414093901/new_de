package com.example.new_project.Utility;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.new_project.R;


public class InfoDialog_paizhao extends Dialog{

    private InfoDialog_paizhao(Context context, int themeResId) {
        super(context, themeResId);
    }



    public static class Builder {

        private View mLayout;
        private Button quxiao,paizhao,xiangce;
        private View.OnClickListener mButton3ClickListener;
        private View.OnClickListener mButtonClickListener;
        private View.OnClickListener mButton2ClickListener;
        private InfoDialog_paizhao mDialog;



        public Builder(Context context) {
            mDialog = new InfoDialog_paizhao(context, R.style.Theme_AppCompat_Dialog);
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //加载布局文件
            mLayout = inflater.inflate(R.layout.dialog_xiangce, null, false);
            //添加布局文件到 Dialog
            mDialog.addContentView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            quxiao = mLayout.findViewById(R.id.quxiao);
            paizhao = mLayout.findViewById(R.id.paizao);
            xiangce = mLayout.findViewById(R.id.xiangce);
        }


        /**
         * 设置按钮文字和监听
         */
        public Builder setButton(View.OnClickListener listener) {
            mButtonClickListener = listener;
            return this;
        }

        public Builder setButton2(View.OnClickListener listener) {
            mButton2ClickListener = listener;
            return this;
        }

        public Builder setButton3(View.OnClickListener listener) {
            mButton3ClickListener = listener;
            return this;
        }


        public InfoDialog_paizhao create() {
            //拍照
            paizhao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonClickListener.onClick(v);
                    mDialog.dismiss();
                }
            });
            //相册
            xiangce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButton2ClickListener.onClick(v);
                    mDialog.dismiss();
                }
            });
            //取消
            quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    mButton3ClickListener.onClick(v);
                }
            });

            mDialog.setContentView(mLayout);
            mDialog.setCancelable(true);                //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
            return mDialog;
        }

    }


    /**
     * 设置infoDialog适配屏幕
     */
    public void setWondow(InfoDialog_paizhao infoDialog,WindowManager m){
        Window window = infoDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams WP = infoDialog.getWindow().getAttributes();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = infoDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth(); //宽度设置为屏幕
        infoDialog.getWindow().setAttributes(WP);
    }
}
