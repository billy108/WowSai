package com.qianfeng.wowsai.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/5
 */
public class Cates {
    private String id;
    private String name;
    private String ico;
    private List<Cate> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public List<Cate> getChild() {
        return child;
    }

    public void setChild(List<Cate> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Cates{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ico='" + ico + '\'' +
                ", child=" + child +
                '}';
    }
}
