package com.yyxnb.module_video.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.yyxnb.common.interfaces.OnSelectListener
import com.yyxnb.common.utils.DpUtils.getScreenWidth
import com.yyxnb.module_video.R
import com.yyxnb.module_video.bean.TikTokBean
import com.yyxnb.module_video.config.DataConfig.formatNum
import com.yyxnb.module_video.widget.tiktok.TikTokView
import com.yyxnb.video.cache.PreloadManager
import java.util.*

class TikTokAdapter(
        /**
         * 数据源
         */
        private val mVideoBeans: List<TikTokBean>?) : PagerAdapter() {
    /**
     * View缓存池，从ViewPager中移除的item将会存到这里面，用来复用
     */
    private val mViewPool: MutableList<View> = ArrayList()
    private var onSelectListener: OnSelectListener? = null
    fun setOnSelectListener(onSelectListener: OnSelectListener?) {
        this.onSelectListener = onSelectListener
    }

    override fun getCount(): Int {
        return mVideoBeans?.size ?: 0
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context
        var view: View? = null
        if (mViewPool.size > 0) {
            //取第一个进行复用
            view = mViewPool[0]
            mViewPool.removeAt(0)
        }
        val viewHolder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_tik_tok, container, false)
            viewHolder = ViewHolder(view)
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val (_, _, _, _, commentCount, coverUrl, videoUrl, title, _, _, _, likeCount) = mVideoBeans!![position]
        //开始预加载
        PreloadManager.getInstance(context).addPreloadTask(videoUrl, position)
        Glide.with(viewHolder.mThumb.context)
                .load(coverUrl)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        val imageView = viewHolder.mThumb
                        val params = imageView.layoutParams
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
        viewHolder.mTitle.text = title
        viewHolder.tvLikeCount.text = formatNum(likeCount)
        viewHolder.tvCommentCount.text = formatNum(commentCount)
        viewHolder.mTitle.setOnClickListener { v: View? ->
            if (onSelectListener != null) {
                onSelectListener!!.onClick(v!!, 0, "标题")
            }
        }
        viewHolder.mFollow.setOnClickListener { v: View? ->
            if (onSelectListener != null) {
                onSelectListener!!.onClick(v!!, 1, "关注")
            }
        }
        viewHolder.mLike.setOnClickListener { v: View? ->
            if (onSelectListener != null) {
                onSelectListener!!.onClick(v!!, 2, "点赞")
            }
        }
        viewHolder.mComment.setOnClickListener { v: View? ->
            if (onSelectListener != null) {
                onSelectListener!!.onClick(v!!, 3, "评论")
            }
        }
        viewHolder.mShare.setOnClickListener { v: View? ->
            if (onSelectListener != null) {
                onSelectListener!!.onClick(v!!, 4, "分享")
            }
        }
        viewHolder.mAvatar.setOnClickListener { v: View? ->
            if (onSelectListener != null) {
                onSelectListener!!.onClick(v!!, 5, "头像")
            }
        }
        viewHolder.mPosition = position
        container.addView(view)
        return view!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val itemView = `object` as View
        container.removeView(itemView)
        val (_, _, _, _, _, _, videoUrl) = mVideoBeans!![position]
        //取消预加载
        PreloadManager.getInstance(container.context).removePreloadTask(videoUrl)
        //保存起来用来复用
        mViewPool.add(itemView)
    }

    /**
     * 借鉴ListView item复用方法
     */
    class ViewHolder internal constructor(itemView: View?) {
        @JvmField
        var mPosition = 0
        var mTitle //标题
                : TextView
        var tvLikeCount //点赞数
                : TextView
        var tvCommentCount //评论数
                : TextView
        var mThumb //封面图
                : ImageView
        var mAvatar //头像
                : ImageView
        var mFollow //关注
                : ImageView
        var mLike //赞
                : ImageView
        var mComment //评论
                : ImageView
        var mShare //分享
                : ImageView

        @JvmField
        var mTikTokView: TikTokView

        @JvmField
        var mPlayerContainer: FrameLayout

        init {
            mTikTokView = itemView!!.findViewById(R.id.tiktok_View)
            mTitle = mTikTokView.findViewById(R.id.tv_title)
            tvLikeCount = mTikTokView.findViewById(R.id.tvLikeCount)
            tvCommentCount = mTikTokView.findViewById(R.id.tvCommentCount)
            mThumb = mTikTokView.findViewById(R.id.iv_thumb)
            mAvatar = mTikTokView.findViewById(R.id.ivAvatar)
            mFollow = mTikTokView.findViewById(R.id.btn_follow)
            mLike = mTikTokView.findViewById(R.id.btn_zan)
            mComment = mTikTokView.findViewById(R.id.btn_comment)
            mShare = mTikTokView.findViewById(R.id.btn_share)
            mPlayerContainer = itemView.findViewById(R.id.container)
            itemView.tag = this
        }
    }
}