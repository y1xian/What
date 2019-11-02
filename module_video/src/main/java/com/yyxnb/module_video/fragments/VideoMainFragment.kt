package com.yyxnb.module_video.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyxnb.module_video.R
import kotlinx.android.synthetic.main.fragment_video_main.*


/**
 * A simple [Fragment] subclass.
 */
class VideoMainFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvStart.setOnClickListener {
        }
    }


}
