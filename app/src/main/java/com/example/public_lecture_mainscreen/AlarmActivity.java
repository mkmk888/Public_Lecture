package com.example.public_lecture_mainscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;


public class AlarmActivity extends AppCompatActivity {
    private Button save, nowAlarmViewId;
    private TimePicker timePicker;
    //public AlarmManager alarmManager;
    CheckBox cbMon, cbTue, cbWed, cbThu, cbFri, cbSat, cbSun;
    String check_Mon, check_Tue, check_Wed, check_Thu, check_Fri, check_Sat, check_Sun;
    TextView alarm_text1, alarm_text2,alarm_text3;
    int alarmCheckIndex;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alarmpage);

        timePicker=(TimePicker)findViewById(R.id.time_picker);
        save=(Button)findViewById(R.id.save);

        cbMon=findViewById(R.id.cb_mon);
        cbTue=findViewById(R.id.cb_tue);
        cbWed=findViewById(R.id.cb_wed);
        cbThu=findViewById(R.id.cb_thu);
        cbFri=findViewById(R.id.cb_fri);
        cbSat=findViewById(R.id.cb_sat);
        cbSun=findViewById(R.id.cb_sun);

        alarm_text1 = findViewById(R.id.Course_title);
        alarm_text2 = findViewById(R.id.A_T2);
        alarm_text3 = findViewById(R.id.A_T4);

        List<Favorites> filterinformation = ((MainActivity) MainActivity.context).mFavoritesDao.getFavoritesAll();
        alarmCheckIndex = ((MainActivity)MainActivity.context).alarmCheckIndex;
        nowAlarmViewId = ((MainActivity)MainActivity.context).nowAlarmViewId;
        List<Alarm> alarmdatabase = ((MainActivity) MainActivity.context).mAlarmDao.getAlarmAll();
        int alarmIndex = alarmIndex();

        if(filterinformation.get(alarmCheckIndex).getAlarm().equals("F")){//알림설정 처음일 때
            alarm_text1.setText(filterinformation.get(alarmCheckIndex).getLctreNm());
            alarm_text2.setText(filterinformation.get(alarmCheckIndex).getEdcStartDay());
            alarm_text3.setText(filterinformation.get(alarmCheckIndex).getEdcEndDay());

            if(filterinformation.get(alarmCheckIndex).getMonday().equals("T")){
                cbMon.setChecked(true);
            }
            if(filterinformation.get(alarmCheckIndex).getTuesday().equals("T")){
                cbTue.setChecked(true);
            }
            if(filterinformation.get(alarmCheckIndex).getWednesday().equals("T")){
                cbWed.setChecked(true);
            }
            if(filterinformation.get(alarmCheckIndex).getThursday().equals("T")){
                cbThu.setChecked(true);
            }
            if(filterinformation.get(alarmCheckIndex).getFriday().equals("T")){
                cbFri.setChecked(true);
            }
            if(filterinformation.get(alarmCheckIndex).getSaturday().equals("T")){
                cbSat.setChecked(true);
            }
            if(filterinformation.get(alarmCheckIndex).getSunday().equals("T")){
                cbSun.setChecked(true);
            }

        }
        else{
            if(alarmIndex != -1){
                alarm_text1.setText(alarmdatabase.get(alarmIndex).getLctreNm());
                alarm_text2.setText(alarmdatabase.get(alarmIndex).getEdcStartDay());
                alarm_text3.setText(alarmdatabase.get(alarmIndex).getEdcEndDay());

                timePicker.setCurrentHour(alarmdatabase.get(alarmIndex).getHour());
                timePicker.setCurrentMinute(alarmdatabase.get(alarmIndex).getMinute());

                if(alarmdatabase.get(alarmIndex).getMonday().equals("T")){
                    cbMon.setChecked(true);
                }
                if(alarmdatabase.get(alarmIndex).getTuesday().equals("T")){
                    cbTue.setChecked(true);
                }
                if(alarmdatabase.get(alarmIndex).getWednesday().equals("T")){
                    cbWed.setChecked(true);
                }
                if(alarmdatabase.get(alarmIndex).getThursday().equals("T")){
                    cbThu.setChecked(true);
                }
                if(alarmdatabase.get(alarmIndex).getFriday().equals("T")){
                    cbFri.setChecked(true);
                }
                if(alarmdatabase.get(alarmIndex).getSaturday().equals("T")){
                    cbSat.setChecked(true);
                }
                if(alarmdatabase.get(alarmIndex).getSunday().equals("T")){
                    cbSun.setChecked(true);
                }
            }
        }


        save.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int hour=timePicker.getHour();
            int minute=timePicker.getMinute();
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);

            //알림 데이터베이스에 저장 코드 시작
            if(alarmIndex == -1){
                Alarm alarm = new Alarm();
                alarm.setLctreNm((String) alarm_text1.getText());
                alarm.setEdcStartDay((String) alarm_text2.getText());
                alarm.setEdcEndDay((String) alarm_text3.getText());

                alarm.setHour(hour);
                alarm.setMinute(minute);

                if(cbMon.isChecked()){
                    alarm.setMonday("T");
                }
                else{
                    alarm.setMonday("F");
                }

                if(cbTue.isChecked()){
                    alarm.setTuesday("T");
                }
                else{
                    alarm.setTuesday("F");
                }

                if(cbWed.isChecked()){
                    alarm.setWednesday("T");
                }
                else{
                    alarm.setWednesday("F");
                }
                if(cbThu.isChecked()){
                    alarm.setThursday("T");
                }
                else{
                    alarm.setThursday("F");
                }
                if(cbFri.isChecked()){
                    alarm.setFriday("T");
                }
                else{
                    alarm.setFriday("F");
                }
                if(cbSat.isChecked()){
                    alarm.setSaturday("T");
                }
                else{
                    alarm.setSaturday("F");
                }
                if(cbSun.isChecked()){
                    alarm.setSunday("T");
                }
                else{
                    alarm.setSunday("F");
                }
                ((MainActivity) MainActivity.context).mAlarmDao.setInsertAlarm(alarm);
                ((MainActivity) MainActivity.context).mFavoritesDao.alarm_T(filterinformation.get(alarmCheckIndex).getId());

                Toast.makeText(this,"알람이 저장되었습니다.",Toast.LENGTH_LONG).show();
                finish();

            }
            else{
                if(cbMon.isChecked()){
                    check_Mon = "T";
                }
                else{
                    check_Mon = "F";
                }

                if(cbTue.isChecked()){
                    check_Tue = "T";
                }
                else{
                    check_Tue = "F";
                }

                if(cbWed.isChecked()){
                    check_Wed = "T";
                }
                else{
                    check_Wed = "F";
                }
                if(cbThu.isChecked()){
                    check_Thu = "T";
                }
                else{
                    check_Thu = "F";
                }
                if(cbFri.isChecked()){
                    check_Fri = "T";
                }
                else{
                    check_Fri = "F";
                }
                if(cbSat.isChecked()){
                    check_Sat = "T";
                }
                else{
                    check_Sat = "F";
                }
                if(cbSun.isChecked()){
                    check_Sun = "T";
                }
                else{
                    check_Sun = "F";
                }
                int alarmUpdate_ID = alarmdatabase.get(alarmIndex).getId();
                ((MainActivity) MainActivity.context).mAlarmDao.alarmUpdate_all((String) alarm_text1.getText(),(String) alarm_text2.getText(),(String) alarm_text3.getText(),hour,minute,
                        check_Mon, check_Tue, check_Wed, check_Thu, check_Fri, check_Sat, check_Sun,alarmUpdate_ID);
                Toast.makeText(this,"알람이 수정되었습니다.",Toast.LENGTH_LONG).show();
                finish();
            }

            boolean[] week = { false, cbSun.isChecked(), cbMon.isChecked(), cbTue.isChecked(), cbWed.isChecked(),
                    cbThu.isChecked(), cbFri.isChecked(), cbSat.isChecked() };

            AlarmManager alarmManager=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

            List<Alarm> alarmdatabase_ID = ((MainActivity) MainActivity.context).mAlarmDao.getAlarmAll();
            int alarmIndex_ID = alarmIndex();
            int alarmID = alarmdatabase_ID.get(alarmIndex_ID).getId();//알람 ID

            if (alarmManager != null) {
                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.putExtra("weekday", week);
                intent.putExtra("alarmID", alarmID);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(this, alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);
            }
            nowAlarmViewId.setBackgroundResource(R.drawable.green_button);
        });
    }

    public void alarmCancle(View view) {
        //알림데이터베이스 삭제
        List<Alarm> alarmdatabase = ((MainActivity) MainActivity.context).mAlarmDao.getAlarmAll();
        int alarmIndex = alarmIndex();
        if(alarmIndex == -1){
            Toast.makeText(this,"알람이 설정되지 않았습니다.",Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            int alarmID = alarmdatabase.get(alarmIndex).getId();
            ((MainActivity) MainActivity.context).mAlarmDao.Alarm_Delete(alarmID);

            AlarmManager alarmManager=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(alarmIntent);


            //즐찾 데이터베이스 알림 F 설정
            List<Favorites> filterinformation = ((MainActivity) MainActivity.context).mFavoritesDao.getFavoritesAll();
            alarmCheckIndex = ((MainActivity)MainActivity.context).alarmCheckIndex;
            int alarm_delet_ID = filterinformation.get(alarmCheckIndex).getId();
            ((MainActivity) MainActivity.context).mFavoritesDao.alarm_F(alarm_delet_ID);

            Toast.makeText(this,"알람이 삭제되었습니다.",Toast.LENGTH_LONG).show();

            nowAlarmViewId.setBackgroundResource(R.drawable.roundedrectangle_gray);

            finish();
        }
    }


    public void alarmClose(View v){
        finish();
    }

    public int alarmIndex(){
        List<Favorites> filterinformation = ((MainActivity) MainActivity.context).mFavoritesDao.getFavoritesAll();
        alarmCheckIndex = ((MainActivity)MainActivity.context).alarmCheckIndex;
        List<Alarm> alarmdatabase = ((MainActivity) MainActivity.context).mAlarmDao.getAlarmAll();
        int alarmdatabase_size = alarmdatabase.size();
        for (int i = 0; i < alarmdatabase_size; i++) {
            if(alarmdatabase.get(i).getLctreNm().equals(filterinformation.get(alarmCheckIndex).getLctreNm()) &&
                    alarmdatabase.get(i).getEdcStartDay().equals(filterinformation.get(alarmCheckIndex).getEdcStartDay()) &&
                    alarmdatabase.get(i).getEdcEndDay().equals(filterinformation.get(alarmCheckIndex).getEdcEndDay())){
                return i;
            }
        }
        return -1;
    }


}
