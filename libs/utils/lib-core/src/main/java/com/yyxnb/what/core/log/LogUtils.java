package com.yyxnb.what.core.log;

import java.util.List;
import java.util.Map;

public final class LogUtils {

    private static final IPrinter LOGGER_PRINTER = new LoggerPrinter();

    private LogUtils() {

    }

    public static LogConfig init() {
        return LOGGER_PRINTER.init();
    }

    public static String getFormatLog() {
        return LOGGER_PRINTER.getFormatLog();
    }

    public static void d(String message, Object... args) {
        LOGGER_PRINTER.d(message, args);
    }

    public static void e(String message, Object... args) {
        LOGGER_PRINTER.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        LOGGER_PRINTER.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        LOGGER_PRINTER.i(message, args);
    }

    public static void v(String message, Object... args) {
        LOGGER_PRINTER.v(message, args);
    }

    public static void w(String message, Object... args) {
        LOGGER_PRINTER.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        LOGGER_PRINTER.wtf(message, args);
    }

    public static void json(String json) {
        LOGGER_PRINTER.json(json);
    }

    public static void xml(String xml) {
        LOGGER_PRINTER.xml(xml);
    }

    public static void map(Map map) {
        LOGGER_PRINTER.map(map);
    }

    public static void list(List list) {
        LOGGER_PRINTER.list(list);
    }

}