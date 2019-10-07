package com.yyxnb.arch.base.nav

import android.support.annotation.AnimRes
import com.yyxnb.arch.R


/**
 * PresentAnimation
 */
enum class PresentAnimation private constructor(@field:AnimRes
                                                internal var enter: Int, @field:AnimRes
                                                internal var exit: Int, @field:AnimRes
                                                internal var popEnter: Int, @field:AnimRes
                                                internal var popExit: Int) {
    //滑动
    Push(R.anim.nav_slide_in_right, R.anim.nav_slide_out_left, R.anim.nav_slide_in_left, R.anim.nav_slide_out_right),
    //延迟
    Delay(R.anim.nav_delay, R.anim.nav_delay, R.anim.nav_delay, R.anim.nav_delay),
    //变淡
    Fade(R.anim.nav_fade_in, R.anim.nav_fade_out, R.anim.nav_fade_in, R.anim.nav_fade_out),
    //无
    None(R.anim.nav_none, R.anim.nav_none, R.anim.nav_none, R.anim.nav_none)

}
