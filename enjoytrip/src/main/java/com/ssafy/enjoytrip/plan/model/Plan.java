package com.ssafy.enjoytrip.plan.model;

import java.util.List;

import lombok.Data;

@Data
public class Plan {
	private int planId;
	private String title;
	private String createTime;
	private String content;
	private String userId;
	private List<PlanAttraction> planAttractions;
}
