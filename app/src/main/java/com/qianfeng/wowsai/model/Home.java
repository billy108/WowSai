package com.qianfeng.wowsai.model;

import java.util.List;

/**
 * Created by King
 * DATE 2015/4/30.
 */
public class Home {
    private List<Slide> slide;
    private List<Nav> nav;
    private List<Adv> adv;
    private User daren;
    private List<Topic> topics;
    private List<Course> courses;

    public List<Slide> getSlide() {
        return slide;
    }

    public void setSlide(List<Slide> slide) {
        this.slide = slide;
    }

    public List<Nav> getNav() {
        return nav;
    }

    public void setNav(List<Nav> nav) {
        this.nav = nav;
    }

    public List<Adv> getAdv() {
        return adv;
    }

    public void setAdv(List<Adv> adv) {
        this.adv = adv;
    }

    public User getDaren() {
        return daren;
    }

    public void setDaren(User daren) {
        this.daren = daren;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
