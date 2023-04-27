package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.plan.model.Plan;

public interface PlanService {
	List<Plan> getPlanList(String userId) throws SQLException;
	void getPlan(String planId) throws SQLException;
	void createPlan(Plan plan) throws SQLException;
	void updatePlan(Plan plan) throws SQLException;
	void deletePlan(String planId) throws SQLException;
}