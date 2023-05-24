package com.example.public_lecture_mainscreen;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class AlarmReceiver extends BroadcastReceiver {

    private Context context;
    private String channelId="alarm_channel";
    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        boolean[] week = intent.getBooleanArrayExtra("weekday");
        int alarmID = intent.getIntExtra("alarmID",0);
        Calendar cal = Calendar.getInstance();
        Calendar statrDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        Calendar nowDay = Calendar.getInstance();


        List<Alarm> alarm_lctreNm = ((MainActivity) MainActivity.context).mAlarmDao.get_lctreNm(alarmID);

        String start_Day = alarm_lctreNm.get(0).getEdcStartDay();
        String end_Day = alarm_lctreNm.get(0).getEdcEndDay();
        String[] strmob = start_Day.split("-");
        String[] strmob2 = end_Day.split("-");
        String str1 = strmob[0];
        String str2 = strmob[1];
        String str3 = strmob[2];
        String endstr1 = strmob2[0];
        String endstr2 = strmob2[1];
        String endstr3 = strmob2[2];
        int a = Integer.parseInt(str1);
        int b = Integer.parseInt(str2) - 1;
        int c = Integer.parseInt(str3);
        int d = Integer.parseInt(endstr1);
        int e = Integer.parseInt(endstr2) - 1;
        int f = Integer.parseInt(endstr3);
        statrDay.set(a, b, c);
        endDay.set(d,e,f);
        statrDay.add(java.util.Calendar.DATE, -1);
        endDay.add(java.util.Calendar.DATE, 1);

        if(nowDay.after(statrDay) && endDay.after(nowDay)){
            if (!week[cal.get(Calendar.DAY_OF_WEEK)])
                return;
            else if(week[cal.get(Calendar.DAY_OF_WEEK)]){
                final NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,channelId)
                        .setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_ALL)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setAutoCancel(true)
                        .setContentTitle(alarm_lctreNm.get(0).getLctreNm())
                        .setContentText("오늘 강좌가 있는 날입니다.");

                final NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    NotificationChannel channel=new NotificationChannel(channelId,"Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                int id=(int)System.currentTimeMillis();

                notificationManager.notify(id,notificationBuilder.build());
            }
        }


    }


}