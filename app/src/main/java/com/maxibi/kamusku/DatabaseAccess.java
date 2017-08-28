package com.maxibi.kamusku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.maxibi.kamusku.DatabaseOpenHelper;

import java.util.ArrayList;

/**
 * Created by User on 8/14/2017.
 */

public class DatabaseAccess {

    private static DatabaseAccess instance;
    private SQLiteOpenHelper opHelper;
    private SQLiteDatabase sqlDatabase;


    private DatabaseAccess(Context context){
        this.opHelper = new DatabaseOpenHelper(context);
    }


    //getter
    public static DatabaseAccess getInstance (Context context){
        if( instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.sqlDatabase = opHelper.getWritableDatabase();
    }

    public void close(){
        if( sqlDatabase != null){
            this.sqlDatabase.close();
        }
    }

    public ArrayList<Word> getQuotes(){
        int temp = 0;
        ArrayList<String> listA = new ArrayList<String>();
        ArrayList<String> listB = new ArrayList<String>();
        ArrayList<String> listBookmark = new ArrayList<String >();
        ArrayList<Word> listC = new ArrayList<Word>();


        String query1 = "SELECT bm FROM quotes ORDER BY bm ASC ";
        String query2 = "SELECT bi FROM quotes ORDER BY bm ASC ";
        String queryBookmark = "SELECT bookmark FROM quotes ORDER BY bm ASC";


        Cursor cursor1 = sqlDatabase.rawQuery(query1, null);
        Cursor cursor2 = sqlDatabase.rawQuery(query2, null);
        Cursor cursorBookmark = sqlDatabase.rawQuery(queryBookmark, null);


        cursor1.moveToFirst();
        while(!cursor1.isAfterLast()){
            listA.add(cursor1.getString(0));
            cursor1.moveToNext();
        }
        cursor1.close();

        cursor2.moveToFirst();
        while(!cursor2.isAfterLast()){
            listB.add(cursor2.getString(0));
            cursor2.moveToNext();
        }
        cursor2.close();

        cursorBookmark.moveToFirst();
        while(!cursorBookmark.isAfterLast())
        {
            listBookmark.add(cursorBookmark.getString(0));
            cursorBookmark.moveToNext();
        }
        cursorBookmark.close();

        for ( int i = 0; i < listA.size(); i++)
        {
            Word wordDefinition = new Word (listA.get(i),listB.get(i), temp, listBookmark.get(i));
            listC.add(wordDefinition);
            temp++;
        }
        return listC;
    }

    public Word getWord( String w){
        int temp = 0;
        Word word = null;
        String query ="SELECT * FROM quotes WHERE bm = '"+w+"'";
        Cursor cursor = sqlDatabase.rawQuery(query, null);

        if ( cursor.moveToFirst()){
            word = new Word(cursor.getString(cursor.getColumnIndex("bm")), cursor.getString(cursor.getColumnIndex("bi")), temp, cursor.getString(cursor.getColumnIndex("bookmark")));
            temp++;
        }
        return word;
    }

    public void setBookmark(String bookmark)
    {
        SQLiteDatabase sqLiteDatabase = opHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("bookmark","1");
        // String query = "UPDATE quotes SET bookmark = null WHERE bm = '"+bookmark+"'";
        sqlDatabase.update("quotes",contentValues,"bm = '"+bookmark+"'",null);
        sqLiteDatabase.close();
    }

    public void unSetBookmark(String bookmark)
    {
        SQLiteDatabase sqLiteDatabase = opHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("bookmark","null");
        // String query = "UPDATE quotes SET bookmark = null WHERE bm = '"+bookmark+"'";
        sqlDatabase.update("quotes",contentValues,"bm = '"+bookmark+"'",null);
        sqLiteDatabase.close();
    }


}
