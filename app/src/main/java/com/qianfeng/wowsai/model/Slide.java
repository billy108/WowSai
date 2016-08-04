package com.qianfeng.wowsai.model;

/**
 * Created by King
 * DATE 2015/4/30.
 */
public class Slide {
    //首页的slide viewpager
    private String host_pic;//图片url
    private String subject;//标题
    private String  user_name;//用户名  貌似没用
    private String itemtype;//类型
    private String hand_id;//id

    public String getHost_pic() {
        return host_pic;
    }

    public void setHost_pic(String host_pic) {
        this.host_pic = host_pic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getHand_id() {
        return hand_id;
    }

    public void setHand_id(String hand_id) {
        this.hand_id = hand_id;
    }
}
