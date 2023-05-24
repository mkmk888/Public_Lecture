package com.example.public_lecture_mainscreen;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favorites {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String lctreNm;
    private String instrctrNm;
    private String edcStartDay;
    private String edcEndDay;
    private String edcStartTime;
    private String edcColseTime;
    private String lctreCo;
    private String edcTrgetType;
    private String edcMthType;
    private String operDay;
    private String edcPlace;
    private String psncpa;
    private String lctreCost;
    private String edcRdnmadr;
    private String operInstitutionNm;
    private String operPhoneNumber;
    private String rceptStartDate;
    private String rceptEndDate;
    private String rceptMthType;
    private String slctnMthType;
    private String hompageUrl;
    private String referenceDate;

    private String calender;
    private String alarm;

    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String Sunday;

    public String getSunday() {
        return Sunday;
    }

    public void setSunday(String sunday) {
        Sunday = sunday;
    }

    public String getCalender() {
        return calender;
    }

    public void setCalender(String calender) {
        this.calender = calender;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLctreNm() {
        return lctreNm;
    }

    public void setLctreNm(String lctreNm) {
        this.lctreNm = lctreNm;
    }

    public String getInstrctrNm() {
        return instrctrNm;
    }

    public void setInstrctrNm(String instrctrNm) {
        this.instrctrNm = instrctrNm;
    }

    public String getEdcStartDay() {
        return edcStartDay;
    }

    public void setEdcStartDay(String edcStartDay) {
        this.edcStartDay = edcStartDay;
    }

    public String getEdcEndDay() {
        return edcEndDay;
    }

    public void setEdcEndDay(String edcEndDay) {
        this.edcEndDay = edcEndDay;
    }

    public String getEdcStartTime() {
        return edcStartTime;
    }

    public void setEdcStartTime(String edcStartTime) {
        this.edcStartTime = edcStartTime;
    }

    public String getEdcColseTime() {
        return edcColseTime;
    }

    public void setEdcColseTime(String edcColseTime) {
        this.edcColseTime = edcColseTime;
    }

    public String getLctreCo() {
        return lctreCo;
    }

    public void setLctreCo(String lctreCo) {
        this.lctreCo = lctreCo;
    }

    public String getEdcTrgetType() {
        return edcTrgetType;
    }

    public void setEdcTrgetType(String edcTrgetType) {
        this.edcTrgetType = edcTrgetType;
    }

    public String getEdcMthType() {
        return edcMthType;
    }

    public void setEdcMthType(String edcMthType) {
        this.edcMthType = edcMthType;
    }

    public String getOperDay() {
        return operDay;
    }

    public void setOperDay(String operDay) {
        this.operDay = operDay;
    }

    public String getEdcPlace() {
        return edcPlace;
    }

    public void setEdcPlace(String edcPlace) {
        this.edcPlace = edcPlace;
    }

    public String getPsncpa() {
        return psncpa;
    }

    public void setPsncpa(String psncpa) {
        this.psncpa = psncpa;
    }

    public String getLctreCost() {
        return lctreCost;
    }

    public void setLctreCost(String lctreCost) {
        this.lctreCost = lctreCost;
    }

    public String getEdcRdnmadr() {
        return edcRdnmadr;
    }

    public void setEdcRdnmadr(String edcRdnmadr) {
        this.edcRdnmadr = edcRdnmadr;
    }

    public String getOperInstitutionNm() {
        return operInstitutionNm;
    }

    public void setOperInstitutionNm(String operInstitutionNm) {
        this.operInstitutionNm = operInstitutionNm;
    }

    public String getOperPhoneNumber() {
        return operPhoneNumber;
    }

    public void setOperPhoneNumber(String operPhoneNumber) {
        this.operPhoneNumber = operPhoneNumber;
    }

    public String getRceptStartDate() {
        return rceptStartDate;
    }

    public void setRceptStartDate(String rceptStartDate) {
        this.rceptStartDate = rceptStartDate;
    }

    public String getRceptEndDate() {
        return rceptEndDate;
    }

    public void setRceptEndDate(String rceptEndDate) {
        this.rceptEndDate = rceptEndDate;
    }

    public String getRceptMthType() {
        return rceptMthType;
    }

    public void setRceptMthType(String rceptMthType) {
        this.rceptMthType = rceptMthType;
    }

    public String getSlctnMthType() {
        return slctnMthType;
    }

    public void setSlctnMthType(String slctnMthType) {
        this.slctnMthType = slctnMthType;
    }

    public String getHompageUrl() {
        return hompageUrl;
    }

    public void setHompageUrl(String hompageUrl) {
        this.hompageUrl = hompageUrl;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }
}
