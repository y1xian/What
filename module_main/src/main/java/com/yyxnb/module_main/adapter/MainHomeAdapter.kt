package com.yyxnb.module_main.adapter

import android.graphics.Color
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.noober.background.drawable.DrawableCreator
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.ItemDelegate
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.common.utils.DpUtils.dp2px
import com.yyxnb.module_main.R
import com.yyxnb.module_main.bean.MainHomeBean

class MainHomeAdapter : MultiItemTypeAdapter<MainHomeBean>() {
    // 统一设置
    private fun setView(mLayout: ConstraintLayout, tvTitle: TextView, bean: MainHomeBean) {
        if (!bean.color.isEmpty()) {
            val drawable = DrawableCreator.Builder()
                    .setSolidColor(Color.parseColor(bean.color))
                    .setCornersRadius(dp2px(mLayout.context, 10f).toFloat())
                    .build()
            mLayout.background = drawable
        }
        tvTitle.text = bean.title
    }

    init {
//        super(new ItemDiffCallback<>());
        // 搜索
        addItemDelegate(object : ItemDelegate<MainHomeBean> {
            override fun layoutId(): Int {
                return R.layout.item_home_type_search_layout
            }

            override fun isThisType(item: MainHomeBean, position: Int): Boolean {
                return item.type == 20
            }

            override fun convert(holder: BaseViewHolder, mainHomeBean: MainHomeBean, position: Int) {}
        })
        addItemDelegate(object : ItemDelegate<MainHomeBean> {
            override fun layoutId(): Int {
                return R.layout.item_home_type_1_layout
            }

            override fun isThisType(item: MainHomeBean, position: Int): Boolean {
                return item.type == 1
            }

            override fun convert(holder: BaseViewHolder, mainHomeBean: MainHomeBean, position: Int) {
                val mLayout: ConstraintLayout = holder.getView(R.id.mLayout)
                val tvTitle = holder.getView<TextView>(R.id.tvTitle)
                setView(mLayout, tvTitle, mainHomeBean)
            }
        })
        addItemDelegate(object : ItemDelegate<MainHomeBean> {
            override fun layoutId(): Int {
                return R.layout.item_home_type_2_layout
            }

            override fun isThisType(item: MainHomeBean, position: Int): Boolean {
                return item.type == 2
            }

            override fun convert(holder: BaseViewHolder, mainHomeBean: MainHomeBean, position: Int) {
                val mLayout: ConstraintLayout = holder.getView(R.id.mLayout)
                val tvTitle = holder.getView<TextView>(R.id.tvTitle)
                setView(mLayout, tvTitle, mainHomeBean)
            }
        })
        addItemDelegate(object : ItemDelegate<MainHomeBean> {
            override fun layoutId(): Int {
                return R.layout.item_home_type_3_layout
            }

            override fun isThisType(item: MainHomeBean, position: Int): Boolean {
                return item.type == 3
            }

            override fun convert(holder: BaseViewHolder, mainHomeBean: MainHomeBean, position: Int) {
                val mLayout: ConstraintLayout = holder.getView(R.id.mLayout)
                val tvTitle = holder.getView<TextView>(R.id.tvTitle)
                setView(mLayout, tvTitle, mainHomeBean)
            }
        })
    }
}