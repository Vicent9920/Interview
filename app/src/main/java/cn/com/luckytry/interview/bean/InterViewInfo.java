package cn.com.luckytry.interview.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 魏兴 on 2017/10/28.
 */

public class InterViewInfo extends BmobObject {
    /**
     * 一级分类
     */
    private String part;
    /**
     * 二级分类
     */
    private String type;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件类型
     */
    private Integer file_Type;
    /**
     * 文件地址
     */
    private String file_Link;
    /**
     * 文件内容
     * 不用提交到后台的数据
     */
    private transient  String file_Content;
    /**
     * 是否收藏
     */
    private Boolean isStar;
    /**
     * 是否已读
     */
    private Boolean isRead;
    /**
     * 音频文件
     */
    private BmobFile speechFile;
    /**
     * 多对多关系：用于存储收藏该文章的所有用户
     */
    private BmobRelation stars;
    /**
     * 多对多关系：用于存储已阅读该文章的所有用户
     */
    private BmobRelation readed;


    public BmobRelation getStars() {
        return stars;
    }

    public void setStars(BmobRelation stars) {
        this.stars = stars;
    }

    public BmobRelation getReaded() {
        return readed;
    }

    public void setReaded(BmobRelation readed) {
        this.readed = readed;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFile_Type() {
        return file_Type;
    }

    public void setFile_Type(Integer file_Type) {
        this.file_Type = file_Type;
    }

    public String getFile_Link() {
        return file_Link;
    }

    public void setFile_Link(String file_Link) {
        this.file_Link = file_Link;
    }

    public String getFile_Content() {
        return file_Content;
    }

    public void setFile_Content(String file_Content) {
        this.file_Content = file_Content;
    }

    public Boolean getStar() {
        return isStar;
    }

    public void setStar(Boolean star) {
        isStar = star;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public BmobFile getSpeechFile() {
        return speechFile;
    }

    public void setSpeechFile(BmobFile speechFile) {
        this.speechFile = speechFile;
    }
}
