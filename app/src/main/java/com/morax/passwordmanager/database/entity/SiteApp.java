package com.morax.passwordmanager.database.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SiteApp {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    public SiteApp() {
    }

    public SiteApp(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
