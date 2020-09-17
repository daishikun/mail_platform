package com.dsk.mail_platform.query;

import lombok.Data;
import java.util.List;

@Data
public class HistoryResult {
    private List<CommonData> result;
    private String reason;
    private String error_code;
}
