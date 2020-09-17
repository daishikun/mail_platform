package com.dsk.mail_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MailPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailPlatformApplication.class, args);
    }

}
