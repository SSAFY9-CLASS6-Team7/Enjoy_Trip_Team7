package com.ssafy.enjoytrip.plan.model;

import lombok.Data;

@Data
public class PlanAttraction {
	private int planAttractionId;
	private int planId;
	private int attractionId;
	private int sequence;
	private String planDate;
}
