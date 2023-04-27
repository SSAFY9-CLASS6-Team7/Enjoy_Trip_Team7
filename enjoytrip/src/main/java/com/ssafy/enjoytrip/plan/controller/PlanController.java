package com.ssafy.enjoytrip.plan.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.service.PlanService;

@RestController
@RequestMapping("/plan")
public class PlanController {
	private PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}
	
	@GetMapping("{userId}")
	public List<Plan> getPlanList(@PathVariable String userId) throws SQLException{
		return planService.getPlanList(userId);
	}
}
/*
void getPlanList(String userId) throws SQLException;
void getPlan(String planId) throws SQLException;
void createPlan(Plan plan) throws SQLException;
void updatePlan(Plan plan) throws SQLException;
void deletePlan(String planId) throws SQLException; 
 */