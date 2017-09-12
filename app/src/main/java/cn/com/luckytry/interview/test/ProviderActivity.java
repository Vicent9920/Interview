package cn.com.luckytry.interview.test;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.util.LUtil;

/**
 * Created by asus on 2017/9/12.
 */

public class ProviderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        // Uri uri = Uri.parse("content://com.ryg.chapter_2.book.provider");
        // getContentResolver().query(uri, null, null, null, null);
        // getContentResolver().query(uri, null, null, null, null);
        // getContentResolver().query(uri, null, null, null, null);

        Uri bookUri = Uri.parse("content://com.ryg.chapter_2.book.provider/book");
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {

            LUtil.e("query book: "+bookCursor.getString(1));
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://com.ryg.chapter_2.book.provider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            LUtil.e("query user:" + userCursor.getString(1));
        }
        userCursor.close();
    }
}
