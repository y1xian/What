package com.yyxnb.module_chat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.base.IFragment;
import com.yyxnb.module_chat.R;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/19
 * 历    史：
 * 描    述：环信 会话列表
 * ================================================
 */
@BindRes(subPage = true)
public class ChatConversationListFragment extends EaseConversationListFragment implements IFragment {

//    @Override
//    public int initLayoutResId() {
//        return R.layout.fragment_chat_conversation_list;
//    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @Override
    public void initViewData() {

    }

    @Override
    protected void setUpView() {

        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                Toast.makeText(getActivity(), username + " , 第 " + position, Toast.LENGTH_SHORT).show();
                if (username.equals(EMClient.getInstance().getCurrentUser())) {
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                } else {
                    // start chat acitivity
//                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                            // it's group chat
//                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);

                        } else {
//                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }

                    }
                    // it's single chat
//                    intent.putExtra(Constant.EXTRA_USER_ID, username);
//                    startActivity(intent);
                }
            }
        });

        super.setUpView();

    }
}