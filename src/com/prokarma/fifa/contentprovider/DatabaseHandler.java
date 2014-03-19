package com.prokarma.fifa.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prokarma.fifa.models.soccer.SeasonType;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "FWC2014";
 
    // Contacts table name
    private static final String TABLE_SEASON_TYPES = "SeasonTypes";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SEASON_TYPES_TABLE = "CREATE TABLE " + TABLE_SEASON_TYPES + "("
                + KEY_ID + " INTEGER," + KEY_NAME + " TEXT,"
                + KEY_START_DATE + " TEXT," + KEY_END_DATE + " TEXT" + ")";
        db.execSQL(CREATE_SEASON_TYPES_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEASON_TYPES);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new Season type
    public void addSeasonType(SeasonType seasonType) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, seasonType.getId());
        values.put(KEY_NAME, seasonType.getName());
        values.put(KEY_START_DATE, seasonType.getStartDate());
        values.put(KEY_END_DATE, seasonType.getEndDate());
     
        // Inserting Row
        db.insert(TABLE_SEASON_TYPES, null, values);
        db.close(); // Closing database connection
    	
    }

}



