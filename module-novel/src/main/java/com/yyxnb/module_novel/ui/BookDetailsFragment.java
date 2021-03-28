package com.yyxnb.module_novel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.common_res.service.impl.LoginImpl;
import com.yyxnb.common_res.service.impl.UserImpl;
import com.yyxnb.what.arch.annotations.BindViewModel;
import com.yyxnb.what.dialog.core.BaseDialog;
import com.yyxnb.what.dialog.core.MessageDialog;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.bean.BookInfoBean;
import com.yyxnb.module_novel.bean.BookShelfBean;
import com.yyxnb.module_novel.databinding.FragmentBookDetailsBinding;
import com.yyxnb.module_novel.db.NovelDatabase;
import com.yyxnb.module_novel.viewmodel.NovelViewModel;

/**
 * 书籍详情
 */
public class BookDetailsFragment extends BaseFragment {

    @BindViewModel
    NovelViewModel mViewModel;
    private FragmentBookDetailsBinding binding;

    private TextView tvAddBookshelf;


    public static BookDetailsFragment newInstance(int bookId) {

        Bundle args = new Bundle();
        args.putInt("bookId", bookId);
        BookDetailsFragment fragment = new BookDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int bookId;

    private BookInfoBean bookInfoBean;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_book_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        tvAddBookshelf = binding.tvAddBookshelf;
        setOnClickListener(binding.tvAddBookshelf, binding.tvRead, tvAddBookshelf);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_add_bookshelf) {
            // 加入书架
            if (LoginImpl.getInstance().isLogin()) {
                BookShelfBean bookShelfBean = NovelDatabase.getInstance().bookShelfDao().getDataById(bookId);
                if (bookShelfBean == null) {
                    bookShelfBean = new BookShelfBean();
                    bookShelfBean.bookInfoBean = bookInfoBean;
                    bookShelfBean.userId = UserImpl.getInstance().getUserInfo().getUserId();
                    bookShelfBean.addTime = System.currentTimeMillis();
                    NovelDatabase.getInstance().bookShelfDao().insertItem(bookShelfBean);
                    tvAddBookshelf.setText(getString(R.string.novel_remove_bookshelf));
                } else {
                    // 移除书架
                    BookShelfBean finalBookShelfBean = bookShelfBean;
                    new MessageDialog.Builder(getContext())
                            // 标题可以不用填写
//                        .setTitle("我是标题")
                            // 内容必须要填写
                            .setMessage("是否移除本书")
                            // 确定按钮文本
                            .setConfirm("确定移除")
                            // 设置 null 表示不显示取消按钮
                            .setCancel("取消")
                            // 设置点击按钮后不关闭对话框
//                        .setAutoDismiss(false)
//                        .setCancelable(false)
                            .setListener(new MessageDialog.OnListener() {

                                @Override
                                public void onConfirm(BaseDialog dialog) {
                                    NovelDatabase.getInstance().bookShelfDao().deleteItem(finalBookShelfBean);
                                    tvAddBookshelf.setText(getString(R.string.novel_put_in_bookshelf));
                                }

                                @Override
                                public void onCancel(BaseDialog dialog) {
                                }
                            })
                            .show();
                }
            } else {
                LoginImpl.getInstance().start(getContext());
            }
        } else if (v.getId() == R.id.tv_read) {
            // 开始阅读
            Intent intent = new Intent(getContext(), NovelReadActivity.class);
            intent.putExtra("bookId", bookId);
            startActivity(intent);
        }
    }

    @Override
    public void initViewData() {
        bookId = getInt("bookId");

        mViewModel.reqBookData(bookId).observe(this, data -> {
            if (data != null) {
                bookInfoBean = data;
                log(data.toString());
                binding.setData(data);
            }
        });

        if (LoginImpl.getInstance().isLogin()) {
            BookShelfBean bookShelfBean = NovelDatabase.getInstance().bookShelfDao().getDataById(bookId);
            if (bookShelfBean != null) {
                tvAddBookshelf.setText(getString(R.string.novel_remove_bookshelf));
            }
        }

    }

    @Override
    public void onVisible() {
        if (LoginImpl.getInstance().isLogin()) {
            tvAddBookshelf.setText(NovelDatabase.getInstance().bookShelfDao().isInBookshelf(bookId) ? getString(R.string.novel_remove_bookshelf) : getString(R.string.novel_put_in_bookshelf));
        }
    }
}