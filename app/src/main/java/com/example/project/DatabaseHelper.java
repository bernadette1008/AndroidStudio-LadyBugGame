package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GAME_DB";
    private final String tbName = "USER_SCORE";
    private static final int DATABASE_VERSION = 1;
    Context context;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+tbName+" (SCORE INTEGER NOT NULL, USER TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER_SCORE");
        onCreate(db);
    }

    public void saveScore(String userName, int score){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("SCORE", score);
        values.put("USER", userName);

//        String sql = String.format("INSERT INTO %s values(%d, '%s');",tbName,score,userName);
//        db.execSQL(sql);

        Long result = db.insert(tbName, null, values);
        System.out.println(result);
    }

    public Cursor getAllScore(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + tbName +" ORDER BY SCORE DESC";
        Cursor res = db.rawQuery(sql, null);
        return res;
    }
}
