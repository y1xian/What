package com.yyxnb.common_res.bean;


import com.yyxnb.what.core.interfaces.IData;

/**
 * 极速api的数据结构
 * https://api.jisuapi.com/
 *
 * @param <T>
 * @author yyx
 */
public class JiSuData<T> implements IData<T> {

    /*
     0 代表执行成功
    101	APPKEY为空或不存在
    102	APPKEY已过期
    103	APPKEY无请求此数据权限
    104	请求超过次数限制
    105	IP被禁止
    106	IP请求超过限制
    107	接口维护中
    108	接口已停用
     */

    public int status;
    public String msg;
    public T result;

    @Override
    public String getCode() {
        return String.valueOf(status);
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public boolean isSuccess() {
        return status == 0;
    }
}
