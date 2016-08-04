package com.qianfeng.wowsai.model;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/29
 */
public class Topic {

    private String id;

    private List<String> pic;

    private String subject;

    private List<Course> courseList;

    private List<Map<String, String>> courseInfo;//存放 course_id  course_pic

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Map<String, String>> getCourseInfo() {
        return courseInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public void setCourseInfo(List<Map<String, String>> courseInfo) {
        this.courseInfo = courseInfo;
    }
}
