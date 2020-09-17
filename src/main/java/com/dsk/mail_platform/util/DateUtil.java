package com.dsk.mail_platform.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String nowTime = df.format(new Date());
        return nowTime;
    }
}
