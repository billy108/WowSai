package com.qianfeng.wowsai.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/28
 */
@Table(name = "collects")
public class Course implements Serializable {
    private User user;

    @Id(column = "hand_id")
    private String hand_id;
    //点赞数
    @Column(column = "laud")
    private String laud;
    //评论数
    @Column(column = "comment_num")
    private String comment_num;
    //浏览数
    @Column(column = "view_num")
    private String view_num;
    //收藏数据
    @Column(column = "collect_num")
    private String collect_num;
    //第一张展示图片
    @Column(column = "cover_pic")
    private String cover_pic;
    //手工简介
    @Column(column = "summary")
    private String summary;
    //标题
    @Column(column = "subject")
    private String subject;
    //朦胧背景图片
    @Column(column = "bg_pic")
    private String bg_pic;
    //分享url
    @Column(column = "share_url")
    private String share_url;

    private String add_time;

    //评论列表
    private List<Map<String, String>> comment;

    private List<Map<String, String>> step;

    private List<Map<String, String>> tools;

    private List<Map<String, String>> material;

    private String last_id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHand_id() {
        return hand_id;
    }

    public void setHand_id(String hand_id) {
        this.hand_id = hand_id;
    }

    public String getLaud() {
        return laud;
    }

    public void setLaud(String laud) {
        this.laud = laud;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public List<Map<String, String>> getStep() {
        return step;
    }

    public void setStep(List<Map<String, String>> step) {
        this.step = step;
    }

    public List<Map<String, String>> getTools() {
        return tools;
    }

    public void setTools(List<Map<String, String>> tools) {
        this.tools = tools;
    }

    public List<Map<String, String>> getMaterial() {
        return material;
    }

    public void setMaterial(List<Map<String, String>> material) {
        this.material = material;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSummary() {
        return summary;
    }

    public String getBg_pic() {
        return bg_pic;
    }

    public void setBg_pic(String bg_pic) {
        this.bg_pic = bg_pic;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public List<Map<String, String>> getComment() {
        return comment;
    }

    public void setComment(List<Map<String, String>> comment) {
        this.comment = comment;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}