package com.yyxnb.module_user.ui

import android.Manifest
import android.content.Context
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.tencent.mmkv.MMKV
import com.yyxnb.common.utils.log.LogUtils.e
import com.yyxnb.common.utils.log.LogUtils.w
import com.yyxnb.common_base.config.Constants.SKIN_PATH
import com.yyxnb.skinloader.SkinManager
import com.yyxnb.skinloader.util.AssetFileUtils
import com.yyxnb.utils.permission.PermissionListener
import com.yyxnb.utils.permission.PermissionUtils
import java.io.File

object UserPresenter {
    fun jumpSetUp() {}

    fun jumpWallet() {
//        ((context)BaseFragment).startFragment(new UserWalletFragment());
    }

    private fun toggleSkin(context: Context) {
        // 存储权限
        PermissionUtils.with(context as AppCompatActivity)
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionsCheckListener(object : PermissionListener {
                    override fun permissionRequestSuccess() {
                        //将assets目录下的皮肤文件拷贝到data/data/.../cache目录下
                        val saveDir = context.getCacheDir().absolutePath + "/skins"
                        //将打包生成的apk文件, 重命名为'xxx.skin', 防止apk结尾的文件造成混淆.
                        val savefileName = "/night.skin"
                        val asset_dir = "skins/night.apk"
                        val file = File(saveDir + File.separator + savefileName)
                        if (!file.exists()) {
                            AssetFileUtils.copyAssetFile(context, asset_dir, saveDir, savefileName)
                        }
                        w(" " + file.absolutePath)
                        MMKV.defaultMMKV().encode(SKIN_PATH, file.absolutePath)
                        SkinManager.get().loadSkin(file.absolutePath)
                    }

                    override fun permissionRequestFail(grantedPermissions: Array<String>, deniedPermissions: Array<String>, forceDeniedPermissions: Array<String>) {}
                })
                .createConfig()
                .setForceAllPermissionsGranted(true)
                .buildConfig()
                .startCheckPermission()
    }

    val isChecked: Boolean
        get() = !SkinManager.get().isUsingDefaultSkin

    @JvmStatic
    fun onCheckedChange(buttonView: CompoundButton, isChecked: Boolean) {
        e("ooo $isChecked")
        if (!isChecked) {
            SkinManager.get().restoreToDefaultSkin()
            MMKV.defaultMMKV().removeValueForKey(SKIN_PATH)
        } else {
            toggleSkin(buttonView.context)
        }
        buttonView.setOnCheckedChangeListener { buttonView1: CompoundButton, isChecked1: Boolean ->
            buttonView1.isChecked = isChecked1
            if (!isChecked1) {
                SkinManager.get().restoreToDefaultSkin()
                MMKV.defaultMMKV().removeValueForKey(SKIN_PATH)
            } else {
                toggleSkin(buttonView.context)
            }
        }

    }
}