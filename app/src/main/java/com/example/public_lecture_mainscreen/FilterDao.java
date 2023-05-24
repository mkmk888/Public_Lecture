package com.example.public_lecture_mainscreen;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FilterDao {
    @Insert
    void setInsertFilter(Filter filter);

    @Update
    void setUpdateFilter(Filter filter);

    @Delete
    void setDeleteFilter(Filter filter);

    @Query("SELECT * FROM Filter")
    List<Filter> getFilterAll();


    @Query("DELETE FROM Filter")
    void AllDelete_Filterdb();

//지역 업데이트
    @Query("UPDATE Filter SET Gangwondo = 'T' WHERE idd = '0'")
    void Gangwondo_T();
    @Query("UPDATE Filter SET Gangwondo = 'F' WHERE idd = '0'")
    void Gangwondo_F();
    @Query("UPDATE Filter SET Seoul = 'T' WHERE idd = '0'")
    void Seoul_T();
    @Query("UPDATE Filter SET Seoul = 'F' WHERE idd = '0'")
    void Seoul_F();
    @Query("UPDATE Filter SET Gyeonggido = 'T' WHERE idd = '0'")
    void Gyeonggido_T();
    @Query("UPDATE Filter SET Gyeonggido = 'F' WHERE idd = '0'")
    void Gyeonggido_F();
    @Query("UPDATE Filter SET Sejong = 'T' WHERE idd = '0'")
    void Sejong_T();
    @Query("UPDATE Filter SET Sejong = 'F' WHERE idd = '0'")
    void Sejong_F();
    @Query("UPDATE Filter SET Gyeongsangnamdo = 'T' WHERE idd = '0'")
    void Gyeongsangnamdo_T();
    @Query("UPDATE Filter SET Gyeongsangnamdo = 'F' WHERE idd = '0'")
    void Gyeongsangnamdo_F();
    @Query("UPDATE Filter SET Gwangju = 'T' WHERE idd = '0'")
    void Gwangju_T();
    @Query("UPDATE Filter SET Gwangju = 'F' WHERE idd = '0'")
    void Gwangju_F();
    @Query("UPDATE Filter SET Gyeongsangbukdo = 'T' WHERE idd = '0'")
    void Gyeongsangbukdo_T();
    @Query("UPDATE Filter SET Gyeongsangbukdo = 'F' WHERE idd = '0'")
    void Gyeongsangbukdo_F();
    @Query("UPDATE Filter SET Daegu = 'T' WHERE idd = '0'")
    void Daegu_T();
    @Query("UPDATE Filter SET Daegu = 'F' WHERE idd = '0'")
    void Daegu_F();
    @Query("UPDATE Filter SET Jeollanamdo = 'T' WHERE idd = '0'")
    void Jeollanamdo_T();
    @Query("UPDATE Filter SET Jeollanamdo = 'F' WHERE idd = '0'")
    void Jeollanamdo_F();
    @Query("UPDATE Filter SET Busan = 'T' WHERE idd = '0'")
    void Busan_T();
    @Query("UPDATE Filter SET Busan = 'F' WHERE idd = '0'")
    void Busan_F();
    @Query("UPDATE Filter SET Jeollabukdo = 'T' WHERE idd = '0'")
    void Jeollabukdo_T();
    @Query("UPDATE Filter SET Jeollabukdo = 'F' WHERE idd = '0'")
    void Jeollabukdo_F();
    @Query("UPDATE Filter SET Ulsan = 'T' WHERE idd = '0'")
    void Ulsan_T();
    @Query("UPDATE Filter SET Ulsan = 'F' WHERE idd = '0'")
    void Ulsan_F();
    @Query("UPDATE Filter SET Chungcheongnamdo = 'T' WHERE idd = '0'")
    void Chungcheongnamdo_T();
    @Query("UPDATE Filter SET Chungcheongnamdo = 'F' WHERE idd = '0'")
    void Chungcheongnamdo_F();
    @Query("UPDATE Filter SET Incheon = 'T' WHERE idd = '0'")
    void Incheon_T();
    @Query("UPDATE Filter SET Incheon = 'F' WHERE idd = '0'")
    void Incheon_F();
    @Query("UPDATE Filter SET Chungcheongbukdo = 'T' WHERE idd = '0'")
    void Chungcheongbukdo_T();
    @Query("UPDATE Filter SET Chungcheongbukdo = 'F' WHERE idd = '0'")
    void Chungcheongbukdo_F();
    @Query("UPDATE Filter SET Jeju = 'T' WHERE idd = '0'")
    void Jeju_T();
    @Query("UPDATE Filter SET Jeju = 'F' WHERE idd = '0'")
    void Jeju_F();
    @Query("UPDATE Filter SET Etc = 'T' WHERE idd = '0'")
    void Etc_T();
    @Query("UPDATE Filter SET Etc = 'F' WHERE idd = '0'")
    void Etc_F();


    //강좌 시작/종료날자 업데이트
    @Query("UPDATE Filter SET CourseStartDay = :stratday WHERE idd = '0'")
    void Update_CourseStartDay(String stratday);
    @Query("UPDATE Filter SET CourseEndDay = :endday WHERE idd = '0'")
    void Update_CourseEndDay(String endday);


    //유로 무료 업데이트
    @Query("UPDATE Filter SET Euro = 'T' WHERE idd = '0'")
    void Euro_T();
    @Query("UPDATE Filter SET Euro = 'F' WHERE idd = '0'")
    void Euro_F();
    @Query("UPDATE Filter SET Free = 'T' WHERE idd = '0'")
    void Free_T();
    @Query("UPDATE Filter SET Free = 'F' WHERE idd = '0'")
    void Free_F();

    //필터유무
}
