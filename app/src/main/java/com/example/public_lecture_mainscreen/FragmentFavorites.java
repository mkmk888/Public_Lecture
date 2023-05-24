package com.example.public_lecture_mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class FragmentFavorites extends Fragment {
    MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        RelativeLayout Favorites_R0 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout0);
        RelativeLayout Favorites_R1 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout1);
        RelativeLayout Favorites_R2 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout2);
        RelativeLayout Favorites_R3 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout3);
        RelativeLayout Favorites_R4 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout4);
        RelativeLayout Favorites_R5 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout5);
        RelativeLayout Favorites_R6 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout6);
        RelativeLayout Favorites_R7 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout7);
        RelativeLayout Favorites_R8 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout8);
        RelativeLayout Favorites_R9 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout9);
        RelativeLayout Favorites_R10 = (RelativeLayout) v.findViewById(R.id.Fa_Re_Layout10);

        TextView favorite_Ttext1 = (TextView) v.findViewById(R.id.top_title1);
        TextView favorite_Ttext2 = (TextView) v.findViewById(R.id.top_title2);
        TextView favorite_Ttext3 = (TextView) v.findViewById(R.id.top_title3);
        TextView favorite_Ttext4 = (TextView) v.findViewById(R.id.top_title4);
        TextView favorite_Ttext5 = (TextView) v.findViewById(R.id.top_title5);
        TextView favorite_Ttext6 = (TextView) v.findViewById(R.id.top_title6);
        TextView favorite_Ttext7 = (TextView) v.findViewById(R.id.top_title7);
        TextView favorite_Ttext8 = (TextView) v.findViewById(R.id.top_title8);
        TextView favorite_Ttext9 = (TextView) v.findViewById(R.id.top_title9);
        TextView favorite_Ttext10 = (TextView) v.findViewById(R.id.top_title10);

        //체크박스
        CheckBox checkBox_favorite1 = (CheckBox) v.findViewById(R.id.fav1);
        CheckBox checkBox_favorite2 = (CheckBox) v.findViewById(R.id.fav2);
        CheckBox checkBox_favorite3 = (CheckBox) v.findViewById(R.id.fav3);
        CheckBox checkBox_favorite4 = (CheckBox) v.findViewById(R.id.fav4);
        CheckBox checkBox_favorite5 = (CheckBox) v.findViewById(R.id.fav5);
        CheckBox checkBox_favorite6 = (CheckBox) v.findViewById(R.id.fav6);
        CheckBox checkBox_favorite7 = (CheckBox) v.findViewById(R.id.fav7);
        CheckBox checkBox_favorite8 = (CheckBox) v.findViewById(R.id.fav8);
        CheckBox checkBox_favorite9 = (CheckBox) v.findViewById(R.id.fav9);
        CheckBox checkBox_favorite10 = (CheckBox) v.findViewById(R.id.fav10);

        List<Favorites> favoritesList = ((MainActivity) MainActivity.context).mFavoritesDao.getFavoritesAll();//다른 액티비티에서 받아오는방법
        int favoritesList_size = favoritesList.size();

        if (favoritesList_size == 0) {
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R1.setVisibility(View.GONE);
            Favorites_R2.setVisibility(View.GONE);
            Favorites_R3.setVisibility(View.GONE);
            Favorites_R4.setVisibility(View.GONE);
            Favorites_R5.setVisibility(View.GONE);
            Favorites_R6.setVisibility(View.GONE);
            Favorites_R7.setVisibility(View.GONE);
            Favorites_R8.setVisibility(View.GONE);
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 1) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            Favorites_R2.setVisibility(View.GONE);
            Favorites_R3.setVisibility(View.GONE);
            Favorites_R4.setVisibility(View.GONE);
            Favorites_R5.setVisibility(View.GONE);
            Favorites_R6.setVisibility(View.GONE);
            Favorites_R7.setVisibility(View.GONE);
            Favorites_R8.setVisibility(View.GONE);
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 2) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            Favorites_R3.setVisibility(View.GONE);
            Favorites_R4.setVisibility(View.GONE);
            Favorites_R5.setVisibility(View.GONE);
            Favorites_R6.setVisibility(View.GONE);
            Favorites_R7.setVisibility(View.GONE);
            Favorites_R8.setVisibility(View.GONE);
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);

        } else if (favoritesList_size == 3) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            Favorites_R3.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            favorite_Ttext3.setText(favoritesList.get(2).getLctreNm());
            Favorites_R4.setVisibility(View.GONE);
            Favorites_R5.setVisibility(View.GONE);
            Favorites_R6.setVisibility(View.GONE);
            Favorites_R7.setVisibility(View.GONE);
            Favorites_R8.setVisibility(View.GONE);
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 4) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            Favorites_R3.setVisibility(View.VISIBLE);
            Favorites_R4.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            favorite_Ttext3.setText(favoritesList.get(2).getLctreNm());
            favorite_Ttext4.setText(favoritesList.get(3).getLctreNm());
            Favorites_R5.setVisibility(View.GONE);
            Favorites_R6.setVisibility(View.GONE);
            Favorites_R7.setVisibility(View.GONE);
            Favorites_R8.setVisibility(View.GONE);
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 5) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            Favorites_R3.setVisibility(View.VISIBLE);
            Favorites_R4.setVisibility(View.VISIBLE);
            Favorites_R5.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            favorite_Ttext3.setText(favoritesList.get(2).getLctreNm());
            favorite_Ttext4.setText(favoritesList.get(3).getLctreNm());
            favorite_Ttext5.setText(favoritesList.get(4).getLctreNm());
            Favorites_R6.setVisibility(View.GONE);
            Favorites_R7.setVisibility(View.GONE);
            Favorites_R8.setVisibility(View.GONE);
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 6) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            Favorites_R3.setVisibility(View.VISIBLE);
            Favorites_R4.setVisibility(View.VISIBLE);
            Favorites_R5.setVisibility(View.VISIBLE);
            Favorites_R6.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            favorite_Ttext3.setText(favoritesList.get(2).getLctreNm());
            favorite_Ttext4.setText(favoritesList.get(3).getLctreNm());
            favorite_Ttext5.setText(favoritesList.get(4).getLctreNm());
            favorite_Ttext6.setText(favoritesList.get(5).getLctreNm());
            Favorites_R7.setVisibility(View.GONE);
            Favorites_R8.setVisibility(View.GONE);
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 7) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            Favorites_R3.setVisibility(View.VISIBLE);
            Favorites_R4.setVisibility(View.VISIBLE);
            Favorites_R5.setVisibility(View.VISIBLE);
            Favorites_R6.setVisibility(View.VISIBLE);
            Favorites_R7.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            favorite_Ttext3.setText(favoritesList.get(2).getLctreNm());
            favorite_Ttext4.setText(favoritesList.get(3).getLctreNm());
            favorite_Ttext5.setText(favoritesList.get(4).getLctreNm());
            favorite_Ttext6.setText(favoritesList.get(5).getLctreNm());
            favorite_Ttext7.setText(favoritesList.get(6).getLctreNm());
            Favorites_R8.setVisibility(View.GONE);
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 8) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            Favorites_R3.setVisibility(View.VISIBLE);
            Favorites_R4.setVisibility(View.VISIBLE);
            Favorites_R5.setVisibility(View.VISIBLE);
            Favorites_R6.setVisibility(View.VISIBLE);
            Favorites_R7.setVisibility(View.VISIBLE);
            Favorites_R8.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            favorite_Ttext3.setText(favoritesList.get(2).getLctreNm());
            favorite_Ttext4.setText(favoritesList.get(3).getLctreNm());
            favorite_Ttext5.setText(favoritesList.get(4).getLctreNm());
            favorite_Ttext6.setText(favoritesList.get(5).getLctreNm());
            favorite_Ttext7.setText(favoritesList.get(6).getLctreNm());
            favorite_Ttext8.setText(favoritesList.get(7).getLctreNm());
            Favorites_R9.setVisibility(View.GONE);
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 9) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            Favorites_R3.setVisibility(View.VISIBLE);
            Favorites_R4.setVisibility(View.VISIBLE);
            Favorites_R5.setVisibility(View.VISIBLE);
            Favorites_R6.setVisibility(View.VISIBLE);
            Favorites_R7.setVisibility(View.VISIBLE);
            Favorites_R8.setVisibility(View.VISIBLE);
            Favorites_R9.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            favorite_Ttext3.setText(favoritesList.get(2).getLctreNm());
            favorite_Ttext4.setText(favoritesList.get(3).getLctreNm());
            favorite_Ttext5.setText(favoritesList.get(4).getLctreNm());
            favorite_Ttext6.setText(favoritesList.get(5).getLctreNm());
            favorite_Ttext7.setText(favoritesList.get(6).getLctreNm());
            favorite_Ttext8.setText(favoritesList.get(7).getLctreNm());
            favorite_Ttext9.setText(favoritesList.get(8).getLctreNm());
            Favorites_R10.setVisibility(View.GONE);
        } else if (favoritesList_size == 10) {
            Favorites_R0.setVisibility(View.GONE);
            Favorites_R1.setVisibility(View.VISIBLE);
            Favorites_R2.setVisibility(View.VISIBLE);
            Favorites_R3.setVisibility(View.VISIBLE);
            Favorites_R4.setVisibility(View.VISIBLE);
            Favorites_R5.setVisibility(View.VISIBLE);
            Favorites_R6.setVisibility(View.VISIBLE);
            Favorites_R7.setVisibility(View.VISIBLE);
            Favorites_R8.setVisibility(View.VISIBLE);
            Favorites_R9.setVisibility(View.VISIBLE);
            Favorites_R10.setVisibility(View.VISIBLE);
            favorite_Ttext1.setText(favoritesList.get(0).getLctreNm());
            favorite_Ttext2.setText(favoritesList.get(1).getLctreNm());
            favorite_Ttext3.setText(favoritesList.get(2).getLctreNm());
            favorite_Ttext4.setText(favoritesList.get(3).getLctreNm());
            favorite_Ttext5.setText(favoritesList.get(4).getLctreNm());
            favorite_Ttext6.setText(favoritesList.get(5).getLctreNm());
            favorite_Ttext7.setText(favoritesList.get(6).getLctreNm());
            favorite_Ttext8.setText(favoritesList.get(7).getLctreNm());
            favorite_Ttext9.setText(favoritesList.get(8).getLctreNm());
            favorite_Ttext10.setText(favoritesList.get(9).getLctreNm());
        }
        //켈린더 체크
        try {
            if (favoritesList.get(0).getCalender().equals("T")) {
                checkBox_favorite1.setChecked(true);
            } else {
                checkBox_favorite1.setChecked(false);
            }
            if (favoritesList.get(1).getCalender().equals("T")) {
                checkBox_favorite2.setChecked(true);
            } else {
                checkBox_favorite2.setChecked(false);
            }
            if (favoritesList.get(2).getCalender().equals("T")) {
                checkBox_favorite3.setChecked(true);
            } else {
                checkBox_favorite3.setChecked(false);
            }
            if (favoritesList.get(3).getCalender().equals("T")) {
                checkBox_favorite4.setChecked(true);
            } else {
                checkBox_favorite4.setChecked(false);
            }
            if (favoritesList.get(4).getCalender().equals("T")) {
                checkBox_favorite5.setChecked(true);
            } else {
                checkBox_favorite5.setChecked(false);
            }
            if (favoritesList.get(5).getCalender().equals("T")) {
                checkBox_favorite6.setChecked(true);
            } else {
                checkBox_favorite6.setChecked(false);
            }
            if (favoritesList.get(6).getCalender().equals("T")) {
                checkBox_favorite7.setChecked(true);
            } else {
                checkBox_favorite7.setChecked(false);
            }
            if (favoritesList.get(7).getCalender().equals("T")) {
                checkBox_favorite8.setChecked(true);
            } else {
                checkBox_favorite8.setChecked(false);
            }
            if (favoritesList.get(8).getCalender().equals("T")) {
                checkBox_favorite9.setChecked(true);
            } else {
                checkBox_favorite9.setChecked(false);
            }
            if (favoritesList.get(9).getCalender().equals("T")) {
                checkBox_favorite10.setChecked(true);
            } else {
                checkBox_favorite10.setChecked(false);
            }
        } catch (Exception e) {
            Log.d("Test", "켈린더 체크부분 오류");
        }

        try{//버튼 색 유지
            if(favoritesList.get(0).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn1);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(1).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn2);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(2).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn3);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(3).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn4);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(4).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn5);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(5).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn6);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(6).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn7);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(7).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn8);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(8).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn9);
                button.setBackgroundResource(R.drawable.green_button);
            }
            if(favoritesList.get(9).getAlarm().equals("T")){
                Button button = v.findViewById(R.id.AlarmCheck_btn10);
                button.setBackgroundResource(R.drawable.green_button);
            }
        }catch(Exception e){
            Log.d("Test", "알람 버튼 색 유지 오류");
        }
            return v;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mainActivity = null;
    }
}
