package com.ssafy.enjoytrip.plan.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;
import com.ssafy.enjoytrip.plan.model.service.PlanService;
import com.ssafy.enjoytrip.user.model.User;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/plan")
public class PlanController {
	private PlanService planService;
	
	public PlanController(PlanService planService) {
		this.planService = planService;
	}

//	private User getLoginUser(HttpSession session) {
//		// ----- DUMMY USER FOR TEST -----
//		User dummyUser = new User();
//		dummyUser.setUserId("TestUser1");
//		session.setAttribute("loginUser", dummyUser);
//		// --------------------------------
//		return (User) session.getAttribute("loginUser");
//	}
	
	@GetMapping
	public Map<String, Object> getPlanList(@RequestParam Map<String, Object> paramMap) throws SQLException {
		return planService.getPlanList(paramMap);
	}

	@GetMapping("{planId}")
	public Plan getPlan(@PathVariable int planId) throws SQLException {
		return planService.getPlan(planId);
	}

	@PostMapping
	public void createPlan(@RequestBody Plan plan) throws SQLException {
		planService.createPlan(plan);
	}
	
	@PostMapping("{planId}")
	public void updatePlan(@RequestBody Plan plan, @PathVariable int planId) throws SQLException {
		plan.setPlanId(planId);
		planService.updatePlan(plan);
	}
	
	@DeleteMapping("{planId}")
	public void deletePlan(@PathVariable int planId) throws SQLException {
		planService.deletePlan(planId);
	}
	
	//-----------이하 Plan Attraction 관련 -----------
	@GetMapping("{planId}/attraction")
	public List<PlanAttraction> getPlanAttractionList(@PathVariable int planId) throws SQLException{
		return planService.getPlanAttractionList(planId);
	}


//	@PostMapping("{planId}/attraction")
//	public void createPlanAttraction(@RequestBody PlanAttraction planAttraction, @PathVariable int planId) throws SQLException {
//		planAttraction.setPlanId(planId);
//		int sequence = getPlan(planId).getPlanAttractions().size();
//		planAttraction.setSequence(sequence);
//		planService.createPlanAttraction(planAttraction);
//	}
	
//	@PostMapping("{planId}/attraction/{planAttractionId}")
//	public void updatePlanAttraction(@RequestBody PlanAttraction planAttraction, @PathVariable int planId, @PathVariable int planAttractionId) throws SQLException {
//		planAttraction.setPlanId(planId);
//		planAttraction.setPlanAttractionId(planAttractionId);
//		planService.updatePlanAttraction(planAttraction);
//	}

	@PostMapping("{planId}/attraction")
	public void modifyPlanAttraction(@RequestBody List<PlanAttraction> planAttractions, @PathVariable int planId) throws SQLException {
		for(PlanAttraction planAttraction : planAttractions){
			planAttraction.setPlanId(planId);
		}
		planService.modifyPlanAttraction(planAttractions);
	}
	
//	@DeleteMapping("{planId}/attraction/{planAttractionId}")
//	public void deletePlanAttraction(@PathVariable int planId, @PathVariable int planAttractionId) throws SQLException {
//		planService.deletePlanAttraction(planAttractionId);
//	}
}