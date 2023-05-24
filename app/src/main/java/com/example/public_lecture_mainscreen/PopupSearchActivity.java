package com.example.public_lecture_mainscreen;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import java.util.Calendar;
import java.util.List;

public class PopupSearchActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_search);

        CheckBox checkBox_Gangwondo = (CheckBox) findViewById(R.id.area1);
        CheckBox checkBox_Seoul = (CheckBox) findViewById(R.id.area2);
        CheckBox checkBox_Gyeonggido = (CheckBox) findViewById(R.id.area3);
        CheckBox checkBox_Sejong = (CheckBox) findViewById(R.id.area4);
        CheckBox checkBox_Gyeongsangnamdo = (CheckBox) findViewById(R.id.area5);
        CheckBox checkBox_Gwangju = (CheckBox) findViewById(R.id.area6);
        CheckBox checkBox_Gyeongsangbukdo = (CheckBox) findViewById(R.id.area7);
        CheckBox checkBox_Daegu = (CheckBox) findViewById(R.id.area8);
        CheckBox checkBox_Jeollanamdo = (CheckBox) findViewById(R.id.area9);
        CheckBox checkBox_Busan = (CheckBox) findViewById(R.id.area10);
        CheckBox checkBox_Jeollabukdo = (CheckBox) findViewById(R.id.area11);
        CheckBox checkBox_Ulsan = (CheckBox) findViewById(R.id.area12);
        CheckBox checkBox_Chungcheongnamdo = (CheckBox) findViewById(R.id.area13);
        CheckBox checkBox_Incheon = (CheckBox) findViewById(R.id.area14);
        CheckBox checkBox_Chungcheongbukdo = (CheckBox) findViewById(R.id.area15);
        CheckBox checkBox_Jeju = (CheckBox) findViewById(R.id.area16);
        CheckBox checkBox_Etc = (CheckBox) findViewById(R.id.area17);

        TextView textView_stratDay,textView_endDay;
        textView_stratDay = findViewById(R.id.course_start_date_textview);
        textView_endDay = findViewById(R.id.course_end_date_textview);

        CheckBox checkBox_Euro = (CheckBox) findViewById(R.id.tuition1);
        CheckBox checkBox_Free = (CheckBox) findViewById(R.id.tuition2);

        //현재 설정 상태 가져오기(T면 체크한 상태로 보여짐
        List<Filter> filterinformation = ((MainActivity) MainActivity.context).mFilterDao.getFilterAll();//다른 액티비티에서 받아오는방법
        if(filterinformation.get(0).getGangwondo().equals("T")){
            checkBox_Gangwondo.setChecked(true);
        }
        if(filterinformation.get(0).getSeoul().equals("T")){
            checkBox_Seoul.setChecked(true);
        }
        if(filterinformation.get(0).getGyeonggido().equals("T")){
            checkBox_Gyeonggido.setChecked(true);
        }
        if(filterinformation.get(0).getSejong().equals("T")){
            checkBox_Sejong.setChecked(true);
        }
        if(filterinformation.get(0).getGyeongsangnamdo().equals("T")){
            checkBox_Gyeongsangnamdo.setChecked(true);
        }
        if(filterinformation.get(0).getGwangju().equals("T")){
            checkBox_Gwangju.setChecked(true);
        }
        if(filterinformation.get(0).getGyeongsangbukdo().equals("T")){
            checkBox_Gyeongsangbukdo.setChecked(true);
        }
        if(filterinformation.get(0).getDaegu().equals("T")){
            checkBox_Daegu.setChecked(true);
        }
        if(filterinformation.get(0).getJeollanamdo().equals("T")){
            checkBox_Jeollanamdo.setChecked(true);
        }
        if(filterinformation.get(0).getBusan().equals("T")){
            checkBox_Busan.setChecked(true);
        }
        if(filterinformation.get(0).getJeollabukdo().equals("T")){
            checkBox_Jeollabukdo.setChecked(true);
        }
        if(filterinformation.get(0).getUlsan().equals("T")){
            checkBox_Ulsan.setChecked(true);
        }
        if(filterinformation.get(0).getChungcheongnamdo().equals("T")){
            checkBox_Chungcheongnamdo.setChecked(true);
        }
        if(filterinformation.get(0).getIncheon().equals("T")){
            checkBox_Incheon.setChecked(true);
        }
        if(filterinformation.get(0).getChungcheongbukdo().equals("T")){
            checkBox_Chungcheongbukdo.setChecked(true);
        }
        if(filterinformation.get(0).getJeju().equals("T")){
            checkBox_Jeju.setChecked(true);
        }
        if(filterinformation.get(0).getEtc().equals("T")){
            checkBox_Etc.setChecked(true);
        }

        //강의 시작/종료 날자 가져오기
        try{
            textView_stratDay.setText(filterinformation.get(0).getCourseStartDay());
            textView_endDay.setText(filterinformation.get(0).getCourseEndDay());
        }catch (Exception e){}

        //강좌 유료 무료 현재 상태 가져오기
        if(filterinformation.get(0).getEuro().equals("T")){
            checkBox_Euro.setChecked(true);
        }
        if(filterinformation.get(0).getFree().equals("T")){
            checkBox_Free.setChecked(true);
        }


    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        Button closeBtn = (Button) findViewById(R.id.search_close_button);
        checkbox_save();
        ((MainActivity) MainActivity.context).LctreNm_search(closeBtn);
        //액티비티(팝업) 닫기
        finish();
    }

    //
    public void checkbox_save(){
        List<Filter> filterinformation = ((MainActivity) MainActivity.context).mFilterDao.getFilterAll();
        int nowgetid = filterinformation.get(0).getId();

        CheckBox checkBox_Gangwondo = (CheckBox) findViewById(R.id.area1);
        CheckBox checkBox_Seoul = (CheckBox) findViewById(R.id.area2);
        CheckBox checkBox_Gyeonggido = (CheckBox) findViewById(R.id.area3);
        CheckBox checkBox_Sejong = (CheckBox) findViewById(R.id.area4);
        CheckBox checkBox_Gyeongsangnamdo = (CheckBox) findViewById(R.id.area5);
        CheckBox checkBox_Gwangju = (CheckBox) findViewById(R.id.area6);
        CheckBox checkBox_Gyeongsangbukdo = (CheckBox) findViewById(R.id.area7);
        CheckBox checkBox_Daegu = (CheckBox) findViewById(R.id.area8);
        CheckBox checkBox_Jeollanamdo = (CheckBox) findViewById(R.id.area9);
        CheckBox checkBox_Busan = (CheckBox) findViewById(R.id.area10);
        CheckBox checkBox_Jeollabukdo = (CheckBox) findViewById(R.id.area11);
        CheckBox checkBox_Ulsan = (CheckBox) findViewById(R.id.area12);
        CheckBox checkBox_Chungcheongnamdo = (CheckBox) findViewById(R.id.area13);
        CheckBox checkBox_Incheon = (CheckBox) findViewById(R.id.area14);
        CheckBox checkBox_Chungcheongbukdo = (CheckBox) findViewById(R.id.area15);
        CheckBox checkBox_Jeju = (CheckBox) findViewById(R.id.area16);
        CheckBox checkBox_Etc = (CheckBox) findViewById(R.id.area17);

        TextView textView_stratDay,textView_endDay;
        textView_stratDay = findViewById(R.id.course_start_date_textview);
        textView_endDay = findViewById(R.id.course_end_date_textview);

        CheckBox checkBox_Euro = (CheckBox) findViewById(R.id.tuition1);
        CheckBox checkBox_Free = (CheckBox) findViewById(R.id.tuition2);

        //현제 체크박스 정보 데이터베이스에 저장
        if(checkBox_Gangwondo.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Gangwondo_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Gangwondo_F();
        }
        if(checkBox_Seoul.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Seoul_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Seoul_F();
        }
        if(checkBox_Gyeonggido.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Gyeonggido_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Gyeonggido_F();
        }
        if(checkBox_Sejong.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Sejong_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Sejong_F();
        }
        if(checkBox_Gyeongsangnamdo.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Gyeongsangnamdo_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Gyeongsangnamdo_F();
        }
        if(checkBox_Gwangju.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Gwangju_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Gwangju_F();
        }
        if(checkBox_Gyeongsangbukdo.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Gyeongsangbukdo_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Gyeongsangbukdo_F();
        }
        if(checkBox_Daegu.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Daegu_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Daegu_F();
        }
        if(checkBox_Jeollanamdo.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Jeollanamdo_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Jeollanamdo_F();
        }
        if(checkBox_Busan.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Busan_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Busan_F();
        }
        if(checkBox_Jeollabukdo.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Jeollabukdo_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Jeollabukdo_F();
        }
        if(checkBox_Ulsan.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Ulsan_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Ulsan_F();
        }
        if(checkBox_Chungcheongnamdo.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Chungcheongnamdo_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Chungcheongnamdo_F();
        }
        if(checkBox_Incheon.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Incheon_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Incheon_F();
        }
        if(checkBox_Chungcheongbukdo.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Chungcheongbukdo_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Chungcheongbukdo_F();
        }
        if(checkBox_Jeju.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Jeju_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Jeju_F();
        }
        if(checkBox_Etc.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Etc_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Etc_F();
        }

        ((MainActivity) MainActivity.context).mFilterDao.Update_CourseStartDay((String) textView_stratDay.getText());
        ((MainActivity) MainActivity.context).mFilterDao.Update_CourseEndDay((String) textView_endDay.getText());


        if(checkBox_Euro.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Euro_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Euro_F();
        }
        if(checkBox_Free.isChecked()){
            ((MainActivity) MainActivity.context).mFilterDao.Free_T();
        }else {
            ((MainActivity) MainActivity.context).mFilterDao.Free_F();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    //분류검색중 날짜 메소드
    public void Course_date(View view) {
        TextView textView_stratDay,textView_endDay;
        textView_stratDay = findViewById(R.id.course_start_date_textview);
        textView_endDay = findViewById(R.id.course_end_date_textview);

        ImageButton Ibtn_stratDay,Ibtn_endDay;
        Ibtn_stratDay = findViewById(R.id.course_start_date_button);
        Ibtn_endDay = findViewById(R.id.course_end_date_button);

        Button btn_cencel1,btn_cencel2;
        btn_cencel1 = findViewById(R.id.calendar_cancel_btn1);
        btn_cencel2 = findViewById(R.id.calendar_cancel_btn2);

        //강좌시작날짜 가져오기
        if (view == Ibtn_stratDay) {
            Calendar c = Calendar.getInstance();
            int nYear = c.get(Calendar.YEAR);
            int nMon = c.get(Calendar.MONTH);
            int nDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                            textView_stratDay.setText(year + "-" + String.format("%02d",(month+1))+"-"+ String.format("%02d",(dayOfMonth)));
                        }
                    };
            DatePickerDialog oDialog = new DatePickerDialog(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog,
                    mDateSetListener, nYear, nMon, nDay);
            oDialog.show();
        }
        //강좌종료날짜 가져오기
        else if(view ==Ibtn_endDay){
            Calendar c = Calendar.getInstance();
            int nYear = c.get(Calendar.YEAR);
            int nMon = c.get(Calendar.MONTH);
            int nDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                            textView_endDay.setText(year + "-" + String.format("%02d",(month+1))+"-"+ String.format("%02d",(dayOfMonth)));
                        }
                    };
            DatePickerDialog oDialog = new DatePickerDialog(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog,
                    mDateSetListener, nYear, nMon, nDay);
            oDialog.show();
        }

        //시작날짜 초기화
        else if(view == btn_cencel1){
            textView_stratDay.setText("-");
        }

        //종료날짜 초기화
        else if(view == btn_cencel2){
            textView_endDay.setText("-");
        }
        else{}
    }

}
