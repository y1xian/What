package com.yyxnb.http.config

import android.os.Environment
import android.text.TextUtils
import com.yyxnb.http.utils.SSLUtils
import com.yyxnb.http.RetrofitMultiUrl
import com.yyxnb.http.interceptor.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit


/**
 * 自定义OkHttpClient
 */
object OkHttpConfig {

    private val defaultCachePath = Environment.getExternalStorageDirectory().absolutePath + "/httpCacheData"
    private const val defaultCacheSize = (1024 * 1024 * 50).toLong()

    private var okHttpClientBuilder = OkHttpClient.Builder()

    lateinit var okHttpClient: OkHttpClient

    private var mDelayTime: Long = 10L


    class Builder {
        private var headerMaps = HashMap<String, Any>()
        private var logEnable: Boolean = true
        private var isCache: Boolean = false
        private var cachePath: String = defaultCachePath
        private var cacheMaxSize: Long = defaultCacheSize
        private var isSaveCookie: Boolean = false
        private var readTimeout: Long = mDelayTime
        private var writeTimeout: Long = mDelayTime
        private var connectTimeout: Long = mDelayTime
        private var bksFile: InputStream? = null
        private var password: String? = null
        private var certificates: Array<out InputStream>? = null
        private var interceptors: Array<out Interceptor>? = null

        /**
         * 添加请求头
         */
        fun setHeaders(headerMaps: HashMap<String, Any>): Builder {
            this.headerMaps = headerMaps
            return this
        }

        /**
         * 是否打开请求log日志
         */
        fun setLogEnable(logEnable: Boolean): Builder {
            this.logEnable = logEnable
            return this
        }

        /**
         * 是否缓存
         */
        fun setCache(isCache: Boolean): Builder {
            this.isCache = isCache
            return this
        }

        /**
         * 缓存路径
         */
        fun setCachePath(cachePath: String): Builder {
            this.cachePath = cachePath
            return this
        }

        /**
         * 缓存大小
         */
        fun setCacheMaxSize(cacheMaxSize: Long): Builder {
            this.cacheMaxSize = cacheMaxSize
            return this
        }

        /**
         * 是否保存Cookie
         */
        fun setSaveCookie(isSaveCookie: Boolean): Builder {
            this.isSaveCookie = isSaveCookie
            return this
        }

        /**
         * 读取超时时间
         */
        fun setReadTimeout(readTimeout: Long): Builder {
            this.readTimeout = readTimeout
            return this
        }

        /**
         * 写入超时时间
         */
        fun setWriteTimeout(writeTimeout: Long): Builder {
            this.writeTimeout = writeTimeout
            return this
        }

        /**
         * 连接超时时间
         */
        fun setConnectTimeout(connectTimeout: Long): Builder {
            this.connectTimeout = connectTimeout
            return this
        }

        /**
         * 设置所有超时
         */
        fun setTimeout(second: Long): Builder {
            setReadTimeout(second)
            setWriteTimeout(second)
            setConnectTimeout(second)
            return this
        }

        /**
         * 添加自定义拦截器
         */
        fun setAddInterceptor(vararg interceptors: Interceptor): Builder {
            this.interceptors = interceptors
            return this
        }

        /**
         * 信任所有证书,不安全有风险（默认信任所有证书）
         */
        fun setSslSocketFactory(vararg certificates: InputStream): Builder {
            this.certificates = certificates
            return this
        }

        /**
         * 使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
         */
        fun setSslSocketFactory(bksFile: InputStream, password: String, vararg certificates: InputStream): Builder {
            this.bksFile = bksFile
            this.password = password
            this.certificates = certificates
            return this
        }


        fun build(): OkHttpClient {

            setCookieConfig()
            setCacheConfig()
            setHeadersConfig()
            setSslConfig()
            addInterceptors()
            setTimeout()
            setDebugConfig()
            RetrofitMultiUrl.with(okHttpClientBuilder)

            okHttpClient = okHttpClientBuilder.build()
            return okHttpClient
        }

        /**
         * 添加拦截器
         */
        private fun addInterceptors() {
            if (null != interceptors) {
                for (interceptor in interceptors!!) {
                    okHttpClientBuilder.addInterceptor(interceptor)
                }
            }
        }

        /**
         * log
         */
        private fun setDebugConfig() {
            if (logEnable) {
                val logInterceptor = HttpLoggingInterceptor(RxHttpLogger())
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(logInterceptor)
            }
        }

        /**
         * 配置headers
         */
        private fun setHeadersConfig() {
            okHttpClientBuilder.addInterceptor(HeaderInterceptor(headerMaps))
        }

        /**
         * 配置cookie保存到sp文件中
         */
        private fun setCookieConfig() {
            if (isSaveCookie) {
                okHttpClientBuilder
                        .addInterceptor(AddCookiesInterceptor())
                        .addInterceptor(ReceivedCookiesInterceptor())
            }

        }

        /**
         * 配置缓存
         */
        private fun setCacheConfig() {
            if (isCache) {
                val cache: Cache
                if (!TextUtils.isEmpty(cachePath) && cacheMaxSize > 0) {
                    cache = Cache(File(cachePath), cacheMaxSize)
                } else {
                    cache = Cache(File(defaultCachePath), defaultCacheSize)
                }
                okHttpClientBuilder
                        .cache(cache)
                        .addInterceptor(NoNetCacheInterceptor())
                        .addNetworkInterceptor(NetCacheInterceptor())
            }
        }

        /**
         * 配置超时信息
         */
        private fun setTimeout() {
            okHttpClientBuilder.readTimeout(readTimeout, TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS)
            okHttpClientBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS)
            okHttpClientBuilder.retryOnConnectionFailure(true)
        }

        /**
         * 配置证书
         */
        private fun setSslConfig() {
            var sslParams: SSLUtils.SSLParams? = null

            if (null == certificates) {
                //信任所有证书,不安全有风险
                sslParams = SSLUtils.sslSocketFactory
            } else {
                if (null != bksFile && !TextUtils.isEmpty(password)) {
                    //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(bksFile, password, *certificates!!)
                } else {
                    //使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(*certificates!!)
                }
            }

            okHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)

        }
    }

}