package com.example.nppproject.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nppproject.Entity.ContentSaveEntity;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLClickHistory";
    static final String DB_NAME_TABLE = "ClickHistory";
    static final String DB_NAME= "ClickHistory.db";
    static final int VERSION= 3;

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    /*public int deleteItemSave(String title){
        sqLiteDatabase= getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME_TABLE, "title=?", new String[]{title});
    }
    public boolean deleteAll(){
        int result;
        sqLiteDatabase = getWritableDatabase();
        result=sqLiteDatabase.delete(DB_NAME_TABLE, null, null);
        closeDB();
        if (result==1)
            return true;
        else return false;
    }*/
    private void closeDB() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ClickHistory = "CREATE TABLE ClickHistory ( "+
                "title TEXT," +
                "link TEXT )";

        sqLiteDatabase.execSQL(ClickHistory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i!= i1){
            sqLiteDatabase.execSQL("drop table if exists "+ DB_NAME_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public void insertContent(String title, String link){
        sqLiteDatabase= getWritableDatabase();
        contentValues= new ContentValues();
        contentValues.put("title",title);
        contentValues.put("link",link);

        sqLiteDatabase.insert(DB_NAME_TABLE, null, contentValues);
        closeDB();
    }

    public List<ContentSaveEntity> getAllContent(){
        List<ContentSaveEntity> list= new ArrayList<>();
        ContentSaveEntity entity;
        sqLiteDatabase= getReadableDatabase();
        cursor= sqLiteDatabase.query(false, DB_NAME_TABLE, null, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String title= cursor.getString(cursor.getColumnIndex("title"));
            String link= cursor.getString(cursor.getColumnIndex("link"));
            entity= new ContentSaveEntity(title, link);
            list.add(entity);
        }
        closeDB();
        return list;
    }
}
