package com.yyxnb.http

import android.app.Application
import android.content.Context
import android.util.Log
import com.yyxnb.http.config.OkHttpConfig
import com.yyxnb.http.gson.GsonAdapter
import com.yyxnb.http.network.LiveDataCallAdapterFactory
import com.yyxnb.http.utils.ISPKeys
import com.yyxnb.http.utils.SPUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.lang.ref.WeakReference
import java.util.*

/**
 * Retrofit封装
 */
object RetrofitManager : Serializable {

    private lateinit var mWeakReferenceContext: WeakReference<Context>

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    fun init(application: Application): RetrofitManager {
        mWeakReferenceContext = WeakReference(application.applicationContext)
        SPUtils.init(application)
        return this
    }

    val context: Context
        get() {
            if (null != mWeakReferenceContext) {
                return mWeakReferenceContext.get()!!.applicationContext
            }
            throw NullPointerException("u should init first")
        }

    private var mClient: OkHttpClient? = null

    private val mMultiUrl = RetrofitMultiUrl

    /**
     * Service 缓存-避免重复创建同一个Service
     */
    private var mServiceMap = HashMap<String, Any?>()

    private var mCacheEnable: Boolean = true

    private val mRetrofitBuilder = Retrofit.Builder().apply {
        addCallAdapterFactory(LiveDataCallAdapterFactory())
        addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()))
    }

    private val mRetrofit: Retrofit
        get() = mRetrofitBuilder.client(mClient ?: OkHttpConfig.okHttpClient).build()

    /**
     * 创建Service
     */
    fun <T> createApi(apiService: Class<T>): T {
        if (mCacheEnable) {
            if (mServiceMap.containsKey(apiService.name)) {
                Log.v("RetrofitManager", "className:" + apiService.name + ";service取自缓存")
                return mServiceMap[apiService.name] as T
            }
            val tClass = mRetrofit.create(apiService)
            mServiceMap[apiService.name] = tClass
            return tClass
        }
        return mRetrofit.create(apiService)
    }

    /**
     * 获取Cookie
     */
    val cookie: HashSet<String>
        get() = SPUtils.getParam(ISPKeys.COOKIE, HashSet<String>()) as HashSet<String>

    /**
     * 设置全局BaseUrl
     */
    fun setBaseUrl(baseUrl: String): RetrofitManager {
        mRetrofitBuilder.baseUrl(baseUrl)
        mMultiUrl.setGlobalBaseUrl(baseUrl)
        return this
    }

    /**
     * 设置自己的client
     */
    fun setOkClient(okClient: OkHttpClient): RetrofitManager {
        mClient = okClient
        return this
    }

    /**
     * Service 缓存
     */
    fun setCacheEnable(userCacheEnable: Boolean): RetrofitManager {
        mCacheEnable = userCacheEnable
        return this
    }

    /**
     * 控制管理器是否拦截,在每个域名地址都已经确定,不需要再动态更改时可设置为 false
     */
    fun setUrlInterceptEnable(enable: Boolean): RetrofitManager {
        mMultiUrl.setIntercept(enable)
        return this
    }

    /**
     * 是否Service Header设置多BaseUrl优先--默认method优先
     */
    fun setHeaderPriorityEnable(enable: Boolean): RetrofitManager {
        mMultiUrl.setHeaderPriorityEnable(enable)
        return this
    }

    /**
     * 存放多BaseUrl 映射关系service 设置header模式-需要才设置
     */
    fun putHeaderBaseUrl(map: Map<String, String>): RetrofitManager {
        mMultiUrl.putHeaderBaseUrl(map)
        return this
    }

    /**
     * 存放多BaseUrl 映射关系设置header模式-需要才设置
     */
    fun putHeaderBaseUrl(urlKey: String, urlValue: String): RetrofitManager {
        mMultiUrl.putHeaderBaseUrl(urlKey, urlValue)
        return this
    }

    /**
     * 存放多BaseUrl 映射关系method模式-需要才设置
     */
    fun putBaseUrl(map: Map<String, String>): RetrofitManager {
        mMultiUrl.putBaseUrl(map)
        return this
    }

    /**
     * 存放多BaseUrl 映射关系method模式-需要才设置
     */
    fun putBaseUrl(method: String, urlValue: String): RetrofitManager {
        mMultiUrl.putBaseUrl(method, urlValue)
        return this
    }

}
