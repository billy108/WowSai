package com.qianfeng.wowsai.tool;

import com.qianfeng.wowsai.model.Comments;
import com.qianfeng.wowsai.model.Course;
import com.qianfeng.wowsai.model.HandCircle;
import com.qianfeng.wowsai.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sky on 2015/4/29.
 */
public class Json2model {
    /**
     * 活动详情json解析。
     *
     * @return
     */
    public static HandCircle json2HandCircle(String json) {
        HandCircle ret = null;
        if (json != null) {
            try {
                ret = new HandCircle();
                JSONObject object = new JSONObject(json);
                JSONObject data = object.optJSONObject("data");
                if (data != null) {
                    User user = new User();
                    user.setUser_id(data.optString("uid"));
                    user.setUser_name(data.optString("uname"));
                    user.setHead_pic(data.optString("avatar"));
                    ret.setUser(user);

                    ret.setLaud_num(data.optString("laud_num"));
                    ret.setComment_num(data.optString("comment_num"));
                    ret.setVotes(data.optString("votes"));
                    ret.setSubject(data.optString("subject"));
                    ret.setAdd_time(data.optString("add_time"));
                    ret.setC_name(data.optString("c_name"));
                    ret.setPics(getCircleListPic_list(data.optJSONArray("pic")));
                    ret.setLaudList(getLaud_list(data.getJSONArray("laud_list")));
                    ret.setCommentList22(getComment_list22(data.optJSONArray("comment")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    /**
     * 获得教程详情的数据，包含若干个图片等。。。
     *
     * @param json
     * @return
     */
    public static Course json2Course(String json) {
        Course ret = null;
        if (json != null) {
            try {
                ret = new Course();
                JSONObject object = new JSONObject(json);
                JSONObject data = object.optJSONObject("data");
                if (data != null) {
                    ret.setHand_id(data.optString("hand_id"));
                    //封面图片
                    ret.setCover_pic(data.optString("host_pic_ss"));
                    //浏览数
                    ret.setView_num(data.optString("view"));
                    //收藏数据
                    ret.setCollect_num(data.optString("collect"));
                    //点赞数
                    ret.setLaud(data.optString("laud"));
                    ret.setComment_num(data.optString("comment_num"));
                    ret.setSummary(data.optString("summary"));
                    ret.setSubject(data.optString("subject"));
                    ret.setBg_pic(data.optString("host_pic_ms"));
                    ret.setShare_url(data.optString("share_url"));

                    ret.setMaterial(getCourseMaterial(data.optJSONArray("material")));
                    ret.setTools(getCourseTools(data.optJSONArray("tools")));
                    ret.setStep(getCourseStep(data.optJSONArray("step")));
                    ret.setComment(getComment_list(data.optJSONArray("comment_list")));
                    ret.setUser(getCourseUser(data));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ret;
    }

    /**
     * 查看个人信息中   手工圈列表+++=手工圈收藏列表也是一样的
     *
     * @param json
     * @return
     */
    public static List<HandCircle> json2Circle_list(String json) {
        List<HandCircle> ret = null;
        if (json != null) {
            try {
                JSONObject object = new JSONObject(json);
                JSONObject data = object.optJSONObject("data");
                if (data != null) {
                    JSONArray jsonList = data.optJSONArray("list");
                    if (jsonList != null) {
                        ret = new ArrayList<HandCircle>();
                        JSONObject temp;
                        for (int i = 0; i < jsonList.length(); i++) {
                            temp = jsonList.getJSONObject(i);
                            HandCircle circle = new HandCircle();
                            //手工圈id
                            circle.setHand_id(temp.optString("id"));
                            //封面图片
                            circle.setHost_pic(temp.optString("avatar"));
                            //作品标题
                            circle.setSubject(temp.optString("subject"));
                            //发布时间
                            circle.setAdd_time(temp.optString("add_time"));
                            //user中的属性
                            User user = new User();
                            user.setUser_id(temp.optString("uid"));
                            user.setUser_name(temp.optString("uname"));

                            circle.setUser(user);
                            //添加图片列表
                            circle.setPics(getCircleListPic_list(temp.getJSONArray("pic")));
                            //添加点赞列表
                            circle.setLaudList(getLaud_list(temp.optJSONArray("laud_list")));
                            circle.setCommentList(getCircleComment_list(temp.optJSONArray("comment")));
                            ret.add(circle);
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ret;
    }

    private static List<Comments> getCircleComment_list(JSONArray comment) {
        List<Comments> list = null;
        if (comment != null) {
            list = new ArrayList<Comments>();
            JSONObject temp;
            for (int i = 0; i < comment.length(); i++) {
                try {
                    temp = comment.getJSONObject(i);
                    Comments comments = new Comments();
                    comments.setAdd_time(temp.optString("add_time"));
                    comments.setContent(temp.optString("content"));
                    User user = new User();
                    user.setUser_id(temp.optString("uid"));
                    user.setUser_name(temp.optString("uname"));
                    user.setHead_pic(temp.optString("avatar"));
                    comments.setMe(user);
                    list.add(comments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

        return list;
    }


    /**
     * 查看个人信息中   发布教程列表++++++++教程收藏
     *
     * @param json
     * @return
     */
    public static List<Map<String, String>> getMyComment_list(String json) {
        List<Map<String, String>> list = null;
        if (json != null && !json.equals("")) {
            list = new ArrayList<Map<String, String>>();
            try {
                JSONObject object = new JSONObject(json);
                JSONObject data = object.optJSONObject("data");
                if (data != null) {
                    JSONArray json_arr = data.optJSONArray("list");
                    JSONObject temp;
                    if (json_arr != null) {
                        for (int i = 0; i < json_arr.length(); i++) {
                            Map<String, String> map = new HashMap<String, String>();
                            temp = json_arr.getJSONObject(i);
                            map.put("hand_id", temp.optString("hand_id"));
                            map.put("user_id", temp.optString("user_id"));
                            map.put("subject", temp.optString("subject"));
                            //图片
                            map.put("host_pic_ss", temp.optString("host_pic_ss"));
                            //人气
                            map.put("view", temp.optString("view"));
                            //赞
                            map.put("laud", temp.optString("laud"));
                            //简介
                            map.put("summary", temp.optString("summary"));
                            list.add(map);
                            map = null;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    /**
     * ***********************所有获取子类对象的方法************************************
     */

    private static List<User> getLaud_list(JSONArray laud_list) {
        List<User> list = null;
        if (laud_list != null) {
            try {
                list = new ArrayList<User>();
                JSONObject temp = null;
                for (int i = 0; i < laud_list.length(); i++) {
                    User user = new User();
                    temp = laud_list.getJSONObject(i);
                    user.setUser_id(temp.optString("uid"));
                    user.setUser_name(temp.optString("uname"));
                    user.setHead_pic(temp.optString("avatar"));
                    list.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    /**
     * 获取查看个人信息中   手工圈列表
     *
     * @param uname
     * @return
     */
    private static List<String> getCircleListPic_list(JSONArray uname) {
        List<String> list = null;
        if (uname != null) {
            list = new ArrayList<String>();
            JSONObject temp;
            for (int i = 0; i < uname.length(); i++) {
                try {
                    temp = uname.getJSONObject(i);
                    if (temp != null) {
                        list.add(temp.optString("url"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    /**
     * 获取Course对象中的所有user对象
     * 注：如果需要其他属性，自己扩展一个方法
     *
     * @param data
     * @return
     */
    private static User getCourseUser(JSONObject data) {
        User user = null;
        if (data != null) {
            user = new User();
            user.setUser_id(data.optString("user_id"));
            user.setUser_name(data.optString("user_name"));
            user.setHead_pic(data.optString("face_pic"));
        }
        return user;
    }

    /**
     * 获取所有的活动详情中的品论列表
     *
     * @param comment
     * @return
     */
    private static List<Map<String, String>> getComment_list22(JSONArray comment) {
        List<Map<String, String>> list = null;
        if (comment != null) {
            list = new ArrayList<Map<String, String>>();
            JSONObject temp;
            for (int i = 0; i < comment.length(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                try {
                    temp = comment.getJSONObject(i);
                    map.put("uid", temp.optString("uid"));
                    map.put("circle_item_id", temp.optString("circle_item_id"));
                    map.put("comment", temp.optString("content"));
                    map.put("add_time", temp.optString("add_time"));
                    map.put("id", temp.optString("id"));
                    map.put("user_name", temp.optString("uname"));
                    map.put("avatar", temp.optString("avatar"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.add(map);
                map = null;
            }
        }
        return list;
    }

    /**
     * 获取Course对象中的所有评论列表
     *
     * @param comment_list
     * @return
     */
    private static List<Map<String, String>> getComment_list(JSONArray comment_list) {
        List<Map<String, String>> list = null;
        if (comment_list != null) {
            list = new ArrayList<Map<String, String>>();
            JSONObject temp;
            for (int i = 0; i < comment_list.length(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                try {
                    temp = comment_list.getJSONObject(i);
                    map.put("comment_id", temp.optString("comment_id"));
                    map.put("hand_daren", temp.optString("hand_daren"));
                    map.put("add_time", temp.optString("add_time"));
                    map.put("hand_id", temp.optString("hand_id"));
                    map.put("user_id", temp.optString("user_id"));
                    map.put("user_name", temp.optString("user_name"));
                    map.put("avatar", temp.optString("avatar"));
                    map.put("comment", temp.optString("comment"));
                    map.put("lasttime", temp.optString("lasttime"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.add(map);
                map = null;
            }
        }
        return list;
    }

    /**
     * 获取Course对象中的所有步骤序列
     *
     * @param step
     * @return
     */
    private static List<Map<String, String>> getCourseStep(JSONArray step) {
        List<Map<String, String>> list = null;
        if (step != null) {
            list = new ArrayList<Map<String, String>>();
            JSONObject temp;
            for (int i = 0; i < step.length(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                try {
                    temp = step.getJSONObject(i);
                    map.put("pic", temp.optString("pic"));
                    map.put("content", temp.optString("content"));
                    map.put("pic_s", temp.optString("pic_s"));
                    map.put("w", temp.optString("w"));
                    map.put("h", temp.optString("h"));
                    map.put("url", temp.optString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.add(map);
                map = null;
            }
        }
        return list;
    }

    /**
     * 获取Course对象中的所有材料序列
     *
     * @param material
     * @return
     */
    private static List<Map<String, String>> getCourseMaterial(JSONArray material) {
        List<Map<String, String>> list = null;
        if (material != null) {
            list = new ArrayList<Map<String, String>>();
            JSONObject temp;
            for (int i = 0; i < material.length(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                try {
                    temp = material.getJSONObject(i);
                    map.put("name", temp.optString("name"));
                    map.put("num", temp.optString("num"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.add(map);
                map = null;
            }
        }
        return list;
    }

    /**
     * 获取Course对象中的所有工具序列
     *
     * @param json_arr
     * @return
     */
    private static List<Map<String, String>> getCourseTools(JSONArray json_arr) {
        List<Map<String, String>> list = null;
        if (json_arr != null) {
            list = new ArrayList<Map<String, String>>();
            JSONObject temp;
            for (int i = 0; i < json_arr.length(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                try {
                    temp = json_arr.getJSONObject(i);
                    map.put("name", temp.optString("name"));
                    map.put("num", temp.optString("num"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.add(map);
                map = null;
            }
        }
        return list;
    }


}
