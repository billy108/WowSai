package com.qianfeng.wowsai.model;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/29
 */
public class HandCircle {
    private String hand_id;//手工圈ID

    private String host_pic;//封面图片

    private String subject;//作品标题

    private String laud_num;//点赞数

    private String comment_num;//评论数

    private String c_name;//活动名称

    private String votes;//投票数

    private String add_time;//上传时间

    private User user;//用户  user_name  head_pic

    private String last_id;//活动分页时需要的id

    private List<String> pics;//图片

    private List<Comments> commentList;//评论列表

    private List<Map<String, String>> commentList22;//评论列表22在活动详情中使用

    private List<User> laudList;//点赞列表

    public String getHand_id() {
        return hand_id;
    }

    public void setHand_id(String hand_id) {
        this.hand_id = hand_id;
    }

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

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<Comments> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comments> commentList) {
        this.commentList = commentList;
    }

    public List<User> getLaudList() {
        return laudList;
    }

    public void setLaudList(List<User> laudList) {
        this.laudList = laudList;
    }


    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public String getLaud_num() {
        return laud_num;
    }

    public void setLaud_num(String laud_num) {
        this.laud_num = laud_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public List<Map<String, String>> getCommentList22() {
        return commentList22;
    }

    public void setCommentList22(List<Map<String, String>> commentList22) {
        this.commentList22 = commentList22;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
}
