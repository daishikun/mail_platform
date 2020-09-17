package com.dsk.mail_platform.query;

import lombok.Data;

import java.util.List;

@Data
public class CommonResult {

    private String reason;
    //private ResultData result;
    private List<DataContent> result;
    private Integer error_code;
}
