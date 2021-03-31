package com.yyxnb.module_novel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_novel.R;
import com.yyxnb.module_novel.adapter.BookShelfAdapter;
import com.yyxnb.module_novel.bean.BookShelfBean;
import com.yyxnb.module_novel.databinding.FragmentBookShelfBinding;
import com.yyxnb.module_novel.db.NovelDatabase;
import com.yyxnb.what.adapter.ItemDecoration;
import com.yyxnb.what.adapter.SimpleOnItemClickListener;
import com.yyxnb.what.adapter.base.BaseViewHolder;
import com.yyxnb.what.arch.annotations.BindRes;
import com.yyxnb.what.dialog.core.BaseDialog;
import com.yyxnb.what.dialog.core.MessageDialog;

import java.util.List;

/**
 * 小说 书架.
 */
@BindRes
public class BookShelfFragment extends BaseFragment {

    private FragmentBookShelfBinding binding;
    private BookShelfAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_book_shelf;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();
        mRecyclerView = binding.rvContent;

        mAdapter = new BookShelfAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ItemDecoration decoration = new ItemDecoration(getContext());
        decoration.setDividerHeight(16)
                .setDividerWidth(16)
                .setOnlySetItemOffsetsButNoDraw(true);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                // 直接阅读
                Intent intent = new Intent(getContext(), NovelReadActivity.class);
                intent.putExtra("bookId", mAdapter.getItem(position).bookInfoBean.bookId);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, BaseViewHolder holder, int position) {
                // 移除书架
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
                                NovelDatabase.getInstance().bookShelfDao().deleteItem(mAdapter.getItem(position));
                                mAdapter.removeDataItem(position);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();

                return super.onItemLongClick(view, holder, position);
            }
        });
    }

    @Override
    public void onVisible() {
        List<BookShelfBean> shelfBeans = NovelDatabase.getInstance().bookShelfDao().getAllList();
        mAdapter.setDataItems(shelfBeans);
        list(shelfBeans);
    }
}