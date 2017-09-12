package cn.com.luckytry.interview.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 2017/9/11.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    public DbOpenHelper(Context context) {
        super(context, "interpath.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "path(_id INTEGER PRIMARY KEY," + "beanId INT,"+
                " + path TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
