package cn.com.luckytry.interview.service;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import cn.com.luckytry.interview.util.DbOpenHelper;
import cn.com.luckytry.interview.util.LUtil;

/**
 * Created by asus on 2017/9/12.
 */

public class InterViewPathProvider extends ContentProvider {

    public static final String AUTHORITY = "cn.com.luckytry.interview.service";
    public static final Uri PATH_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/path");
    public static final int PATH_URI_CODE = 0;
    private static final UriMatcher sUriMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "path", PATH_URI_CODE);
    }
    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDb =new DbOpenHelper(mContext).getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        LUtil.e("query");
        return mDb.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private String getTableName(Uri uri) {
        LUtil.e("getTableName");
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case PATH_URI_CODE:
                tableName = "interpath.db";
                break;

            default:break;
        }

        return tableName;
    }
}
