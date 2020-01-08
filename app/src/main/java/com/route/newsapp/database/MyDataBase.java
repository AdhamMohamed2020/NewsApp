package com.route.newsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.route.newsapp.model.sourcesResponse.SourcesItem;


@Database(exportSchema = false,entities = {SourcesItem.class},version = 1)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase myDataBase ;
    public abstract SourcesDao sourcesDao ();

    public static MyDataBase getInstance(){
        if(myDataBase==null)
            throw new NullPointerException("database is null try to call init first");
        return myDataBase;
    }
    public static void init(Context context){
        myDataBase = Room.databaseBuilder(context,MyDataBase.class,
                "newsDataBase")
                .fallbackToDestructiveMigration()
                .build();
    }
}
