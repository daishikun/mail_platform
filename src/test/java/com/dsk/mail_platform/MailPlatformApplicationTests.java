package com.dsk.mail_platform;


import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest
class MailPlatformApplicationTests {
    public static void main(String[] args) {
        Calendar rightNow    =    Calendar.getInstance();
        /*用Calendar的get(int field)方法返回给定日历字段的值。
        HOUR 用于 12 小时制时钟 (0 - 11)，HOUR_OF_DAY 用于 24 小时制时钟。*/
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH)+1; //第一个月从0开始，所以得到月份＋1
        Integer day = rightNow.get(rightNow.DAY_OF_MONTH);
        Integer hour12 = rightNow.get(rightNow.HOUR);
        Integer hour24 = rightNow.get(rightNow.HOUR_OF_DAY);
        Integer minute = rightNow.get(rightNow.MINUTE);
        Integer second = rightNow.get(rightNow.SECOND);
        Integer millisecond = rightNow.get(rightNow.MILLISECOND);
   System.out.println(month);
   System.out.println(day);
    }

}


