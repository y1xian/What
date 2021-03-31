package com.yyxnb.what.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/*
        PermissionUtils.with(getActivity())
                //添加所有你需要申请的权限
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .addPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .addPermissions(Manifest.permission.CALL_PHONE)
                .addPermissions(Manifest.permission.ACCESS_WIFI_STATE)
                .addPermissions(Manifest.permission.CAMERA)
                //添加权限申请回调监听 如果申请失败 会返回已申请成功的权限列表，用户拒绝的权限列表和用户点击了不再提醒的永久拒绝的权限列表
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
                    //所有权限授权成功才会回调这里
                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                    //当有权限没有被授权就会回调这里
                    //会返回已申请成功的权限列表（grantedPermissions）
                    //用户拒绝的权限列表（deniedPermissions）
                    //用户点击了不再提醒的永久拒绝的权限列表（forceDeniedPermissions）
                    }
                })
                //生成配置
                .createConfig()
                //配置是否强制用户授权才可以使用，当设置为true的时候，如果用户拒绝授权，会一直弹出授权框让用户授权
                .setForceAllPermissionsGranted(true)
                //配置当用户点击了不再提示的时候，会弹窗指引用户去设置页面授权，这个参数是弹窗里面的提示内容
                .setForceDeniedPermissionTips("请前往设置->应用->【" + PermissionUtils.getAppName(MainActivity.this) + "】->权限中打开相关权限，否则功能无法正常运行！")
                //构建配置并生效
                .buildConfig()
                //开始授权
                .startCheckPermission();

                // 快速使用
                PermissionUtils.with(getActivity())
                .addPermissions(PermissionUtils.FILE_REQUIRE_PERMISSIONS)
                .defaultConfig();
 */

/**
 * 权限检查主要帮助类
 */
public class PermissionUtils {

    public static final String[] VOICE_REQUIRE_PERMISSIONS = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] CAMERA_REQUIRE_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] FILE_REQUIRE_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] CALL_PERMISSIONS = {Manifest.permission.CALL_PHONE};
    public static final String[] LOCATION_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public static final String[] READ_PHONE_STATE_PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};
    public static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA};

    //宿主Activity
    private final WeakReference<Activity> mContext;
    //回调监听
    private PermissionListener listener;
    //存储所有的权限列表
    private List<String> permissions = new ArrayList<>();

    private PermissionConfig checkConfig;

    private PermissionUtils(Activity mContext) {
        this.mContext = new WeakReference<>(mContext);
    }

    public static PermissionUtils with(Activity context) {
        return new PermissionUtils(context);
    }

    /**
     * 生成配置
     */
    public PermissionConfig createConfig() {
        checkConfig = new PermissionConfig(this);
        return checkConfig;
    }

    /**
     * 默认配置
     */
    public PermissionUtils defaultConfig() {
        checkConfig = new PermissionConfig(this);
        checkConfig.setForceAllPermissionsGranted(true);
        checkConfig.buildConfig();
        startCheckPermission();
        return this;
    }

    /**
     * 添加权限
     *
     * @param permission
     */
    public PermissionUtils addPermissions(String... permission) {
        for (String p : permission) {
            if (!permissions.contains(p)) {
                permissions.add(p);
            }
        }
        return this;
    }

    /**
     * 判断是否已开启权限
     *
     * @param context
     * @param permission
     */
    public static boolean hasPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        boolean hasPermissions = true;
        for (String permission : permissions) {
            hasPermissions = hasPermissions && hasPermission(context, permission);
        }
        return hasPermissions;
    }

    /**
     * 添加监听
     *
     * @param listener
     */
    public PermissionUtils setPermissionsCheckListener(PermissionListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 开始申请权限
     */
    public void startCheckPermission() {
        PermissionFragment.newInstance(permissions.toArray(new String[permissions.size()]), checkConfig)
                .setPermissionCheckListener(listener).start(mContext.get());
    }

    /**
     * 获取App的名称
     *
     * @param context 上下文
     * @return 名称
     */
    public static String getAppName(Context context) {
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //获取应用 信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //获取albelRes
            int labelRes = applicationInfo.labelRes;
            //返回App的名称
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}