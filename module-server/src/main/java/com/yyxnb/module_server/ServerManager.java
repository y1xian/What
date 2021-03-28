package com.yyxnb.module_server;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.yyxnb.module_server.service.ServerService;
import com.yyxnb.what.app.AppUtils;

import java.io.File;

public class ServerManager extends BroadcastReceiver {

    private static final String ACTION = "com.yyxnb.module_server.receiver";

    private static final String CMD_KEY = "CMD_KEY";
    private static final String MESSAGE_KEY = "MESSAGE_KEY";

    private static final int CMD_VALUE_START = 1;
    private static final int CMD_VALUE_ERROR = 2;
    private static final int CMD_VALUE_STOP = 4;

    @SuppressLint("StaticFieldLeak")
    private static volatile ServerManager mInstance = null;


    private final Context mContext;
    private final Intent mService;

    private ServerManager() {
        this.mContext = AppUtils.getApp();
        mService = new Intent(mContext, ServerService.class);
    }

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

    /**
     * Notify serverStart.
     *
     * @param context context.
     */
    public void onServerStart(Context context, String hostAddress) {
        sendBroadcast(context, CMD_VALUE_START, hostAddress);
    }

    /**
     * Notify serverStop.
     *
     * @param context context.
     */
    public void onServerError(Context context, String error) {
        sendBroadcast(context, CMD_VALUE_ERROR, error);
    }

    /**
     * Notify serverStop.
     *
     * @param context context.
     */
    public void onServerStop(Context context) {
        sendBroadcast(context, CMD_VALUE_STOP);
    }

    private void sendBroadcast(Context context, int cmd) {
        sendBroadcast(context, cmd, null);
    }

    private void sendBroadcast(Context context, int cmd, String message) {
        Intent broadcast = new Intent(ACTION);
        broadcast.putExtra(CMD_KEY, cmd);
        broadcast.putExtra(MESSAGE_KEY, message);
        context.sendBroadcast(broadcast);
    }


    /**
     * Register broadcast.
     */
    public void register() {
        IntentFilter filter = new IntentFilter(ACTION);
        mContext.registerReceiver(this, filter);
    }

    /**
     * UnRegister broadcast.
     */
    public void unRegister() {
        mContext.unregisterReceiver(this);
    }

    public void startServer() {
        mContext.startService(mService);
    }

    public void stopServer() {
        mContext.stopService(mService);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION.equals(action)) {

        }
    }

    /**
     * 上传文件存储地址
     */
    public File uploadTempDir() {
        return new File(mContext.getExternalCacheDir(), "_server_upload_cache_");
    }
}