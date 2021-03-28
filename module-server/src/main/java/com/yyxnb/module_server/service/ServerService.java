package com.yyxnb.module_server.service;

import android.content.Intent;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yyxnb.module_server.ServerManager;
import com.yyxnb.what.core.NetworkUtils;
import com.yyxnb.what.core.log.LogUtils;
import com.yyxnb.what.service.BaseService;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class ServerService extends BaseService {

    private Server mServer;

    @Override
    public void onCreate() {
        super.onCreate();

        mServer = AndServer.webServer(this)
                .port(8080)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        // TODO The server started successfully.
                        try {
                            InetAddress address = NetworkUtils.getLocalIPAddress();
                            LogUtils.w(String.format("onStarted : http:/%s:8080", address));
                            ServerManager.getInstance().onServerStart(ServerService.this, address.getHostAddress());
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtils.e("无网络或开了飞行模式，获取不到ip地址");
                        }
                    }

                    @Override
                    public void onStopped() {
                        // TODO The server has stopped.
                        LogUtils.w("onStopped");
                        ServerManager.getInstance().onServerStop(ServerService.this);
                    }

                    @Override
                    public void onException(Exception e) {
                        // TODO An exception occurred while the server was starting.
                        e.printStackTrace();
                        LogUtils.e("onException : " + e.getMessage());
                        ServerManager.getInstance().onServerError(ServerService.this, e.getMessage());
                    }
                })
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startServer();
        return START_STICKY;
    }

    /**
     * 启动服务.
     */
    private void startServer() {
        mServer.startup();
    }

    /**
     * 停止服务.
     */
    private void stopServer() {
        mServer.shutdown();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopServer();
    }
}
