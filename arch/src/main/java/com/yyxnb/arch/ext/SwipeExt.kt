package com.yyxnb.arch.ext

import android.view.View
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.calculator.ScaledCalculator
import com.billy.android.swipe.consumer.SpaceConsumer

@JvmOverloads
fun View?.wrap(scale: Float = 0.2F): SmartSwipeWrapper =
        SmartSwipe.wrap(this)
                .addConsumer(SpaceConsumer().setSwipeDistanceCalculator(ScaledCalculator(scale)))
                .enableVertical().wrapper
