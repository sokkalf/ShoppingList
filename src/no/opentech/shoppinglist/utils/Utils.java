package no.opentech.shoppinglist.utils;

import java.util.Date;

/**
 * User: sokkalf
 * Date: 15.12.11
 * Time: 21:52
 */
public class Utils {
    public static long getTimeStamp(Date date) {
        if(null != date) return date.getTime();
        else return new Date().getTime();
    }
}
