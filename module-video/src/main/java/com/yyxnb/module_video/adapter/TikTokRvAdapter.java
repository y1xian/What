package com.yyxnb.module_video.adapter;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.module_video.widget.tiktok.TikTokView;
import com.yyxnb.what.adapter.base.BaseAdapter;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.core.DpUtils;
import com.yyxnb.what.video.cache.PreloadManager;

public class TikTokRvAdapter extends BaseAdapter<TikTokBean> {


    public TikTokRvAdapter() {
        super(R.layout.item_tik_tok);
    }

    public TikTokView mTikTokView;
    public int mPosition;
    public TextView mTitle;//标题
    public TextView tvLikeCount;//点赞数
    public TextView tvCommentCount;//评论数
    public ImageView mThumb;//封面图
    public ImageView mAvatar;//头像
    public ImageView mFollow;//关注
    public ImageView mLike;//赞
    public ImageView mComment;//评论
    public ImageView mShare;//分享
    public FrameLayout mPlayerContainer;

    @Override
    protected void bind(BaseViewHolder holder, TikTokBean tikTokBean, int position) {

        mTikTokView = holder.getView(R.id.tiktok_View);

        mThumb = mTikTokView.getView().findViewById(R.id.iv_thumb);
        mPlayerContainer = mTikTokView.findViewById(R.id.container);
        mTitle = mTikTokView.findViewById(R.id.tvTitle);
        tvLikeCount = mTikTokView.findViewById(R.id.tvLikeCount);
        tvCommentCount = mTikTokView.findViewById(R.id.tvCommentCount);
        mThumb = mTikTokView.findViewById(R.id.iv_thumb);
        mAvatar = mTikTokView.findViewById(R.id.ivAvatar);
        mFollow = mTikTokView.findViewById(R.id.btn_follow);
        mLike = mTikTokView.findViewById(R.id.btn_zan);
        mComment = mTikTokView.findViewById(R.id.btn_comment);
        mShare = mTikTokView.findViewById(R.id.btn_share);

        mTikTokView.setTag(this);
        mPosition = position;

        //开始预加载
        PreloadManager.getInstance(holder.itemView.getContext()).addPreloadTask(tikTokBean.videoUrl, position);

        Glide.with(mThumb.getContext())
                .load(tikTokBean.coverUrl)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ImageView imageView = mThumb;
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();

                        int w = resource.getMinimumWidth();
                        int h = resource.getMinimumHeight();
                        float wh = 0f;

                        if (w < h) {
                            wh = (float) h / (float) w;
                        } else {
                            wh = (float) w / (float) h;
                        }

                        int pw = DpUtils.getScreenWidth(imageView.getContext());

                        int hh = (int) (pw * wh);

                        params.width = pw;
                        params.height = hh;

                        imageView.setLayoutParams(params);
                        imageView.setImageDrawable(resource);
                    }
                });

        mTitle.setText(tikTokBean.title);

    }

    @Override
    protected void onViewDetachedFromWindow2(BaseViewHolder holder) {
        super.onViewDetachedFromWindow2(holder);
        TikTokBean item = getData().get(holder.getLayoutPosition());
        //取消预加载
        PreloadManager.getInstance(holder.itemView.getContext()).removePreloadTask(item.videoUrl);
    }

}
