package com.yyxnb.module_main.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.yyxnb.what.arch.base.IActivity;
import com.yyxnb.what.core.KeyboardUtils;
import com.yyxnb.module_main.R;
import com.yyxnb.module_main.databinding.ActivityMainSearchBinding;

/**
 * 搜索
 */
public class MainSearchActivity extends AppCompatActivity implements IActivity {

    private ActivityMainSearchBinding binding;

//    @Override
//    public int initLayoutResId() {
//        return R.layout.activity_main_search;
//    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_search);

        binding.etInput.setFocusable(true);
        binding.etInput.setFocusableInTouchMode(true);
        binding.etInput.requestFocus();

//        runOnUiThread(new TimerTask() {
//            @Override
//            public void run() {
                KeyboardUtils.showSoftInput(binding.etInput);
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