package com.yyxnb.module_widget.ui;

import android.arch.paging.PagedListAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.SimpleOnItemClickListener;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.common_base.base.AbsListFragment;
import com.yyxnb.dialog.core.BaseDialog;
import com.yyxnb.dialog.core.MessageDialog;
import com.yyxnb.module_widget.R;
import com.yyxnb.module_widget.adapter.MainListAdapter;
import com.yyxnb.module_widget.bean.MainBean;
import com.yyxnb.module_widget.view.dialog.HintDialog;
import com.yyxnb.module_widget.view.dialog.InputDialog;
import com.yyxnb.module_widget.view.dialog.MenuDialog;
import com.yyxnb.module_widget.view.dialog.SelectDialog;
import com.yyxnb.module_widget.view.dialog.WaitDialog;
import com.yyxnb.module_widget.viewmodel.DialogViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 对话框 使用
 */
@BindRes
public class DialogFragment extends AbsListFragment<MainBean, DialogViewModel> {

    private MainListAdapter mAdapter = new MainListAdapter();

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRefreshLayout.setEnableRefresh(false).setEnableLoadMore(false).setEnableOverScrollDrag(true);

        mAdapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                setMenu(mAdapter.getData().get(position).id);
            }
        });

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            if (mAdapter.getData().get(position).type == 1) {
                return 2;
            }
            return 1;
        });
        mRecyclerView.setLayoutManager(manager);
        decoration.setDividerWidth(5);
        decoration.setDividerHeight(5);
        decoration.setDrawBorderTopAndBottom(true);
        decoration.setDrawBorderLeftAndRight(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setMenu(int position) {
        switch (position) {
            case 1:
                // 消息对话框
                new MessageDialog.Builder(getContext())
                        // 标题可以不用填写
                        .setTitle("我是标题")
                        // 内容必须要填写
                        .setMessage("我是内容")
                        // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel))
                        // 设置点击按钮后不关闭对话框
//                        .setAutoDismiss(false)
//                        .setCancelable(false)
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog) {
                                toast("确定了");
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                toast("取消了");
                            }
                        })
                        .show();
                break;
            case 2:
                // 输入对话框
                new InputDialog.Builder(getContext())
                        // 标题可以不用填写
                        .setTitle("我是标题")
                        // 内容可以不用填写
                        .setContent("我是内容")
                        // 提示可以不用填写
                        .setHint("我是提示")
                        // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel))
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(new InputDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog, String content) {
                                toast("确定了：" + content);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                toast("取消了");
                            }
                        })
                        .show();
                break;
            case 3:
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    data.add("我是数据" + (i + 1));
                }
                // 底部选择框
                new MenuDialog.Builder(getContext())
                        // 设置 null 表示不显示取消按钮
                        //.setCancel(getString(R.string.common_cancel))
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setList(data)
                        .setListener(new MenuDialog.OnListener<String>() {

                            @Override
                            public void onSelected(BaseDialog dialog, int position, String string) {
                                toast("位置：" + position + "，文本：" + string);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                toast("取消了");
                            }
                        })
                        .show();
                break;
            case 4:
                List<String> data1 = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    data1.add("我是数据" + (i + 1));
                }
                // 居中选择框
                new MenuDialog.Builder(getContext())
                        .setGravity(Gravity.CENTER)
                        // 设置 null 表示不显示取消按钮
                        .setCancel(null)
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setList(data1)
                        .setListener(new MenuDialog.OnListener<String>() {

                            @Override
                            public void onSelected(BaseDialog dialog, int position, String string) {
                                toast("位置：" + position + "，文本：" + string);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                toast("取消了");
                            }
                        })
                        .show();
                break;
            case 5:
                // 单选对话框
                new SelectDialog.Builder(getContext())
                        .setTitle("请选择你的性别")
                        .setList("男", "女")
                        // 设置单选模式
                        .setSingleSelect()
                        // 设置默认选中
                        .setSelect(0)
                        .setListener(new SelectDialog.OnListener<String>() {

                            @Override
                            public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                                toast("确定了：" + data.toString());
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                toast("取消了");
                            }
                        })
                        .show();
                break;
            case 6:
                // 多选对话框
                new SelectDialog.Builder(getContext())
                        .setTitle("请选择工作日")
                        .setList("星期一", "星期二", "星期三", "星期四", "星期五")
                        // 设置最大选择数
                        .setMaxSelect(3)
                        // 设置默认选中
                        .setSelect(2, 3, 4)
                        .setListener(new SelectDialog.OnListener<String>() {

                            @Override
                            public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                                toast("确定了：" + data.toString());
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                toast("取消了");
                            }
                        })
                        .show();
                break;
            case 7:
                // 成功对话框
                new HintDialog.Builder(getContext())
                        .setIcon(HintDialog.ICON_FINISH)
                        .setMessage("完成")
                        .show();
                break;
            case 8:
                // 失败对话框
                new HintDialog.Builder(getContext())
                        .setIcon(HintDialog.ICON_ERROR)
                        .setMessage("错误")
                        .show();
                break;
            case 9:
                // 警告对话框
                new HintDialog.Builder(getContext())
                        .setIcon(HintDialog.ICON_WARNING)
                        .setMessage("警告")
                        .show();
                break;
            case 10:
                // 等待对话框
                final WaitDialog.Builder waitDialog = new WaitDialog.Builder(getContext());
                // 消息文本可以不用填写
                waitDialog.setMessage(getString(R.string.widget_loading))
                        .show();
                postDelayed(() -> waitDialog.setMessage("文字发生变化了"), 2000);
                postDelayed(waitDialog::dismiss, 4000);
                break;
            default:
                break;
        }
    }

    @Override
    public PagedListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void initViewData() {

    }

    //使用默认浏览器打开链接
//    public boolean openUrl(String url) {
//        try {
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
//            return true;
//        } catch (Exception e) {
//            if (DEBUGMODE) {
//                e.printStackTrace();
//            }
//            return false;
//        }
//    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}