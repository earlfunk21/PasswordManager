package com.example.passwordmanager.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.passwordmanager.database.entity.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert
    void insert(Account account);

    @Query("SELECT * FROM Account WHERE appId = :appId")
    List<Account> getAccountListByAppId(long appId);

    @Query("DELETE FROM Account")
    void deleteAll();

    @Delete
    void delete(Account account);

    @Query("DELETE FROM Account WHERE appId = :appId")
    void deleteAllByAppId(long appId);
}
