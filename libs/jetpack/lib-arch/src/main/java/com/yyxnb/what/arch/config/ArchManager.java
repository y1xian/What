package com.yyxnb.what.arch.config;

/**
 * ================================================
 * 作    者：yyx
 * 版    本：1.0
 * 日    期：2020/11/19
 * 历    史：
 * 描    述：配置
 * ================================================
 */
public class ArchManager {

    private static volatile ArchManager manager;

    private ArchConfig config;

    public static ArchManager getInstance() {
        if (null == manager) {
            synchronized (ArchManager.class) {
                if (null == manager) {
                    manager = new ArchManager();
                }
            }
        }
        return manager;
    }

    public ArchConfig getConfig() {
        return config;
    }

    public void setConfig(ArchConfig config) {
        this.config = config;
    }
}
