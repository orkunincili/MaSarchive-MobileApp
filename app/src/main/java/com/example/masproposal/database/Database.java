package com.example.masproposal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    private static final String TAG="Database";
    public static String DATABASE_NAME = "masdb";
    public static final String TABLE_MOVIE="movies";
    private static final String COL_ID="ID";
    private static final String COL1="movie_name";
    private static final String COL2="movie_rate";
    private static final String COL3="movie_duration";
    private static final String COL4="movie_summary";
    private static final String COL5="movie_poster_url";
    private static final String COL6="movie_page_url";
    private static final String COL7="watched";
    private static final String COL8="user_notes";


    public static final String TABLE_TV="tv_series";
    private static final String tv_ID="ID";
    private static final String tv_name="tv_name";
    private static final String tv_rate="tv_rate";
    private static final String tv_duration="tv_duration";
    private static final String tv_summary="tv_summary";
    private static final String tv_poster_url="tv_poster_url";
    private static final String tv_page_url="tv_page_url";
    private static final String seasons="seasons";
    private static final String tv_watched="tv_watched";
    private static final String fav_tv="fav_tv";
    private static final String rand_ep_poster="rand_ep_poster";
    private static final String user_tv_summary="user_summary";

    public Database(Context context){

        super(context,DATABASE_NAME,null,1);
    }
    public boolean addMovie(String name,String rate,String duration,
                            String summary,String poster,String movie_page_url,
                            String watched)
    {
        SQLiteDatabase movies_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,name);
        contentValues.put(COL2,rate);
        contentValues.put(COL3,duration);
        contentValues.put(COL4,summary);
        contentValues.put(COL5,poster);
        contentValues.put(COL6,movie_page_url);
        contentValues.put(COL7,watched);



        Log.d(TAG, "addData: Adding " +name+ " to " + TABLE_MOVIE);

        long result = movies_db.insert(TABLE_MOVIE, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean addTv(String name,String rate,String duration,String summary,String poster,
                         String page_url,String season,String watched,String fav,String ep_poster,String user_summary)
    {
        SQLiteDatabase movies_db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(tv_name,name);
        contentValues.put(tv_rate,rate);
        contentValues.put(tv_duration,duration);
        contentValues.put(tv_summary,summary);
        contentValues.put(tv_poster_url,poster);
        contentValues.put(tv_page_url,page_url);
        contentValues.put(seasons,season);
        contentValues.put(tv_watched,watched);
        contentValues.put(fav_tv,fav);
        contentValues.put(rand_ep_poster,ep_poster);
        contentValues.put(user_tv_summary,user_summary);



        Log.d(TAG, "addData: Adding " +name+ " to " + TABLE_MOVIE);

        long result = movies_db.insert(TABLE_TV, null, contentValues);


        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase movies_db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MOVIE;
        Cursor data = movies_db.rawQuery(query, null);
        return data;
    }
    public Cursor getDatatv(){
        SQLiteDatabase tv_db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TV;
        Cursor data = tv_db.rawQuery(query, null);
        return data;

    }
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + "*" + " FROM " + TABLE_MOVIE+
                " WHERE " + COL5 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteMovie(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_MOVIE + " WHERE "
                + COL1 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
    public void addNote(String name,String note ){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+ TABLE_MOVIE + " SET "+ COL8 + " = "+ "'"+note+"'"+" WHERE "+
                COL1 + " = "+ "'"+name+"'";


        Log.d(TAG, "not eklendi" + query);
        db.execSQL(query);

    }
    public Cursor getSummary(String movie_name){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + "*" + " FROM " + TABLE_MOVIE+
                " WHERE " + COL1 + " = '" + movie_name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    /*
    public void watched(String movie_name,String watched){
        SQLiteDatabase db=this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL7 +
                " = '" + watched + "' WHERE " + COL1 + " = '" + movie_name+"'";
    }
    */
    public Cursor getWatched(String watched){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + "*" + " FROM " + TABLE_MOVIE +
                " WHERE " + COL7 + " = '" + watched + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableMovie = "CREATE TABLE " + TABLE_MOVIE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL1 +" TEXT, " +
                COL2+" TEXT, "+
                COL3+" TEXT, "+
                COL4+" TEXT, "+COL5+" TEXT, "+COL6+" TEXT, "+COL7+" TEXT, "+COL8+" TEXT)";
        String createTableTv = "CREATE TABLE " + TABLE_TV + "(" + tv_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                tv_name +" TEXT, " +
                tv_rate+" TEXT, "+
                tv_duration+" TEXT, "+
                tv_summary+" TEXT, "+
                tv_poster_url+" TEXT, "+
                tv_page_url+" TEXT, "+
                seasons+" TEXT, "+
                tv_watched+" TEXT, "+
                fav_tv+" TEXT, "+
                rand_ep_poster+" TEXT);";

        sqLiteDatabase.execSQL(createTableMovie);
        sqLiteDatabase.execSQL(createTableTv);
    }
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_MOVIE;
        db.execSQL(clearDBQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        onCreate(sqLiteDatabase);
    }
}
