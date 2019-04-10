package com.example.new_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.new_project.Utility.Http_url;
import com.example.new_project.base.msgbase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerView_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public void setArrayList(ArrayList<msgbase.DataBean.ListBean> arrayList) {
        this.arrayList = arrayList;
    }

    private ArrayList<msgbase.DataBean.ListBean> arrayList;
    FootViewHolder footViewHolder;
    HeadViewHolder headViewHolder;
    String photoPath;
    String phone;


    private int isLoading = 1 ;
    public int isLoading() {
        return isLoading;
    }

    public void setLoading(int loading) {
        isLoading = loading;
        switch (loading){
            case 1://可以加载更多
                footViewHolder.footPb.setVisibility(View.VISIBLE);
                footViewHolder.foot_tx.setVisibility(View.GONE);
                break;
            case 2://正在加载中,不能再次加载
                footViewHolder.footPb.setVisibility(View.VISIBLE);
                footViewHolder.foot_tx.setVisibility(View.GONE);
                break;
            case 3://没有更多数据了
                footViewHolder.footPb.setVisibility(View.GONE);
                footViewHolder.foot_tx.setVisibility(View.VISIBLE);
                break;
        }
    }

    public RecyclerView_adapter(Context context, ArrayList<msgbase.DataBean.ListBean> arrayList, String photoPath, String phone) {
        this.context = context;
        this.arrayList = arrayList;
        this.photoPath = photoPath;
        this.phone = phone;
        Log.e("333","size"+arrayList.size()+"photoPath:"+photoPath+"phone"+phone);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.rv_item_layout, viewGroup,false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }
        else if (i == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.foot_rv, viewGroup,false);
             footViewHolder = new FootViewHolder(view);
             Log.e("444","footViewHolder布局选择的i:"+i);
            return footViewHolder;
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.headed_layout, viewGroup,false);
            headViewHolder = new HeadViewHolder(view);
            Log.e("444","headViewHolder布局选择的i:"+i);
            return headViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof FootViewHolder) {
             footViewHolder = (FootViewHolder) viewHolder;
            Log.e("444","footViewHolder内容数据的i:"+i);
        } else if(viewHolder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
            itemViewHolder.initDate(i);
        }
        else {
            headViewHolder = (HeadViewHolder) viewHolder;
            Log.e("444","headViewHolder内容数据的i:"+i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){//0
            return 2;
        }
        else if (position == getItemCount() - 1) {//11
            return 1;
        }
        else {//1-10
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() + 1 : 0;
    }


    class HeadViewHolder extends RecyclerView.ViewHolder{
        ImageView head_background,head_foreground;
        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            head_background = itemView.findViewById(R.id.head_background);
            head_foreground = itemView.findViewById(R.id.head_foreground);

            try {
                //背景图片
                Picasso.with(context).load(Http_url.HTTP_URL + photoPath).into(head_background);

            } catch (Exception e) {
                Picasso.with(context).load(R.mipmap.qq).into(head_background);
            }
            try {
                //前景图片
                Picasso.with(context).load(Http_url.HTTP_URL + photoPath).into(head_foreground);
            } catch (Exception e) {
                Picasso.with(context).load(R.mipmap.qq).into(head_foreground);
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        CircleImageView itemCircleImageView;
        TextView itemName, itemTitle, itemContent;
        ImageView itemImg;

        TextView ggAuthor;
        ImageView gg1, gg2, gg3;

        LinearLayout itemGg;
        LinearLayout itemMsg;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemMsg = itemView.findViewById(R.id.item_msg);
            //信息布局
            itemCircleImageView = itemView.findViewById(R.id.item_CircleImageView);
            itemName = itemView.findViewById(R.id.item_name);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemContent = itemView.findViewById(R.id.item_content);
            itemImg = itemView.findViewById(R.id.item_img);


            itemGg = itemView.findViewById(R.id.item_gg);
            //广告布局
            ggAuthor = itemView.findViewById(R.id.gg_author);
            gg1 = itemView.findViewById(R.id.gg_1);
            gg2 = itemView.findViewById(R.id.gg_2);
            gg3 = itemView.findViewById(R.id.gg_3);
        }

        private void initDate(int i) {
            if (arrayList.get(i).getImgBases() == null) {
                //消息内容
                itemMsg.setVisibility(View.VISIBLE);
                itemGg.setVisibility(View.GONE);
                try {
                    itemTitle.setText(arrayList.get(i).getMtitle().toString());
                } catch (Exception e) {
                    itemTitle.setText("null");
                }
                try {
                    itemContent.setText(arrayList.get(i).getMmsg().toString());
                } catch (Exception e) {
                    itemContent.setText("null");
                }
                try {
                    itemName.setText(arrayList.get(i).getUserBase().getUname());
                } catch (Exception e) {
                    itemName.setText("null");
                }
                try {
                    //内容图片
                    Picasso.with(context).load(Http_url.HTTP_URL + arrayList.get(i).getMimg()).into(itemImg);
                } catch (Exception e) {
                    Picasso.with(context).load(R.mipmap.qq).into(itemImg);
                }
                try {
                    //头像
                    Picasso.with(context).load(Http_url.HTTP_URL + arrayList.get(i).getUserBase().getUimg()).into(itemCircleImageView);
                } catch (Exception e) {
                    Picasso.with(context).load(R.mipmap.qq).into(itemCircleImageView);
                }


            } else {
                //广告内容
                itemMsg.setVisibility(View.GONE);
                itemGg.setVisibility(View.VISIBLE);
                try {
                    Picasso.with(context).load(Http_url.HTTP_URL + arrayList.get(i).getImgBases().get(0).getIimg()).into(gg1);
                } catch (Exception e) {
                    Picasso.with(context).load(R.mipmap.weixin).into(gg1);
                }
                try{
                    Picasso.with(context).load(Http_url.HTTP_URL + arrayList.get(i).getImgBases().get(1).getIimg()).into(gg2);
                }catch (Exception e){
                    Picasso.with(context).load(R.mipmap.qq).into(gg2);
                }
                try{
                    Picasso.with(context).load(Http_url.HTTP_URL + arrayList.get(i).getImgBases().get(2).getIimg()).into(gg3);
                }catch (Exception e){
                    Picasso.with(context).load(R.mipmap.xinlang).into(gg3);
                }
                ggAuthor.setText("广告:"+arrayList.get(i).getUphone());
            }


        }

    }

    //foot底部加载更多
    class FootViewHolder extends RecyclerView.ViewHolder {
        ProgressBar footPb;
        TextView foot_tx;
        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            footPb = itemView.findViewById(R.id.foot_pb);
            foot_tx = itemView.findViewById(R.id.foot_tx);
        }
    }

}
