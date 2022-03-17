package com.sbs.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	private int id;
	private String title;
	private String body;
	private String regDate;
	private String updateDate;
	private int memberId;
	private int boardId;
	private int hitCount;

	private String extra__writerName;
	private int extra__LikePoint;

	public String getForPrintType1RegDate() {
		return regDate.substring(0, 10).replace(" ", "<br>");
	}

	public String getForPrintType1UpdateDate() {
		return updateDate.substring(0, 10).replace(" ", "<br>");
	}

	public String getForPrintType2RegDate() {
		return regDate.substring(2, 16);
	}

	public String getForPrintType2UpdateDate() {
		return updateDate.substring(2, 16);
	}
}
