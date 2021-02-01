package com.yyxnb.util_file;

import java.io.Closeable;
import java.io.IOException;

public final class CloseUtils {

    private CloseUtils() {
    }

    /**
     * 关闭IO
     *
     * @param closeables The closeables.
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}