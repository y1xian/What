package com.yyxnb.module_video.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yyxnb.common.interfaces.OnSelectListener;
import com.yyxnb.module_video.R;
import com.yyxnb.module_video.bean.TikTokBean;
import com.yyxnb.module_video.utils.cache.PreloadManager;
import com.yyxnb.module_video.widget.TikTokView;

import java.util.ArrayList;
import java.util.List;

public class TikTokAdapter extends PagerAdapter {

    /**
     * View缓存池，从ViewPager中移除的item将会存到这里面，用来复用
     */
    private List<View> mViewPool = new ArrayList<>();

    private OnSelectListener onSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    /**
     * 数据源
     */
    private List<TikTokBean> mVideoBeans;

    public TikTokAdapter(List<TikTokBean> videoBeans) {
        this.mVideoBeans = videoBeans;
    }

    @Override
    public int getCount() {
        return mVideoBeans == null ? 0 : mVideoBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Context context = container.getContext();
        View view = null;
        if (mViewPool.size() > 0) {//取第一个进行复用
            view = mViewPool.get(0);
            mViewPool.remove(0);
        }

        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_tik_tok, container, false);
            viewHolder = new ViewHolder(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        TikTokBean item = mVideoBeans.get(position);
        //开始预加载
        PreloadManager.getInstance(context).addPreloadTask(item.videoUrl, position);
        Glide.with(context)
                .load(item.coverUrl)
//                .placeholder(android.R.color.white)
                .into(viewHolder.mThumb);
        viewHolder.mTitle.setText(item.title);
        viewHolder.mTitle.setOnClickListener(v -> {
            if (onSelectListener != null) {
                onSelectListener.onClick(v, 0, "标题");
            }
        });
        viewHolder.mFollow.setOnClickListener(v -> {
            if (onSelectListener != null) {
                onSelectListener.onClick(v, 1, "标题");
            }
        });
        viewHolder.mLike.setOnClickListener(v -> {
            if (onSelectListener != null) {
                onSelectListener.onClick(v, 2, "点赞");
            }
        });
        viewHolder.mComment.setOnClickListener(v -> {
            if (onSelectListener != null) {
                onSelectListener.onClick(v, 3, "评论");
            }
        });
        viewHolder.mShare.setOnClickListener(v -> {
            if (onSelectListener != null) {
                onSelectListener.onClick(v, 4, "分享");
            }
        });
        viewHolder.mAvatar.setOnClickListener(v -> {
            if (onSelectListener != null) {
                onSelectListener.onClick(v, 5, "头像");
            }
        });
        viewHolder.mPosition = position;
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View itemView = (View) object;
        container.removeView(itemView);
        TikTokBean item = mVideoBeans.get(position);
        //取消预加载
        PreloadManager.getInstance(container.getContext()).removePreloadTask(item.videoUrl);
        //保存起来用来复用
        mViewPool.add(itemView);
    }

    /**
     * 借鉴ListView item复用方法
     */
    public static class ViewHolder {

        public int mPosition;
        public TextView mTitle;//标题
        public ImageView mThumb;//封面图
        public ImageView mAvatar;//头像
        public ImageView mFollow;//关注
        public ImageView mLike;//赞
        public ImageView mComment;//评论
        public ImageView mShare;//分享
        public TikTokView mTikTokView;
        public FrameLayout mPlayerContainer;

        ViewHolder(View itemView) {
            mTikTokView = itemView.findViewById(R.id.tiktok_View);
            mTitle = mTikTokView.findViewById(R.id.tv_title);
            mThumb = mTikTokView.findViewById(R.id.iv_thumb);
            mAvatar = mTikTokView.findViewById(R.id.ivAvatar);
            mFollow = mTikTokView.findViewById(R.id.btn_follow);
            mLike = mTikTokView.findViewById(R.id.btn_zan);
            mComment = mTikTokView.findViewById(R.id.btn_comment);
            mShare = mTikTokView.findViewById(R.id.btn_share);
            mPlayerContainer = itemView.findViewById(R.id.container);
            itemView.setTag(this);
        }
    }
}
