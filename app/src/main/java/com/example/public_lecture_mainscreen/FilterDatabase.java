package com.example.public_lecture_mainscreen;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Filter.class}, version = 3)
public abstract class FilterDatabase extends RoomDatabase {
    public abstract FilterDao filterDao();
}
