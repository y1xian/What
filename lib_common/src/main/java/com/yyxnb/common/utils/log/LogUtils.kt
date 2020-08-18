package com.yyxnb.common.utils.log

object LogUtils {

    private val LOGGER_PRINTER: IPrinter = LoggerPrinter

    fun init(): LogConfig {
        return LOGGER_PRINTER.init()
    }

    val formatLog: String
        get() = LOGGER_PRINTER.formatLog

    @JvmStatic
    fun d(message: String?, vararg args: Any) {
        LOGGER_PRINTER.d(message, *args)
    }

    @JvmStatic
    fun e(message: String?, vararg args: Any) {
        LOGGER_PRINTER.e(null, message, *args)
    }

    fun e(throwable: Throwable?, message: String?, vararg args: Any) {
        LOGGER_PRINTER.e(throwable, message, *args)
    }

    fun i(message: String?, vararg args: Any) {
        LOGGER_PRINTER.i(message, *args)
    }

    fun v(message: String?, vararg args: Any) {
        LOGGER_PRINTER.v(message, *args)
    }

    @JvmStatic
    fun w(message: String?, vararg args: Any) {
        LOGGER_PRINTER.w(message, *args)
    }

    fun wtf(message: String?, vararg args: Any) {
        LOGGER_PRINTER.wtf(message, *args)
    }

    fun json(json: String) {
        LOGGER_PRINTER.json(json)
    }

    fun xml(xml: String) {
        LOGGER_PRINTER.xml(xml)
    }

    fun map(map: Map<*, *>?) {
        LOGGER_PRINTER.map(map)
    }

    @JvmStatic
    fun list(list: List<*>?) {
        LOGGER_PRINTER.list(list)
    }
}