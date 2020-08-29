package com.yyxnb.arch.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * 方便MsgEvent的使用
 */
object Bus {
    fun post(msgEvent: MsgEvent) {
        LiveEventBus.get(ArchConfig.MSG_EVENT, MsgEvent::class.java).post(msgEvent)
    }

    @JvmStatic
    fun post(msgEvent: MsgEvent, delay: Long) {
        LiveEventBus.get(ArchConfig.MSG_EVENT, MsgEvent::class.java).postDelay(msgEvent, delay)
    }

    fun broadcast(msgEvent: MsgEvent) {
        LiveEventBus.get(ArchConfig.MSG_EVENT, MsgEvent::class.java).broadcast(msgEvent, false, false)
    }

    @JvmStatic
    fun observe(owner: LifecycleOwner, observer: Observer<MsgEvent>) {
        LiveEventBus.get(ArchConfig.MSG_EVENT, MsgEvent::class.java).observe(owner, observer)
    }

    fun observeSticky(owner: LifecycleOwner, observer: Observer<MsgEvent>) {
        LiveEventBus.get(ArchConfig.MSG_EVENT, MsgEvent::class.java).observeSticky(owner, observer)
    }
}