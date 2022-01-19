package com.cookandroid.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class myDBHelper extends SQLiteOpenHelper {
    public myDBHelper(Context context){
        super(context, "friends",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("데이터베이스실습앱", "MyDBHelper: onCreate() called");
        sqLiteDatabase.execSQL("CREATE TABLE friends (name CHAR(30) PRIMARY KEY, phone CHAR(20), birthday CHAR(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("데이터베이스실습앱", "MyDBHelper: onUpgrade() called");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS friends");
        onCreate(sqLiteDatabase);
    }
}
