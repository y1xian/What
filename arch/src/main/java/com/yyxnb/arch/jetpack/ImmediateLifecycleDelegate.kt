package com.yyxnb.arch.jetpack

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Looper
import java.util.*

class ImmediateLifecycleDelegate(private val lifecycleOwner: LifecycleOwner) : LifecycleObserver {

    private val tasks = LinkedList<Runnable>()

    private var executing: Boolean = false

    private val isAtLeastStarted: Boolean
        get() = lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)

    private val lifecycle: Lifecycle
        get() = lifecycleOwner.lifecycle

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun scheduleTaskAtStarted(runnable: Runnable) {
        if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
            assertMainThread()
            tasks.add(runnable)
            considerExecute()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    internal fun onStateChange() {
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            tasks.clear()
            lifecycle.removeObserver(this)
        } else {
            considerExecute()
        }
    }

    private fun considerExecute() {
        if (isAtLeastStarted && !executing) {
            executing = true
            var runnable: Runnable? = tasks.poll()
            while (runnable != null) {
                runnable.run()
                runnable = tasks.poll()
            }
            executing = false
        }
    }

    private fun assertMainThread() {
        if (!isMainThread) {
            throw IllegalStateException("you should perform the task at main thread.")
        }
    }

    companion object {

        internal val isMainThread: Boolean
            get() = Looper.getMainLooper().thread === Thread.currentThread()
    }

}
