package com.sbs.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private int id;
    private String body;
    private String regDate;
    private String updateDate;
    private int memberId;
    private int relId;

    private String extra__writerName;

    public String getForPrintType1RegDate() {
        return regDate.substring(2, 16);
    }
}
