package com.qianfeng.wowsai.tool;

import com.qianfeng.wowsai.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User: Moon
 * Date:2015/4/29
 */
public class GetJsonInfo {

    /**
     * 获得月榜列表
     *
     * @param json
     * @return
     */
    public static List<Map<String, Object>> getMonthRankInfo(String json) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String key = obj.getString("key");
                    if (key.equals("mouth")) {
                        list.add(getRankInfo(obj));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获得总榜列表
     *
     * @param json
     * @return
     */
    public static List<Map<String, Object>> getAllRankInfo(String json) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String key = obj.getString("key");
                    if (key.equals("all")) {
                        list.add(getRankInfo(obj));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Map<String, Object> getRankInfo(JSONObject obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("key", obj.getString("key"));
            map.put("id", obj.getString("id"));
            map.put("title", obj.getString("title"));
            String type = obj.getString("a");
            map.put("a", type);
            if (type.equals("course")) {
                JSONArray data = obj.getJSONArray("data");
                List<Course> courseList = new ArrayList<>();
                if (data != null) {
                    for (int j = 0; j < data.length(); j++) {
                        JSONObject objj = data.getJSONObject(j);
                        Course course = new Course();
                        course.setHand_id(objj.getString("itemId"));
                        course.setCover_pic(objj.getString("image"));
                        courseList.add(course);
                    }
                }
                map.put("list_show", courseList);
            } else {
                List<User> userList = new ArrayList<>();
                JSONArray data = obj.getJSONArray("data");
                if (data != null) {
                    for (int j = 0; j < data.length(); j++) {
                        JSONObject objj = data.getJSONObject(j);
                        User user = new User();
                        user.setUser_id(objj.getString("itemId"));
                        user.setUser_name(objj.getString("subject"));
                        user.setHead_pic(objj.getString("image"));
                        userList.add(user);
                    }
                }
                map.put("list_show", userList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获得CourseList
     *
     * @param json
     * @return
     */
    public static List<Course> getRankCourseListInfo(String json) {
        List<Course> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Course course = new Course();
                course.setHand_id(obj.getString("hand_id"));
                course.setCover_pic(obj.getString("host_pic"));
                course.setSubject(obj.getString("subject"));
                course.setView_num(obj.getString("view"));
                course.setLast_id(obj.getString("last_id"));
                User user = new User();
                user.setDaren(obj.getString("daren"));
                user.setUser_id(obj.getString("uid"));
                user.setUser_name(obj.getString("uname"));
                user.setHead_pic(obj.getString("avatar"));
                course.setUser(user);
                list.add(course);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获得UserList
     *
     * @param json
     * @return
     */
    public static List<User> getRankUserListInfo(String json) {
        List<User> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                User user = new User();
                user.setUser_id(obj.getString("uid"));
                user.setView_num(obj.getString("view"));
                user.setLevel(obj.getString("level"));
                user.setUser_name(obj.getString("uname"));
                user.setHead_pic(obj.getString("avatar"));
                user.setDaren(obj.getString("daren"));
                user.setLast_id(obj.getString("last_id"));
                list.add(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String, Object> getLoginInfo(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(json);
            map.put("info", obj.getString("info"));
            JSONObject data = obj.optJSONObject("data");
            if (data != null) {
                User user = new User();
                user.setUser_name(data.getString("uname"));
                user.setUser_id(data.getString("uid"));
                user.setHead_pic(data.getString("avatar"));
                user.setLevel(data.getString("level"));
                user.setDaren(data.getString("hand_daren"));
                map.put("user", user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static User getWeiboInfo(HashMap<String, Object> hashMap) {
        User user = new User();
        user.setUser_name(hashMap.get("screen_name").toString());
        user.setUser_id(hashMap.get("idstr").toString());
        user.setHead_pic(hashMap.get("profile_image_url").toString());
        user.setGender(hashMap.get("gender").toString());
        return user;
    }

    public static List<Map<String, Object>> getSearchInfo(String json) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json);
            Map<String, Object> m = new HashMap<>();
            String info = obj.getString("info");
            m.put("info", info);
            list.add(m);
            if (info.contains("成功")) {
                JSONObject data = obj.getJSONObject("data");
                JSONArray course_data = data.optJSONArray("course_data");
                if (course_data != null) {
                    Map<String, Object> map = new HashMap<>();
                    List<Course> cList = new ArrayList<>();
                    for (int i = 0; i < course_data.length(); i++) {
                        JSONObject objj = course_data.getJSONObject(i);
                        Course course = new Course();
                        course.setHand_id(objj.getString("hand_id"));
                        course.setSubject(objj.getString("subject"));
                        course.setCover_pic(objj.getString("host_pic"));
                        course.setAdd_time(objj.getString("add_time"));
                        User user = new User();
                        user.setUser_id(objj.getString("user_id"));
                        user.setUser_name(objj.getString("user_name"));
                        course.setUser(user);
                        cList.add(course);
                    }
                    map.put("cate", "course");
                    map.put("title", "相关教程");
                    map.put("courseList", cList);
                    list.add(map);
                }
                JSONArray user_data = data.optJSONArray("user_data");
                if (user_data != null) {
                    Map<String, Object> map = new HashMap<>();
                    List<User> uList = new ArrayList<>();
                    for (int i = 0; i < user_data.length(); i++) {
                        JSONObject objj = user_data.getJSONObject(i);
                        User user = new User();
                        user.setUser_id(objj.getString("uid"));
                        user.setUser_name(objj.getString("uname"));
                        user.setHead_pic(objj.getString("avatar"));
                        user.setDaren(objj.getString("daren"));
                        uList.add(user);
                    }
                    map.put("cate", "user");
                    map.put("title", "相关用户");
                    map.put("userList", uList);
                    list.add(map);
                }
                JSONArray circle_data = data.optJSONArray("circle_data");
                if (circle_data != null) {
                    Map<String, Object> map = new HashMap<>();
                    List<HandCircle> circleList = new ArrayList<>();
                    for (int i = 0; i < circle_data.length(); i++) {
                        JSONObject objj = circle_data.getJSONObject(i);
                        HandCircle circle = new HandCircle();
                        circle.setHand_id(objj.getString("circle_id"));
                        circle.setHost_pic(objj.getString("pic"));
                        circle.setAdd_time(objj.getString("add_time"));
                        circle.setSubject(objj.getString("subject"));
                        User user = new User();
                        user.setUser_id(objj.getString("user_id"));
                        user.setUser_name(objj.getString("user_name"));
                        circle.setUser(user);
                        circleList.add(circle);
                    }
                    map.put("cate", "circle");
                    map.put("title", "相关手工圈");
                    map.put("circleList", circleList);
                    list.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Cates> getCatesInfo(String json) {
        List<Cates> all = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                Cates cates = new Cates();
                cates.setId(obj.getString("id"));
                cates.setIco(obj.getString("ico"));
                cates.setName(obj.getString("name"));
                JSONArray child = obj.optJSONArray("child");
                List<Cate> list = new ArrayList<>();
                if (child != null) {
                    for (int j = 0; j < child.length(); j++) {
                        JSONObject jsonObject = child.getJSONObject(j);
                        Cate cate = new Cate();
                        cate.setId(jsonObject.getString("id"));
                        cate.setName(jsonObject.getString("name"));
                        JSONArray child3 = jsonObject.optJSONArray("child");
                        List<Cate3> list3 = new ArrayList<>();
                        if (child3 != null) {
                            for (int k = 0; k < child3.length(); k++) {
                                Cate3 cate3 = new Cate3();
                                JSONObject jsonObject1 = child3.getJSONObject(k);
                                cate3.setId(jsonObject1.getString("id"));
                                cate3.setName(jsonObject1.getString("name"));
                                list3.add(cate3);
                            }
                        }
                        cate.setCate3s(list3);
                        list.add(cate);
                    }
                }
                cates.setChild(list);
                all.add(cates);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return all;
    }
}