package com.example.kuou.module.search.database;

import static com.example.kuou.module.search.database.HistoryWordDatabaseManager.DATABASE_NAME;
import static com.example.kuou.module.search.database.HistoryWordDatabaseManager.DATABASE_VERSION;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * @Author JonesYang
 * @Date 2021-12-14
 * @Description 历史词数据库，负责数据库的创建升级
 */
public class SearchHistoryDataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "search_history_word";
    private static final String CREATE_TABLE = "CREATE TABLE search_history_word(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "history_key TEXT," +
            "history_value TEXT)";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS search_history_word";


    public SearchHistoryDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

}
