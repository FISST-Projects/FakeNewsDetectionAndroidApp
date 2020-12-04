package com.example.fakenewsdetection.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fakenewsdetection.ui.home.ModelHistory;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FakeNewsDetection.db";
    public static final String HISTORY_TABLE_NAME = "history";
    public static final String HISTORY_COLUMN_ID = "id";
    public static final String HISTORY_COLUMN_LINK = "link";
    public static final String HISTORY_COLUMN_DATETIME = "datetime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists history " +
                        "(id integer primary key autoincrement, link text,datetime date)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE_NAME );
        onCreate(db);

    }
    public boolean insertHistory (String link,String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("link", link);
        contentValues.put("datetime", datetime);
        db.insert(HISTORY_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getHistoryById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from  where id="+id+"", null );
        return res;
    }

    public ArrayList<ModelHistory> getAllHistory() {
        ArrayList<ModelHistory> array_list = new ArrayList<ModelHistory>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from history order by datetime desc", null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            array_list.add(new ModelHistory(res.getInt(res.getColumnIndex(HISTORY_COLUMN_ID)),
                    res.getString(res.getColumnIndex(HISTORY_COLUMN_LINK)),
                    res.getString(res.getColumnIndex(HISTORY_COLUMN_DATETIME))
            ));
            res.moveToNext();
        }
        return array_list;
    }

    public Integer deleteHistoryById(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("history",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void deleteAllHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+  HISTORY_TABLE_NAME);
        db.close();
    }
}
