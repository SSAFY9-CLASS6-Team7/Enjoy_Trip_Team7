package com.ssafy.plan.model;

import lombok.Data;

@Data
public class Plan {
	private int planId;
	private String title;
	private String createTime;
	private String content;
	private String userId;
}
