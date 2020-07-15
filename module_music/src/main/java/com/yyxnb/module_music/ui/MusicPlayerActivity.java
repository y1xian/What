package com.yyxnb.module_music.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.yyxnb.common_base.base.BaseActivity;
import com.yyxnb.module_music.R;

/**
 * 播放音乐Activity
 */
public class MusicPlayerActivity extends BaseActivity {

    public static void start(Activity context) {
        Intent intent = new Intent(context, MusicPlayerActivity.class);
        ActivityCompat.startActivity(context, intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(context).toBundle());
    }

    @Override
    public int initLayoutResId() {
        return R.layout.activity_music_player;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}