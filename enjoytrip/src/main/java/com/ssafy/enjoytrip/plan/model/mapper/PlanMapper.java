package com.ssafy.enjoytrip.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;

@Mapper
public interface PlanMapper {
	List<Plan> selectPlanList(String userId) throws SQLException;
	Plan selectPlanById(int planId) throws SQLException;
	void insertPlan(Plan plan) throws SQLException;
	void updatePlan(Plan plan) throws SQLException;
	void deletePlan(int planId) throws SQLException;
	
	void insertPlanAttraction(PlanAttraction planAttraction) throws SQLException;
	void updatePlanAttraction(PlanAttraction planAttraction) throws SQLException;
	void updatePlanAttractionsSequence(PlanAttraction planAttraction) throws SQLException;
	void deletePlanAttraction(PlanAttraction planAttraction) throws SQLException;
}
