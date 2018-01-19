package cn.com.luckytry.interview.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import cn.com.luckytry.interview.bean.InterViewMoudle;

/**
 * 数据库管理类
 * Created by 魏兴 on 2018/1/1.
 */

public class DataBaseManager {

    private static DataBaseHelper dataHelper;
    public static void initDataBaseManager(Context context){
        if(dataHelper == null){
            dataHelper = new DataBaseHelper(context,"data.db",null,1);
        }
    }
    public static void saveData(List<InterViewMoudle> data){
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        for (InterViewMoudle moudle:data) {
            ContentValues values = new ContentValues();
            values.put("group", moudle.getGroupName());
            values.put("fileName", moudle.getFileName());
            values.put("adress", moudle.getAdress());
            values.put("isStar", moudle.isStar()?0:1);
            values.put("isRead", moudle.isRead()?0:1);
            db.insert("interviews", null, values);
        }
    }


}
