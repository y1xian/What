package com.yyxnb.lib_network.rx;

/**
 * 前端自定义Exception
 */
public class ApiException extends Exception {
    private String statusCode;//错误码
    private String statusDesc;//错误信息

    public ApiException(Throwable throwable, String statusCode) {
        super(throwable);
        this.statusCode = statusCode;
    }

    public ApiException(String statusCode, String statusDesc) {
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}