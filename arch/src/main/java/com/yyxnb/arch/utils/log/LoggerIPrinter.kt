package com.yyxnb.arch.utils.log

import android.text.TextUtils
import android.util.Log
import com.yyxnb.arch.interfaces.IPrinter
import org.json.JSONArray
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

 class LoggerIPrinter : IPrinter {

    private val logStr = StringBuilder()

    /**
     * 返回最后一次格式化的打印结果样式
     * @return
     */
    override val formatLog: String
        get() = logStr.toString()

    /**
     * 初始化
     */
    override fun init(): LogConfig {
        return config
    }

    override fun d(message: String, vararg args: Any) {
        log(Log.DEBUG, message, *args)
    }

    override fun e(message: String, vararg args: Any) {
        e(null, message, *args)
    }

    override fun e(throwable: Throwable?, message: String?, vararg args: Any) {
        var message = message
        if (throwable != null && message != null) {
            message += " : $throwable"
        }
        if (throwable != null && message == null) {
            message = throwable.toString()
        }
        if (message == null) {
            message = "message/exception 为空！"
        }
        log(Log.ERROR, message, *args)
    }

    override fun w(message: String, vararg args: Any) {
        log(Log.WARN, message, *args)
    }

    override fun i(message: String, vararg args: Any) {
        log(Log.INFO, message, *args)
    }

    override fun v(message: String, vararg args: Any) {
        log(Log.VERBOSE, message, *args)
    }

    override fun wtf(message: String, vararg args: Any) {
        log(Log.ASSERT, message, *args)
    }

    /**
     * 格式化json
     */
    override fun json(json: String) {
        if (TextUtils.isEmpty(json)) {
            d("json 数据为空！")
            return
        }
        try {
            var message = ""
            if (json.startsWith("{")) {
                val jo = JSONObject(json)
                message = jo.toString(4)
            } else if (json.startsWith("[")) {
                val ja = JSONArray(json)
                message = ja.toString(4)
            }
            d(message)
        } catch (e: Exception) {
            e(e.cause?.message + LINE_SEPARATOR + json)
        }

    }

    /**
     * 格式化xml
     */
    override fun xml(xml: String) {
        if (TextUtils.isEmpty(xml)) {
            d("xml 数据为空！")
            return
        }
        try {
            val xmlInput = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
            transformer.transform(xmlInput, xmlOutput)
            val message = xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">" + LINE_SEPARATOR!!)
            d(message)
        } catch (e: TransformerException) {
            e(e.cause?.message + LINE_SEPARATOR + xml)
        }

    }

    /**
     * 格式化Map集合
     */
    override fun map(map: Map<*, *>?) {
        if (map != null) {
            val stringBuilder = StringBuilder()
            for (entry in map.entries) {
                stringBuilder.append("[key] → ")
                stringBuilder.append((entry as java.util.Map.Entry<*, *>).key)
                stringBuilder.append(",[value] → ")
                stringBuilder.append((entry as java.util.Map.Entry<*, *>).value)
                stringBuilder.append(LINE_SEPARATOR)
            }
            d(stringBuilder.toString())
        }
    }

    /**
     * 格式化List集合
     */
    override fun list(list: List<*>?) {
        if (list != null) {
            val stringBuilder = StringBuilder()
            for (i in list.indices) {
                stringBuilder.append("[$i] → ")
                stringBuilder.append(list[i])
                stringBuilder.append(LINE_SEPARATOR)
            }
            d(stringBuilder.toString())
        }
    }

    /**
     * 同步日志打印顺序
     */
    @Synchronized
    private fun log(priority: Int, msg: String, vararg args: Any) {
        if (!config.isDebug()) {
            return
        }
        logStr.delete(0, logStr.length)
        val message = if (args.size == 0) msg else String.format(msg, *args)
        logChunk(priority, TOP_BORDER)
        if (config.isShowThreadInfo()) {
            //打印线程
            getStackInfo(priority)
        }
        //得到系统的默认字符集的信息字节（UTF-8）
        val bytes = message.toByteArray()
        val length = bytes.size
        if (length <= CHUNK_SIZE) {
            logContent(priority, message)
            logChunk(priority, BOTTOM_BORDER)
            return
        }
        var i = 0
        while (i < length) {
            val count = Math.min(length - i, CHUNK_SIZE)
            //创建系统的默认字符集的一个新的字符串（UTF-8）
            logContent(priority, String(bytes, i, count))
            i += CHUNK_SIZE
        }
        logChunk(priority, BOTTOM_BORDER)
    }

    private fun logContent(priority: Int, chunk: String) {
        val lines = chunk.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            logChunk(priority, "$HORIZONTAL_DOUBLE_LINE $line")
        }
    }

    private fun logChunk(priority: Int, chunk: String) {
        logStr.append(LINE_SEPARATOR)
        logStr.append(chunk)
        val TAG = config.getTag()
        when (priority) {
            Log.ERROR -> Log.e(TAG, chunk)
            Log.INFO -> Log.i(TAG, chunk)
            Log.VERBOSE -> Log.v(TAG, chunk)
            Log.WARN -> Log.w(TAG, chunk)
            Log.ASSERT -> Log.wtf(TAG, chunk)
            Log.DEBUG -> Log.d(TAG, chunk)
            else -> Log.d(TAG, chunk)
        }
    }

    /**
     * 打印堆栈信息.
     */
    private fun getStackInfo(priority: Int) {
        logChunk(priority, HORIZONTAL_DOUBLE_LINE + "[Thread] → " + Thread.currentThread().name)
        logChunk(priority, MIDDLE_BORDER)
        var str = ""
        val traces = Thread.currentThread().stackTrace

        for (i in traces.indices) {
            val element = traces[i]
            val perTrace = StringBuilder(str)
            if (element.isNativeMethod) {
                continue
            }
            val className = element.className
            if (className.startsWith("android.")
                    || className.contains("com.android")
                    || className.contains("java.lang")
                    || className.contains("com.youth.xframe")) {
                continue
            }
            perTrace.append(element.className)
                    .append('.')
                    .append(element.methodName)
                    .append("  (")
                    .append(element.fileName)
                    .append(':')
                    .append(element.lineNumber)
                    .append(")")
            str += "  "
            logContent(priority, perTrace.toString())
        }
        logChunk(priority, MIDDLE_BORDER)
    }

    companion object {

        /**
         * Android一个日志条目不能超过4076字节，
         * 这里设置以4000字节来计算
         */
        private val CHUNK_SIZE = 4000
        /**
         * log settings
         */
        private val config = LogConfig()
        /**
         * 样式
         */
        private val TOP_LEFT_CORNER = '┏'
        private val BOTTOM_LEFT_CORNER = '┗'
        private val MIDDLE_CORNER = '┠'
        private val HORIZONTAL_DOUBLE_LINE = '┃'
        private val DOUBLE_DIVIDER = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        private val SINGLE_DIVIDER = "──────────────────────────────────────────────"
        private val TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER
        private val BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER
        private val MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER

        var LINE_SEPARATOR = System.getProperty("line.separator")
    }
}
