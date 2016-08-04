package com.qianfeng.wowsai.model;

/**
 * Created by King
 * DATE 2015/4/29.
 */
public class Action {
    private User user;//用户信息
    private String msgtoid;//
    private String type;
    private String event_action;//操作
    private String timeline;//时间
    private HandCircle circle;//朋友圈
    private Course course;//教程
    private User follow;//关注
    private String pmid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMsgtoid() {
        return msgtoid;
    }

    public void setMsgtoid(String msgtoid) {
        this.msgtoid = msgtoid;
    }

    public String getEvent_action() {
        return event_action;
    }

    public void setEvent_action(String event_action) {
        this.event_action = event_action;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public HandCircle getCircle() {
        return circle;
    }

    public void setCircle(HandCircle circle) {
        this.circle = circle;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getFollow() {
        return follow;
    }

    public void setFollow(User follow) {
        this.follow = follow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }
}
