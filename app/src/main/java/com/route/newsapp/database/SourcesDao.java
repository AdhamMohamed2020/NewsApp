package com.route.newsapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.route.newsapp.model.sourcesResponse.SourcesItem;

import java.util.List;


@Dao
public interface SourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllSources(List<SourcesItem> items);

    @Query("select * from sourcesitem")
    List<SourcesItem> getAllSources();
}
