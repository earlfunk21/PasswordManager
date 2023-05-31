package com.example.passwordmanager.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.passwordmanager.database.entity.SiteApp;

import java.util.List;

@Dao
public interface SiteAppDao {

    @Insert
    long insert(SiteApp siteApp);

    @Query("SELECT * FROM SiteApp")
    List<SiteApp> getSiteAppList();

    @Query("DELETE FROM SiteApp")
    void deleteAll();

    @Delete
    void delete(SiteApp siteApp);

    @Query("SELECT * FROM SiteApp WHERE id = :id")
    SiteApp getSiteAppById(long id);
}
