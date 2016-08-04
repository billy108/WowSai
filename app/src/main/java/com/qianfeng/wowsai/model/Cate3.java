package com.qianfeng.wowsai.model;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/5/5
 */
public class Cate3 {
    private String id;
    private String name;

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

    @Override
    public String toString() {
        return "Cate3{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
