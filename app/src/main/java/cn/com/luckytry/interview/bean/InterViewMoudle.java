package cn.com.luckytry.interview.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by 魏兴 on 2017/12/14.
 * 新版文章实体
 */

public class InterViewMoudle extends DataSupport {


    private int id;
    /**
     * 组别
     */
    @Column(unique = true)
    private String group;
    /**
     * 文章名称
     */
    private String fileName;
    /**
     * 文章地址
     */
    private String adress;
    /**
     * 是否收藏
     */
    private boolean isStar;
    /**
     * 是否已读
     */
    private boolean isRead;
    /**
     * 文章内容
     */
    @Column(ignore = true)
    private String text;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
