package com.yyxnb.module_main.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.yyxnb.arch.base.IActivity
import com.yyxnb.module_main.R

/**
 * 启动页
 *
 * @author yyx
 */
class SplashActivity : AppCompatActivity(), IActivity {

    override fun initLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {
        // 白屏时间 + 2秒进入首页 ≈ 3~5秒
        handler.sendEmptyMessageDelayed(1, 2 * 1000.toLong())
    }

    private val handler = Handler { message: Message ->
        if (message.what == 1) {
            inMain()
        }
        false
    }

    private fun inMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 避免内存泄漏
        handler.removeCallbacksAndMessages(null)
    }
}