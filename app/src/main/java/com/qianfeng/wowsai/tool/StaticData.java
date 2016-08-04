package com.qianfeng.wowsai.tool;

/**
 * Created by Sky on 2015/4/29.
 */
public class StaticData {

    /**
     * 发现
     * 首页列表
     */
    public static final String FIND_SHOUYE =
            "http://m.shougongke.com/index.php?&c=index&a=indexnew";
    /**
     * 发现
     * 动态
     */
    public static final String FIND_DONGTAI =
            "http://d.shougongke.com/index.php?c=Mobnotice&a=dynami";
    /**
     * 发现
     * 动态 分页  id 为上一页最后一条的pmid
     */
    public static final String FIND_DONGTAI_PAGE =
            "http://d.shougongke.com/index.php?c=Mobnotice&a=dynami&act=lt&id=";

    /**
     * 发现
     * slide 简介
     */
    public static final String SLIDE_URL =
            "http://m.shougongke.com/index.php?c=HandClass&a=info&id=";
    /**
     * 发现
     * slide 评论
     */
    public static final String SLIDE_COMMENT =
            "http://m.shougongke.com/index.php?&c=HandClass&a=commentList&id=";
    /**
     * 发现
     * 达人
     */
    public static final String FOUND_DAREN =
            "http://m.shougongke.com/index.php?&c=Index&a=daren";
    /**
     * 发现
     * 达人 分页   ！！！post请求
     * 参数 last_id  达人列表中的  course_time
     */
    public static final String FOUND_DAREN_PAGE =
            "http://m.shougongke.com/index.php?&c=Index&a=daren";
    /**
     * 发现
     * 专题
     */
    public static final String FOUND_TOPIC =
            "http://m.shougongke.com/index.php?&c=Course&a=topic";
    /**
     * 发现
     * 专题  分页
     */
    public static final String FOUND_TOPIC_PAGE =
            "http://m.shougongke.com/index.php?&c=Course&a=topic&page=";
    /**
     * 发现
     * 专题 详情
     * <p/>
     * 分页  后面再传  page  ;
     */
    public static final String TOPIC_DETIAL =
            "http://m.shougongke.com/index.php?&c=Course&a=topic&id=";
    /**
     * 详情点击
     */
    public static final String TOPIC_DETIAL_CHECK =
            "http://m.shougongke.com/index.php?c=Course&a=CourseDetial&id=";
    /**
     * 发现
     * 活动
     */
    public static final String FOUND_ACTIVITY =
            "http://m.shougongke.com/index.php?&c=Course&a=activityList";
    /**
     * 发现
     * 活动 分页
     * 上一页最后一条的id
     */
    public static final String FOUND_ACTIVITY_PAGE =
            "http://m.shougongke.com/index.php?&c=Course&a=activityList&id=";
    /**
     * 活动图片
     */
    public static final String ACTIVITY_PIC =
            "http://m.shougongke.com/index.php?c=Competition&cid=";
    /**
     * 活动最新作品
     * 分页 参数 last_id  上一页最后一条的last_id
     */
    public static final String ACTIVITY_ZUIXIN =
            "http://m.shougongke.com/index.php?c=Competition&a=getOpus&cid=";
    /**
     * 活动作品的详情页
     * item_id 为 circle_item_id 字段
     */
    public static final String ACTIVITY_DETIAL =
            "http://m.shougongke.com/index.php?c=HandCircle&a=info&item_id=";
    /**
     * 投票最多
     */
    public static final String VOTES =
            "http://m.shougongke.com/index.php?c=Competition&a=getOpus&order=votes&cid=";

    /**
     * 教程
     * 最新教程 列表
     * 分页  lastid从上一个页面获取
     */
    public static final String COURSE_LIST =
            "http://m.shougongke.com/index.php?c=Course&a=CourseList&lastid=";
    /**
     * 教程
     * 最新教程详情
     */
    public static final String COURSE_DETIAL =
            "http://m.shougongke.com/index.php?c=Course&a=CourseDetial&id=";
    /**
     * 以下是点击头像，查看个人信息需要访问的url
     */
    //发布教程
    public static final String USER_MYCOURSE =
            "http://m.shougongke.com/index.php?&c=User&a=myCourse&uid=";
    //手工圈
    //手工圈收藏 后面再添加参数 collect=1;
    public static final String USER_CIRCLE =
            "http://m.shougongke.com/index.php?c=User&a=getCircleList&uid=";
    //教程收藏
    public static final String USER_COLLECT_COURSE =
            "http://m.shougongke.com/index.php?&c=User&a=collectCourse&uid=";
    //关注
    //!!! 需要用post方法
    //    act=up&uid=13623588&
    public static final String USER_GUANZHU =
            "http://m.shougongke.com/index.php?&c=User&a=guanList";
    //粉丝
    //!!! 需要用post方法
    //    uid=13623588&act=up
    public static final String USER_FEN =
            "http://m.shougongke.com/index.php?&c=User&a=fenList";
    /**
     * 最热教程
     * <p/>
     * 列表
     * <p/>
     * 详情同上
     */
    public static final String HOT_COURSE_LIST =
            "http://m.shougongke.com/index.php?c=Course&a=CourseList&type=2&lastid=";

    /**
     * 类别
     * 1.难度：初学、入门、能手、高手、大师id：1、2、3、4、5
     */
    public static final String COURSE_LIST_TYPE =
            "http://m.shougongke.com/index.php?c=Course&a=CourseList&type=2&gcate=difficulty&id=";
    /**
     * 2.风格：可爱、复古、清新、民族、摇滚、英伦、北欧、田园
     */
    public static final String COURSE_LIST_STYLE =
            "http://m.shougongke.com/index.php?c=Course&a=CourseList&type=2&gcate=style&id=";
    /**
     * 3.人群：儿童、妈妈、老年人、小清新、宅男、宅女、上班族、学生党、极客
     */
    public static final String COURES_LIST_CROWD =
            "http://m.shougongke.com/index.php?c=Course&a=CourseList&type=2&gcate=crowd&id=";

    /**
     * 排行榜
     * <p/>
     * 1.列表
     */
    public static final String RANKING =
            "http://m.shougongke.com/index.php?c=Ranking";
    /**
     * 2.一周列表
     * 分页   &last_id=10
     */
    public static final String RANKING_WEEK =
            "http://m.shougongke.com/index.php?c=Ranking&a=course&id=w";

    /**
     * 登录
     */
    public static final String LOGIN = "http://m.shougongke.com/index.php?c=Login&a=login";

    /**
     * 获得验证码
     */
    public static final String GET_CODE = "http://m.shougongke.com/index.php?c=Login&a=getVerify";

    /**
     * 注册
     */
    public static final String REGISTER = "http://m.shougongke.com/index.php?c=Login&a=register";

    /**
     * 查询
     */
    public static final String SEARCH = "http://m.shougongke.com/index.php?&c=Search&a=index";

    /**
     * 分类
     */
    public static final String CATES = "http://m.shougongke.com/index.php?c=course&a=ccate";
}