package com.ssafy.enjoytrip.history.model;

import lombok.Data;

@Data
public class History {
	private int historyId;
	private String title;
	private String createTime;
	private String content;
	private String userId;
	private String startDay;
	private String endDay;
}
