package com.yyxnb.module_chat.adapter

import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.ItemDelegate
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.module_chat.R
import com.yyxnb.module_chat.bean.MessageBean

class MessageAdapter : MultiItemTypeAdapter<MessageBean>() {

    init {
        addItemDelegate(object : ItemDelegate<MessageBean> {
            override fun layoutId(): Int {
                return R.layout.item_message_layout
            }

            override fun isThisType(item: MessageBean, position: Int): Boolean {
                return true
            }

            override fun convert(holder: BaseViewHolder, messageBean: MessageBean, position: Int) {
                holder.setText(R.id.tvText, messageBean.text)
                        .setText(R.id.tvName, messageBean.name)
            }
        })
    }
}