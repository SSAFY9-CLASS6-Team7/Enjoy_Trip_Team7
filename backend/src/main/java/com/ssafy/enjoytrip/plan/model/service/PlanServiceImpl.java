package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.util.model.Page;
import com.ssafy.enjoytrip.util.model.PageResult;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {
	
	//페이징 용 : 한 페이지에 보일 글의 수
    private final int LIST_SIZE = 4;

	private PlanMapper planMapper;
	
	public PlanServiceImpl(PlanMapper planMapper) {
		this.planMapper = planMapper;
	}

	@Override
	public Map<String, Object> getPlanList(Map<String, Object> map) throws SQLException {
		Page page = new Page();
		int pageNo = Integer.parseInt(String.valueOf(map.get("pgno")));
		page.setPageNo(pageNo);
		page.setListSize(LIST_SIZE);
		map.put("page", page);
		map.put("userId", map.get("userId"));

		Map<String, Object> result = new HashMap<>();
		int totalCount = planMapper.selectPlanCount();
		PageResult pageResult = new PageResult(pageNo, totalCount, LIST_SIZE, 5);
		result.put("plans", planMapper.selectPlanList(map));
		result.put("pageResult", pageResult);
		return result;
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
		planMapper.cascadeDeletePlanAttraction(planId);
		planMapper.deletePlan(planId);
	}

	//-----------이하 Plan Attraction 관련 -----------
	public List<PlanAttraction> getPlanAttractionList(int planId) throws SQLException{
		return planMapper.selectPlanAttractionListByPlanId(planId);
	}

	@Override
	public void createPlanAttraction(PlanAttraction planAttraction) throws SQLException {
		planMapper.insertPlanAttraction(planAttraction);
	}
	
//	@Override
//	public void updatePlanAttraction(PlanAttraction planAttraction) throws SQLException {
//		int beforeSequence = planMapper.selectPlanAttraction(planAttraction.getPlanAttractionId()).getSequence();
//		int afterSequence = planAttraction.getSequence();
//		planMapper.updatePlanAttraction(planAttraction);
//
//		if(beforeSequence != afterSequence) {
//			int planId = planAttraction.getPlanId();
//			List<PlanAttraction> list = getPlan(planId).getPlanAttractions();
//			for(int i=afterSequence; i<list.size(); i++) {
//				PlanAttraction tempAttraction = list.get(i);
//				if(planAttraction.equals(tempAttraction)) continue;
//				else if(tempAttraction.getSequence() == afterSequence) {
//					tempAttraction.setSequence(i+1);
//				} else {
//					tempAttraction.setSequence(i);
//				}
//				planMapper.updatePlanAttraction(tempAttraction);
//			}
//		}
//	}

	@Override
	public void updatePlanAttraction(List<PlanAttraction> planAttractions) throws SQLException {
		for(PlanAttraction planAttraction : planAttractions) {
			planMapper.updatePlanAttraction(planAttraction);
		}
	}

	@Override
	public void deletePlanAttraction(int planAttractionId) throws SQLException {
		PlanAttraction planAttraction = planMapper.selectPlanAttraction(planAttractionId);
		int sequence = planAttraction.getSequence();
		int planId = planAttraction.getPlanId();
		List<PlanAttraction> list = getPlan(planId).getPlanAttractions();
		for(int i=sequence+1; i<list.size(); i++) {
			PlanAttraction tempAttraction = list.get(i);
			tempAttraction.setSequence(i-1);
			planMapper.updatePlanAttraction(tempAttraction);
		}
		planMapper.deletePlanAttraction(planAttractionId);
	}
}
