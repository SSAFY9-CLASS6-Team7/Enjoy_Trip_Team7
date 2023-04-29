package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {

	private PlanMapper planMapper;
	
	public PlanServiceImpl(PlanMapper planMapper) {
		this.planMapper = planMapper;
	}

	@Override
	public List<Plan> getPlanList(String userId) throws SQLException {
		return planMapper.selectPlanList(userId);
	}

	@Override
	public Plan getPlan(int planId) throws SQLException {
		return planMapper.selectPlanById(planId);
	}

	@Override
	public void createPlan(Plan plan) throws SQLException {
		planMapper.insertPlan(plan);
	}

	@Override
	public void updatePlan(Plan plan) throws SQLException {
		planMapper.updatePlan(plan);
	}

	@Override
	public void deletePlan(int planId) throws SQLException {
		List<PlanAttraction> list = getPlan(planId).getPlanAttractions();
		for(PlanAttraction pa : list) {
			planMapper.deletePlanAttraction(pa);
		}
		planMapper.deletePlan(planId);
	}

	//-----------이하 Plan Attraction 관련 -----------
	
	@Override
	public void createPlanAttraction(PlanAttraction planAttraction) throws SQLException {
		planMapper.insertPlanAttraction(planAttraction);
	}

}
