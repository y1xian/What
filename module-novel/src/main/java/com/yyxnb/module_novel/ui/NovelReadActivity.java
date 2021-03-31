package com.yyxnb.module_novel.ui;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.yyxnb.common_base.base.BaseActivity;
import com.yyxnb.common_res.service.impl.LoginImpl;
import com.yyxnb.common_res.service.impl.UserImpl;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.adapter.NovelCategoryAdapter;
import com.yyxnb.module_novel.bean.BookChapterBean;
import com.yyxnb.module_novel.bean.BookInfoBean;
import com.yyxnb.module_novel.bean.BookShelfBean;
import com.yyxnb.module_novel.databinding.ActivityNovelReadBinding;
import com.yyxnb.module_novel.db.NovelDatabase;
import com.yyxnb.module_novel.view.dialog.DialogVisibleListener;
import com.yyxnb.module_novel.view.dialog.NovelReadSettingDialog;
import com.yyxnb.module_novel.view.page.BookManager;
import com.yyxnb.module_novel.view.page.BrightnessUtils;
import com.yyxnb.module_novel.view.page.IOUtils;
import com.yyxnb.module_novel.view.page.PageLoader;
import com.yyxnb.module_novel.view.page.PageView;
import com.yyxnb.module_novel.view.page.ReadSettingManager;
import com.yyxnb.module_novel.view.page.TxtChapter;
import com.yyxnb.module_novel.viewmodel.NovelViewModel;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.arch.annotations.BarStyle;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.arch.annotations.BindViewModel;
import com.yyxnb.what.core.DpUtils;
import com.yyxnb.what.core.StatusBarUtils;
import com.yyxnb.what.core.log.LogUtils;
import com.yyxnb.what.dialog.core.BaseDialog;
import com.yyxnb.what.dialog.core.MessageDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import me.jessyan.autosize.utils.ScreenUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 阅读页
 *
 * @author yyx
 */
@BindRes(statusBarStyle = BarStyle.LIGHT_CONTENT)
public class NovelReadActivity extends BaseActivity {

    private static final int WHAT_CATEGORY = 1;
    private static final int WHAT_CHAPTER = 2;

    // 注册 Brightness 的 uri
    private final Uri BRIGHTNESS_MODE_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);
    private final Uri BRIGHTNESS_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    private final Uri BRIGHTNESS_ADJ_URI =
            Settings.System.getUriFor("screen_auto_brightness_adj");

    private ConstraintLayout clHead;
    private LinearLayout llBottom;
    private DrawerLayout mDrawerLayout;
    private ImageView ivPic;
    private ImageView ivBack;
    private ImageView ivDownload;
    private ImageView ivMenuBack;
    private ImageView ivCategory;
    private ImageView ivNight;
    private ImageView ivSetting;
    private ImageView ivMsg;
    private ImageView ivMore;
    private TextView tvPageTip;
    private TextView tvPreChapter;
    private TextView tvNextChapter;
    private TextView tvJoin;
    private TextView tvNum;
    private SeekBar sbChapterProgress;
    private PageView mPvPage;

    private RecyclerView mRecyclerView;
    private NovelCategoryAdapter mAdapter;

    private PageLoader mPageLoader;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;

