package cn.com.luckytry.interview.bean;

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

/**
 * 面试文章实体
 * Created by 魏兴 on 2017/8/25.
 */

public class InterviewBean extends DataSupport {
    //卷
    private String part;
    //知识类型
    private String tag;
    //文章名称
    private String name;
    //网址
    private String adress;
    //文章内容
    private String content;
    //是否可加载
    private boolean isCanLoad;
    //是否已经收藏
    private boolean isStar;

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCanLoad() {
        return isCanLoad;
    }

    public void setCanLoad(boolean canLoad) {
        isCanLoad = canLoad;
    }

    public String toGson(){
        return new Gson().toJson(this);
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }
}
