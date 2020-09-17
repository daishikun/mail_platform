package com.dsk.mail_platform.job;

import com.alibaba.fastjson.JSONObject;
import com.dsk.mail_platform.query.CommonResult;
import com.dsk.mail_platform.query.HistoryResult;
import com.dsk.mail_platform.util.SendApiUtil;
import com.dsk.mail_platform.util.SendMailUtilA;
import com.dsk.mail_platform.util.SendMailUtilB;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class SendMailJob {
    private static String url = "http://v.juhe.cn/joke/randJoke.php?key=99d89931bc86a968a021262661c4ddc9";
    private static String historyTodayUrl = "http://api.juheapi.com/japi/toh?v=1.0&month=9&day=16&key=40dcc1b17933d8a0d16e8ba7851a27c1";
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendMail() throws IOException, GeneralSecurityException, MessagingException {
        JSONObject jsonObject = new JSONObject();

        String result = SendApiUtil.doPost(url,jsonObject.toJSONString());

        JSONObject json = JSONObject.parseObject(result);
        CommonResult commonResult = JSONObject.toJavaObject(json,CommonResult.class);
        System.out.println(result);

        String historyResult = SendApiUtil.getRequest1();
        JSONObject json1 = JSONObject.parseObject(historyResult);
        HistoryResult historyResult1 = JSONObject.toJavaObject(json1,HistoryResult.class);
        System.out.println(historyResult1);

        SendMailUtilA a = new SendMailUtilA();
        a.sendMailText(commonResult,historyResult1);
        SendMailUtilB b = new SendMailUtilB();
        b.sendMailText(commonResult,historyResult1);

    }
}
