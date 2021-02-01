package com.yyxnb.lib_network.rx;

/**
 * 服务器返回的Exception
 */
public class ServerException extends RuntimeException {
    private String statusCode;//错误码
    private String statusDesc;//错误信息

    public ServerException(String statusCode, String statusDesc) {
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