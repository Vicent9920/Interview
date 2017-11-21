package cn.com.luckytry.interview.bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * Created by 魏兴 on 2017/11/6.
 */

public class InterViewUser extends BmobUser {

    private ArrayList<String> readed = new ArrayList<>();
    private ArrayList<String> stars = new ArrayList<>();

    public ArrayList<String> getReaded() {
        return readed;
    }

    public void setReaded(ArrayList<String> readed) {
        this.readed = readed;
    }

    public ArrayList<String> getStars() {
        return stars;
    }

    public void setStars(ArrayList<String> stars) {
        this.stars = stars;
    }
}