//    private CollBookBean mCollBook;

    private boolean isCollected = false; // isFromSDCard
    private boolean isNightMode = false;
    private boolean isFullScreen = false;
    private boolean isRegistered = false;
    private boolean isBookShelf = false; //是否在书架

    private List<BookChapterBean> bookChapterBeans;

    private String bookPath = "";
    private String cst = "laozi";
    private int selection = 0;
    private String identCode;

    private Timer timer = new Timer();
    private int cnt = 0; //计算时长

    //控制屏幕常亮
    private PowerManager.WakeLock mWakeLock;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WHAT_CATEGORY:
                    mRecyclerView.scrollToPosition(mPageLoader.getChapterPos());
                    break;
                case WHAT_CHAPTER:
                    mPageLoader.openChapter();
                    break;
                default:
                    break;
            }
        }
    };

    @BindViewModel
    NovelViewModel mViewModel;

    private ActivityNovelReadBinding binding;

    private BookInfoBean bookInfoBean;

    private int bookId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_novel_read);
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public int initLayoutResId() {
//        return R.layout.activity_novel_read;
//    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void initView(Bundle savedInstanceState) {

        clHead = binding.clHead;
        llBottom = binding.llBottom;
        mDrawerLayout = binding.mDrawerLayout;
        ivPic = binding.ivPic;
        ivBack = binding.ivBack;
        ivDownload = binding.ivDownload;
        ivMenuBack = binding.ivMenuBack;
        ivCategory = binding.ivCategory;
        ivNight = binding.ivNight;
        ivSetting = binding.ivSetting;
        ivMsg = binding.ivMsg;
        ivMore = binding.ivMore;
        tvPageTip = binding.tvPageTip;
        tvPreChapter = binding.tvPreChapter;
        tvNextChapter = binding.tvNextChapter;
        tvJoin = binding.tvJoin;
        tvNum = binding.tvNum;
        sbChapterProgress = binding.sbChapterProgress;
        mPvPage = binding.mPageView;
        mRecyclerView = binding.rvContent;

        bookId = getInt("bookId");

        if (LoginImpl.getInstance().isLogin()) {
            isBookShelf = NovelDatabase.getInstance().bookShelfDao().isInBookshelf(bookId);
        }


        log("-- " + bookId + " , " + isBookShelf);

        mAdapter = new NovelCategoryAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator())
                .setSupportsChangeAnimations(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                setChapter(position);
                mDrawerLayout.closeDrawer(Gravity.START);
                mPageLoader.skipToChapter(position);
                toast(mAdapter.getItem(position).name);
            }
        });

        bookInfoBean = NovelDatabase.getInstance().bookHomeDao().getBook(bookId);

        //获取页面加载器
        mPageLoader = mPvPage.getPageLoader(bookInfoBean);
        mPageLoader.setBookName(bookInfoBean.title);
        if (selection == 0) {
            selection = mPageLoader.getChapterPos();
        } else {
//            setChapter(selection);
            mPageLoader.skipToChapter(selection);
        }

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, intentFilter);

        //设置当前Activity的Brightness
        if (ReadSettingManager.getInstance().isBrightnessAuto()) {
            BrightnessUtils.setDefaultBrightness(this);
        } else {
            BrightnessUtils.setBrightness(this, ReadSettingManager.getInstance().getBrightness());
        }

        //初始化屏幕常亮类
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = Objects.requireNonNull(pm).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "keep bright");

        setOnClickListener(ivNight, ivSetting, ivMsg, tvPreChapter, tvNextChapter,
                ivSetting, ivMore, ivBack, ivMenuBack, ivCategory, ivDownload, tvJoin);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivBack) {
            exit();
        } else if (id == R.id.ivNight) {
            // 日/夜间模式
            if (isNightMode) {
                isNightMode = false;
                //设置当前 Activity 的亮度
                BrightnessUtils.setBrightness(this, 100);
                //存储亮度的进度条
                ReadSettingManager.getInstance().setBrightness(100);
            } else {
                isNightMode = true;
                //设置当前 Activity 的亮度
                BrightnessUtils.setBrightness(this, 60);
                //存储亮度的进度条
                ReadSettingManager.getInstance().setBrightness(60);
            }
            mPageLoader.setNightMode(isNightMode);
            toggleNightMode();
        } else if (id == R.id.ivSetting) {
            // 设置
            toggleMenu(false);
            NovelReadSettingDialog.Builder settingDialog = new NovelReadSettingDialog.Builder(getContext());
            settingDialog.setPageLoader(mPageLoader);
            settingDialog.show();
            settingDialog.setDismissListener(new DialogVisibleListener() {
                @Override
                public void onDialogShow() {

                }

                @Override
                public void onDialogDismiss() {
//                    tvJoin.setVisibility(GONE);
                    hideReadMenu();
                }
            });
        } else if (id == R.id.ivMsg) {
            // 评论

        } else if (id == R.id.tvPreChapter) {
            // 上一章
            if (mPageLoader.skipPreChapter()) {
                setChapter(mPageLoader.getChapterPos());
            } else {
                toast("当前已是第一章");
            }
        } else if (id == R.id.tvNextChapter) {
            // 下一章
            if (mPageLoader.skipNextChapter()) {
                setChapter(mPageLoader.getChapterPos());
            } else {
                toast("当前已是最后一章");
            }
        } else if (id == R.id.ivCategory) {
            // 菜单
            //移动到指定位置
            if (mAdapter.getData().size() > 0) {
                mRecyclerView.scrollToPosition(mPageLoader.getChapterPos());
            }
            //切换菜单
            toggleMenu(true);
            //打开侧滑动栏
            mDrawerLayout.openDrawer(Gravity.START);

            mAdapter.notifyDataSetChanged();
        } else if (id == R.id.ivMenuBack) {
            // 关闭菜单
            mDrawerLayout.closeDrawers();
        } else if (id == R.id.ivMore) {
            // 更多
        } else if (id == R.id.ivDownload) {
            // 下载
        } else if (id == R.id.tvJoin) {
            // 加入书架
            if (LoginImpl.getInstance().isLogin()) {
                BookShelfBean bookShelfBean = new BookShelfBean();
                bookShelfBean.bookInfoBean = bookInfoBean;
                bookShelfBean.userId = UserImpl.getInstance().getUserInfo().getUserId();
                bookShelfBean.addTime = System.currentTimeMillis();
                NovelDatabase.getInstance().bookShelfDao().insertItem(bookShelfBean);
                tvJoin.setVisibility(GONE);
                tvJoin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_right));
                isBookShelf = true;
                toast("加入成功");
            } else {
                LoginImpl.getInstance().start(getContext());
            }
        } else if (id == R.id.ivPic) {
            // 首次进入阅读
            ivPic.setVisibility(GONE);
            toggleMenu(true);
        }
    }

    @Override
    public void initViewData() {

        isNightMode = ReadSettingManager.getInstance().isNightMode();


        //禁止滑动展示DrawerLayout
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //侧边打开后，返回键能够起作用
        mDrawerLayout.setFocusableInTouchMode(false);

        mPvPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                return !hideReadMenu();
            }

            @Override
            public void center() {
                toggleMenu(true);
            }

            @Override
            public void prePage() {
            }

            @Override
            public void nextPage() {
            }

            @Override
            public void cancel() {
            }
        });

        mPageLoader.setOnPageChangeListener(new PageLoader.OnPageChangeListener() {
            @Override
            public void onChapterChange(int pos) {
                setChapter(pos);
            }

            @Override
            public void requestChapters(List<TxtChapter> requestChapters) {
                for (TxtChapter chapter : requestChapters) {
                    mViewModel.reqChapterDetail(cst, chapter.getChapterId());
                }
                mHandler.sendEmptyMessage(WHAT_CATEGORY);
                //隐藏提示
                tvPageTip.setVisibility(GONE);

                LogUtils.list(requestChapters);
//                LogUtils.INSTANCE.w("requestChapters " + requestChapters.size());
            }

            @Override
            public void onCategoryFinish(List<TxtChapter> chapters) {
//                LogUtils.INSTANCE.list(chapters);
                log("onCategoryFinish " + chapters.size());
            }

            @Override
            public void onPageCountChange(int count) {
                sbChapterProgress.setMax(Math.max(0, count - 1));
                sbChapterProgress.setProgress(0);
                // 如果处于错误状态，那么就冻结使用
                if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING
                        || mPageLoader.getPageStatus() == PageLoader.STATUS_ERROR) {
                    sbChapterProgress.setEnabled(false);
                } else {
                    sbChapterProgress.setEnabled(true);
                }
                log("onPageCountChange " + count);
            }

            @Override
            public void onPageChange(int pos) {
                sbChapterProgress.setProgress(pos);
                log("onPageChange " + pos);
            }
        });

        sbChapterProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (llBottom.getVisibility() == VISIBLE) {
                    //显示标题
                    tvPageTip.setText((progress + 1) + "/" + (sbChapterProgress.getMax() + 1));
                    tvPageTip.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //进行切换
                int pagePos = sbChapterProgress.getProgress();
                if (pagePos != mPageLoader.getPagePos()) {
                    mPageLoader.skipToPage(pagePos);
                }
                //隐藏提示
                tvPageTip.setVisibility(GONE);
            }
        });


        //初始化TopMenu
        initTopMenu();

        //初始化BottomMenu
        initBottomMenu();

        //夜间模式按钮的状态
        toggleNightMode();
    }

    @Override
    public void initObservable() {

//        mViewModel.reqBookData(bookId).observe(this, data -> {
//            if (data != null) {
//                bookHomeBean = data;
//                log(data.toString());
//            }
//        });


        if (bookInfoBean != null) {
            mViewModel.reqChapterList(bookInfoBean.label);
//            mViewModel.reqChapterDetail(bookHomeBean.label, "1");

            cst = bookInfoBean.label;
        }

        mViewModel.chapterList.observe(this, data -> {
            if (data != null) {
//                list(data);
                bookChapterBeans = data;

                mPageLoader.setBookChapters(bookChapterBeans);
                mPageLoader.refreshChapterList();

                if (selection == 0) {
                    selection = mPageLoader.getChapterPos();
                }
                setChapter(selection);
                mAdapter.setDataItems(bookChapterBeans);

                tvNum.setText("连载中 共" + mAdapter.getData().size() + "章");
            }
        });

        mViewModel.chapterDetail.observe(this, data -> {
            if (data != null) {
                log(data.toString());
                post(() -> {

                    StringBuilder builder = new StringBuilder();
                    builder.append(data.content);
                    if (!TextUtils.isEmpty(data.commentary)) {
                        builder.append("\n注释：\n").append(data.commentary);
                    }
                    if (!TextUtils.isEmpty(data.translation)) {
                        builder.append("\n翻译：\n").append(data.translation);
                    }
                    if (!TextUtils.isEmpty(data.appreciation)) {
                        builder.append("\n赏析：\n").append(data.appreciation);
                    }
                    if (!TextUtils.isEmpty(data.interpretation)) {
                        builder.append("\n解读：\n").append(data.interpretation);
                    }

                    saveChapterInfo(bookInfoBean.bookId + "", data.name, builder.toString());

                    if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
                        mHandler.sendEmptyMessage(WHAT_CHAPTER);
                    }
                });
            }
        });


    }

    /**
     * 储存本地 章节内容
     *
     * @param bookId
     * @param fileName
     * @param content
     */
    public void saveChapterInfo(String bookId, String fileName, String content) {
        File file = BookManager.getBookFile(bookId, fileName);
        loge(file.getAbsolutePath());
        //获取流并存储
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            IOUtils.close(writer);
        }
    }

    private void setChapter(int position) {
        bookChapterBeans.get(selection).selected = false;
//        for (BookChapterBean chapterBean : bookChapterBeans){
//            chapterBean.setSelected(false);
//        }
        selection = position;
        bookChapterBeans.get(position).selected = (true);
        mAdapter.setDataItems(bookChapterBeans);
//        mAdapter.notifyDataSetChanged();
    }

    private void initTopMenu() {
        if (Build.VERSION.SDK_INT >= 19) {
            clHead.setPadding(0, ScreenUtils.getStatusBarHeight(), 0, 0);
        }
    }

    private void initBottomMenu() {
        //判断是否全屏
        if (ReadSettingManager.getInstance().isFullScreen()) {
            //还需要设置mBottomMenu的底部高度
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) llBottom.getLayoutParams();
            params.bottomMargin = DpUtils.getNavigationBarHeight(getContext());
            llBottom.setLayoutParams(params);
        } else {
            //设置mBottomMenu的底部距离
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) llBottom.getLayoutParams();
            params.bottomMargin = 0;
            llBottom.setLayoutParams(params);
        }
    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private boolean hideReadMenu() {
        hideSystemBar();
        if (clHead.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return true;
//        } else if (mSettingDialog.isShowing()) {
//            mSettingDialog.dismiss();
//            return true;
        }
        return false;
    }

    private void showSystemBar() {
        //显示
        StatusBarUtils.setStatusBarHidden(getWindow(), false);

        if (isFullScreen) {
//            SystemBarUtils.showUnStableNavBar(this);
            StatusBarUtils.setNavigationBarHidden(getWindow(), false);
        }
    }

    private void hideSystemBar() {
        //隐藏
        StatusBarUtils.setStatusBarHidden(getWindow(), true);
        if (isFullScreen) {
//            SystemBarUtils.hideStableNavBar(this);
            StatusBarUtils.setNavigationBarHidden(getWindow(), true);
        }
    }

    private void toggleNightMode() {
        if (isNightMode) {
            ivNight.setImageResource(R.mipmap.icon_xs_yd_rjms);
        } else {
            ivNight.setImageResource(R.mipmap.icon_xs_yd_yjms);
        }
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu(boolean hideStatusBar) {
        initMenuAnim();

        if (clHead.getVisibility() == View.VISIBLE) {
            //关闭
            clHead.startAnimation(mTopOutAnim);
            llBottom.startAnimation(mBottomOutAnim);
            clHead.setVisibility(GONE);
            llBottom.setVisibility(GONE);
            tvPageTip.setVisibility(GONE);
            if (!isBookShelf) {
                tvJoin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_right));
                tvJoin.setVisibility(GONE);
            }

            if (hideStatusBar) {
                hideSystemBar();
            }
        } else {
            clHead.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
            clHead.startAnimation(mTopInAnim);
            llBottom.startAnimation(mBottomInAnim);
            if (!isBookShelf) {
                tvJoin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
                tvJoin.setVisibility(VISIBLE);
            }

            showSystemBar();
        }
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) {
            return;
        }

        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        //退出的速度要快
