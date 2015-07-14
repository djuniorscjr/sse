package util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Domingos Junior on 25/06/2015.
 */
public class Converter {

    public static DateTime retornaDataPorString(String data){
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTime dateTime = dtf.parseDateTime(data);
        return dateTime;
    }

    public static String retornaStringPorData(DateTime data){
        String dataEmString = data.getDayOfMonth() + "/" + data.getMonthOfYear() + "/" + data.getYear();
        return dataEmString;
    }

}
