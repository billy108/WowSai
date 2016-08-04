package com.qianfeng.wowsai.model;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/29
 */
public class Comments {
    private User other;//发评论的人

    private User me;//我

    private String content;//评论内容

    private String add_time;//发布时间

    public User getOther() {
        return other;
    }

    public void setOther(User other) {
        this.other = other;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
