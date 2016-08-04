package com.qianfeng.wowsai.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/5
 */
public class Cate {
    private String id;
    private String name;
    private List<Cate3> cate3s;

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

    public List<Cate3> getCate3s() {
        return cate3s;
    }

    public void setCate3s(List<Cate3> cate3s) {
        this.cate3s = cate3s;
    }

    @Override
    public String toString() {
        return "Cate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cate3s=" + cate3s +
                '}';
    }
}
