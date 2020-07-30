package com.yyxnb.module_widget.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yyxnb.common_base.base.BaseFragment;
import com.yyxnb.module_widget.R;
import com.yyxnb.module_widget.databinding.FragmentDialogBinding;

import static com.yyxnb.dialog.DialogManager.DEBUGMODE;

/**
 * 对话框 使用
 */
public class DialogFragment extends BaseFragment {

    private FragmentDialogBinding binding;

    private SmartRefreshLayout refreshLayout;


    @Override
    public int initLayoutResId() {
        return R.layout.fragment_dialog;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        refreshLayout = binding.refreshLayout;


        setEvents();
    }

    @Override
    public void initViewData() {

    }


    private void setEvents() {


    }

    //使用默认浏览器打开链接
    public boolean openUrl(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        } catch (Exception e) {
            if (DEBUGMODE) {
                e.printStackTrace();
            }
            return false;
        }
    }
}