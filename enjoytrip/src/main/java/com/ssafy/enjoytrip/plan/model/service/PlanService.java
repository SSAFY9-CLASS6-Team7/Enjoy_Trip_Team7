package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;

public interface PlanService {
	List<Plan> getPlanList(Map<String, String> map) throws SQLException;
	Plan getPlan(int planId) throws SQLException;
	void createPlan(Plan plan) throws SQLException;
	void updatePlan(Plan plan) throws SQLException;
	void deletePlan(int planId) throws SQLException;
	
	void createPlanAttraction(PlanAttraction planAttraction) throws SQLException;
	void updatePlanAttraction(PlanAttraction planAttraction) throws SQLException;
	void deletePlanAttraction(PlanAttraction planAttraction) throws SQLException;
}