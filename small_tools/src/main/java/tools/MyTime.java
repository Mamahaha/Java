package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class MyTime {
    
    public static long generateUtcDate(String formattedDate, String format) throws ParseException {
        //used to transfer a formatted time string to UTC
        //verify using python code : 
        //  time.strftime('%Y %m %d %H %M %S', time.gmtime(1429584323))

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.parse(formattedDate).getTime()/1000;
    }
}
