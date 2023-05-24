package com.example.public_lecture_mainscreen;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences prefs; //첫 실행 변수 선언
    public static Context context;//다른 액티비티에 접근하는 방법
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentHome FragmentHome;
    private FragmentFavorites FragmentFavorites;
    private FragmentCalender FragmentCalender;
    private FragmentSetting FragmentSetting;
    public CourseDao mCourseDao;//모든 강좌 저장하는 데이터베이스
    public FilterDao mFilterDao;//필터 정보저장 데이터베이스
    public FavoritesDao mFavoritesDao;//즐겨찾기 데이터베이스
    public AlarmDao mAlarmDao;//알람 데이터베이스
    public int nowPage = 0;
    public int touchIndex = 0;
    public int favoritesIndex = 0;
    public int alarmCheckIndex = 0;
    public int InformationIdex = 0;
    public Button nowAlarmViewId;
    private SimpleSQLiteQuery query;
    public List<Course> searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //처음실행시만 실행 정의
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        context = this;//다른 액티비티에 접근하는 방법
        //모든 강좌 저장하는 데이터베이스
        CourseDatabase database = Room.databaseBuilder(getApplicationContext(), CourseDatabase.class, "mainscreen_test")// 데이터베이스 생성
                .fallbackToDestructiveMigration()//데이터베이스 버전 변경 가능
                .allowMainThreadQueries()//Main Tread에서 DB입출력을 가능하게 함
                .build();
        //필터 정보저장 데이터베이스
        FilterDatabase filterDatabase = Room.databaseBuilder(getApplicationContext(), FilterDatabase.class, "FilterDatabase")
                .fallbackToDestructiveMigration()//데이터베이스 버전 변경 가능
                .allowMainThreadQueries()//Main Tread에서 DB입출력을 가능하게 함
                .build();
        //즐겨찾기 데이터베이스
        FavoritesDatabase favoritesDatabase = Room.databaseBuilder(getApplicationContext(), FavoritesDatabase.class, "FavoritesDatabase")
                .fallbackToDestructiveMigration()//데이터베이스 버전 변경 가능
                .allowMainThreadQueries()//Main Tread에서 DB입출력을 가능하게 함
                .build();
        //알람 데이터베이스
        AlarmDatabase alarmDatabase = Room.databaseBuilder(getApplicationContext(), AlarmDatabase.class, "AlarmDatabase")
                .fallbackToDestructiveMigration()//데이터베이스 버전 변경 가능
                .allowMainThreadQueries()//Main Tread에서 DB입출력을 가능하게 함
                .build();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //강좌 데이터베이스 인터페이스 객체 할당
        mCourseDao = database.courseDao();
        //필터 데이터베이스 인터페이스 객체 할당
        mFilterDao = filterDatabase.filterDao();
        checkFirstRun();//처음실행시만 실행
        //즐겨찾기 데이터베이스 인터페이스 객체 할당
        mFavoritesDao = favoritesDatabase.favoritesDao();
        //알람 데이터베이스 인터페이스 객체 할당
        mAlarmDao = alarmDatabase.alarmDao();
        //아래 매뉴바
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.meun_home:
                        setFrag(0);
                        break;
                    case R.id.meun_favorites:
                        setFrag(1);
                        break;
                    case R.id.meun_calender:
                        setFrag(2);
                        break;
                    case R.id.meun_setting:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        FragmentHome = new FragmentHome();
        FragmentFavorites = new FragmentFavorites();
        FragmentCalender = new FragmentCalender();
        FragmentSetting = new FragmentSetting();
        setFrag(0);
    }

    //설정-강좌최신화
    public void Update_DB(View view) {
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivityForResult(intent, 1);
    }

    public void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.fragments_Mainframe, FragmentHome);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.fragments_Mainframe, FragmentFavorites);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.fragments_Mainframe, FragmentCalender);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.fragments_Mainframe, FragmentSetting);
                ft.commit();
                break;
        }
    }

    //제목 검색
    public void LctreNm_search(View view) {
        ScrollView mScrollView;
        EditText edit;
        TextView pageNumView, lctName0;
        edit = (EditText) findViewById(R.id.editTextTextPersonName);
        lctName0 = (TextView) findViewById(R.id.lcturNm0);

        mScrollView = findViewById(R.id.Scroll_View);
        mScrollView.fullScroll(ScrollView.FOCUS_UP);

        nowPage = 1; //페이지
        String str = edit.getText().toString();
        String searchKeyword = "%" + str + "%";
        List<Filter> filterinformation = mFilterDao.getFilterAll();

        // Query string
        String queryString = new String();
        //쿼리에서 값으로 들어갈 리스트
        List<Object> args = new ArrayList();
        //where추가 판별
        boolean where_discriminate = false;
        //and 추가 판별
        boolean and_discriminate = true;
        //쿼리문 시작
        queryString += "SELECT * FROM Course";

        if(!searchKeyword.isEmpty()){
            queryString += " WHERE";
            queryString += " lctreNm LIKE ?";
            args.add(searchKeyword);
            where_discriminate = true;
        }

        //날짜 쿼리 추가
        if(filterinformation.get(0).getCourseStartDay().equals("-")==false
                && filterinformation.get(0).getCourseEndDay().equals("-")==false){

            if (where_discriminate) {
                queryString += " AND edcStartDay BETWEEN";
            } else {
                queryString += " WHERE edcStartDay BETWEEN";
                where_discriminate = true;
            }
            queryString += " ? AND";
            args.add(filterinformation.get(0).getCourseStartDay());
            queryString += " ?";
            args.add(filterinformation.get(0).getCourseEndDay());
        }
        else if(filterinformation.get(0).getCourseStartDay().equals("-")==false){
            if (where_discriminate) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                where_discriminate = true;
            }
            queryString += " edcStartDay > ?";
            args.add(filterinformation.get(0).getCourseStartDay());
        }
        else if(filterinformation.get(0).getCourseEndDay().equals("-")==false){
            if (where_discriminate) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                where_discriminate = true;
            }
            queryString += " edcStartDay < ?";
            args.add(filterinformation.get(0).getCourseEndDay());
        }
        if(filterinformation.get(0).getFree().equals("T") && filterinformation.get(0).getEuro().equals("T")){ }
        else if(filterinformation.get(0).getFree().equals("T")){
            if (where_discriminate) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                where_discriminate = true;
            }
            queryString += " lctreCost = ?";
            args.add("0");
        }
        else if(filterinformation.get(0).getEuro().equals("T")){
            if (where_discriminate) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                where_discriminate = true;
            }
            queryString += " lctreCost > ?";
            args.add("0");
        }
        // 지역 쿼리 시작
        if(filterinformation.get(0).getGangwondo().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;

            queryString += " edcRdnmadr LIKE ?";
            args.add("%강원도%");
        }
        if(filterinformation.get(0).getSeoul().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%서울특별시%");
        }
        if(filterinformation.get(0).getGyeonggido().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%경기도%");
        }
        if(filterinformation.get(0).getSejong().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%세종특별자치시%");
        }
        if(filterinformation.get(0).getGyeongsangnamdo().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%경상남도%");
        }
        if(filterinformation.get(0).getGwangju().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%광주광역시%");
        }
        if(filterinformation.get(0).getGyeongsangbukdo().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%경상북도%");
        }
        if(filterinformation.get(0).getDaegu().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%대구광역시%");
        }
        if(filterinformation.get(0).getJeollanamdo().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%전라남도%");
        }
        if(filterinformation.get(0).getBusan().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%부산광역시%");
        }
        if(filterinformation.get(0).getJeollabukdo().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%전라북도%");
        }
        if(filterinformation.get(0).getUlsan().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%울산광역시%");
        }
        if(filterinformation.get(0).getChungcheongnamdo().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%충청남도%");
        }
        if(filterinformation.get(0).getIncheon().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%인천광역시%");
        }
        if(filterinformation.get(0).getChungcheongbukdo().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%충청북도%");
        }
        if(filterinformation.get(0).getJeju().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%제주특별자치도%");
        }
        if(filterinformation.get(0).getEtc().equals("T")){
            if(and_discriminate){
                if (where_discriminate) {
                    queryString += " AND id IN(SELECT id FROM Course WHERE";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            else{
                if (where_discriminate) {
                    queryString += " OR";
                } else {
                    queryString += " WHERE";
                    where_discriminate = true;
                }
            }
            and_discriminate = false;
            queryString += " edcRdnmadr LIKE ?";
            args.add("%없음%");
        }
        if(!and_discriminate){
            queryString += ")";
        }

// End of query string
        queryString += ";";
//        queryString, args.toArray()
        query = new SimpleSQLiteQuery(queryString, args.toArray());
        searchResult = mCourseDao.getLct(query);

        // 시작 날짜 기준으로 정렬
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            searchResult = searchResult.stream()
                    .sorted(Comparator.comparing(Course::getEdcStartDay).reversed())
                    .collect(Collectors.toList());
        }

        int resultPage = searchResult.size() / 10 + 1;
        int pageIndex = (nowPage - 1) * 10; //인덱스
        pageNumView = (TextView) findViewById(R.id.pageNumber);
        pageNumView.setText(nowPage + " / " + resultPage);
        lctName0.setVisibility(View.GONE);
        try {
            lct_load(resultPage,pageIndex);
        } catch (Exception e) {
            Log.d("Test", "error남!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }

    public void search_next(View view) {
        TextView pageNumView;

        ScrollView mScrollView;
        mScrollView = findViewById(R.id.Scroll_View);
        mScrollView.fullScroll(ScrollView.FOCUS_UP);

        nowPage = nowPage + 1; //페이지
        searchResult = mCourseDao.getLct(query);

        // 시작 날짜 기준으로 정렬
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            searchResult = searchResult.stream()
                    .sorted(Comparator.comparing(Course::getEdcStartDay).reversed())
                    .collect(Collectors.toList());
        }
        int resultPage = searchResult.size() / 10 + 1;
        if (nowPage > resultPage) {//페이지초과시 원상복귀
            nowPage = resultPage;
        }
        int pageIndex = (nowPage - 1) * 10; //인덱스
        pageNumView = (TextView) findViewById(R.id.pageNumber);
        pageNumView.setText(nowPage + " / " + resultPage);
        try {
            lct_load(resultPage,pageIndex);
        } catch (Exception e) {
            Log.d("Test", "error남!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void search_prev(View view) {
        TextView pageNumView;
        ScrollView mScrollView;
        mScrollView = findViewById(R.id.Scroll_View);
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        nowPage = nowPage - 1;
        searchResult = mCourseDao.getLct(query);

        // 시작 날짜 기준으로 정렬
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            searchResult = searchResult.stream()
                    .sorted(Comparator.comparing(Course::getEdcStartDay).reversed())
                    .collect(Collectors.toList());
        }
        int resultPage = searchResult.size() / 10 + 1;
        if (nowPage < 1) {
            nowPage = 1;
        }
        int pageIndex = (nowPage - 1) * 10; //인덱스
        pageNumView = (TextView) findViewById(R.id.pageNumber);
        pageNumView.setText(nowPage + " / " + resultPage);
        try {
            lct_load(resultPage,pageIndex);
        } catch (Exception e) {
            Log.d("Test", "error남!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }
    public void lct_load(int resultPage, int pageIndex){
        TextView lctName1, lctName2, lctName3, lctName4, lctName5, lctName6, lctName7, lctName8, lctName9, lctName10, subName1, subName2, subName3, subName4, subName5, subName6, subName7, subName8, subName9, subName10;
        lctName1 = (TextView) findViewById(R.id.lcturNm1);
        subName1 = (TextView) findViewById(R.id.instNm1);
        lctName2 = (TextView) findViewById(R.id.lcturNm2);
        subName2 = (TextView) findViewById(R.id.instNm2);
        lctName3 = (TextView) findViewById(R.id.lcturNm3);
        subName3 = (TextView) findViewById(R.id.instNm3);
        lctName4 = (TextView) findViewById(R.id.lcturNm4);
        subName4 = (TextView) findViewById(R.id.instNm4);
        lctName5 = (TextView) findViewById(R.id.lcturNm5);
        subName5 = (TextView) findViewById(R.id.instNm5);
        lctName6 = (TextView) findViewById(R.id.lcturNm6);
        subName6 = (TextView) findViewById(R.id.instNm6);
        lctName7 = (TextView) findViewById(R.id.lcturNm7);
        subName7 = (TextView) findViewById(R.id.instNm7);
        lctName8 = (TextView) findViewById(R.id.lcturNm8);
        subName8 = (TextView) findViewById(R.id.instNm8);
        lctName9 = (TextView) findViewById(R.id.lcturNm9);
        subName9 = (TextView) findViewById(R.id.instNm9);
        lctName10 = (TextView) findViewById(R.id.lcturNm10);
        subName10 = (TextView) findViewById(R.id.instNm10);
        if (nowPage < resultPage) {
            // 검색결과가 10개
            lctName1.setVisibility(View.VISIBLE);
            subName1.setVisibility(View.VISIBLE);
            lctName2.setVisibility(View.VISIBLE);
            subName2.setVisibility(View.VISIBLE);
            lctName3.setVisibility(View.VISIBLE);
            subName3.setVisibility(View.VISIBLE);
            lctName4.setVisibility(View.VISIBLE);
            subName4.setVisibility(View.VISIBLE);
            lctName5.setVisibility(View.VISIBLE);
            subName5.setVisibility(View.VISIBLE);
            lctName6.setVisibility(View.VISIBLE);
            subName6.setVisibility(View.VISIBLE);
            lctName7.setVisibility(View.VISIBLE);
            subName7.setVisibility(View.VISIBLE);
            lctName8.setVisibility(View.VISIBLE);
            subName8.setVisibility(View.VISIBLE);
            lctName9.setVisibility(View.VISIBLE);
            subName9.setVisibility(View.VISIBLE);
            lctName10.setVisibility(View.VISIBLE);
            subName10.setVisibility(View.VISIBLE);
            lctName1.setText(searchResult.get(pageIndex).getLctreNm());
            subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
            lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
            subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
            lctName3.setText(searchResult.get(pageIndex + 2).getLctreNm());
            subName3.setText(searchResult.get(pageIndex + 2).getEdcStartDay() + " / " + searchResult.get(pageIndex + 2).getEdcRdnmadr());
            lctName4.setText(searchResult.get(pageIndex + 3).getLctreNm());
            subName4.setText(searchResult.get(pageIndex + 3).getEdcStartDay() + " / " + searchResult.get(pageIndex + 3).getEdcRdnmadr());
            lctName5.setText(searchResult.get(pageIndex + 4).getLctreNm());
            subName5.setText(searchResult.get(pageIndex + 4).getEdcStartDay() + " / " + searchResult.get(pageIndex + 4).getEdcRdnmadr());
            lctName6.setText(searchResult.get(pageIndex + 5).getLctreNm());
            subName6.setText(searchResult.get(pageIndex + 5).getEdcStartDay() + " / " + searchResult.get(pageIndex + 5).getEdcRdnmadr());
            lctName7.setText(searchResult.get(pageIndex + 6).getLctreNm());
            subName7.setText(searchResult.get(pageIndex + 6).getEdcStartDay() + " / " + searchResult.get(pageIndex + 6).getEdcRdnmadr());
            lctName8.setText(searchResult.get(pageIndex + 7).getLctreNm());
            subName8.setText(searchResult.get(pageIndex + 7).getEdcStartDay() + " / " + searchResult.get(pageIndex + 7).getEdcRdnmadr());
            lctName9.setText(searchResult.get(pageIndex + 8).getLctreNm());
            subName9.setText(searchResult.get(pageIndex + 8).getEdcStartDay() + " / " + searchResult.get(pageIndex + 8).getEdcRdnmadr());
            lctName10.setText(searchResult.get(pageIndex + 9).getLctreNm());
            subName10.setText(searchResult.get(pageIndex + 9).getEdcStartDay() + " / " + searchResult.get(pageIndex + 9).getEdcRdnmadr());
        } else if (searchResult.size() == 0) {
            // 검색결과가 없음
            lctName1.setVisibility(View.VISIBLE);
            lctName1.setText("검색결과 없음");
            subName1.setVisibility(View.GONE);
            lctName2.setVisibility(View.GONE);
            subName2.setVisibility(View.GONE);
            lctName3.setVisibility(View.GONE);
            subName3.setVisibility(View.GONE);
            lctName4.setVisibility(View.GONE);
            subName4.setVisibility(View.GONE);
            lctName5.setVisibility(View.GONE);
            subName5.setVisibility(View.GONE);
            lctName6.setVisibility(View.GONE);
            subName6.setVisibility(View.GONE);
            lctName7.setVisibility(View.GONE);
            subName7.setVisibility(View.GONE);
            lctName8.setVisibility(View.GONE);
            subName8.setVisibility(View.GONE);
            lctName9.setVisibility(View.GONE);
            subName9.setVisibility(View.GONE);
            lctName10.setVisibility(View.GONE);
            subName10.setVisibility(View.GONE);
        } else if (nowPage == resultPage) {
            if (searchResult.size() % 10 == 1) {
                // 검색결과가 1개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.GONE);
                subName2.setVisibility(View.GONE);
                lctName3.setVisibility(View.GONE);
                subName3.setVisibility(View.GONE);
                lctName4.setVisibility(View.GONE);
                subName4.setVisibility(View.GONE);
                lctName5.setVisibility(View.GONE);
                subName5.setVisibility(View.GONE);
                lctName6.setVisibility(View.GONE);
                subName6.setVisibility(View.GONE);
                lctName7.setVisibility(View.GONE);
                subName7.setVisibility(View.GONE);
                lctName8.setVisibility(View.GONE);
                subName8.setVisibility(View.GONE);
                lctName9.setVisibility(View.GONE);
                subName9.setVisibility(View.GONE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
            } else if (searchResult.size() % 10 == 2) {
                // 검색결과가 2개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.VISIBLE);
                subName2.setVisibility(View.VISIBLE);
                lctName3.setVisibility(View.GONE);
                subName3.setVisibility(View.GONE);
                lctName4.setVisibility(View.GONE);
                subName4.setVisibility(View.GONE);
                lctName5.setVisibility(View.GONE);
                subName5.setVisibility(View.GONE);
                lctName6.setVisibility(View.GONE);
                subName6.setVisibility(View.GONE);
                lctName7.setVisibility(View.GONE);
                subName7.setVisibility(View.GONE);
                lctName8.setVisibility(View.GONE);
                subName8.setVisibility(View.GONE);
                lctName9.setVisibility(View.GONE);
                subName9.setVisibility(View.GONE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
                lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
                subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
            } else if (searchResult.size() % 10 == 3) {
                // 검색결과가 3개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.VISIBLE);
                subName2.setVisibility(View.VISIBLE);
                lctName3.setVisibility(View.VISIBLE);
                subName3.setVisibility(View.VISIBLE);
                lctName4.setVisibility(View.GONE);
                subName4.setVisibility(View.GONE);
                lctName5.setVisibility(View.GONE);
                subName5.setVisibility(View.GONE);
                lctName6.setVisibility(View.GONE);
                subName6.setVisibility(View.GONE);
                lctName7.setVisibility(View.GONE);
                subName7.setVisibility(View.GONE);
                lctName8.setVisibility(View.GONE);
                subName8.setVisibility(View.GONE);
                lctName9.setVisibility(View.GONE);
                subName9.setVisibility(View.GONE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
                lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
                subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
                lctName3.setText(searchResult.get(pageIndex + 2).getLctreNm());
                subName3.setText(searchResult.get(pageIndex + 2).getEdcStartDay() + " / " + searchResult.get(pageIndex + 2).getEdcRdnmadr());
            } else if (searchResult.size() % 10 == 4) {
                // 검색결과가 4개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.VISIBLE);
                subName2.setVisibility(View.VISIBLE);
                lctName3.setVisibility(View.VISIBLE);
                subName3.setVisibility(View.VISIBLE);
                lctName4.setVisibility(View.VISIBLE);
                subName4.setVisibility(View.VISIBLE);
                lctName5.setVisibility(View.GONE);
                subName5.setVisibility(View.GONE);
                lctName6.setVisibility(View.GONE);
                subName6.setVisibility(View.GONE);
                lctName7.setVisibility(View.GONE);
                subName7.setVisibility(View.GONE);
                lctName8.setVisibility(View.GONE);
                subName8.setVisibility(View.GONE);
                lctName9.setVisibility(View.GONE);
                subName9.setVisibility(View.GONE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
                lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
                subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
                lctName3.setText(searchResult.get(pageIndex + 2).getLctreNm());
                subName3.setText(searchResult.get(pageIndex + 2).getEdcStartDay() + " / " + searchResult.get(pageIndex + 2).getEdcRdnmadr());
                lctName4.setText(searchResult.get(pageIndex + 3).getLctreNm());
                subName4.setText(searchResult.get(pageIndex + 3).getEdcStartDay() + " / " + searchResult.get(pageIndex + 3).getEdcRdnmadr());
            } else if (searchResult.size() % 10 == 5) {
                // 검색결과가 5개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.VISIBLE);
                subName2.setVisibility(View.VISIBLE);
                lctName3.setVisibility(View.VISIBLE);
                subName3.setVisibility(View.VISIBLE);
                lctName4.setVisibility(View.VISIBLE);
                subName4.setVisibility(View.VISIBLE);
                lctName5.setVisibility(View.VISIBLE);
                subName5.setVisibility(View.VISIBLE);
                lctName6.setVisibility(View.GONE);
                subName6.setVisibility(View.GONE);
                lctName7.setVisibility(View.GONE);
                subName7.setVisibility(View.GONE);
                lctName8.setVisibility(View.GONE);
                subName8.setVisibility(View.GONE);
                lctName9.setVisibility(View.GONE);
                subName9.setVisibility(View.GONE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
                lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
                subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
                lctName3.setText(searchResult.get(pageIndex + 2).getLctreNm());
                subName3.setText(searchResult.get(pageIndex + 2).getEdcStartDay() + " / " + searchResult.get(pageIndex + 2).getEdcRdnmadr());
                lctName4.setText(searchResult.get(pageIndex + 3).getLctreNm());
                subName4.setText(searchResult.get(pageIndex + 3).getEdcStartDay() + " / " + searchResult.get(pageIndex + 3).getEdcRdnmadr());
                lctName5.setText(searchResult.get(pageIndex + 4).getLctreNm());
                subName5.setText(searchResult.get(pageIndex + 4).getEdcStartDay() + " / " + searchResult.get(pageIndex + 4).getEdcRdnmadr());
            } else if (searchResult.size() % 10 == 6) {
                // 검색결과가 6개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.VISIBLE);
                subName2.setVisibility(View.VISIBLE);
                lctName3.setVisibility(View.VISIBLE);
                subName3.setVisibility(View.VISIBLE);
                lctName4.setVisibility(View.VISIBLE);
                subName4.setVisibility(View.VISIBLE);
                lctName5.setVisibility(View.VISIBLE);
                subName5.setVisibility(View.VISIBLE);
                lctName6.setVisibility(View.VISIBLE);
                subName6.setVisibility(View.VISIBLE);
                lctName7.setVisibility(View.GONE);
                subName7.setVisibility(View.GONE);
                lctName8.setVisibility(View.GONE);
                subName8.setVisibility(View.GONE);
                lctName9.setVisibility(View.GONE);
                subName9.setVisibility(View.GONE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
                lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
                subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
                lctName3.setText(searchResult.get(pageIndex + 2).getLctreNm());
                subName3.setText(searchResult.get(pageIndex + 2).getEdcStartDay() + " / " + searchResult.get(pageIndex + 2).getEdcRdnmadr());
                lctName4.setText(searchResult.get(pageIndex + 3).getLctreNm());
                subName4.setText(searchResult.get(pageIndex + 3).getEdcStartDay() + " / " + searchResult.get(pageIndex + 3).getEdcRdnmadr());
                lctName5.setText(searchResult.get(pageIndex + 4).getLctreNm());
                subName5.setText(searchResult.get(pageIndex + 4).getEdcStartDay() + " / " + searchResult.get(pageIndex + 4).getEdcRdnmadr());
                lctName6.setText(searchResult.get(pageIndex + 5).getLctreNm());
                subName6.setText(searchResult.get(pageIndex + 5).getEdcStartDay() + " / " + searchResult.get(pageIndex + 5).getEdcRdnmadr());
            } else if (searchResult.size() % 10 == 7) {
                // 검색결과가 7개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.VISIBLE);
                subName2.setVisibility(View.VISIBLE);
                lctName3.setVisibility(View.VISIBLE);
                subName3.setVisibility(View.VISIBLE);
                lctName4.setVisibility(View.VISIBLE);
                subName4.setVisibility(View.VISIBLE);
                lctName5.setVisibility(View.VISIBLE);
                subName5.setVisibility(View.VISIBLE);
                lctName6.setVisibility(View.VISIBLE);
                subName6.setVisibility(View.VISIBLE);
                lctName7.setVisibility(View.VISIBLE);
                subName7.setVisibility(View.VISIBLE);
                lctName8.setVisibility(View.GONE);
                subName8.setVisibility(View.GONE);
                lctName9.setVisibility(View.GONE);
                subName9.setVisibility(View.GONE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
                lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
                subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
                lctName3.setText(searchResult.get(pageIndex + 2).getLctreNm());
                subName3.setText(searchResult.get(pageIndex + 2).getEdcStartDay() + " / " + searchResult.get(pageIndex + 2).getEdcRdnmadr());
                lctName4.setText(searchResult.get(pageIndex + 3).getLctreNm());
                subName4.setText(searchResult.get(pageIndex + 3).getEdcStartDay() + " / " + searchResult.get(pageIndex + 3).getEdcRdnmadr());
                lctName5.setText(searchResult.get(pageIndex + 4).getLctreNm());
                subName5.setText(searchResult.get(pageIndex + 4).getEdcStartDay() + " / " + searchResult.get(pageIndex + 4).getEdcRdnmadr());
                lctName6.setText(searchResult.get(pageIndex + 5).getLctreNm());
                subName6.setText(searchResult.get(pageIndex + 5).getEdcStartDay() + " / " + searchResult.get(pageIndex + 5).getEdcRdnmadr());
                lctName7.setText(searchResult.get(pageIndex + 6).getLctreNm());
                subName7.setText(searchResult.get(pageIndex + 6).getEdcStartDay() + " / " + searchResult.get(pageIndex + 6).getEdcRdnmadr());
            } else if (searchResult.size() % 10 == 8) {
                // 검색결과가 8개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.VISIBLE);
                subName2.setVisibility(View.VISIBLE);
                lctName3.setVisibility(View.VISIBLE);
                subName3.setVisibility(View.VISIBLE);
                lctName4.setVisibility(View.VISIBLE);
                subName4.setVisibility(View.VISIBLE);
                lctName5.setVisibility(View.VISIBLE);
                subName5.setVisibility(View.VISIBLE);
                lctName6.setVisibility(View.VISIBLE);
                subName6.setVisibility(View.VISIBLE);
                lctName7.setVisibility(View.VISIBLE);
                subName7.setVisibility(View.VISIBLE);
                lctName8.setVisibility(View.VISIBLE);
                subName8.setVisibility(View.VISIBLE);
                lctName9.setVisibility(View.GONE);
                subName9.setVisibility(View.GONE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
                lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
                subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
                lctName3.setText(searchResult.get(pageIndex + 2).getLctreNm());
                subName3.setText(searchResult.get(pageIndex + 2).getEdcStartDay() + " / " + searchResult.get(pageIndex + 2).getEdcRdnmadr());
                lctName4.setText(searchResult.get(pageIndex + 3).getLctreNm());
                subName4.setText(searchResult.get(pageIndex + 3).getEdcStartDay() + " / " + searchResult.get(pageIndex + 3).getEdcRdnmadr());
                lctName5.setText(searchResult.get(pageIndex + 4).getLctreNm());
                subName5.setText(searchResult.get(pageIndex + 4).getEdcStartDay() + " / " + searchResult.get(pageIndex + 4).getEdcRdnmadr());
                lctName6.setText(searchResult.get(pageIndex + 5).getLctreNm());
                subName6.setText(searchResult.get(pageIndex + 5).getEdcStartDay() + " / " + searchResult.get(pageIndex + 5).getEdcRdnmadr());
                lctName7.setText(searchResult.get(pageIndex + 6).getLctreNm());
                subName7.setText(searchResult.get(pageIndex + 6).getEdcStartDay() + " / " + searchResult.get(pageIndex + 6).getEdcRdnmadr());
                lctName8.setText(searchResult.get(pageIndex + 7).getLctreNm());
                subName8.setText(searchResult.get(pageIndex + 7).getEdcStartDay() + " / " + searchResult.get(pageIndex + 7).getEdcRdnmadr());
            } else if (searchResult.size() % 10 == 9) {
                // 검색결과가 9개
                lctName1.setVisibility(View.VISIBLE);
                subName1.setVisibility(View.VISIBLE);
                lctName2.setVisibility(View.VISIBLE);
                subName2.setVisibility(View.VISIBLE);
                lctName3.setVisibility(View.VISIBLE);
                subName3.setVisibility(View.VISIBLE);
                lctName4.setVisibility(View.VISIBLE);
                subName4.setVisibility(View.VISIBLE);
                lctName5.setVisibility(View.VISIBLE);
                subName5.setVisibility(View.VISIBLE);
                lctName6.setVisibility(View.VISIBLE);
                subName6.setVisibility(View.VISIBLE);
                lctName7.setVisibility(View.VISIBLE);
                subName7.setVisibility(View.VISIBLE);
                lctName8.setVisibility(View.VISIBLE);
                subName8.setVisibility(View.VISIBLE);
                lctName9.setVisibility(View.VISIBLE);
                subName9.setVisibility(View.VISIBLE);
                lctName10.setVisibility(View.GONE);
                subName10.setVisibility(View.GONE);
                lctName1.setText(searchResult.get(pageIndex).getLctreNm());
                subName1.setText(searchResult.get(pageIndex).getEdcStartDay() + " / " + searchResult.get(pageIndex).getEdcRdnmadr());
                lctName2.setText(searchResult.get(pageIndex + 1).getLctreNm());
                subName2.setText(searchResult.get(pageIndex + 1).getEdcStartDay() + " / " + searchResult.get(pageIndex + 1).getEdcRdnmadr());
                lctName3.setText(searchResult.get(pageIndex + 2).getLctreNm());
                subName3.setText(searchResult.get(pageIndex + 2).getEdcStartDay() + " / " + searchResult.get(pageIndex + 2).getEdcRdnmadr());
                lctName4.setText(searchResult.get(pageIndex + 3).getLctreNm());
                subName4.setText(searchResult.get(pageIndex + 3).getEdcStartDay() + " / " + searchResult.get(pageIndex + 3).getEdcRdnmadr());
                lctName5.setText(searchResult.get(pageIndex + 4).getLctreNm());
                subName5.setText(searchResult.get(pageIndex + 4).getEdcStartDay() + " / " + searchResult.get(pageIndex + 4).getEdcRdnmadr());
                lctName6.setText(searchResult.get(pageIndex + 5).getLctreNm());
                subName6.setText(searchResult.get(pageIndex + 5).getEdcStartDay() + " / " + searchResult.get(pageIndex + 5).getEdcRdnmadr());
                lctName7.setText(searchResult.get(pageIndex + 6).getLctreNm());
                subName7.setText(searchResult.get(pageIndex + 6).getEdcStartDay() + " / " + searchResult.get(pageIndex + 6).getEdcRdnmadr());
                lctName8.setText(searchResult.get(pageIndex + 7).getLctreNm());
                subName8.setText(searchResult.get(pageIndex + 7).getEdcStartDay() + " / " + searchResult.get(pageIndex + 7).getEdcRdnmadr());
                lctName9.setText(searchResult.get(pageIndex + 8).getLctreNm());
                subName9.setText(searchResult.get(pageIndex + 8).getEdcStartDay() + " / " + searchResult.get(pageIndex + 8).getEdcRdnmadr());
            }
        }
    }

    public void lctClick(View view){
        if(nowPage!=0){
            if(view.getId() == R.id.lcturNm1){ touchIndex=0; }
            if(view.getId() == R.id.lcturNm2){ touchIndex=1; }
            if(view.getId() == R.id.lcturNm3){ touchIndex=2; }
            if(view.getId() == R.id.lcturNm4){ touchIndex=3; }
            if(view.getId() == R.id.lcturNm5){ touchIndex=4; }
            if(view.getId() == R.id.lcturNm6){ touchIndex=5; }
            if(view.getId() == R.id.lcturNm7){ touchIndex=6; }
            if(view.getId() == R.id.lcturNm8){ touchIndex=7; }
            if(view.getId() == R.id.lcturNm9){ touchIndex=8; }
            if(view.getId() == R.id.lcturNm10){ touchIndex=9; }
            InformationIdex = 1;
            Intent intent = new Intent(this, PopupInformationActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    //분류검색pop호출
    public void Popup_classification_search_button(View view) {
        Intent intent = new Intent(this, PopupSearchActivity.class);
        startActivityForResult(intent, 1);
    }

    // 알람 호출
    public void Alarm(View view){
        if(view.getId() == R.id.AlarmCheck_btn1){
            alarmCheckIndex=0;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn1);
        }
        else if(view.getId() == R.id.AlarmCheck_btn2){
            alarmCheckIndex=1;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn2);
        }
        else if(view.getId() == R.id.AlarmCheck_btn3){
            alarmCheckIndex=2;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn3);
        }
        else if(view.getId() == R.id.AlarmCheck_btn4){
            alarmCheckIndex=3;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn4);
        }
        else if(view.getId() == R.id.AlarmCheck_btn5){
            alarmCheckIndex=4;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn5);
        }
        else if(view.getId() == R.id.AlarmCheck_btn6){
            alarmCheckIndex=5;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn6);
        }
        else if(view.getId() == R.id.AlarmCheck_btn7){
            alarmCheckIndex=6;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn7);
        }
        else if(view.getId() == R.id.AlarmCheck_btn8){
            alarmCheckIndex=7;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn8);
        }
        else if(view.getId() == R.id.AlarmCheck_btn9){
            alarmCheckIndex=8;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn9);
        }
        else if(view.getId() == R.id.AlarmCheck_btn10){
            alarmCheckIndex=9;
            nowAlarmViewId=findViewById(R.id.AlarmCheck_btn10);
        }
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivityForResult(intent, 1);
    }
    //즐겨찾기 코드 시작
    //즐겨찾기 삭제코드
    public void favorites_delete(View view){
        List<Favorites> favoritesList = mFavoritesDao.getFavoritesAll();
        int size = favoritesList.size();

        if(view.getId() == R.id.Fdelete_btn1){
            alarmCheckIndex=0;
            if(favoritesList.get(0).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(0).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn2){
            alarmCheckIndex=1;
            if(favoritesList.get(1).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(1).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn3){
            alarmCheckIndex=2;
            if(favoritesList.get(2).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(2).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn4){
            alarmCheckIndex=3;
            if(favoritesList.get(3).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(3).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn5){
            alarmCheckIndex=4;
            if(favoritesList.get(4).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(4).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn6){
            alarmCheckIndex=5;
            if(favoritesList.get(5).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(5).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn7){
            alarmCheckIndex=6;
            if(favoritesList.get(6).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(6).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn8){
            alarmCheckIndex=7;
            if(favoritesList.get(7).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(7).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn9){
            alarmCheckIndex=8;
            if(favoritesList.get(8).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(8).getId());
        }
        else if(view.getId() == R.id.Fdelete_btn10){
            alarmCheckIndex=9;
            if(favoritesList.get(9).getAlarm().equals("T")){
                favorites_alarm_delet();
            }
            mFavoritesDao.F_Delete(favoritesList.get(9).getId());
        } else{}
        calender_checkBox(size);
        FRefresh();
    }

    public void calender_checkBox(int size){
        CheckBox checkBox_favorite1 = (CheckBox) findViewById(R.id.fav1);
        CheckBox checkBox_favorite2 = (CheckBox) findViewById(R.id.fav2);
        CheckBox checkBox_favorite3 = (CheckBox) findViewById(R.id.fav3);
        CheckBox checkBox_favorite4 = (CheckBox) findViewById(R.id.fav4);
        CheckBox checkBox_favorite5 = (CheckBox) findViewById(R.id.fav5);
        CheckBox checkBox_favorite6 = (CheckBox) findViewById(R.id.fav6);
        CheckBox checkBox_favorite7 = (CheckBox) findViewById(R.id.fav7);
        CheckBox checkBox_favorite8 = (CheckBox) findViewById(R.id.fav8);
        CheckBox checkBox_favorite9 = (CheckBox) findViewById(R.id.fav9);
        CheckBox checkBox_favorite10 = (CheckBox) findViewById(R.id.fav10);

        if(size == 1){ checkBox_favorite1.setChecked(false); }
        else if(size == 2){ checkBox_favorite2.setChecked(false); }
        else if(size == 3){ checkBox_favorite3.setChecked(false); }
        else if(size == 4){ checkBox_favorite4.setChecked(false); }
        else if(size == 5){ checkBox_favorite5.setChecked(false); }
        else if(size == 6){ checkBox_favorite6.setChecked(false); }
        else if(size == 7){ checkBox_favorite7.setChecked(false); }
        else if(size == 8){ checkBox_favorite8.setChecked(false); }
        else if(size == 9){ checkBox_favorite9.setChecked(false); }
        else if(size == 10){ checkBox_favorite10.setChecked(false); }

    }

    public void favorites_alarm_delet() {
        //알림데이터베이스 삭제
        List<Alarm> alarmdatabase =mAlarmDao.getAlarmAll();
        int alarmIndex = alarmIndex();
        int alarmID = alarmdatabase.get(alarmIndex).getId();
        mAlarmDao.Alarm_Delete(alarmID);

        AlarmManager alarmManager=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(alarmIntent);
        Toast.makeText(this,"선택하신 즐겨찾기가 삭제되었습니다.",Toast.LENGTH_LONG).show();
    }

    public int alarmIndex(){
        List<Favorites> filterinformation = mFavoritesDao.getFavoritesAll();
        List<Alarm> alarmdatabase = mAlarmDao.getAlarmAll();
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

    //삭제 후 화면 새로고침
    public void FRefresh(){
        CheckBox checkBox_favorite1 = (CheckBox) findViewById(R.id.fav1);
        CheckBox checkBox_favorite2 = (CheckBox) findViewById(R.id.fav2);
        CheckBox checkBox_favorite3 = (CheckBox) findViewById(R.id.fav3);
        CheckBox checkBox_favorite4 = (CheckBox) findViewById(R.id.fav4);
        CheckBox checkBox_favorite5 = (CheckBox) findViewById(R.id.fav5);
        CheckBox checkBox_favorite6 = (CheckBox) findViewById(R.id.fav6);
        CheckBox checkBox_favorite7 = (CheckBox) findViewById(R.id.fav7);
        CheckBox checkBox_favorite8 = (CheckBox) findViewById(R.id.fav8);
        CheckBox checkBox_favorite9 = (CheckBox) findViewById(R.id.fav9);
        CheckBox checkBox_favorite10 = (CheckBox) findViewById(R.id.fav10);

        RelativeLayout Favorites_R0 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout0);
        RelativeLayout Favorites_R1 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout1);
        RelativeLayout Favorites_R2 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout2);
        RelativeLayout Favorites_R3 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout3);
        RelativeLayout Favorites_R4 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout4);
        RelativeLayout Favorites_R5 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout5);
        RelativeLayout Favorites_R6 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout6);
        RelativeLayout Favorites_R7 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout7);
        RelativeLayout Favorites_R8 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout8);
        RelativeLayout Favorites_R9 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout9);
        RelativeLayout Favorites_R10 = (RelativeLayout)findViewById(R.id.Fa_Re_Layout10);

        TextView favorite_Ttext1 = (TextView) findViewById(R.id.top_title1);
        TextView favorite_Ttext2 = (TextView) findViewById(R.id.top_title2);
        TextView favorite_Ttext3 = (TextView) findViewById(R.id.top_title3);
        TextView favorite_Ttext4 = (TextView) findViewById(R.id.top_title4);
        TextView favorite_Ttext5 = (TextView) findViewById(R.id.top_title5);
        TextView favorite_Ttext6 = (TextView) findViewById(R.id.top_title6);
        TextView favorite_Ttext7 = (TextView) findViewById(R.id.top_title7);
        TextView favorite_Ttext8 = (TextView) findViewById(R.id.top_title8);
        TextView favorite_Ttext9 = (TextView) findViewById(R.id.top_title9);
        TextView favorite_Ttext10 = (TextView) findViewById(R.id.top_title10);

        List<Favorites> favoritesList = mFavoritesDao.getFavoritesAll();
        int favoritesList_size = favoritesList.size();

        if(favoritesList_size == 0){
            Favorites_R0.setVisibility(View.VISIBLE);
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
        }
        else if(favoritesList_size == 1){
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
        }
        else if(favoritesList_size == 2){
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
        }
        else if(favoritesList_size == 3){
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
        }
        else if(favoritesList_size == 4){
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
        }
        else if(favoritesList_size == 5){
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
        }
        else if(favoritesList_size == 6){
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
        }
        else if(favoritesList_size == 7){
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
        }
        else if(favoritesList_size == 8){
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
        }
        else if(favoritesList_size == 9){
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
        }
        else if(favoritesList_size == 10){
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
        else{}
        //켈린더 체크
        try{
            if(favoritesList.get(0).getCalender().equals("T")){
                checkBox_favorite1.setChecked(true);
            } else{checkBox_favorite1.setChecked(false);}
            if(favoritesList.get(1).getCalender().equals("T")){
                checkBox_favorite2.setChecked(true);
            } else{checkBox_favorite2.setChecked(false);}
            if(favoritesList.get(2).getCalender().equals("T")){
                checkBox_favorite3.setChecked(true);
            } else{checkBox_favorite3.setChecked(false);}
            if(favoritesList.get(3).getCalender().equals("T")){
                checkBox_favorite4.setChecked(true);
            } else{checkBox_favorite4.setChecked(false);}
            if(favoritesList.get(4).getCalender().equals("T")){
                checkBox_favorite5.setChecked(true);
            } else{checkBox_favorite5.setChecked(false);}
            if(favoritesList.get(5).getCalender().equals("T")){
                checkBox_favorite6.setChecked(true);
            } else{checkBox_favorite6.setChecked(false);}
            if(favoritesList.get(6).getCalender().equals("T")){
                checkBox_favorite7.setChecked(true);
            } else{checkBox_favorite7.setChecked(false);}
            if(favoritesList.get(7).getCalender().equals("T")){
                checkBox_favorite8.setChecked(true);
            } else{checkBox_favorite8.setChecked(false);}
            if(favoritesList.get(8).getCalender().equals("T")){
                checkBox_favorite9.setChecked(true);
            } else{checkBox_favorite9.setChecked(false);}
            if(favoritesList.get(9).getCalender().equals("T")){
                checkBox_favorite10.setChecked(true);
            } else{checkBox_favorite10.setChecked(false);}
        }catch(Exception e){
            Log.d("Test", "켈린더 체크 새로고침 채크");
        }
    }
    //즐찾 제목 텍스트뷰 눌리면 상세정보창으로 이동
    public void favorites_text_click(View view){
        if(view.getId() == R.id.top_title1){ favoritesIndex = 0; }
        else if(view.getId() == R.id.top_title2){ favoritesIndex = 1; }
        else if(view.getId() == R.id.top_title3){ favoritesIndex = 2; }
        else if(view.getId() == R.id.top_title4){ favoritesIndex = 3; }
        else if(view.getId() == R.id.top_title5){ favoritesIndex = 4; }
        else if(view.getId() == R.id.top_title6){ favoritesIndex = 5; }
        else if(view.getId() == R.id.top_title7){ favoritesIndex = 6; }
        else if(view.getId() == R.id.top_title8){ favoritesIndex = 7; }
        else if(view.getId() == R.id.top_title9){ favoritesIndex = 8; }
        else if(view.getId() == R.id.top_title10){ favoritesIndex = 9; }
        InformationIdex = 2;
        Intent intent = new Intent(this, PopupInformationActivity.class);
        startActivityForResult(intent, 1);
    }

    public void favoriteCalCheck(View v){ //favorite - Calendar
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

        if(v.getId() == R.id.fav1){
            if(checkBox_favorite1.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(0).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(0).getId()); }
        }
        else if(v.getId() == R.id.fav2){
            if(checkBox_favorite2.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(1).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(1).getId()); }
        }
        else if(v.getId() == R.id.fav3){
            if(checkBox_favorite3.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(2).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(2).getId()); }
        }
        else if(v.getId() == R.id.fav4){
            if(checkBox_favorite4.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(3).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(3).getId()); }
        }
        else if(v.getId() == R.id.fav5){
            if(checkBox_favorite5.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(4).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(4).getId()); }
        }
        else if(v.getId() == R.id.fav6){
            if(checkBox_favorite6.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(5).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(5).getId()); }
        }
        else if(v.getId() == R.id.fav7){
            if(checkBox_favorite7.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(6).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(6).getId()); }
        }
        else if(v.getId() == R.id.fav8){
            if(checkBox_favorite8.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(7).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(7).getId()); }
        }
        else if(v.getId() == R.id.fav9){
            if(checkBox_favorite9.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(8).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(8).getId()); }
        }
        else if(v.getId() == R.id.fav10){
            if(checkBox_favorite10.isChecked()){
                ((MainActivity) MainActivity.context).mFavoritesDao.set_T(favoritesList.get(9).getId());
            }else { ((MainActivity) MainActivity.context).mFavoritesDao.set_F(favoritesList.get(9).getId()); }
        }
    }
    //도움말 링크
    public void helpweb(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://granite-sprite-166.notion.site/f99bdff8015e491791fbb34cf5c56c35"));
        startActivity(intent);
    }

    //처음실행시에만 실행
    public void checkFirstRun() {
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            //필터검색 처음 초기화
            mFilterDao.AllDelete_Filterdb();
            Filter filter = new Filter();
            filter.setIdd("0");
            filter.setGangwondo("F");
            filter.setSeoul("F");
            filter.setGyeonggido("F");
            filter.setSejong("F");
            filter.setGyeongsangnamdo("F");
            filter.setGwangju("F");
            filter.setGyeongsangbukdo("F");
            filter.setDaegu("F");
            filter.setJeollanamdo("F");
            filter.setBusan("F");
            filter.setJeollabukdo("F");
            filter.setUlsan("F");
            filter.setChungcheongnamdo("F");
            filter.setIncheon("F");
            filter.setChungcheongbukdo("F");
            filter.setJeju("F");
            filter.setEtc("F");

            filter.setCourseStartDay("-");
            filter.setCourseEndDay("-");

            filter.setEuro("F");
            filter.setFree("F");
            mFilterDao.setInsertFilter(filter);

            Intent intent = new Intent(this, LoadingActivity.class);
            startActivityForResult(intent, 1);

            //처음실행시만 실행
            prefs.edit().putBoolean("isFirstRun", false).apply();
            //처음만 true 그다음부터는 false 바꾸는 동작
        }
    }
}