package com.example.public_lecture_mainscreen;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;

public class FragmentCalender extends Fragment {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        TextView textView1 = view.findViewById(R.id.calpage);
        TextView textView2 = view.findViewById(R.id.caltext);

        ArrayList<CalendarDay> dates = new ArrayList<>();


        List<Favorites> CalendarT = ((MainActivity) MainActivity.context).mFavoritesDao.getCalT();

        String fragString = "총 " + CalendarT.size() + "개\n";

        String calendertext = "";
        for (int i = 0; i < CalendarT.size(); i++) {
            calendertext += (i + 1) + ". " +CalendarT.get(i).getLctreNm()+"\n"+ CalendarT.get(i).getEdcStartDay() + " ~ " + CalendarT.get(i).getEdcEndDay() + " / 요일 : " + CalendarT.get(i).getOperDay() + "\n\n";


            //0>size i
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            String str = CalendarT.get(i).getEdcStartDay();
            String endstr = CalendarT.get(i).getEdcEndDay();
            String[] strmob = str.split("-");
            String[] strmob2 = endstr.split("-");
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


            cal.set(a, b, c); // 시작날짜
            cal2.set(d, e, f); // 끝날짜


            int weekIndex;
            Calendar cal1Index = cal;
            Calendar cal2Index = cal2;


            if (CalendarT.get(i).getSunday().equals("T")) {
                weekIndex=1;
                cal1Index.set(a,b,c);
                cal2Index.set(d,e,f);
                for (int j = 0; j < 7; j++) {
                    if (cal1Index.get(java.util.Calendar.DAY_OF_WEEK) == weekIndex) { // 요일 인덱스
                        cal2Index.add(java.util.Calendar.DATE, 1);
                        while (cal2Index.after(cal1Index)) {
                            dates.add(CalendarDay.from(cal1Index.get(java.util.Calendar.YEAR), cal1Index.get(java.util.Calendar.MONTH), cal1Index.get(java.util.Calendar.DATE)));
                            cal1Index.add(java.util.Calendar.DATE, 7);
                        }
                    }
                    cal1Index.add(java.util.Calendar.DATE, 1);//해당날짜이후 인덱스
                }
            }
            if (CalendarT.get(i).getMonday().equals("T")) {
                weekIndex=2;
                cal1Index.set(a,b,c);
                cal2Index.set(d,e,f);
                for (int j = 0; j < 7; j++) {
                    if (cal1Index.get(java.util.Calendar.DAY_OF_WEEK) == weekIndex) { // 요일 인덱스
                        cal2Index.add(java.util.Calendar.DATE, 1);
                        while (cal2Index.after(cal1Index)) {
                            dates.add(CalendarDay.from(cal1Index.get(java.util.Calendar.YEAR), cal1Index.get(java.util.Calendar.MONTH), cal1Index.get(java.util.Calendar.DATE)));
                            cal1Index.add(java.util.Calendar.DATE, 7);
                        }
                    }
                    cal1Index.add(java.util.Calendar.DATE, 1);//해당날짜이후 인덱스
                }
            }
            if (CalendarT.get(i).getTuesday().equals("T")) {
                weekIndex=3;
                cal1Index.set(a,b,c);
                cal2Index.set(d,e,f);
                for (int j = 0; j < 7; j++) {
                    if (cal1Index.get(java.util.Calendar.DAY_OF_WEEK) == weekIndex) { // 요일 인덱스
                        cal2Index.add(java.util.Calendar.DATE, 1);
                        while (cal2Index.after(cal1Index)) {
                            dates.add(CalendarDay.from(cal1Index.get(java.util.Calendar.YEAR), cal1Index.get(java.util.Calendar.MONTH), cal1Index.get(java.util.Calendar.DATE)));
                            cal1Index.add(java.util.Calendar.DATE, 7);
                        }

                    }
                    cal1Index.add(java.util.Calendar.DATE, 1);//해당날짜이후 인덱스
                }
            }
            if (CalendarT.get(i).getWednesday().equals("T")) {
                weekIndex=4;
                cal1Index.set(a,b,c);
                cal2Index.set(d,e,f);
                for (int j = 0; j < 7; j++) {
                    if (cal1Index.get(java.util.Calendar.DAY_OF_WEEK) == weekIndex) { // 요일 인덱스
                        cal2Index.add(java.util.Calendar.DATE, 1);
                        while (cal2.after(cal1Index)) {
                            dates.add(CalendarDay.from(cal1Index.get(java.util.Calendar.YEAR), cal1Index.get(java.util.Calendar.MONTH), cal1Index.get(java.util.Calendar.DATE)));
                            cal1Index.add(java.util.Calendar.DATE, 7);
                        }
                    }
                    cal1Index.add(java.util.Calendar.DATE, 1);//해당날짜이후 인덱스
                }
            }
            if (CalendarT.get(i).getThursday().equals("T")) {
                weekIndex=5;
                cal1Index.set(a,b,c);
                cal2Index.set(d,e,f);
                for (int j = 0; j < 7; j++) {
                    if (cal1Index.get(java.util.Calendar.DAY_OF_WEEK) == weekIndex) { // 요일 인덱스
                        cal2Index.add(java.util.Calendar.DATE, 1);
                        while (cal2Index.after(cal1Index)) {
                            dates.add(CalendarDay.from(cal1Index.get(java.util.Calendar.YEAR), cal1Index.get(java.util.Calendar.MONTH), cal1Index.get(java.util.Calendar.DATE)));
                            cal1Index.add(java.util.Calendar.DATE, 7);
                        }
                    }
                    cal1Index.add(java.util.Calendar.DATE, 1);//해당날짜이후 인덱스
                }
            }
            if (CalendarT.get(i).getFriday().equals("T")) {
                weekIndex=6;
                cal1Index.set(a,b,c);
                cal2Index.set(d,e,f);
                for (int j = 0; j < 7; j++) {
                    if (cal1Index.get(java.util.Calendar.DAY_OF_WEEK) == weekIndex) { // 요일 인덱스
                        cal2Index.add(java.util.Calendar.DATE, 1);
                        while (cal2Index.after(cal1Index)) {
                            dates.add(CalendarDay.from(cal1Index.get(java.util.Calendar.YEAR), cal1Index.get(java.util.Calendar.MONTH), cal1Index.get(java.util.Calendar.DATE)));
                            cal1Index.add(java.util.Calendar.DATE, 7);
                        }
                    }
                    cal1Index.add(java.util.Calendar.DATE, 1);//해당날짜이후 인덱스
                }
            }
            if (CalendarT.get(i).getSaturday().equals("T")) {
                weekIndex=7;
                cal1Index.set(a,b,c);
                cal2Index.set(d,e,f);
                for (int j = 0; j < 7; j++) {
                    if (cal1Index.get(java.util.Calendar.DAY_OF_WEEK) == weekIndex) { // 요일 인덱스
                        cal2Index.add(java.util.Calendar.DATE, 1);
                        while (cal2Index.after(cal1Index)) {
                            dates.add(CalendarDay.from(cal1Index.get(java.util.Calendar.YEAR), cal1Index.get(java.util.Calendar.MONTH), cal1Index.get(java.util.Calendar.DATE)));
                            cal1Index.add(java.util.Calendar.DATE, 7);
                        }
                    }
                    cal1Index.add(java.util.Calendar.DATE, 1);//해당날짜이후 인덱스
                }
            }

        }


        MaterialCalendarView materialCalendarView = view.findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .commit();
        materialCalendarView.addDecorators(new SaturdayDecorator(), new SundayDecorator(), new OneDayDecorator(),
                new EventDecorator(Color.RED, dates));

        textView1.setText(fragString);
        textView2.setText(calendertext);

        return view;
    }

}
