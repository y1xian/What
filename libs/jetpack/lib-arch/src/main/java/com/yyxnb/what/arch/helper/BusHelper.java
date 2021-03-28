package com.yyxnb.what.arch.helper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.yyxnb.what.arch.bean.MsgEvent;
import com.yyxnb.what.arch.constants.ArgumentKeys;

/**
 * 方便MsgEvent的使用
 */
public class BusHelper {

    public static void post(@NonNull MsgEvent msgEvent) {
        LiveEventBus.get(ArgumentKeys.MSG_EVENT, MsgEvent.class).post(msgEvent);
    }

    public static void post(@NonNull MsgEvent msgEvent, long delay) {
        LiveEventBus.get(ArgumentKeys.MSG_EVENT, MsgEvent.class).postDelay(msgEvent, delay);
    }

    public static void broadcast(@NonNull MsgEvent msgEvent) {
        LiveEventBus.get(ArgumentKeys.MSG_EVENT, MsgEvent.class).broadcast(msgEvent, false, false);
    }

    public static void observe(@NonNull LifecycleOwner owner, @NonNull Observer<MsgEvent> observer) {
        LiveEventBus.get(ArgumentKeys.MSG_EVENT, MsgEvent.class).observe(owner, observer);
    }

    public static void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<MsgEvent> observer) {
        LiveEventBus.get(ArgumentKeys.MSG_EVENT, MsgEvent.class).observeSticky(owner, observer);
    }
}
