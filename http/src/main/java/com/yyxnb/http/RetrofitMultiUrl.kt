package com.yyxnb.http


import android.text.TextUtils
import android.util.Log
import com.yyxnb.http.utils.IUrlParser
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

/**
 * Function:RetrofitMultiUrl 以简洁的 Api,让 Retrofit 不仅支持多 BaseUrl
 * 还可以在 App 运行时动态切换任意 BaseUrl,在多 BaseUrl 场景下也不会影响到其他不需要切换的 BaseUrl
 * Description:设置支持多BaseUrl
 */
object RetrofitMultiUrl {

    private val TAG = "RetrofitMultiUrl"
    private val BASE_URL_NAME = "BASE_URL_NAME"
    private val GLOBAL_BASE_URL_NAME = "GLOBAL_BASE_URL_NAME"
    /**
     * 用于单独设置其它BaseUrl的Service设置Header标记
     */
    @JvmField
    val BASE_URL_NAME_HEADER: String = "$BASE_URL_NAME: "

    /**
     * 是否开启拦截开始运行,可以随时停止运行,比如你在 App 启动后已经不需要在动态切换 baseUrl 了
     */
    private var mIsIntercept = false
    /**
     * 是否Service Header设置多BaseUrl优先
     */
    private var mHeaderPriorityEnable: Boolean = false
    /**
     * header模式baseUrl
     */
    private val mHeaderBaseUrlMap = HashMap<String, HttpUrl>()
    /**
     * 通过method控制不同BaseUrl集合
     */
    private val mBaseUrlMap = HashMap<String, HttpUrl>()
    private val mInterceptor: Interceptor
    private var mUrlParser: IUrlParser? = null
    /**
     * retrofit 设置的BaseUrl
     */
    private var mBaseUrl: String? = null

    /**
     * 获取全局BaseUrl
     */
    private val globalBaseUrl: HttpUrl?
        get() = mBaseUrlMap[GLOBAL_BASE_URL_NAME]

    init {
        //初始化解析器
        setUrlParser(object : IUrlParser {
            override fun parseUrl(domainUrl: HttpUrl, url: HttpUrl): HttpUrl {
                // 只支持 http 和 https
                if (null == domainUrl) {
                    return url
                }
                //解析得到service里的方法名(即@POST或@GET里的内容)--如果@GET 并添加参数 则为方法名+参数拼接
                val method = if (!TextUtils.isEmpty(mBaseUrl)) url.toString().replace(mBaseUrl!!.toString(), "") else ""
                return checkUrl(domainUrl.toString() + method)
            }
        })
        mInterceptor = Interceptor { chain ->
            val request = chain.request()
            // 可以在 App 运行时,随时通过 setIntercept(false) 来结束本管理器的拦截
            if (!isIntercept()) {
                return@Interceptor chain.proceed(request)
            }
            //如果请求url不包含默认的BaseUrl也不进行拦截
            if (request != null && request.url != null && !request.url.toString().contains(mBaseUrl!!)) {
                Log.i(TAG, "无统一BaseUrl不拦截:" + request.url + ";BaseUrl:" + mBaseUrl)
                return@Interceptor chain.proceed(request)
            }
            chain.proceed(processRequest(request))
        }
    }

    /**
     * 将 [OkHttpClient.Builder] 传入,配置一些本管理器需要的参数
     */
    fun with(builder: OkHttpClient.Builder): RetrofitMultiUrl? {
        builder.addInterceptor(mInterceptor)
        return this
    }

