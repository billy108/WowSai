package com.qianfeng.wowsai.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/28
 */
public class User implements Serializable {
    private String user_name;//用户名

    private String user_id;//ID

    private String head_pic;//头像

    private String course_count;//教程数量

    private String opus_count;//手工圈数量

    private String course_time;//时间

    private String fan_count;//粉丝数量

    private String bg_image;//用户名片的背景图片

    private List<HandCircle> releasehandCircleList;//发布的手工圈

    private List<HandCircle> collectHandCircleList;//收藏的手工圈

    private List<Course> releaseCourseList;//发布的教程

    private List<Course> collectCourseList;//收藏的教程

    private List<User> fenList;//粉丝列表

    private List<User> guanList;//关注列表

    private String region;//地址

    private String guanStatus;//是否关注

    private String view_num;

    private String last_id;

    private String level;

    private String Daren;

    private String gender;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public List<HandCircle> getReleasehandCircleList() {
        return releasehandCircleList;
    }

    public void setReleasehandCircleList(List<HandCircle> releasehandCircleList) {
        this.releasehandCircleList = releasehandCircleList;
    }

    public List<HandCircle> getCollectHandCircleList() {
        return collectHandCircleList;
    }

    public void setCollectHandCircleList(List<HandCircle> collectHandCircleList) {
        this.collectHandCircleList = collectHandCircleList;
    }

    public List<Course> getReleaseCourseList() {
        return releaseCourseList;
    }

    public void setReleaseCourseList(List<Course> releaseCourseList) {
        this.releaseCourseList = releaseCourseList;
    }

    public List<Course> getCollectCourseList() {
        return collectCourseList;
    }

    public void setCollectCourseList(List<Course> collectCourseList) {
        this.collectCourseList = collectCourseList;
    }

    public List<User> getFenList() {
        return fenList;
    }

    public void setFenList(List<User> fenList) {
        this.fenList = fenList;
    }

    public List<User> getGuanList() {
        return guanList;
    }

    public void setGuanList(List<User> guanList) {
        this.guanList = guanList;
    }

    public String getCourse_count() {
        return course_count;
    }

    public void setCourse_count(String course_count) {
        this.course_count = course_count;
    }

    public String getOpus_count() {
        return opus_count;
    }

    public void setOpus_count(String opus_count) {
        this.opus_count = opus_count;
    }

    public String getCourse_time() {
        return course_time;
    }

    public void setCourse_time(String course_time) {
        this.course_time = course_time;
    }

    public String getFan_count() {
        return fan_count;
    }

    public void setFan_count(String fan_count) {
        this.fan_count = fan_count;
    }

    public String getBg_image() {
        return bg_image;
    }

    public void setBg_image(String bg_image) {
        this.bg_image = bg_image;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDaren() {
        return Daren;
    }

    public void setDaren(String daren) {
        Daren = daren;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGuanStatus() {
        return guanStatus;
    }

    public void setGuanStatus(String guanStatus) {
        this.guanStatus = guanStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
