package com.hyphenate.easeui.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EMLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * For ding-type message handle.
 * <p>
 * Created by zhangsong on 18-1-22.
 */

public class EaseDingMessageHelper {
    /**
     * To notify if a ding-type msg acked users updated.
     */
    public interface IAckUserUpdateListener {
        void onUpdate(List<String> list);
    }

    private static final String TAG = "EaseDingMessageHelper";

    // Cache 5 conversations in memory at most.
    static final int CACHE_SIZE_CONVERSATION = 5;
    // Cache 10 ding-type messages every conversation at most.
    static final int CACHE_SIZE_MESSAGE = 10;

    static final String KEY_DING = "EMDingMessage";
    static final String KEY_DING_ACK = "EMDingMessageAck";
    static final String KEY_CONVERSATION_ID = "EMConversationID";

    private static String NAME_PREFS = "group-ack-data-prefs";

    private static EaseDingMessageHelper instance;

    // Map<msgId, IAckUserUpdateListener>
    private Map<String, WeakReference<IAckUserUpdateListener>> listenerMap;

    // LruCache<conversationId, LruCache<msgId, List<username>>>
    private LruCache<String, LruCache<String, List<String>>> dataCache;

    private SharedPreferences dataPrefs;
    private SharedPreferences.Editor prefsEditor;

    public static EaseDingMessageHelper get() {
        if (instance == null) {
            synchronized (EaseDingMessageHelper.class) {
                if (instance == null) {
                    instance = new EaseDingMessageHelper(EMClient.getInstance().getContext());
                }
            }
        }
        return instance;
    }

    /**
     * Set a ack-user update listener.
     *
     * @param msg
     * @param listener Nullable, if this is null, will remove this msg's listener from listener map.
     */
    public void setUserUpdateListener(EMMessage msg, @Nullable IAckUserUpdateListener listener) {
        if (!validateMessage(msg)) {
            return;
        }

        String key = msg.getMsgId();
        if (listener == null) {
            listenerMap.remove(key);
        } else {
            listenerMap.put(key, new WeakReference<>(listener));
        }
    }

    /**
     * Contains ding msg and ding-ack msg.
     *
     * @param message
     * @return
     */
    public boolean isDingMessage(EMMessage message) {
         return message.isNeedGroupAck();
    }

    /**
     * Create a ding-type message.
     */
    public EMMessage createDingMessage(String to, String content) {
        EMMessage message = EMMessage.createTxtSendMessage(content, to);
        message.setIsNeedGroupAck(true);
        return message;
    }

    public void sendAckMessage(EMMessage message) {
        if (!validateMessage(message)) {
            return;
        }

        if (message.isAcked()) {
            return;
        }

        // May a user login from multiple devices, so do not need to send the ack msg.
        if (EMClient.getInstance().getCurrentUser().equalsIgnoreCase(message.getFrom())) {
            return;
        }

        try {
            if (message.isNeedGroupAck() && !message.isUnread()) {
                String to = message.conversationId(); // do not user getFrom() here
                String msgId = message.getMsgId();
                EMClient.getInstance().chatManager().ackGroupMessageRead(to, msgId, ((EMTextMessageBody)message.getBody()).getMessage());
                message.setUnread(false);
                EMLog.i(TAG, "Send the group ack cmd-type message.");
            }
        } catch (Exception e) {
            EMLog.d(TAG, e.getMessage());
        }
    }

