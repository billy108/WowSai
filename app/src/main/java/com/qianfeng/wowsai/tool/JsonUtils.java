package com.qianfeng.wowsai.tool;

import android.util.Log;
import com.qianfeng.wowsai.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by King
 * DATE 2015/4/29.
 */
public class JsonUtils {
    /**
     * 动态页
     *
     * @param jsonStr
     * @return
     */
    public static List<Action> parseAction(String jsonStr) {
        List<Action> list = new ArrayList<Action>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray datas = obj.optJSONArray("data");
            int length = datas.length();
            for (int i = 0; i < length; i++) {
                JSONObject data = datas.optJSONObject(i);
                Action action = new Action();
                User user = new User();
                user.setUser_name(data.optString("user_name"));
                user.setUser_id(data.optString("user_id"));
                user.setHead_pic(data.optString("head_pic"));
                action.setUser(user);
                action.setEvent_action(data.optString("event_action"));
                action.setTimeline(data.optString("timeline"));
                action.setPmid(data.optString("pmid"));
                String type = data.optString("type");
                action.setType(type);
                switch (type) {
                    case "course":
                        JSONArray courses = data.optJSONArray("course");
                        JSONObject course = courses.optJSONObject(0);
                        Course course1 = new Course();
                        course1.setHand_id(course.optString("hand_id"));
                        course1.setSubject(course.optString("zhuti"));
                        course1.setCover_pic(course.optString("host_pic"));
                        User user1 = new User();
                        user1.setUser_name(course.optString("user_name"));
                        user1.setHead_pic(course.optString("head_pic"));
                        course1.setUser(user1);
                        action.setCourse(course1);
                        break;
                    case "follow":
                        JSONArray follows = data.optJSONArray("follow");
                        JSONObject follow = follows.optJSONObject(0);
                        User flow = new User();
                        flow.setUser_id(follow.optString("user_id"));
                        flow.setHead_pic(follow.optString("head_pic"));
                        flow.setUser_name(follow.optString("user_name"));
                        action.setFollow(flow);
                        break;
                    case "circle":
                        JSONArray circles = data.optJSONArray("circle");
                        JSONObject circle = circles.optJSONObject(0);
                        HandCircle handCircle = new HandCircle();
                        handCircle.setHand_id(circle.optString("circle_id"));
                        handCircle.setHost_pic(circle.optString("host_pic"));
                        handCircle.setSubject(circle.optString("message"));
                        User user2 = new User();
                        user2.setHead_pic(circle.optString("head_pic"));
                        user2.setUser_name(circle.optString("username"));
                        handCircle.setUser(user2);
                        action.setCircle(handCircle);
                        break;
                }
                list.add(action);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 达人页
     *
     * @param jsonStr
     * @return
     */
    public static List<User> parseDaren(String jsonStr) {
        List<User> list = new ArrayList<User>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray datas = obj.optJSONArray("data");
            int length = datas.length();
            for (int i = 0; i < length; i++) {
                JSONObject data = datas.optJSONObject(i);
                User user = new User();
                user.setUser_id(data.optString("user_id"));
                user.setUser_name(data.optString("nick_name"));
                user.setCourse_time(data.optString("course_time"));
                user.setCourse_count(data.optString("course_count"));
                user.setOpus_count(data.optString("opus_count"));
                List<Course> courses = new ArrayList<Course>();
                JSONArray arrList = data.optJSONArray("list");
                int length1 = arrList.length();
                for (int j = 0; j < length1; j++) {
                    JSONObject objList = arrList.optJSONObject(j);
                    Course course = new Course();
                    course.setHand_id(objList.optString("hand_id"));
                    course.setCover_pic(objList.optString("host_pic"));
                    courses.add(course);
                }
                user.setReleaseCourseList(courses);
                user.setHead_pic(data.optString("avatar"));
                list.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 专题首页
     *
     * @param jsonStr
     * @return
     */
    public static List<Topic> parseTopic(String jsonStr) {
        List<Topic> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONObject data = obj.optJSONObject("data");
            JSONArray arrList = data.optJSONArray("list");
            int length = arrList.length();
            for (int i = 0; i < length; i++) {
                JSONObject objList = arrList.getJSONObject(i);
                Topic topic = new Topic();
                topic.setId(objList.optString("id"));
                topic.setSubject(objList.optString("subject"));
                JSONArray arrPic = objList.optJSONArray("pic");
                List<String> pic = new ArrayList<>();
                int length1 = arrPic.length();
                for (int j = 0; j < length1; j++) {
                    pic.add(arrPic.optString(j));
                }
                topic.setPic(pic);
                list.add(topic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 专题详情页
     *
     * @param jsonStr
     * @return
     */
    public static Topic parseTopicDetail(String jsonStr) {
        Topic topic = new Topic();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONObject data = obj.optJSONObject("data");
            if(data == null){
                return null;
            }
            topic.setSubject(data.optString("subject"));
            List<Course> list = new ArrayList<>();
            JSONArray arrCourse = data.optJSONArray("course");
            int length = arrCourse.length();
            for (int i = 0; i < length; i++) {
                Course course = new Course();
                JSONObject objCourse = arrCourse.optJSONObject(i);
                course.setCover_pic(objCourse.optString("thumb"));
                course.setHand_id(objCourse.optString("hand_id"));
                course.setSubject(objCourse.optString("subject"));
                list.add(course);
            }
            topic.setCourseList(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return topic;
    }

    /**
     * 活动列表
     */
    public static List<Campaign> parseCampaign(String jsonStr) {
        List<Campaign> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray datas = obj.optJSONArray("data");
            if(datas == null){
                return null;
            }
            int length = datas.length();
            for (int i = 0; i < length; i++) {
                Campaign campaign = new Campaign();
                JSONObject data = datas.optJSONObject(i);
                campaign.setId(data.optString("id"));
                campaign.setC_statuse(data.optString("c_status"));
                campaign.setC_id(data.optString("c_id"));
                campaign.setC_name(data.optString("c_name"));
                campaign.setC_time(data.optString("c_time"));
                campaign.setM_logo(data.optString("m_logo"));
                list.add(campaign);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 活动最新作品
     * 投票最多
     */
    public static List<HandCircle> parseCampaignDetial(String jsonStr) {
        List<HandCircle> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray datas = obj.optJSONArray("data");
            if(datas == null){
                return null;
            }
            int length = datas.length();
            for (int i = 0; i < length; i++) {
                HandCircle circle = new HandCircle();
                JSONObject data = datas.optJSONObject(i);
                circle.setSubject(data.optString("subject"));
                circle.setHand_id(data.optString("circle_item_id"));
                circle.setHost_pic(data.optString("host_pic"));
                circle.setVotes(data.optString("votes"));
                User user = new User();
                user.setUser_id(data.optString("uid"));
                user.setUser_name(data.optString("uname"));
                user.setHead_pic(data.optString("avatar"));
                circle.setUser(user);
                circle.setLast_id(data.optString("last_id"));
                list.add(circle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 教程列表
     * 最热教程
     */
    public static Map<String, Object> parseCourseList(String jsonStr) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONObject data = obj.optJSONObject("data");
            JSONArray arrList = data.optJSONArray("list");
            List<Course> list = new ArrayList<>();
            int length = arrList.length();
            for (int i = 0; i < length; i++) {
                JSONObject objList = arrList.optJSONObject(i);
                Course course = new Course();
                course.setCover_pic(objList.optString("host_pic_s"));
                course.setSubject(objList.optString("subject"));
                course.setHand_id(objList.optString("hand_id"));
                course.setComment_num(objList.optString("comment_num"));
                course.setCollect_num(objList.optString("collect"));
                course.setLaud(objList.optString("laud"));
                course.setView_num(objList.optString("view"));
                User user = new User();
                user.setUser_id(objList.optString("user_id"));
                user.setUser_name(objList.optString("user_name"));
                user.setHead_pic(objList.optString("face_pic"));
                course.setUser(user);
                list.add(course);
            }
            map.put("course",list);
            map.put("lastid",data.optString("lastid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
/**
 * 解析首页
 */
    public static Home parseHome(String jsonStr){
        Home home = new Home();
        try {
            JSONObject obj = new JSONObject(jsonStr);
            JSONObject data = obj.optJSONObject("data");

            JSONArray slides = data.optJSONArray("slide");
            int length = slides.length();
            List<Slide> slideList = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                JSONObject objSlide = slides.optJSONObject(i);
                Slide slide = new Slide();
                slide.setHost_pic(objSlide.optString("host_pic"));
                slide.setHand_id(objSlide.optString("hand_id"));
                slide.setItemtype(objSlide.optString("itemtype"));
                slide.setSubject(objSlide.optString("subject"));
                slide.setUser_name(objSlide.optString("user_name"));
                slideList.add(slide);
            }
            home.setSlide(slideList);

            JSONArray navArr = data.optJSONArray("nav");
            List<Nav> navList = new ArrayList<>();
            int length1 = navArr.length();
            for (int i = 0; i < length1; i++) {
                JSONObject navObj = navArr.optJSONObject(i);
                Nav nav = new Nav();
                nav.setItemtype(navObj.optString("itemtype"));
                nav.setHost_pic(navObj.optString("host_pic"));
                nav.setSubject(navObj.optString("subject"));
                navList.add(nav);
            }
            home.setNav(navList);

            JSONArray advArr = data.optJSONArray("adv");
            List<Adv> advList = new ArrayList<>();
            int length2 = advArr.length();
            for (int i = 0; i < length2; i++) {
                JSONObject advObj = advArr.optJSONObject(i);
                Adv adv = new Adv();
                adv.setType(advObj.optString("type"));
                adv.setSubject(advObj.optString("subject"));
                adv.setAction(advObj.optString("action"));
                adv.setAd_img(advObj.optString("ad_img"));
                adv.setId(advObj.optString("id"));
                adv.setTitle(advObj.optString("title"));
                advList.add(adv);
            }
            home.setAdv(advList);

            User daren = new User();
            JSONObject darenObj = data.optJSONObject("daren");
            daren.setUser_name(darenObj.optString("uname"));
            daren.setCourse_count(darenObj.optString("coursecount"));
            daren.setHead_pic(darenObj.optString("avatar"));
            daren.setOpus_count(darenObj.optString("circle_count"));
            daren.setFan_count(darenObj.optString("fen_num"));
            daren.setBg_image(darenObj.optString("bg_image"));
            daren.setUser_id(darenObj.optString("uid"));
            daren.setRegion(darenObj.optString("region"));
            daren.setGuanStatus(darenObj.optString("guan_status"));
            home.setDaren(daren);

            JSONArray topicArr = data.optJSONArray("topic");
            List<Topic> topicList = new ArrayList<>();
            int length3 = topicArr.length();
            for (int i = 0; i < length3; i++) {
                JSONObject topicObj = topicArr.optJSONObject(i);
                Topic topic = new Topic();
                topic.setId(topicObj.optString("id"));
                topic.setSubject(topicObj.optString("subject"));
                JSONArray picArr = topicObj.optJSONArray("pic");
                int length4 = picArr.length();
                List<String> pic = new ArrayList<>();
                for (int j = 0; j < length4; j++) {
                    pic.add(picArr.optString(j));
                }
                topic.setPic(pic);
                topicList.add(topic);
            }
            home.setTopics(topicList);

            JSONArray courseArr = data.optJSONArray("course");
            List<Course> courseList = new ArrayList<>();
            int length4 = courseArr.length();
            for (int i = 0; i < length4; i++) {
                JSONObject courseObj = courseArr.optJSONObject(i);
                Course course = new Course();
                course.setHand_id(courseObj.optString("hand_id"));
                course.setSubject(courseObj.optString("subject"));
                course.setCover_pic(courseObj.optString("host_pic"));
                User user = new User();
                user.setUser_name(courseObj.optString("user_name"));
                user.setUser_id(courseObj.optString("user_id"));
                user.setHead_pic(courseObj.optString("avatar"));
                course.setUser(user);
                courseList.add(course);
            }
            home.setCourses(courseList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return home;
    }


}
