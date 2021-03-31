package com.yyxnb.what.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PermissionFragment extends Fragment {
    private String[] permissions = null;
    public static final int PERMISSION_REQUEST_CODE = 1001;
    public static final int REQUEST_PERMISSION_SETTING = 1002;
    private PermissionListener permissionCheckListener;
    private WeakReference<Activity> mContext;

    private PermissionConfig checkConfig;

    private String forceDeniedPermissionTips = "";

    public PermissionFragment setPermissionCheckListener(PermissionListener listener) {
        this.permissionCheckListener = listener;
        return this;
    }

    public static PermissionFragment newInstance(String[] permissions, PermissionConfig checkConfig) {
        Bundle args = new Bundle();
        args.putStringArray("permissions", permissions);
        args.putParcelable("config", checkConfig);
        PermissionFragment fragment = new PermissionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 开始请求
     */
    public void start(Activity activity) {
        if (activity != null) {
            mContext = new WeakReference<>(activity);
            if (Looper.getMainLooper() != Looper.myLooper()) {
                return;
            }
            activity.getFragmentManager().beginTransaction().add(this, activity.getClass().getName()).commit();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        forceDeniedPermissionTips = "请前往设置->应用->【" + PermissionUtils.getAppName(mContext.get()) + "】->权限中打开相关权限，否则功能无法正常运行！";

        //获取传输过来的权限
        permissions = this.getArguments().getStringArray("permissions");
        checkConfig = this.getArguments().getParcelable("config");

        if (checkConfig != null && TextUtils.isEmpty(checkConfig.getForceDeniedPermissionTips())) {
            checkConfig.setForceDeniedPermissionTips(forceDeniedPermissionTips);
        }

        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
            requestPermissionsSuccess();
        } else {
            requestPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            //记录点击了不再提醒的未授权权限
            List<String> forceDeniedPermissions = new ArrayList<>();
            //记录点击了普通的未授权权限
            List<String> normalDeniedPermissions = new ArrayList<>();
            List<String> grantedPermissions = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                String permission = permissions[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    //授权通过 nothing to do
                    grantedPermissions.add(permission);
                } else {
                    //授权拒绝
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(mContext.get(), permission)) {
                        forceDeniedPermissions.add(permission);
                    } else {
                        normalDeniedPermissions.add(permission);
                    }
                }
            }
            if (forceDeniedPermissions.size() == 0 && normalDeniedPermissions.size() == 0) {
                //全部授权通过
                requestPermissionsSuccess();
            } else {
                //部分授权通过 如果用户希望一直提示授权直到给权限位置 那么就一直去请求权限
                if (checkConfig != null && checkConfig.isForceAllPermissionsGranted()) {
                    if (normalDeniedPermissions.size() != 0) {
                        //还有普通拒绝的权限可以弹窗
                        requestPermission();
                    } else {
                        //所有没有通过的权限都是用户点击了不再提示的 我擦 这里本来是想把未授权的所有权限的名称列出来展示的 后来想想觉得配置有点麻烦
//                        StringBuilder deniedString = new StringBuilder();
//                        for (String forceDeniedPermission : forceDeniedPermissions) {
//                            deniedString.append(forceDeniedPermission + ",");
//                        }
//                        String denied = deniedString.substring(0, deniedString.length() - 1);
                        new AlertDialog.Builder(mContext.get())
                                .setTitle("警告")
                                .setMessage(checkConfig == null ? forceDeniedPermissionTips : checkConfig.getForceDeniedPermissionTips())
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        openSettingPage();
                                    }
                                }).show();
                    }
                } else {
                    for (String permission : this.permissions) {
                        if (grantedPermissions.contains(permission)
                                || normalDeniedPermissions.contains(permission)
                                || forceDeniedPermissions.contains(permission)) {

                        } else {
                            //如果三者都不包含他 包名这个权限不是隐私权限 直接给就完事了 所以要放到已授权的权限列表里面去
                            grantedPermissions.add(permission);
                        }
                    }
                    requestPermissionsFail(grantedPermissions.toArray(new String[grantedPermissions.size()]),
                            normalDeniedPermissions.toArray(new String[normalDeniedPermissions.size()]),
                            forceDeniedPermissions.toArray(new String[forceDeniedPermissions.size()]));
                }
            }
        }
    }

    private void openSettingPage() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.get().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
    }

    private void requestPermissionsSuccess() {
        if (permissionCheckListener != null) {
            permissionCheckListener.permissionRequestSuccess();
        }
        mContext.get().getFragmentManager().beginTransaction().remove(this).commit();
    }

    private void requestPermissionsFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
        if (permissionCheckListener != null) {
            permissionCheckListener.permissionRequestFail(grantedPermissions, deniedPermissions, forceDeniedPermissions);
        }
        mContext.get().getFragmentManager().beginTransaction().remove(this).commit();
    }

    /**
     * 获取权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermission() {
        //记录未授权的权限
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            int check = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (check == PackageManager.PERMISSION_GRANTED) {
                //授权通过了已经 do nothing
            } else {
                deniedPermissions.add(permission);
            }
        }
        if (deniedPermissions.size() != 0) {
            //有权限没有通过
            requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), PERMISSION_REQUEST_CODE);
        } else {
            //授权全部通过
            requestPermissionsSuccess();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            //设置页面回来了
            requestPermission();
        }
    }
}