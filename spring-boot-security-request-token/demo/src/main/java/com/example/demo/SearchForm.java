package com.example.demo;

import com.example.demo.check.CheckDate;
import com.example.demo.check.CheckFromToDate;
import lombok.Data;
import java.util.LinkedHashMap;
import java.util.Map;

//生年月日_from,toの日付チェックを独自アノテーションで実施
@Data
@CheckDate(dtYear = "fromBirthYear", dtMonth = "fromBirthMonth"
        , dtDay = "fromBirthDay", message = "{validation.date-invalidate-from}")
@CheckDate(dtYear = "toBirthYear", dtMonth = "toBirthMonth"
        , dtDay = "toBirthDay", message = "{validation.date-invalidate-to}")
@CheckFromToDate(dtYearFrom = "fromBirthYear", dtMonthFrom = "fromBirthMonth"
        , dtDayFrom = "fromBirthDay", dtYearTo = "toBirthYear"
        , dtMonthTo = "toBirthMonth", dtDayTo = "toBirthDay"
        , message = "{validation.date-invalidate-from-to}")
public class SearchForm {

    /** 検索用名前 */
    private String searchName;

    /** 生年月日_年_from */
    private String fromBirthYear;

    /** 生年月日_月_from */
    private String fromBirthMonth;

    /** 生年月日_日_from */
    private String fromBirthDay;

    /** 生年月日_年_to */
    private String toBirthYear;

    /** 生年月日_月_to */
    private String toBirthMonth;

    /** 生年月日_日_to */
    private String toBirthDay;

    /** 検索用性別 */
    private String searchSex;

    /** 一覧画面の現在ページ数 */
    private int currentPageNum;

    /** 生年月日_月のMapオブジェクト */
    public Map<String,String> getMonthItems(){
        Map<String, String> monthMap = new LinkedHashMap<String, String>();
        for(int i = 1; i <= 12; i++){
            monthMap.put(String.valueOf(i), String.valueOf(i));
        }
        return monthMap;
    }

    /** 生年月日_日のMapオブジェクト */
    public Map<String,String> getDayItems(){
        Map<String, String> dayMap = new LinkedHashMap<String, String>();
        for(int i = 1; i <= 31; i++){
            dayMap.put(String.valueOf(i), String.valueOf(i));
        }
        return dayMap;
    }

    /** 性別のMapオブジェクト */
    public Map<String,String> getSexItems(){
        Map<String, String> sexMap = new LinkedHashMap<String, String>();
        sexMap.put("1", "男");
        sexMap.put("2", "女");
        return sexMap;
    }

}
