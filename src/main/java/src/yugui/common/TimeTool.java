package src.yugui.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    //今日的结束时间TZ格式
    public static String getTodayEndTimeZT(){
        Timestamp ts = new Timestamp(System.currentTimeMillis() );
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'24:00:00.000'Z'");
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

    //过去一个月时间
    public static String getOutMoonTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }
}
