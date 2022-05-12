package Utils;

import java.sql.Timestamp;
import java.util.Date;

public class UsefulMethodes {
    public static Date  convertStringToDate(String str){
        str=str.subSequence(0,str.indexOf("+")).toString();
        str=str.replace('T',' ');
        return Date.from(Timestamp.valueOf(str).toInstant());
    }
}