    public void fetchGroupReadAck(EMMessage msg) {
        // fetch from server
        String msgId = msg.getMsgId();
        EMClient.getInstance().chatManager().asyncFetchGroupReadAcks(msgId, 20, "", new EMValueCallBack<EMCursorResult<EMGroupReadAck>>() {
            @Override
            public void onSuccess(EMCursorResult<EMGroupReadAck> value) {
                EMLog.d(TAG, "asyncFetchGroupReadAcks success");

                if (value.getData() != null && value.getData().size() > 0) {
                    List<EMGroupReadAck> acks = value.getData();

                    for (EMGroupReadAck c : acks) {
                        handleGroupReadAck(c);
                    }

                } else {
                    EMLog.d(TAG, "no data");
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                EMLog.d(TAG, "asyncFetchGroupReadAcks fail: " + error);
            }
        });
    }

    /**
     * To handle ding-type ack msg.
     * Need store native for reload when app restart.
     *
     * @param ack The ding-type message.
     */
    public void handleGroupReadAck(EMGroupReadAck ack) {
        if (ack == null) return;;

        EMLog.d(TAG, "handle group read ack: " + ack.getMsgId());

        String username = ack.getFrom();
        String msgId = ack.getMsgId();
        String conversationId = EMClient.getInstance().chatManager().getMessage(msgId).conversationId();

        // Get a message map.
        LruCache<String, List<String>> msgCache = dataCache.get(conversationId);
        if (msgCache == null) {
            msgCache = createCache();
            dataCache.put(conversationId, msgCache);
        }

        // Get the msg ack-user list.
        List<String> userList = msgCache.get(msgId);
        if (userList == null) {
            userList = new ArrayList<>();
            msgCache.put(msgId, userList);
        }

        if (!userList.contains(username)) {
            userList.add(username);
        }

        // Notify ack-user list changed.
        WeakReference<IAckUserUpdateListener> listenerRefs = listenerMap.get(msgId);
        if (listenerRefs != null) {
            listenerRefs.get().onUpdate(userList);
        }

        // Store in preferences.
        String key = generateKey(conversationId, msgId);
        Set<String> set = new HashSet<>();
        set.addAll(userList);
        prefsEditor.putStringSet(key, set).commit();
    }


    /**
     * Delete the native stored acked users if this message deleted.
     *
     * @param message
     */
    public void delete(EMMessage message) {
        if (!validateMessage(message)) {
            return;
        }

        String conversationId = message.getTo();
        String msgId = message.getMsgId();

        // Remove the memory data.
        LruCache<String, List<String>> msgCache = dataCache.get(conversationId);
        if (msgCache != null) {
            msgCache.remove(msgId);
        }

        // Delete the data in preferences.
        String key = generateKey(conversationId, msgId);
        if (dataPrefs.contains(key)) {
            prefsEditor.remove(key).commit();
        }
    }

    /**
     * Delete the native stored acked users if this conversation deleted.
     *
     * @param conversation
     */
    public void delete(EMConversation conversation) {
        if (!conversation.isGroup()) {
            return;
        }

        // Remove the memory data.
        String conversationId = conversation.conversationId();
        dataCache.remove(conversationId);

        // Remove the preferences data.
        String keyPrefix = generateKey(conversationId, "");
        Map<String, ?> prefsMap = dataPrefs.getAll();
        Set<String> keySet = prefsMap.keySet();
        for (String key : keySet) {
            if (key.startsWith(keyPrefix)) {
                prefsEditor.remove(key);
            }
        }
        prefsEditor.commit();
    }

    // Package level interface, for test.
    Map<String, WeakReference<IAckUserUpdateListener>> getListenerMap() {
        return listenerMap;
    }

    // Package level interface, for test.
    LruCache<String, LruCache<String, List<String>>> getDataCache() {
        return dataCache;
    }

    // Package level interface, for test.
    SharedPreferences getDataPrefs() {
        return dataPrefs;
    }

    EaseDingMessageHelper(Context context) {
        dataCache = new LruCache<String, LruCache<String, List<String>>>(CACHE_SIZE_CONVERSATION) {
            @Override
            protected int sizeOf(String key, LruCache<String, List<String>> value) {
                return 1;
            }
        };

        listenerMap = new HashMap<>();

        dataPrefs = context.getSharedPreferences(NAME_PREFS, Context.MODE_PRIVATE);
        prefsEditor = dataPrefs.edit();
    }

    /**
     * Generate a key for SharedPreferences to store
     *
     * @param conversationId Group chat conversation id.
     * @param originalMsgId  The id of the ding-type message.
     * @return
     */
    String generateKey(@NonNull String conversationId, @NonNull String originalMsgId) {
        return conversationId + "|" + originalMsgId;
    }

    private boolean validateMessage(EMMessage message) {
        if (message == null) {
            return false;
        }

        if (message.getChatType() != EMMessage.ChatType.GroupChat) {
            return false;
        }

        if (!isDingMessage(message)) {
            return false;
        }

        return true;
    }

    private LruCache<String, List<String>> createCache() {
        return new LruCache<String, List<String>>(CACHE_SIZE_MESSAGE) {
            @Override
            protected int sizeOf(String key, List<String> value) {
                return 1;
            }
        };
    }
}
