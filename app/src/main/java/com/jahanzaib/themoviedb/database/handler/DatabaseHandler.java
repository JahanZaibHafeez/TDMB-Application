package com.jahanzaib.themoviedb.database.handler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jahanzaib.themoviedb.database.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "themoviedb";

    // movie table name
    private static final String TABLE_MOVIE = "movie";

    // movie Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_RELEASE_YEAR = "release_year";
    private static final String KEY_IMAGE_DATA = "image_data";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MOVIE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MOVIE_ID + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_RELEASE_YEAR + " TEXT," + KEY_IMAGE_DATA + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);

        // Create tables again
        onCreate(db);
    }

    // Adding new movie
    public void addMovie(Movie movie) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_MOVIE_ID, movie.getMovieId());
            values.put(KEY_NAME, movie.getName());
            values.put(KEY_RELEASE_YEAR, movie.getReleaseYear());
            values.put(KEY_IMAGE_DATA, movie.getImageData());

            // Inserting Row
            db.insert(TABLE_MOVIE, null, values);
            db.close(); // Closing database connection


    }

    // Getting All Movies
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setMovieId(cursor.getString(1));
                movie.setName(cursor.getString(2));
                movie.setReleaseYear(cursor.getString(3));
                movie.setImageData(cursor.getString(4));
                // Adding movie to list
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        // return movie list
        return movieList;
    }

    public boolean checkAvailabilty(String movieID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MOVIE, new String[] { KEY_MOVIE_ID,
                        KEY_NAME }, KEY_MOVIE_ID + "=?",
                new String[] { String.valueOf(movieID) }, null, null, null, null);

        if(cursor.getCount() > 0 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Deleting single movie
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIE, KEY_ID + " = ?",
                new String[] { String.valueOf(movie.getId()) });
        db.close();
    }


    // Getting movie Count
    public int getMoviesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MOVIE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}