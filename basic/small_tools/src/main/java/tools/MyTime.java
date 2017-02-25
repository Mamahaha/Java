package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MyTime {
     
    public static Date long2Date(long time, String format) {
        //translate UTC time to Date with given format
        //SimpleDateFormat shortSdf = new SimpleDateFormat(format);
        //Date date = shortSdf.parse(shortSdf.format(new Date(date)));
        return new Date(time);
    }
    
    public static Date string2Date(String time, String format) throws ParseException {
        SimpleDateFormat shortSdf = new SimpleDateFormat(format);
        Date date = shortSdf.parse(time);
        return date;
    }
    
    
    public static long generateUtcDate(String formattedDate, String format) throws ParseException {
        //translate a formatted time string to UTC
        //verify using python code : 
        //  time.strftime('%Y %m %d %H %M %S', time.gmtime(1429584323))

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.parse(formattedDate).getTime()/1000;
    }
}
