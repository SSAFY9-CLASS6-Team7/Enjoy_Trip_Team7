package com.ssafy.enjoytrip.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;

@Mapper
public interface PlanMapper {
	List<Plan> selectPlanList(Map<String, Object> param) throws SQLException;
	int selectPlanCount(Map<String, Object> map) throws SQLException;
	Plan selectPlanById(int planId) throws SQLException;
	void insertPlan(Plan plan) throws SQLException;
	void updatePlan(Plan plan) throws SQLException;
	void deletePlan(int planId) throws SQLException;

	List<PlanAttraction> selectPlanAttractionListByPlanId(int planId) throws SQLException;
	PlanAttraction selectPlanAttraction(int planAttractionId) throws SQLException;
	void insertPlanAttraction(PlanAttraction planAttraction) throws SQLException;
	void updatePlanAttraction(PlanAttraction planAttraction) throws SQLException;
	void deletePlanAttraction(int planAttractionId) throws SQLException;
	void cascadeDeletePlanAttraction(int planId) throws SQLException;
}
