package com.yyxnb.arch.utils.log

object LogUtils {

    private val printer = LoggerIPrinter()

    val formatLog: String
        get() = printer.formatLog

    fun init(): LogConfig {
        return printer.init()
    }

    fun d(message: String, vararg args: Any) {
        printer.d(message, *args)
    }

    fun e(message: String, vararg args: Any) {
        printer.e(null, message, *args)
    }

    fun e(throwable: Throwable, message: String, vararg args: Any) {
        printer.e(throwable, message, *args)
    }

    fun i(message: String, vararg args: Any) {
        printer.i(message, *args)
    }

    fun v(message: String, vararg args: Any) {
        printer.v(message, *args)
    }

    fun w(message: String, vararg args: Any) {
        printer.w(message, *args)
    }

    fun wtf(message: String, vararg args: Any) {
        printer.wtf(message, *args)
    }

    fun json(json: String) {
        printer.json(json)
    }

    fun xml(xml: String) {
        printer.xml(xml)
    }

    fun map(map: Map<*, *>) {
        printer.map(map)
    }

    fun list(list: List<*>) {
        printer.list(list)
    }

}
