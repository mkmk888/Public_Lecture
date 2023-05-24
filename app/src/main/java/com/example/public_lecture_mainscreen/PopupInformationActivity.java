package com.example.public_lecture_mainscreen;

import static android.text.TextUtils.isEmpty;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Query;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.internal.util.StringUtils;
import com.naver.maps.map.overlay.Marker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PopupInformationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    public static NaverMap naverMap;
    public double mLat=0;
    public double mLng=0;
    int InformationIdex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.information_list);

        TextView lctreNm,instrNM,startDay,endDay,startTime,endTime,naeyong,target,edcHow,
                weekDay,edcPlace,peopleCount,Fee,RoadAddress,institution,tel,rceptStart,
                rceptEnd,rceptHow,pickHow,dataCreate,web;

        String weblink;

        lctreNm = findViewById(R.id.I_text1);
        instrNM = findViewById(R.id.I_text2);
        startDay = findViewById(R.id.I_text3);
        endDay = findViewById(R.id.I_text4);
        startTime = findViewById(R.id.I_text5);
        endTime = findViewById(R.id.I_text6);
        naeyong = findViewById(R.id.I_text7);
        target = findViewById(R.id.I_text8);
        edcHow = findViewById(R.id.I_text9);
        weekDay = findViewById(R.id.I_text10);
        edcPlace = findViewById(R.id.I_text11);
        peopleCount = findViewById(R.id.I_text12);
        Fee = findViewById(R.id.I_text13);
        RoadAddress = findViewById(R.id.I_text14);
        institution = findViewById(R.id.I_text15);
        tel = findViewById(R.id.I_text16);
        rceptStart = findViewById(R.id.I_text17);
        rceptEnd = findViewById(R.id.I_text18);
        rceptHow = findViewById(R.id.I_text19);
        pickHow = findViewById(R.id.I_text20);
        dataCreate = findViewById(R.id.I_text21);
        web = findViewById(R.id.I_text22);

        InformationIdex = ((MainActivity)MainActivity.context).InformationIdex;
        if(InformationIdex == 1){//메인화면 강좌이름 눌렸을 때
            int nowPage = ((MainActivity)MainActivity.context).nowPage;
            List<Course> searchResult = ((MainActivity)MainActivity.context).searchResult;
            int pageIndex = (nowPage - 1) * 10; //페이지인덱스
            int touchIndex = ((MainActivity)MainActivity.context).touchIndex;//터치인덱스

            lctreNm.setText(searchResult.get(pageIndex+touchIndex).getLctreNm());
            instrNM.setText(searchResult.get(pageIndex+touchIndex).getInstrctrNm());
            startDay.setText(searchResult.get(pageIndex+touchIndex).getEdcStartDay());
            endDay.setText(searchResult.get(pageIndex+touchIndex).getEdcEndDay());
            startTime.setText(searchResult.get(pageIndex+touchIndex).getEdcStartTime());
            endTime.setText(searchResult.get(pageIndex+touchIndex).getEdcColseTime());
            naeyong.setText(searchResult.get(pageIndex+touchIndex).getLctreCo());
            target.setText(searchResult.get(pageIndex+touchIndex).getEdcTrgetType());
            edcHow.setText(searchResult.get(pageIndex+touchIndex).getEdcMthType());
            weekDay.setText(searchResult.get(pageIndex+touchIndex).getOperDay());
            edcPlace.setText(searchResult.get(pageIndex+touchIndex).getEdcPlace());
            peopleCount.setText(searchResult.get(pageIndex+touchIndex).getPsncpa());
            Fee.setText(searchResult.get(pageIndex+touchIndex).getLctreCost());
            RoadAddress.setText(searchResult.get(pageIndex+touchIndex).getEdcRdnmadr());
            institution.setText(searchResult.get(pageIndex+touchIndex).getOperInstitutionNm());
            tel.setText(searchResult.get(pageIndex+touchIndex).getOperPhoneNumber());
            rceptStart.setText(searchResult.get(pageIndex+touchIndex).getRceptStartDate());
            rceptEnd.setText(searchResult.get(pageIndex+touchIndex).getRceptEndDate());
            rceptHow.setText(searchResult.get(pageIndex+touchIndex).getRceptMthType());
            pickHow.setText(searchResult.get(pageIndex+touchIndex).getSlctnMthType());
            dataCreate.setText(searchResult.get(pageIndex+touchIndex).getReferenceDate());
            web.setText(searchResult.get(pageIndex+touchIndex).getHompageUrl());
            weblink = searchResult.get(pageIndex+touchIndex).getHompageUrl();//웹 주소 저장

            final Geocoder g = new Geocoder(this);

            String address = searchResult.get(pageIndex+touchIndex).getEdcRdnmadr();


            try {
                List<Address> mResultLocation = g.getFromLocationName(address,1);
                mLat = mResultLocation.get(0).getLatitude();
                mLng = mResultLocation.get(0).getLongitude();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("GeoCoder", "주소변환 실패" + e);
            }

            mapView = findViewById(R.id.map_fragment);
            mapView.getMapAsync(this);

        }
        else{//즐겨찾기 강좌이름 눌렸을 때
            int favoritesIndex = ((MainActivity)MainActivity.context).favoritesIndex;
            List<Favorites> favoritesList = ((MainActivity)MainActivity.context).mFavoritesDao.getFavoritesAll();

            lctreNm.setText(favoritesList.get(favoritesIndex).getLctreNm());
            instrNM.setText(favoritesList.get(favoritesIndex).getInstrctrNm());
            startDay.setText(favoritesList.get(favoritesIndex).getEdcStartDay());
            endDay.setText(favoritesList.get(favoritesIndex).getEdcEndDay());
            startTime.setText(favoritesList.get(favoritesIndex).getEdcStartTime());
            endTime.setText(favoritesList.get(favoritesIndex).getEdcColseTime());
            naeyong.setText(favoritesList.get(favoritesIndex).getLctreCo());
            target.setText(favoritesList.get(favoritesIndex).getEdcTrgetType());
            edcHow.setText(favoritesList.get(favoritesIndex).getEdcMthType());
            weekDay.setText(favoritesList.get(favoritesIndex).getOperDay());
            edcPlace.setText(favoritesList.get(favoritesIndex).getEdcPlace());
            peopleCount.setText(favoritesList.get(favoritesIndex).getPsncpa());
            Fee.setText(favoritesList.get(favoritesIndex).getLctreCost());
            RoadAddress.setText(favoritesList.get(favoritesIndex).getEdcRdnmadr());
            institution.setText(favoritesList.get(favoritesIndex).getOperInstitutionNm());
            tel.setText(favoritesList.get(favoritesIndex).getOperPhoneNumber());
            rceptStart.setText(favoritesList.get(favoritesIndex).getRceptStartDate());
            rceptEnd.setText(favoritesList.get(favoritesIndex).getRceptEndDate());
            rceptHow.setText(favoritesList.get(favoritesIndex).getRceptMthType());
            pickHow.setText(favoritesList.get(favoritesIndex).getSlctnMthType());
            dataCreate.setText(favoritesList.get(favoritesIndex).getReferenceDate());
            web.setText(favoritesList.get(favoritesIndex).getHompageUrl());

            weblink = favoritesList.get(favoritesIndex).getHompageUrl();//웹 주소 저장
            final Geocoder g = new Geocoder(this);
            String address = favoritesList.get(favoritesIndex).getEdcRdnmadr();

            try {
                List<Address> mResultLocation = g.getFromLocationName(address,1);
                mLat = mResultLocation.get(0).getLatitude();
                mLng = mResultLocation.get(0).getLongitude();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("GeoCoder", "주소변환 실패" + e);
            }

            mapView = findViewById(R.id.map_fragment);
            mapView.getMapAsync(this);

        }
        Button web_but = (Button) findViewById(R.id.web_but);
        web_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    if(isEmpty(weblink)){
                        Toast.makeText(getApplicationContext(), "등록된 웹 주소가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(weblink));
                        startActivity(intent);
                    }
                }
                catch (Exception e) {
                    try{
                        Intent intent;
                        if(weblink.contains("www")){
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + weblink));
                        }
                        else{
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www." + weblink));
                        }
                        startActivity(intent);
                    }
                    catch (Exception x){
                        Toast.makeText(getApplicationContext(), "웹 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        naverMap.setCameraPosition(new CameraPosition(new LatLng(mLat, mLng),16));

        Marker marker = new Marker();
        marker.setPosition(new LatLng(mLat, mLng));
        marker.setMap(naverMap);
    }

    //즐찾추가 버튼 눌리면 해당 강좌 데이터베이스 저장
    public void Information_Add(View v){
        TextView lctreNm,instrNM,startDay,endDay,startTime,endTime,naeyong,target,edcHow,
                weekDay,edcPlace,peopleCount,Fee,RoadAddress,institution,tel,rceptStart,
                rceptEnd,rceptHow,pickHow,dataCreate,web;

        String SlctreNm, SinstrNM, SstartDay, SendDay, Snaeyong, SRoadAddress;
        int nowid;

        lctreNm = findViewById(R.id.I_text1);
        instrNM = findViewById(R.id.I_text2);
        startDay = findViewById(R.id.I_text3);
        endDay = findViewById(R.id.I_text4);
        startTime = findViewById(R.id.I_text5);
        endTime = findViewById(R.id.I_text6);
        naeyong = findViewById(R.id.I_text7);
        target = findViewById(R.id.I_text8);
        edcHow = findViewById(R.id.I_text9);
        weekDay = findViewById(R.id.I_text10);
        edcPlace = findViewById(R.id.I_text11);
        peopleCount = findViewById(R.id.I_text12);
        Fee = findViewById(R.id.I_text13);
        RoadAddress = findViewById(R.id.I_text14);
        institution = findViewById(R.id.I_text15);
        tel = findViewById(R.id.I_text16);
        rceptStart = findViewById(R.id.I_text17);
        rceptEnd = findViewById(R.id.I_text18);
        rceptHow = findViewById(R.id.I_text19);
        pickHow = findViewById(R.id.I_text20);
        dataCreate = findViewById(R.id.I_text21);
        web = findViewById(R.id.I_text22);

        SlctreNm = (String) lctreNm.getText();
        SinstrNM = (String) instrNM.getText();
        SstartDay = (String) startDay.getText();
        SendDay = (String) endDay.getText();
        Snaeyong = (String) naeyong.getText();
        SRoadAddress = (String) RoadAddress.getText();


        if(Checksame(SlctreNm, SinstrNM, SstartDay, SendDay, Snaeyong, SRoadAddress) == 1){
            Favorites favorites = new Favorites();
            favorites.setLctreNm((String) lctreNm.getText());
            favorites.setInstrctrNm((String) instrNM.getText());
            favorites.setEdcStartDay((String) startDay.getText());
            favorites.setEdcEndDay((String) endDay.getText());
            favorites.setEdcStartTime((String) startTime.getText());
            favorites.setEdcColseTime((String) endTime.getText());
            favorites.setLctreCo((String) naeyong.getText());
            favorites.setEdcTrgetType((String) target.getText());
            favorites.setEdcMthType((String) edcHow.getText());
            favorites.setOperDay((String) weekDay.getText());
            favorites.setEdcPlace((String) edcPlace.getText());
            favorites.setPsncpa((String) peopleCount.getText());
            favorites.setLctreCost((String) Fee.getText());
            favorites.setEdcRdnmadr((String) RoadAddress.getText());
            favorites.setOperInstitutionNm((String) institution.getText());
            favorites.setOperPhoneNumber((String) tel.getText());
            favorites.setRceptStartDate((String) rceptStart.getText());
            favorites.setRceptEndDate((String) rceptEnd.getText());
            favorites.setRceptMthType((String) rceptHow.getText());
            favorites.setSlctnMthType((String) pickHow.getText());
            favorites.setReferenceDate((String) dataCreate.getText());
            favorites.setHompageUrl((String) web.getText());
            favorites.setCalender("F");
            favorites.setAlarm("F");

            favorites.setMonday("F");
            favorites.setTuesday("F");
            favorites.setWednesday("F");
            favorites.setThursday("F");
            favorites.setFriday("F");
            favorites.setSaturday("F");
            favorites.setSunday("F");
            ((MainActivity) MainActivity.context).mFavoritesDao.setInsertFavorites(favorites);

            List<Favorites> favoritesList = ((MainActivity) MainActivity.context).mFavoritesDao.getFavoritesAll();
            int favoritesListsize = favoritesList.size();
            nowid = favoritesList.get(favoritesListsize - 1).getId();

            ((MainActivity) MainActivity.context).mFavoritesDao.monday_T(nowid);
            ((MainActivity) MainActivity.context).mFavoritesDao.tuesday_T(nowid);
            ((MainActivity) MainActivity.context).mFavoritesDao.wednesday_T(nowid);
            ((MainActivity) MainActivity.context).mFavoritesDao.thursday_T(nowid);
            ((MainActivity) MainActivity.context).mFavoritesDao.friday_T(nowid);
            ((MainActivity) MainActivity.context).mFavoritesDao.saturday_T(nowid);
            ((MainActivity) MainActivity.context).mFavoritesDao.Sunday_T1(nowid);
            ((MainActivity) MainActivity.context).mFavoritesDao.Sunday_T2(nowid);

            List<Favorites> favoritesList2 = ((MainActivity) MainActivity.context).mFavoritesDao.getFavoritesAll();
            int favoritesListsize2 = favoritesList2.size();
            nowid = favoritesList2.get(favoritesListsize2 - 1).getId();

            if( favoritesList2.get(favoritesListsize2-1).getMonday().equals("F") && favoritesList2.get(favoritesListsize2-1).getTuesday().equals("F") &&
                    favoritesList2.get(favoritesListsize2-1).getWednesday().equals("F") && favoritesList2.get(favoritesListsize2-1).getThursday().equals("F") &&
                    favoritesList2.get(favoritesListsize2-1).getFriday().equals("F") && favoritesList2.get(favoritesListsize2-1).getSaturday().equals("F") &&
                    favoritesList2.get(favoritesListsize2-1).getSunday().equals("F")){
                ((MainActivity) MainActivity.context).mFavoritesDao.All_week(nowid);
            }

            Toast.makeText(getApplicationContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(Checksame(SlctreNm, SinstrNM, SstartDay, SendDay, Snaeyong, SRoadAddress) == 2){
            Toast.makeText(getApplicationContext(), "즐겨찾기 강좌수 최대10개를 초과하셨습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "즐겨찾기에 이미 같은 강좌가 있습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    //즐겨찾기 추가 강좌가 중복 확인, 최대 10개 체크
    public int Checksame(String SlctreNm, String SinstrNM, String SstartDay, String SendDay, String Snaeyong, String SRoadAddress){
        List<Favorites> favoritesList = ((MainActivity) MainActivity.context).mFavoritesDao.getFavoritesAll();
        int favoritesListsize = favoritesList.size();

        if(favoritesListsize == 10){//즐찾 강좌가 10개초과일 때
            return 2;
        }

        for (int i = 0; i < favoritesListsize; i++) {//즐찾 목록이 같은 강좌가 있을 때
            if(SlctreNm.equals(favoritesList.get(i).getLctreNm()) && SinstrNM.equals(favoritesList.get(i).getInstrctrNm()) &&
                    SstartDay.equals(favoritesList.get(i).getEdcStartDay()) && SendDay.equals(favoritesList.get(i).getEdcEndDay()) &&
                    Snaeyong.equals(favoritesList.get(i).getLctreCo()) && SRoadAddress.equals(favoritesList.get(i).getEdcRdnmadr())){
                return 0;
            }
        }
        return 1;//즐찾에 강좌 추가
    }

    public void Information_Close(View v){
        finish();
    }
}
