package com.yyxnb.module_server;

import android.util.Log;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yyxnb.util_app.AppUtils;
import com.yyxnb.util_core.log.LogUtils;

import java.util.concurrent.TimeUnit;

public class ServerManager {

    private static volatile ServerManager mInstance = null;

    public static ServerManager getInstance() {
        if (null == mInstance) {
            synchronized (ServerManager.class) {
                if (null == mInstance) {
                    mInstance = new ServerManager();
                }
            }
        }
        return mInstance;
    }

    private Server mServer;

    /**
     * Create server.
     */
    private ServerManager() {
        mServer = AndServer.webServer(AppUtils.getApp())
                .port(8080)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        // TODO The server started successfully.
                        LogUtils.w("onStarted");
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

    /**
     * Start server.
     */
    public void startServer() {
        if (mServer.isRunning()) {
            // TODO The server is already up.
        } else {
            mServer.startup();
        }
    }

    /**
     * Stop server.
     */
    public void stopServer() {
        if (mServer.isRunning()) {
            mServer.shutdown();
        } else {
            Log.w("AndServer", "The server has not started yet.");
        }
    }
}