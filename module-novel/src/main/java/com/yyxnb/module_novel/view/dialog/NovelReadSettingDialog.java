package com.yyxnb.module_novel.view.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.adapter.NovelPageStyleAdapter;
import com.yyxnb.module_novel.view.page.BrightnessUtils;
import com.yyxnb.module_novel.view.page.PageLoader;
import com.yyxnb.module_novel.view.page.PageMode;
import com.yyxnb.module_novel.view.page.PageStyle;
import com.yyxnb.module_novel.view.page.ReadSettingManager;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.dialog.core.BaseDialog;

import java.util.Arrays;

public class NovelReadSettingDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private PageLoader mPageLoader;
        private PageMode mPageMode;
        private PageStyle mPageStyle;
        private ReadSettingManager mSettingManager;

        private ImageView mIvBrightnessMinus;
        private SeekBar mSbBrightness;
        private ImageView mIvBrightnessPlus;
        private TextView mTvFontMinus;
        private TextView mTvFont;
        private TextView mTvFontPlus;
        private RadioGroup mRgPageMode;
        private RadioButton mRbSimulation;
        private RadioButton mRbCover;
        private RadioButton mRbSlide;
        private RadioButton mRbScroll;
        private RadioButton mRbNone;
        private RecyclerView mRecyclerView;

        private NovelPageStyleAdapter mAdapter;

        private DialogVisibleListener dismissListener;

        private int mBrightness;
        private int mTextSize;


        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_novel_read_setting_layout);
            setAnimStyle(BaseDialog.ANIM_BOTTOM).setBackgroundDimAmount(0.1f);

            initViews();
        }


        public Builder setDismissListener(DialogVisibleListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }


        public Builder setPageLoader(PageLoader mPageLoader) {
            this.mPageLoader = mPageLoader;
            return this;
        }


        public void initViews() {

            mRgPageMode = findViewById(R.id.read_setting_rg_page_mode);
            mIvBrightnessMinus = findViewById(R.id.read_setting_iv_brightness_minus);
            mSbBrightness = findViewById(R.id.read_setting_sb_brightness);
            mIvBrightnessPlus = findViewById(R.id.read_setting_iv_brightness_plus);
            mTvFontMinus = findViewById(R.id.read_setting_tv_font_minus);
            mTvFont = findViewById(R.id.read_setting_tv_font);
            mTvFontPlus = findViewById(R.id.read_setting_tv_font_plus);
            mRbSimulation = findViewById(R.id.read_setting_rb_simulation);
            mRbCover = findViewById(R.id.read_setting_rb_cover);
            mRbSlide = findViewById(R.id.read_setting_rb_slide);
            mRbScroll = findViewById(R.id.read_setting_rb_scroll);
            mRbNone = findViewById(R.id.read_setting_rb_none);
            mRecyclerView = findViewById(R.id.rvContent);


            initData();
            initWidget();

            //Page Mode 切换
            mRgPageMode.setOnCheckedChangeListener(
                    (group, checkedId) -> {
                        PageMode pageMode;
                        if (checkedId == R.id.read_setting_rb_simulation) {
                            pageMode = PageMode.SIMULATION;
                        } else if (checkedId == R.id.read_setting_rb_cover) {
                            pageMode = PageMode.COVER;
                        } else if (checkedId == R.id.read_setting_rb_slide) {
                            pageMode = PageMode.SLIDE;
                        } else if (checkedId == R.id.read_setting_rb_scroll) {
                            pageMode = PageMode.SCROLL;
                        } else if (checkedId == R.id.read_setting_rb_none) {
                            pageMode = PageMode.NONE;
                        } else {
                            pageMode = PageMode.SIMULATION;
                        }
                        mPageLoader.setPageMode(pageMode);
                    }
            );

            //亮度调节
            mIvBrightnessMinus.setOnClickListener(
                    (v) -> {
                        int progress = mSbBrightness.getProgress() - 1;
                        if (progress < 0) {
                            return;
                        }
                        mSbBrightness.setProgress(progress);
                        BrightnessUtils.setBrightness(getActivity(), progress);
                    }
            );
            mIvBrightnessPlus.setOnClickListener(
                    (v) -> {
                        int progress = mSbBrightness.getProgress() + 1;
                        if (progress > mSbBrightness.getMax()) {
                            return;
                        }
                        mSbBrightness.setProgress(progress);
                        BrightnessUtils.setBrightness(getActivity(), progress);
                        //设置进度
                        ReadSettingManager.getInstance().setBrightness(progress);
                    }
            );

            mSbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int progress = seekBar.getProgress();
                    //设置当前 Activity 的亮度
                    BrightnessUtils.setBrightness(getActivity(), progress);
                    //存储亮度的进度条
                    ReadSettingManager.getInstance().setBrightness(progress);
                }
            });

            //字体大小调节
            mTvFontMinus.setOnClickListener(
                    (v) -> {

                        int fontSize = Integer.valueOf(mTvFont.getText().toString()) - 1;
                        if (fontSize < 5) {
                            return;
                        }
                        mTvFont.setText(fontSize + "");
                        mPageLoader.setTextSize(fontSize);
                    }
            );

            mTvFontPlus.setOnClickListener(
                    (v) -> {
                        int fontSize = Integer.valueOf(mTvFont.getText().toString()) + 1;
                        if (fontSize > 80) {
                            return;
                        }
                        mTvFont.setText(fontSize + "");
                        mPageLoader.setTextSize(fontSize);
                    }
            );

            mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
                @Override
                public void onItemClick(View view, BaseViewHolder holder, int position) {
                    super.onItemClick(view, holder, position);
                    mPageLoader.setPageStyle(PageStyle.values()[position]);
                    mAdapter.setPagePosition(position);
                    mAdapter.notifyDataSetChanged();
//                LiveEventBus.get().with("NightMode").postValue("");
                }
            });

            if (dismissListener != null) {
                dismissListener.onDialogShow();
            }

        }

        @Override
        public void dismiss() {
            super.dismiss();
            if (dismissListener != null) {
                dismissListener.onDialogDismiss();
            }
        }

        private void initData() {
            mSettingManager = ReadSettingManager.getInstance();

            mBrightness = mSettingManager.getBrightness();
            mTextSize = mSettingManager.getTextSize();
//        isTextDefault = mSettingManager.isDefaultTextSize();
            mPageMode = mSettingManager.getPageMode();
            mPageStyle = mSettingManager.getPageStyle();
        }

        private void initWidget() {
            mSbBrightness.setProgress(mBrightness);
            mTvFont.setText(mTextSize + "");
//        mCbBrightnessAuto.setChecked(isBrightnessAuto);
//        mCbFontDefault.setChecked(isTextDefault);
            initPageMode();
            //RecyclerView
            setUpAdapter();
        }

        private void setUpAdapter() {

            Drawable[] drawables = {
                    getDrawable(R.color.nb_read_bg_1)
                    , getDrawable(R.color.nb_read_bg_2)
                    , getDrawable(R.color.nb_read_bg_3)
                    , getDrawable(R.color.nb_read_bg_4)
                    , getDrawable(R.color.nb_read_bg_5)};

            mAdapter = new NovelPageStyleAdapter();
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setDataItems(Arrays.asList(drawables));
            mAdapter.setPageStyleChecked(mPageStyle);

        }

//        public Drawable getDrawable(int drawRes) {
//            return ContextCompat.getDrawable(getContext(), drawRes);
//        }

        private void initPageMode() {
            switch (mPageMode) {
                case SIMULATION:
                    mRbSimulation.setChecked(true);
                    break;
                case COVER:
                    mRbCover.setChecked(true);
                    break;
                case SLIDE:
                    mRbSlide.setChecked(true);
                    break;
                case NONE:
                    mRbNone.setChecked(true);
                    break;
                case SCROLL:
                    mRbScroll.setChecked(true);
                    break;
                default:
                    break;
            }
        }
    }

}
