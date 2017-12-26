package cn.com.luckytry.interview.util;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.com.luckytry.interview.bean.InterViewMoudle;

/**
 * 数据库工具类
 * Created by 魏兴 on 2017/12/17.
 */

public class DataBaseHelper {

    /**
     * 是否正在查询
     */
    private static boolean isQuery = false;

    /**
     * 更新数据库
     * @param newSize
     * @param data
     */
    public static void updateDataBase(int newSize, List<InterViewMoudle> data){
        if(!isQuery){
            isQuery = true;
            int size = DataSupport.findAll(InterViewMoudle.class).size();
            if(size != newSize){
                DataSupport.deleteAll(InterViewMoudle.class);
                DataSupport.saveAll(data);
            }
        }else{

        }

        while (!isQuery){


            isQuery = false;
            break;
        };

    }

    public static void getGroupData(){

    }
}
