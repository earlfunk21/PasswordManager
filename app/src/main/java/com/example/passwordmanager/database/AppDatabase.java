package com.example.passwordmanager.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.passwordmanager.database.dao.AccountDao;
import com.example.passwordmanager.database.dao.SiteAppDao;
import com.example.passwordmanager.database.entity.Account;
import com.example.passwordmanager.database.entity.SiteApp;

@Database(entities = {SiteApp.class, Account.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SiteAppDao siteAppDao();
    public abstract AccountDao accountDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database1")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

