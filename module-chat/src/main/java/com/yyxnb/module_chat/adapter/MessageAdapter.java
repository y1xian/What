package com.yyxnb.module_chat.adapter;

import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.adapter.ItemDelegate;
import com.yyxnb.what.adapter.base.MultiItemTypeAdapter;
import com.yyxnb.module_chat.R;
import com.yyxnb.module_chat.bean.MessageBean;

public class MessageAdapter extends MultiItemTypeAdapter<MessageBean> {

    public MessageAdapter() {
        super();
        addItemDelegate(new ItemDelegate<MessageBean>() {
            @Override
            public int layoutId() {
                return R.layout.item_message_layout;
            }

            @Override
            public boolean isThisType(MessageBean item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, MessageBean messageBean, int position) {
                holder.setText(R.id.tvText, messageBean.text)
                        .setText(R.id.tvName, messageBean.name);
            }
        });
    }
}
