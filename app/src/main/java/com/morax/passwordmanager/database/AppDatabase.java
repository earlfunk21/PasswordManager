package com.morax.passwordmanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.morax.passwordmanager.database.dao.AccountDao;
import com.morax.passwordmanager.database.dao.SiteAppDao;
import com.morax.passwordmanager.database.entity.Account;
import com.morax.passwordmanager.database.entity.SiteApp;

@Database(entities = {SiteApp.class, Account.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SiteAppDao siteAppDao();
    public abstract AccountDao accountDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "password_manager_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

