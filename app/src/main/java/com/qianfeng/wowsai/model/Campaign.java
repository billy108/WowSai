package com.qianfeng.wowsai.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/29
 */
public class Campaign {
    private String id;//ID

    private String c_statuse;//活动状态

    private String c_name;//活动名称

    private String m_logo;//活动封面图片

    private String c_time;//征集作品时间

    private String instruct_pic;//活动介绍图片

    private List<HandCircle> latestPdcs;//最新作品

    private List<HandCircle> votePdcs;//投票最多作品

    private String c_id;//进入详情页需要传递的id

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getC_statuse() {
        return c_statuse;
    }

    public void setC_statuse(String c_statuse) {
        this.c_statuse = c_statuse;
    }

    public String getM_logo() {
        return m_logo;
    }

    public void setM_logo(String m_logo) {
        this.m_logo = m_logo;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getInstruct_pic() {
        return instruct_pic;
    }

    public void setInstruct_pic(String instruct_pic) {
        this.instruct_pic = instruct_pic;
    }

    public List<HandCircle> getLatestPdcs() {
        return latestPdcs;
    }

    public void setLatestPdcs(List<HandCircle> latestPdcs) {
        this.latestPdcs = latestPdcs;
    }

    public List<HandCircle> getVotePdcs() {
        return votePdcs;
    }

    public void setVotePdcs(List<HandCircle> votePdcs) {
        this.votePdcs = votePdcs;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getStatusString(){
        switch (c_statuse){
            case "0":
                return "即将进行";
            case "1":
                return "进行中";
            case "2":
                return "已结束";
        }
        return "";
    }

}