    /**
     * 对 [Request] 进行一些必要的加工
     */
    private fun processRequest(request: Request): Request {
        val newBuilder = request.newBuilder()
        var httpUrl = getHeaderHttpUrl(request, newBuilder)
        if (null != httpUrl) {
            val newUrl = mUrlParser!!.parseUrl(httpUrl, request.url)
            Log.i(TAG, "Header 模式重定向Url:Base Url is { " + mBaseUrl + " }" + ";New Url is { " + newUrl + " }" + ";Old Url is { " + request.url + " }")
            return newBuilder
                    .url(newUrl)
                    .build()
        }
        httpUrl = getMethodHttpUrl(request)
        if (null != httpUrl) {
            Log.i(TAG, "Method 模式重定向Url:Base Url is { " + mBaseUrl + " }" + ";New Url is { " + httpUrl + " }" + ";Old Url is { " + request.url + " }")
            return newBuilder
                    .url(httpUrl)
                    .build()
        }
        //重定向BaseUrl mBaseUrl是okhttp里设置的base url--程序运行过程中只有一个值--用于拆分method
        val httpUrlBase = globalBaseUrl
        if (httpUrlBase != null && httpUrlBase.toString() != mBaseUrl) {
            val httpNew = checkUrl(request.url.toString().replace(mBaseUrl!!, httpUrlBase.toString()))
            Log.i(TAG, "重定向Url:Base Url is { " + httpUrlBase.toString() + " }" + ";New Url is { " + httpNew + " }" + ";Old Url is { " + request.url + " }")
            return newBuilder
                    .url(httpNew)
                    .build()
        }
        return newBuilder.build()
    }

    /**
     * 获取以Header 优先的HttpUrl
     */
    private fun getHeaderHttpUrl(request: Request?, newBuilder: Request.Builder): HttpUrl? {
        if (request == null) {
            return null
        }
        var httpUrl: HttpUrl? = null
        //header 标记优先
        if (mHeaderPriorityEnable) {
            val keyName = getHeaderBaseUrlKey(request)
            // 如果有 header,获取 header 中配置的url,否则检查全局的 BaseUrl,未找到则为null
            if (!TextUtils.isEmpty(keyName)) {
                httpUrl = getHeaderBaseUrl(keyName)
                newBuilder.removeHeader(BASE_URL_NAME)
            } else {
                httpUrl = getHeaderBaseUrl(GLOBAL_BASE_URL_NAME)
            }
        }
        return httpUrl
    }

    /**
     * 获取以method 优先的HttpUrl
     */
    private fun getMethodHttpUrl(request: Request?): HttpUrl? {
        if (request == null) {
            return null
        }
        val httpUrl: HttpUrl? = null
        val url = request.url
        //解析得到service里的方法名(即@POST或@GET里的内容)
        val method = if (!TextUtils.isEmpty(mBaseUrl)) url.toString().replace(mBaseUrl!!.toString(), "") else ""

        var methodKey = method
        //包含? 很大可能是get请求增加了参数需过滤掉
        val isContainKey = methodKey.contains("?")
        if (isContainKey) {
            methodKey = methodKey.substring(0, methodKey.indexOf("?"))
        }
        Log.d(TAG, "Base Url is { " + mBaseUrl + " }" + ";Old Url is{" + url.newBuilder().toString() + "};Method is <<" + methodKey + ">>")
        //如果
        return if (!mHeaderPriorityEnable && mBaseUrlMap.containsKey(methodKey)) {
            checkUrl(getBaseUrl(methodKey)!!.toString() + method)
        } else httpUrl
    }

    /**
     * 是否拦截--可以在固定的时机停止[.setIntercept]
     */
    fun isIntercept(): Boolean {
        return mIsIntercept
    }

    /**
     * 控制管理器是否拦截,在每个域名地址都已经确定,不需要再动态更改时可设置为 false
     *
     * @param enable
     */
    fun setIntercept(enable: Boolean): RetrofitMultiUrl? {
        mIsIntercept = enable
        return this
    }

    /**
     * 是否Service Header设置多BaseUrl优先--默认method优先
     */
    fun setHeaderPriorityEnable(enable: Boolean): RetrofitMultiUrl? {
        mHeaderPriorityEnable = enable
        return this
    }

