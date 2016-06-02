package me.kasun.technews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kasun on 2016-05-26.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TechNews.db";
    public static final String NEWS_TABLE_NAME = "news";
    public static final String NEWS_COLUMN_TITLE = "title";
    public static final String NEWS_COLUMN_DESCRIPTION = "description";
    public static final String NEWS_COLUMN_IMAGEURL = "imageurl";
    public static final String NEWS_COLUMN_NEWSURL = "newsurl";
    public static final String NEWS_COLUMN_NEWSDATE = "newsdate";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NEWS_TABLE_NAME + " (id integer primary key, title text UNIQUE, description text, imageurl text, newsurl text, newsdate TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS news");
        onCreate(db);
    }

    public long insertNews(String title, String description, String imageurl, String newsurl, String newsDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String currentDate = new Date().toString();
        try {
            Date date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(newsDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            currentDate = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        contentValues.put(NEWS_COLUMN_TITLE, title);
        contentValues.put(NEWS_COLUMN_DESCRIPTION, description);
        contentValues.put(NEWS_COLUMN_IMAGEURL, imageurl);
        contentValues.put(NEWS_COLUMN_NEWSURL, newsurl);
        contentValues.put(NEWS_COLUMN_NEWSDATE, currentDate);
        long result = db.insert(NEWS_TABLE_NAME, null, contentValues);
        return result;
    }

    public ArrayList<News> getNews() {
        ArrayList<News> news_list = new ArrayList<News>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from news", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            News tmpNews = new News();
            tmpNews.id = Integer.parseInt(res.getString(0));
            tmpNews.title = res.getString(1);
            tmpNews.description = res.getString(2);
            tmpNews.imageUrl = res.getString(3);
            tmpNews.newsUrl = res.getString(4);
            tmpNews.newsDate = res.getString(5);
            news_list.add(tmpNews);
            res.moveToNext();
        }
        res.close();
        Collections.sort(news_list, Collections.reverseOrder());
        return news_list;
    }

    public Integer deleteNews(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("news", "id=? ", new String[]{Integer.toString(id)});
    }
}
