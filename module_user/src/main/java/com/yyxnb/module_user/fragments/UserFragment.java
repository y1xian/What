package com.yyxnb.module_user.fragments;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yyxnb.arch.annotations.BarStyle;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.common.SPUtils;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.module_base.arouter.ARouterUtils;
import com.yyxnb.module_base.base.BaseFragment;
import com.yyxnb.module_user.R;
import com.yyxnb.module_user.databinding.FragmentUserBinding;
import com.yyxnb.module_user.fragments.wallet.UserWalletFragment;
import com.yyxnb.skinloader.SkinManager;
import com.yyxnb.skinloader.util.AssetFileUtils;
import com.yyxnb.utils.permission.PermissionListener;
import com.yyxnb.utils.permission.PermissionUtils;

import java.io.File;

import static com.yyxnb.module_base.arouter.ARouterConstant.LOGIN_FRAGMENT;
import static com.yyxnb.module_base.arouter.ARouterConstant.USER_FRAGMENT;
import static com.yyxnb.module_base.config.Constants.SKIN_PATH;

/**
 * 我的 - 界面.
 */
@BindRes(statusBarStyle = BarStyle.LightContent)
@Route(path = USER_FRAGMENT)
public class UserFragment extends BaseFragment {

    private FragmentUserBinding binding;

    private CheckBox mCheckBox;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        binding = getBinding();
        mCheckBox = binding.mCheckBox;


        binding.clHead.setOnClickListener(v -> {
            startFragment(ARouterUtils.navFragment(LOGIN_FRAGMENT));
        });
        binding.ivWallet.setOnClickListener(v -> {
            startFragment(new UserWalletFragment());
        });

        mCheckBox.setChecked(!SkinManager.get().isUsingDefaultSkin());

        mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                SkinManager.get().restoreToDefaultSkin();
                SPUtils.clear(SKIN_PATH);
            } else {
                changeSkin();
            }
        });

    }

    @SuppressWarnings("ConstantConditions")
    private void changeSkin() {
        // 存储权限
        PermissionUtils.with(getActivity())
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
                        //将assets目录下的皮肤文件拷贝到data/data/.../cache目录下
                        String saveDir = getActivity().getCacheDir().getAbsolutePath() + "/skins";
                        //将打包生成的apk文件, 重命名为'xxx.skin', 防止apk结尾的文件造成混淆.
                        String savefileName = "/night.skin";
                        String asset_dir = "skins/night.apk";
                        File file = new File(saveDir + File.separator + savefileName);
//                        if (!file.exists()) {
                            AssetFileUtils.copyAssetFile(getActivity(), asset_dir, saveDir, savefileName);
//                        }
                        LogUtils.w(" " + file.getAbsolutePath());
                        SPUtils.setParam(SKIN_PATH, file.getAbsolutePath());
                        SkinManager.get().loadSkin(file.getAbsolutePath());
                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                    }
                })
                .createConfig()
                .setForceAllPermissionsGranted(true)
                .buildConfig()
                .startCheckPermission();

    }

    @Override
    public void onInVisible() {
        super.onInVisible();
        LogUtils.d("---onInVisible---");
    }

    @Override
    public void onVisible() {
        super.onVisible();
        getBaseDelegate().setNeedsStatusBarAppearanceUpdate();
        LogUtils.d("---onVisible---");
    }
}
