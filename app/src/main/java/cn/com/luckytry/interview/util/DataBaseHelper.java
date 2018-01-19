package cn.com.luckytry.interview.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.com.luckytry.interview.bean.InterViewMoudle;

/**
 * 数据库工具类
 * Created by 魏兴 on 2017/12/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * 是否正在查询
     */
    private static boolean isQuery = false;

    public static final String CREATE_INTERVIEWS = "create table interviews ("
            + "id integer primary key autoincrement, "
            + "group text, "
            + "fileName text, "
            + "adress text,"
            + "isStar integer,"
            + "isRead integer)";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

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



    }

    public static void getGroupData(){

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INTERVIEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
