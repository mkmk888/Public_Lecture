package com.example.public_lecture_mainscreen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LoadingActivity extends Activity {
    Thread thread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        thread = new Thread(){
            public void run(){
                ((MainActivity) MainActivity.context).mCourseDao.AllDelete_Couresdb();

                for (int i = 0; i < 20; i++) {// 한번에 1600개
                    try {
                        Document doc = Jsoup.connect("http://api.data.go.kr/openapi/tn_pubr_public_lftm_lrn_lctre_api?serviceKey=Lf0RXpn7am7%2BfOAF61EGfis7i5wsOACE1Zj2AC1XOLpam9NaBgXmwXJynXxD%2B19I9bkhGpugYBs%2FQxQ7kTTiwA%3D%3D&pageNo=" + String.valueOf(i + 1) + "&numOfRows=1600&type=xml").get();
                        Elements contents = doc.select("item"); // tag

                        for (Element content : contents) {
                            Course course = new Course();
                            Elements subnode = content.select("lctreNm"); //강좌이름
                            course.setLctreNm(subnode.text());
                            subnode = content.select("instrctrNm");//강사명
                            course.setInstrctrNm(subnode.text());
                            subnode = content.select("edcStartDay");//강의 시작 날짜
                            course.setEdcStartDay(subnode.text());
                            subnode = content.select("edcEndDay");//강의 종료 날짜
                            course.setEdcEndDay(subnode.text());
                            subnode = content.select("edcStartTime");//강의 시작 시간
                            course.setEdcStartTime(subnode.text());
                            subnode = content.select("edcColseTime");//강의 종료 시간
                            course.setEdcColseTime(subnode.text());
                            subnode = content.select("lctreCo");//강좌내용
                            course.setLctreCo(subnode.text());
                            subnode = content.select("edcTrgetType");//강의 대상 구분
                            course.setEdcTrgetType(subnode.text());
                            subnode = content.select("edcMthType");//강의 방법 구분
                            course.setEdcMthType(subnode.text());
                            subnode = content.select("operDay");//운영요일
                            course.setOperDay(subnode.text());
                            subnode = content.select("edcPlace");//장소
                            course.setEdcPlace(subnode.text());
                            subnode = content.select("psncpa");//정원수
                            course.setPsncpa(subnode.text());
                            subnode = content.select("lctreCost");//수강료
                            course.setLctreCost(subnode.text());
                            subnode = content.select("edcRdnmadr");//교육장도로명주소
                            course.setEdcRdnmadr(subnode.text());
                            subnode = content.select("operInstitutionNm");//운영기관명
                            course.setOperInstitutionNm(subnode.text());
                            subnode = content.select("operPhoneNumber");//운영기관전화번호
                            course.setOperPhoneNumber(subnode.text());
                            subnode = content.select("rceptStartDate");//접수시작일자
                            course.setRceptStartDate(subnode.text());
                            subnode = content.select("rceptEndDate");//접수종료일자
                            course.setRceptEndDate(subnode.text());
                            subnode = content.select("rceptMthType");//접수방법구분
                            course.setRceptMthType(subnode.text());
                            subnode = content.select("slctnMthType");//신청방법구분
                            course.setSlctnMthType(subnode.text());
                            subnode = content.select("homepageUrl");//홈페이지주소
                            course.setHompageUrl(subnode.text());
                            subnode = content.select("referenceDate");//데이터기준일
                            course.setReferenceDate(subnode.text());
                            ((MainActivity) MainActivity.context).mCourseDao.setInsertCourse(course);
                        }
                    } catch (Exception e) {
                        Log.d("Test", "error!!!!!!!!!!!!!!!!!");
                    }
                }
                finish();//팝업창 종료
            }
        };
        thread.start();//스레드 시작
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
}