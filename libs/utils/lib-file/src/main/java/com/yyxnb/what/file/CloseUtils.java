package com.yyxnb.what.file;

import java.io.Closeable;
import java.io.IOException;

public final class CloseUtils {

    private CloseUtils() {
    }

    /**
     * 关闭IO
     *
     * @param closeables
     */
    public static void close(Closeable... closeables) {
        int length = closeables.length;
        try {
            for (Closeable closeable : closeables) {
                if (null != closeable) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for (int i = 0; i < length; i++) {
                closeables[i] = null;
            }
        }
    }

}