//        mTopOutAnim.setDuration(200);
//        mBottomOutAnim.setDuration(200);
    }

    // 接收电池信息和时间更新的广播
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                mPageLoader.updateBattery(level);
            }
            // 监听分钟的变化
            else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                mPageLoader.updateTime();
            }
        }
    };

    // 亮度调节监听
    // 由于亮度调节没有 Broadcast 而是直接修改 ContentProvider 的。所以需要创建一个 Observer 来监听 ContentProvider 的变化情况。
    private ContentObserver mBrightObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);

            // 判断当前是否跟随屏幕亮度，如果不是则返回
            if (selfChange /*|| !mSettingDialog.isBrightFollowSystem()*/) {
                return;
            }

            // 如果系统亮度改变，则修改当前 Activity 亮度
            if (BRIGHTNESS_MODE_URI.equals(uri)) {
                Log.d("---", "亮度模式改变");
            } else if (BRIGHTNESS_URI.equals(uri) && !BrightnessUtils.isAutoBrightness(NovelReadActivity.this)) {
                Log.d("---", "亮度模式为手动模式 值改变");
                BrightnessUtils.setBrightness(NovelReadActivity.this, BrightnessUtils.getScreenBrightness(NovelReadActivity.this));
            } else if (BRIGHTNESS_ADJ_URI.equals(uri) && BrightnessUtils.isAutoBrightness(NovelReadActivity.this)) {
                Log.d("---", "亮度模式为自动模式 值改变");
                BrightnessUtils.setDefaultBrightness(NovelReadActivity.this);
            } else {
                Log.d("---", "亮度调整 其他");
            }
        }
    };

    // 注册亮度观察者
    private void registerBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (!isRegistered) {
                    final ContentResolver cr = getContentResolver();
                    cr.unregisterContentObserver(mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_MODE_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_ADJ_URI, false, mBrightObserver);
                    isRegistered = true;
                }
            }
        } catch (Throwable throwable) {

        }
    }

    //解注册
    private void unregisterBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (isRegistered) {
                    getContentResolver().unregisterContentObserver(mBrightObserver);
                    isRegistered = false;
                }
            }
        } catch (Throwable throwable) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBrightObserver();
    }

    @SuppressLint("WakelockTimeout")
    @Override
    protected void onResume() {
        super.onResume();
        if (mWakeLock != null) {
            mWakeLock.acquire();
        }
        if (LoginImpl.getInstance().isLogin()) {
            isBookShelf = NovelDatabase.getInstance().bookShelfDao().isInBookshelf(bookId);
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cnt++;
                    }
                });
            }
        };
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, 0, 1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
        mPageLoader.saveRecord();
        timer.cancel();
        timer = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBrightObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);

        mHandler.removeMessages(WHAT_CATEGORY);
        mHandler.removeMessages(WHAT_CHAPTER);

        mPageLoader.closeBook();
        mPageLoader = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isVolumeTurnPage = ReadSettingManager
                .getInstance().isVolumeTurnPage();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToPrePage();
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToNextPage();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawers();
                return;
            }
            initExit();
        }
    }

    private void initExit() {
        if (!isBookShelf) {
            new MessageDialog.Builder(getContext())
                    // 标题可以不用填写
//                        .setTitle("我是标题")
                    // 内容必须要填写
                    .setMessage("喜欢本书就加入书架吧")
                    // 确定按钮文本
                    .setConfirm("确定")
                    // 设置 null 表示不显示取消按钮
                    .setCancel("取消")
                    // 设置点击按钮后不关闭对话框
//                        .setAutoDismiss(false)
//                        .setCancelable(false)
                    .setListener(new MessageDialog.OnListener() {

                        @Override
                        public void onConfirm(BaseDialog dialog) {

                            if (LoginImpl.getInstance().isLogin()) {
                                BookShelfBean bookShelfBean = new BookShelfBean();
                                bookShelfBean.bookInfoBean = bookInfoBean;
                                bookShelfBean.userId = UserImpl.getInstance().getUserInfo().getUserId();
                                bookShelfBean.addTime = System.currentTimeMillis();
                                NovelDatabase.getInstance().bookShelfDao().insertItem(bookShelfBean);
                                tvJoin.setVisibility(GONE);
                                isBookShelf = true;
                                toast("加入成功");
                                // 给点时间 避免泄漏
                                postAtTime(() -> exit(), 500);
                            } else {
                                LoginImpl.getInstance().start(getContext());
                            }

                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                            exit();
                        }
                    })
                    .show();
        } else {
            exit();
        }
    }

    // 退出
    private void exit() {

        super.onBackPressed();
    }
}