package cn.com.luckytry.interview.bean;

import org.litepal.crud.DataSupport;

/**
 * 面试音频
 * Created by 魏兴 on 2017/9/7.
 */

public class InterViewPath extends DataSupport {
    //实体绑定的id
    private int beanId;
    //实体音频地址
    private String path;

    public int getBeanId() {
        return beanId;
    }

    public void setBeanId(int beanId) {
        this.beanId = beanId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
