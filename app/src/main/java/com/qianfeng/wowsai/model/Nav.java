package com.qianfeng.wowsai.model;

/**
 * Created by King
 * DATE 2015/4/30.
 */
public class Nav {
    /**
     * 达人 、排行榜 、 专题 、 活动 那一排
     */
    private String subject; //标题
    private String host_pic; //图片url
    private String itemtype; //类型

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHost_pic() {
        return host_pic;
    }

    public void setHost_pic(String host_pic) {
        this.host_pic = host_pic;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }
}
