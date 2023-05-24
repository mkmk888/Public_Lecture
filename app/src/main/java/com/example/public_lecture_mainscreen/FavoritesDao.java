package com.example.public_lecture_mainscreen;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Insert
    void setInsertFavorites(Favorites favorites);

    @Update
    void setUpdateFavorites(Favorites favorites);

    @Delete
    void setDeleteFavorites(Favorites favorites);

    @Query("SELECT * FROM Favorites")
    List<Favorites> getFavoritesAll();

    @Query("SELECT * FROM Favorites WHERE calender = 'T'")
    List<Favorites> getCalT();

    @Query("DELETE FROM Favorites WHERE id = :id_num")
    void F_Delete(int id_num);
    @Query("DELETE FROM Favorites")
    void F_Delete_All();

    //알림 판별
    @Query("UPDATE Favorites SET alarm = 'T' WHERE id = :id")
    void alarm_T(int id);
    @Query("UPDATE Favorites SET alarm = 'F' WHERE id = :id")
    void alarm_F(int id);

    //요일 판별
    @Query("UPDATE Favorites SET monday = 'T' WHERE id = :id AND operDay LIKE '%월%'")
    void monday_T(int id);
    @Query("UPDATE Favorites SET tuesday = 'T' WHERE id = :id AND operDay LIKE '%화%'")
    void tuesday_T(int id);
    @Query("UPDATE Favorites SET wednesday = 'T' WHERE id = :id AND operDay LIKE '%수%'")
    void wednesday_T(int id);
    @Query("UPDATE Favorites SET thursday = 'T' WHERE id = :id AND operDay LIKE '%목%'")
    void thursday_T(int id);
    @Query("UPDATE Favorites SET friday = 'T' WHERE id = :id AND operDay LIKE '%금%'")
    void friday_T(int id);
    @Query("UPDATE Favorites SET saturday = 'T' WHERE id = :id AND operDay LIKE '%토%'")
    void saturday_T(int id);
    @Query("UPDATE Favorites SET Sunday = 'T' WHERE id = :id AND operDay LIKE '%일요일%'")
    void Sunday_T1(int id);
    @Query("UPDATE Favorites SET Sunday = 'T' WHERE id = :id AND operDay LIKE '일'")
    void Sunday_T2(int id);

    @Query("UPDATE Favorites SET monday = 'T', tuesday = 'T', wednesday = 'T', thursday = 'T', friday = 'T', saturday = 'T', Sunday = 'T' WHERE id = :id")
    void All_week(int id);

    @Query("UPDATE Favorites SET calender = 'T' WHERE id = :id")
    void set_T(int id);
    @Query("UPDATE Favorites SET calender = 'F' WHERE id = :id")
    void set_F(int id);



}
