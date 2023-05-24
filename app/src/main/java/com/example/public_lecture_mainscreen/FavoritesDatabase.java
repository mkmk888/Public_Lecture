package com.example.public_lecture_mainscreen;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favorites.class}, version = 2)
public abstract class FavoritesDatabase extends RoomDatabase {
    public abstract FavoritesDao favoritesDao();
}
