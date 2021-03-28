package com.yyxnb.what.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * ================================================
 * 作    者：yyx
 * 日    期：2021/03/12
 * 描    述：BaseService
 * ================================================
 */
public abstract class BaseService extends Service {

    private static final String TAG = "BaseService";

    /**
     * 绑定服务时才会调用,必须要实现的方法
     * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate invoke");
        super.onCreate();
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     * * <p>
     * 当另一个组件（如 Activity）通过调用 startService() 请求启动服务时，系统将调用此方法。一旦执行此方法，服务即会启动并可在后台无限期运行。
     * 如果自己实现此方法，则需要在服务工作完成后，通过调用 stopSelf() 或 stopService() 来停止服务。（在绑定状态下，无需实现此方法。）
     *
     * @param intent  启动时，启动组件传递过来的Intent，如Activity可利用Intent封装所需要的参数并传递给Service
     * @param flags   表示启动请求时是否有额外数据
     * @param startId 指明当前服务的唯一ID，与stopSelfResult (int startId)配合使用，stopSelfResult 可以更安全地根据ID停止服务
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand invoke");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 当服务不再使用且将被销毁时，系统将调用此方法。服务应该实现此方法来清理所有资源，如线程、注册的侦听器、接收器等，这是服务接收的最后一个调用。
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy invoke");
        super.onDestroy();
    }
}