    /**
     * 全局动态替换 BaseUrl,优先级 Header中配置的url > 全局配置的url
     * 除了作为备用的 BaseUrl ,当你项目中只有一个 BaseUrl ,但需要动态改变
     * 这种方式不用在每个接口方法上加 Header,也是个很好的选择
     */
    fun setGlobalBaseUrl(url: String): RetrofitMultiUrl? {
        synchronized(mBaseUrlMap) {
            mBaseUrlMap.put(GLOBAL_BASE_URL_NAME, checkUrl(url))
        }
        //保证唯一性为retrofit设置的baseUrl
        if (TextUtils.isEmpty(mBaseUrl)) {
            mBaseUrl = url
        } else {
            setIntercept(true)
        }
        return this
    }

    fun putHeaderBaseUrl(map: Map<String, String>?): RetrofitMultiUrl? {
        if (map != null && map.size > 0) {
            for ((key, value) in map) {
                putHeaderBaseUrl(key, value)
            }
        }
        return this
    }

    /**
     * 存放 BaseUrl 的映射关系
     */
    fun putHeaderBaseUrl(urlKey: String, urlValue: String): RetrofitMultiUrl? {
        synchronized(mHeaderBaseUrlMap) {
            mHeaderBaseUrlMap[urlKey] = checkUrl(urlValue)
            setIntercept(true)
        }
        return this
    }

    /**
     * 取出对应 urlKey 的 Url
     */
    fun getHeaderBaseUrl(urlKey: String?): HttpUrl? {
        return mHeaderBaseUrlMap[urlKey]
    }

    /**
     * 根据key删除BaseUrl
     */
    fun removeHeaderBaseUrl(urlKey: String): RetrofitMultiUrl? {
        synchronized(mHeaderBaseUrlMap) {
            mHeaderBaseUrlMap.remove(urlKey)
        }
        return this
    }

    /**
     * 清除所有BaseUrl
     */
    fun clearAllHeaderBaseUrl(): RetrofitMultiUrl? {
        mHeaderBaseUrlMap.clear()
        return this
    }

    /**
     * 可自行实现 [IUrlParser] 动态切换 Url 解析策略
     */
    fun setUrlParser(parser: IUrlParser): RetrofitMultiUrl? {
        mUrlParser = parser
        return this
    }

    fun putBaseUrl(map: Map<String, String>?): RetrofitMultiUrl? {
        if (map != null && map.size > 0) {
            for ((key, value) in map) {
                putBaseUrl(key, value)
            }
        }
        return this
    }

    /**
     * 存放 BaseUrl 的映射关系
     */
    fun putBaseUrl(urlKey: String, urlValue: String): RetrofitMultiUrl? {
        synchronized(mBaseUrlMap) {
            mBaseUrlMap[urlKey] = checkUrl(urlValue)
            setIntercept(true)
        }
        return this
    }

    /**
     * 获取BaseUrl
     */
    fun getBaseUrl(urlKey: String): HttpUrl? {
        return mBaseUrlMap[urlKey]
    }

    /**
     * 根据key删除BaseUrl
     */
    fun removeBaseUrl(urlKey: String): RetrofitMultiUrl? {
        synchronized(mBaseUrlMap) {
            mBaseUrlMap.remove(urlKey)
        }
        return this
    }

    /**
     * 清除所有BaseUrl
     */
    fun clearAllBaseUrl(): RetrofitMultiUrl? {
        mBaseUrlMap.clear()
        return this
    }

    /**
     * 从 [Request.header] 中取出BASE_URL_NAME
     */
    private fun getHeaderBaseUrlKey(request: Request): String? {
        val heads = request.headers
        if (heads != null) {
            Log.i(TAG, "header:$heads")
        }
        val headers = request.headers(BASE_URL_NAME)
        if (headers == null || headers.size == 0) {
            return null
        }
        if (headers.size > 1) {
            throw IllegalArgumentException("Only one $BASE_URL_NAME in the headers")
        }
        return request.header(BASE_URL_NAME)
    }

    /**
     * 校验url合法性
     */
    private fun checkUrl(url: String): HttpUrl {
        val parseUrl =  url.toHttpUrlOrNull()
        return parseUrl ?: throw NullPointerException("You've configured an invalid url")
    }


}
