package com.ssafy.enjoytrip.plan.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;
import com.ssafy.enjoytrip.plan.model.service.PlanService;
import com.ssafy.enjoytrip.user.model.User;

@RestController
@CrossOrigin("*")
@RequestMapping("/plan")
public class PlanController {
	private PlanService planService;
	
	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	private User getLoginUser(HttpSession session) {
		// ----- DUMMY USER FOR TEST -----
		User dummyUser = new User();
		dummyUser.setUserId("TestUser1");
		session.setAttribute("loginUser", dummyUser);
		// --------------------------------
		return (User) session.getAttribute("loginUser");
	}
	
	@GetMapping
	public List<Plan> getPlanList(@RequestParam(required = false) String pageNo, HttpSession session) throws SQLException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", getLoginUser(session).getUserId());
		param.put("pgno", pageNo);
		return planService.getPlanList(param);
	}

	@GetMapping("{planId}")
	public Plan getPlan(@PathVariable int planId) throws SQLException {
		return planService.getPlan(planId);
	}

	@PostMapping
	public List<Plan> createPlan(@RequestBody Plan plan, HttpSession session) throws SQLException {
		plan.setUserId(getLoginUser(session).getUserId());
		planService.createPlan(plan);
		return getPlanList("1", session);
	}
	
	@PostMapping("{planId}")
	public List<Plan> updatePlan(@RequestBody Plan plan, @PathVariable int planId, HttpSession session) throws SQLException {
		plan.setPlanId(planId);
		planService.updatePlan(plan);
		return getPlanList("1", session);
	}
	
	@DeleteMapping("{planId}")
	public List<Plan> deletePlan(@PathVariable int planId, HttpSession session) throws SQLException {
		planService.deletePlan(planId);
		return getPlanList("1", session);
	}
	
	//-----------이하 Plan Attraction 관련 -----------
	
	@PostMapping("{planId}/attraction")
	public Plan createPlanAttraction(@RequestBody PlanAttraction planAttraction, @PathVariable int planId) throws SQLException {
		planAttraction.setPlanId(planId);
		int sequence = getPlan(planId).getPlanAttractions().size();
		planAttraction.setSequence(sequence);
		planService.createPlanAttraction(planAttraction);
		return planService.getPlan(planId);
	}
	
	@PostMapping("{planId}/attraction/{planAttractionId}")
	public Plan updatePlanAttraction(@RequestBody PlanAttraction planAttraction, @PathVariable int planId, @PathVariable int planAttractionId) throws SQLException {
		planAttraction.setPlanId(planId);
		planAttraction.setPlanAttractionId(planAttractionId);
		planService.updatePlanAttraction(planAttraction);
		return planService.getPlan(planId);
	}
	
	@DeleteMapping("{planId}/attraction/{planAttractionId}")
	public Plan deletePlanAttraction(@PathVariable int planId, @PathVariable int planAttractionId) throws SQLException {
		planService.deletePlanAttraction(planAttractionId);
		return planService.getPlan(planId);
	}
}