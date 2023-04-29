package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;
import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;

public interface PlanService {
	List<Plan> getPlanList(String userId) throws SQLException;
	Plan getPlan(int planId) throws SQLException;
	void createPlan(Plan plan) throws SQLException;
	void updatePlan(Plan plan) throws SQLException;
	void deletePlan(int planId) throws SQLException;
	
	void createPlanAttraction(PlanAttraction planAttraction) throws SQLException;
}