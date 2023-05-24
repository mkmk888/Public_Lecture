package com.example.public_lecture_mainscreen;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Filter {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String idd;
    private String Gangwondo;
    private String Seoul;
    private String Gyeonggido;
    private String Sejong;
    private String Gyeongsangnamdo;
    private String Gwangju;
    private String Gyeongsangbukdo;
    private String Daegu;
    private String Jeollanamdo;
    private String Busan;
    private String Jeollabukdo;
    private String Ulsan;
    private String Chungcheongnamdo;
    private String Incheon;
    private String Chungcheongbukdo;
    private String Jeju;
    private String Etc;

    private String CourseStartDay;
    private String CourseEndDay;

    private String Euro;
    private String Free;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getGangwondo() {
        return Gangwondo;
    }

    public void setGangwondo(String gangwondo) {
        Gangwondo = gangwondo;
    }

    public String getSeoul() {
        return Seoul;
    }

    public void setSeoul(String seoul) {
        Seoul = seoul;
    }

    public String getGyeonggido() {
        return Gyeonggido;
    }

    public void setGyeonggido(String gyeonggido) {
        Gyeonggido = gyeonggido;
    }

    public String getSejong() {
        return Sejong;
    }

    public void setSejong(String sejong) {
        Sejong = sejong;
    }

    public String getGyeongsangnamdo() {
        return Gyeongsangnamdo;
    }

    public void setGyeongsangnamdo(String gyeongsangnamdo) {
        Gyeongsangnamdo = gyeongsangnamdo;
    }

    public String getGwangju() {
        return Gwangju;
    }

    public void setGwangju(String gwangju) {
        Gwangju = gwangju;
    }

    public String getGyeongsangbukdo() {
        return Gyeongsangbukdo;
    }

    public void setGyeongsangbukdo(String gyeongsangbukdo) {
        Gyeongsangbukdo = gyeongsangbukdo;
    }

    public String getDaegu() {
        return Daegu;
    }

    public void setDaegu(String daegu) {
        Daegu = daegu;
    }

    public String getJeollanamdo() {
        return Jeollanamdo;
    }

    public void setJeollanamdo(String jeollanamdo) {
        Jeollanamdo = jeollanamdo;
    }

    public String getBusan() {
        return Busan;
    }

    public void setBusan(String busan) {
        Busan = busan;
    }

    public String getJeollabukdo() {
        return Jeollabukdo;
    }

    public void setJeollabukdo(String jeollabukdo) {
        Jeollabukdo = jeollabukdo;
    }

    public String getUlsan() {
        return Ulsan;
    }

    public void setUlsan(String ulsan) {
        Ulsan = ulsan;
    }

    public String getChungcheongnamdo() {
        return Chungcheongnamdo;
    }

    public void setChungcheongnamdo(String chungcheongnamdo) {
        Chungcheongnamdo = chungcheongnamdo;
    }

    public String getIncheon() {
        return Incheon;
    }

    public void setIncheon(String incheon) {
        Incheon = incheon;
    }

    public String getChungcheongbukdo() {
        return Chungcheongbukdo;
    }

    public void setChungcheongbukdo(String chungcheongbukdo) {
        Chungcheongbukdo = chungcheongbukdo;
    }

    public String getJeju() {
        return Jeju;
    }

    public void setJeju(String jeju) {
        Jeju = jeju;
    }

    public String getEtc() {
        return Etc;
    }

    public void setEtc(String etc) {
        Etc = etc;
    }

    public String getCourseStartDay() {
        return CourseStartDay;
    }

    public void setCourseStartDay(String courseStartDay) {
        CourseStartDay = courseStartDay;
    }

    public String getCourseEndDay() {
        return CourseEndDay;
    }

    public void setCourseEndDay(String courseEndDay) {
        CourseEndDay = courseEndDay;
    }

    public String getEuro() {
        return Euro;
    }

    public void setEuro(String euro) {
        Euro = euro;
    }

    public String getFree() {
        return Free;
    }

    public void setFree(String free) {
        Free = free;
    }
//alt+ins


}
