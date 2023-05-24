package com.example.public_lecture_mainscreen;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 2)
public abstract class CourseDatabase extends RoomDatabase{
    public abstract CourseDao courseDao();

}