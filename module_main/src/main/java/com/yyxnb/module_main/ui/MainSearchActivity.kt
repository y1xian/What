package com.yyxnb.module_main.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yyxnb.arch.base.IActivity
import com.yyxnb.common.utils.KeyboardUtils.showSoftInput
import com.yyxnb.module_main.R
import com.yyxnb.module_main.databinding.ActivityMainSearchBinding

/**
 * 搜索
 */
class MainSearchActivity : AppCompatActivity(), IActivity {

    private var binding: ActivityMainSearchBinding? = null

    //    @Override
    //    public int initLayoutResId() {
    //        return R.layout.activity_main_search;
    //    }

    override fun initView(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_search)
        binding!!.etInput.isFocusable = true
        binding!!.etInput.isFocusableInTouchMode = true
        binding!!.etInput.requestFocus()

//        runOnUiThread(new TimerTask() {
//            @Override
//            public void run() {
        showSoftInput(binding!!.etInput)
        //            }
//        });
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//                           @Override
//                           public void run() {
//                               InputMethodManager inputManager =
//                                       (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                               inputManager.showSoftInput(binding.etInput, 0);
//                           }
//                       },
//                100);
//        KeyboardUtils.showSoftInput(binding.etInput);
    }
}