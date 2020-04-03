package com.example.cga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "coursedb";
    private static final String TABLE_Users = "coursetable";
    private static final String KEY_ID = "id";
    private static final String COURSE_NAME = "coursename";
    private static final String COURSE_CODE = "coursecode";
    private static final String COURSE_LOAD = "courseload";
    private static final String COURSE_SCORE = "coursescore";
    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COURSE_NAME + " TEXT,"
                + COURSE_CODE + " TEXT,"
                + COURSE_LOAD + " INTEGER,"
                + COURSE_SCORE + " INTEGER"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    public void myDataPut(String courseTitle,String courseCode, int courseLoad, int courseScore){
       //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
//Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(COURSE_NAME, courseTitle);
        cValues.put(COURSE_CODE, courseCode);
        cValues.put(COURSE_LOAD, courseLoad);
        cValues.put(COURSE_SCORE, courseScore);
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
    }
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT coursename, coursecode, courseload,coursescore FROM "+ TABLE_Users;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("coursename",cursor.getString(cursor.getColumnIndex(COURSE_NAME)));
            user.put("coursecode",cursor.getString(cursor.getColumnIndex(COURSE_CODE)));
            user.put("courseload",cursor.getString(cursor.getColumnIndex(COURSE_LOAD)));
            user.put("coursescore",cursor.getString(cursor.getColumnIndex(COURSE_SCORE)));
            userList.add(user);
        }
        return  userList;
    }

}
