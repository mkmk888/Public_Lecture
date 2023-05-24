package com.example.public_lecture_mainscreen;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void setInsertAlarm(Alarm alarm);

    @Update
    void setUpdateAlarm(Alarm alarm);

    @Delete
    void setDeleteAlarm(Alarm alarm);

    @Query("SELECT * FROM Alarm")
    List<Alarm> getAlarmAll();

    @Query("UPDATE Alarm SET lctreNm = :lctreNm, edcStartDay = :edcStartDay, edcEndDay = :edcEndDay, hour = :hour, minute = :minute, " +
            "monday = :monday, tuesday = :tuesday, wednesday = :wednesday, thursday = :thursday, friday = :friday, saturday = :saturday, Sunday = :Sunday WHERE id = :id")
    void alarmUpdate_all(String lctreNm, String edcStartDay, String edcEndDay, int hour, int minute,
                         String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String Sunday, int id);

    @Query("DELETE FROM Alarm WHERE id = :id")
    void Alarm_Delete(int id);

    @Query("DELETE FROM Alarm")
    void Alarm_Delete_All();

    @Query("SELECT * FROM Alarm WHERE id = :id")
    List<Alarm> get_lctreNm(int id);

}
