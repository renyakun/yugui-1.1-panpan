package src.yugui.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimeTool {

    static public  String getCurrentTime(){
        Timestamp ts = new Timestamp(System.currentTimeMillis() );
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        tsStr = sdf.format(ts);
        return tsStr;
    }

    //今日的开始时间
    public static String getTodayStartTime(){
        Timestamp ts = new Timestamp(System.currentTimeMillis() );
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
        tsStr = sdf.format(ts);
        return tsStr;
    }

    //今日的结束时间
    public static String getTodayEndTime(){
        Timestamp ts = new Timestamp(System.currentTimeMillis() );
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 24:00:00");
        tsStr = sdf.format(ts);
        return tsStr;
    }

    //前一分钟内的时间
    public static String getOneMinuteAgoTime(){
        Timestamp ts = new Timestamp(System.currentTimeMillis() );
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:00:00");
        tsStr = sdf.format(ts);

        return tsStr;
    }

    //当前时间ZT格式
    public static String getNowTime(){
        Timestamp ts = new Timestamp(System.currentTimeMillis() );
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        tsStr = sdf.format(ts);

        return tsStr;
    }
}
