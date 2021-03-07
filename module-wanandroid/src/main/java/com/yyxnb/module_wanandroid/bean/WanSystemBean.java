package com.yyxnb.module_wanandroid.bean;

import java.util.List;

public class WanSystemBean {

    /**
     * children : [{"children":[],"courseId":13,"id":10,"name":"Activity","order":10000,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":15,"name":"Service","order":10001,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":16,"name":"BroadcastReceiver","order":10002,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":17,"name":"ContentProvider","order":10003,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":19,"name":"Intent","order":10004,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":40,"name":"Context","order":10005,"parentChapterId":9,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":267,"name":"handler","order":10006,"parentChapterId":9,"userControlSetTop":false,"visible":1}]
     * courseId : 13
     * id : 9
     * name : 四大组件
     * order : 10
     * parentChapterId : 0
     * userControlSetTop : false
     * visible : 1
     */

    public int courseId;
    public int id;
    public String name;
    public int order;
    public int parentChapterId;
    public boolean userControlSetTop;
    public int visible;
    public List<WanClassifyBean> children;


}
