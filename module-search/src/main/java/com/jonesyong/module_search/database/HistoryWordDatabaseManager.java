package com.jonesyong.module_search.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author JonesYang
 * @Date 2021-12-14
 * @Description 提供数据库的管理，包括数据库的打开，以及 SQL 语句,需要保证这个 Manager 的单例属性，否则在多线程的
 * 情况下会对数据库进行并发访问，会创建多个数据库实例
 */
public class HistoryWordDatabaseManager {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SearchHistory.db";

    private SQLiteDatabase mDatabase;
    // 只有一个线程执行打开数据库的操作
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private HistoryWordDatabaseManager() {
    }

    private static HistoryWordDatabaseManager instance;

    public static HistoryWordDatabaseManager createInstance() {
        if (instance == null) {
            synchronized (HistoryWordDatabaseManager.class) {
                if (instance == null) {
                    instance = new HistoryWordDatabaseManager();
                }
            }
        }
        return instance;
    }

    public void destroyInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public void openDataBase(@NotNull SQLiteOpenHelper helper) {
        // 线程池，用于打开数据库，这是一个耗时操作，应该放在子线程
        executor.execute(() -> mDatabase = helper.getWritableDatabase());
    }

    public void closeDataBase(SQLiteOpenHelper helper) {
        if (mDatabase != null) {
            mDatabase.close();
        }
        helper.close();
    }

    public Cursor query() {
        if (mDatabase == null) {
            return null;
        }
        return mDatabase.query(SearchHistoryDataBaseHelper.TABLE_NAME, null, null, null,
                null, null, null);
    }

    public long insert(@NotNull ContentValues contentValues) {
        if (mDatabase == null) {
            return -1;
        }
        return mDatabase.insert(SearchHistoryDataBaseHelper.TABLE_NAME, null, contentValues);
    }

    public int delete(String whereClause, String[] whereArgs) {
        if (mDatabase == null) {
            return -1;
        }
        return mDatabase.delete(SearchHistoryDataBaseHelper.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * 判断数据库中时候存在相同的 key，如果存在就不需要进行删除操作
     *
     * @param key key
     * @return false 不存在，true 存在
     */
    public boolean isExitKey(String key) {
        Cursor cursor = query();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String s = cursor.getString(cursor.getColumnIndex("history_key"));
            if (key.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
