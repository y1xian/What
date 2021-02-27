package com.yyxnb.module_server;

import android.Manifest;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yyxnb.common_base.core.BaseFragment;
import com.yyxnb.module_server.databinding.FragmentServerBinding;
import com.yyxnb.util_core.NetworkUtils;
import com.yyxnb.util_core.log.LogUtils;
import com.yyxnb.util_permission.PermissionListener;
import com.yyxnb.util_permission.PermissionUtils;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/02/26
 * 描    述：Http API
 * ================================================
 */
@Route(path = "/server/ServerMainFragment")
public class ServerFragment extends BaseFragment {

    private FragmentServerBinding binding;
    private Server mServer;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_server;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        binding = getBinding();

        binding.btnStatus.setOnClickListener(v -> {
//            binding.btnStatus.setSelected(!binding.btnStatus.isSelected());
            if (mServer.isRunning()) {
                mServer.shutdown();
                binding.btnStatus.setText("未启动");
            } else {
                mServer.startup();
                binding.btnStatus.setText("已启动");
            }

        });

        PermissionUtils.with(getActivity())
                .addPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
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
    public void initViewData() {
        mServer = AndServer.webServer(getContext())
                .port(8080)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        // TODO The server started successfully.
                        InetAddress address = NetworkUtils.getLocalIPAddress();
                        LogUtils.w("onStarted : " + address);
                    }

                    @Override
                    public void onStopped() {
                        // TODO The server has stopped.
                        LogUtils.w("onStopped");
                    }

                    @Override
                    public void onException(Exception e) {
                        // TODO An exception occurred while the server was starting.
                        e.printStackTrace();
                        LogUtils.e("onException : " + e.getMessage());
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        mServer.shutdown();
        super.onDestroy();
    }
}