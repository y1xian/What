package com.yyxnb.arch.common;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.jeremyliao.liveeventbus.LiveEventBus;

import static com.yyxnb.arch.common.ArchConfig.MSG_EVENT;

/**
 * 方便MsgEvent的使用
 */
public class Bus {

    public static void post(@NonNull MsgEvent msgEvent) {
        LiveEventBus.get(MSG_EVENT, MsgEvent.class).post(msgEvent);
    }

    public static void post(@NonNull MsgEvent msgEvent, long delay) {
        LiveEventBus.get(MSG_EVENT, MsgEvent.class).postDelay(msgEvent, delay);
    }

    public static void broadcast(@NonNull MsgEvent msgEvent) {
        LiveEventBus.get(MSG_EVENT, MsgEvent.class).broadcast(msgEvent);
    }

    public static void observe(@NonNull LifecycleOwner owner, @NonNull Observer<MsgEvent> observer) {
        LiveEventBus.get(MSG_EVENT, MsgEvent.class).observe(owner, observer);
    }

    public static void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<MsgEvent> observer) {
        LiveEventBus.get(MSG_EVENT, MsgEvent.class).observeSticky(owner, observer);
    }
}
