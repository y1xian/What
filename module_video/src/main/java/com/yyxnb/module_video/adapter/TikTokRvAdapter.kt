package com.yyxnb.module_video.adapter

import android.graphics.drawable.Drawable
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.common.utils.DpUtils.getScreenWidth
import com.yyxnb.module_video.R
import com.yyxnb.module_video.bean.TikTokBean
import com.yyxnb.module_video.widget.tiktok.TikTokView
import com.yyxnb.video.cache.PreloadManager

class TikTokRvAdapter : BaseAdapter<TikTokBean>(R.layout.item_tik_tok) {
    var mTikTokView: TikTokView? = null
    var mPosition = 0
    var mTitle //标题
            : TextView? = null
    var tvLikeCount //点赞数
            : TextView? = null
    var tvCommentCount //评论数
            : TextView? = null
    var mThumb //封面图
            : ImageView? = null
    var mAvatar //头像
            : ImageView? = null
    var mFollow //关注
            : ImageView? = null
    var mLike //赞
            : ImageView? = null
    var mComment //评论
            : ImageView? = null
    var mShare //分享
            : ImageView? = null
    var mPlayerContainer: FrameLayout? = null

    protected override fun bind(holder: BaseViewHolder, tikTokBean: TikTokBean, position: Int) {
        mTikTokView = holder.getView(R.id.tiktok_View)
        mTikTokView?.apply {
            mThumb = findViewById(R.id.iv_thumb)
            mPlayerContainer = findViewById(R.id.container)
            mTitle = findViewById(R.id.tv_title)
            tvLikeCount = findViewById(R.id.tvLikeCount)
            tvCommentCount = findViewById(R.id.tvCommentCount)
            mThumb = findViewById(R.id.iv_thumb)
            mAvatar = findViewById(R.id.ivAvatar)
            mFollow = findViewById(R.id.btn_follow)
            mLike = findViewById(R.id.btn_zan)
            mComment = findViewById(R.id.btn_comment)
            mShare = findViewById(R.id.btn_share)
            setTag(this)
        }

        mPosition = position

        //开始预加载
        PreloadManager.getInstance(holder.itemView.context).addPreloadTask(tikTokBean.videoUrl, position)
        Glide.with(mThumb!!.getContext())
                .load(tikTokBean.coverUrl)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        val imageView = mThumb
                        val params = imageView!!.layoutParams
                        val w = resource.minimumWidth
                        val h = resource.minimumHeight
                        var wh = 0f
                        wh = if (w < h) {
                            h.toFloat() / w.toFloat()
                        } else {
                            w.toFloat() / h.toFloat()
                        }
                        val pw = getScreenWidth(imageView.context)
                        val hh = (pw * wh).toInt()
                        params.width = pw
                        params.height = hh
                        imageView.layoutParams = params
                        imageView.setImageDrawable(resource)
                    }
                })
        mTitle?.text = tikTokBean.title
    }

    override fun onViewDetachedFromWindow2(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow2(holder)
        val (_, _, _, _, _, _, videoUrl) = data[holder.layoutPosition]!!
        //取消预加载
        PreloadManager.getInstance(holder.itemView.context).removePreloadTask(videoUrl)
    }
}