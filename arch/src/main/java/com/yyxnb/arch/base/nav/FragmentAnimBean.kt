package com.yyxnb.arch.base.nav

import android.os.Parcel
import android.os.Parcelable
import com.yyxnb.arch.R

/**
 * fragment跳转的自定义动画
 * Push(R.anim.nav_slide_in_right, R.anim.nav_slide_out_left, R.anim.nav_slide_in_left, R.anim.nav_slide_out_right),
 */
class FragmentAnimBean constructor(var enter: Int = R.anim.nav_slide_in_right,
                                   var exit: Int = R.anim.nav_slide_out_left,
                                   var popEnter: Int = R.anim.nav_slide_in_left,
                                   var popExit: Int = R.anim.nav_slide_out_right) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(enter)
        parcel.writeInt(exit)
        parcel.writeInt(popEnter)
        parcel.writeInt(popExit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FragmentAnimBean> {
        override fun createFromParcel(parcel: Parcel): FragmentAnimBean {
            return FragmentAnimBean(parcel)
        }

        override fun newArray(size: Int): Array<FragmentAnimBean?> {
            return arrayOfNulls(size)
        }
    }


}
