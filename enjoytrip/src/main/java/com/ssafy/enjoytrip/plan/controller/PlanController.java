package com.ssafy.enjoytrip.plan.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.plan.model.Plan;
import com.ssafy.enjoytrip.plan.model.PlanAttraction;
import com.ssafy.enjoytrip.plan.model.service.PlanService;
import com.ssafy.enjoytrip.user.model.User;

@RestController
@RequestMapping("/plan")
public class PlanController {
	private PlanService planService;
	
	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	private User getLoginUser(HttpSession session) {
		// ----- DUMMY USER FOR TEST -----
		User dummyUser = new User();
		dummyUser.setUserId("ssafy");
		session.setAttribute("loginUser", dummyUser);
		// --------------------------------
		return (User) session.getAttribute("loginUser");
	}
	
	@GetMapping
	public List<Plan> getPlanList(HttpSession session) throws SQLException {
		return planService.getPlanList(getLoginUser(session).getUserId());
	}

	@GetMapping("{planId}")
	public Plan getPlan(@PathVariable int planId) throws SQLException {
		return planService.getPlan(planId);
	}

	@PostMapping
	public List<Plan> createPlan(@RequestBody Plan plan, HttpSession session) throws SQLException {
		planService.createPlan(plan);
		return planService.getPlanList(getLoginUser(session).getUserId());
	}
	
	@PostMapping("{planId}")
	public List<Plan> updatePlan(@RequestBody Plan plan, @PathVariable int planId, HttpSession session) throws SQLException {
		plan.setPlanId(planId);
		planService.updatePlan(plan);
		return planService.getPlanList(getLoginUser(session).getUserId());
	}
	
	@DeleteMapping("{planId}")
	public List<Plan> deletePlan(@PathVariable int planId, HttpSession session) throws SQLException {
		planService.deletePlan(planId);
		return planService.getPlanList(getLoginUser(session).getUserId());
	}
	
	//-----------이하 Plan Attraction 관련 -----------
	
	@PostMapping("{planId}/attraction")
	public Plan createPlanAttraction(@RequestBody PlanAttraction planAttraction, @PathVariable int planId, HttpSession session) throws SQLException {
		planAttraction.setPlanId(planId);
		int sequence = getPlan(planId).getPlanAttractions().size();
		planAttraction.setSequence(sequence+1);
		planService.createPlanAttraction(planAttraction);
		return planService.getPlan(planId);
	}
	
	/*
	@PostMapping("{planId}/attraction/{attractionId}")
	public List<Plan> updatePlanAttraction(@RequestBody PlanAttraction planAttraction, @PathVariable int planId, @PathVariable int attractionId, HttpSession session) throws SQLException {
		planAttraction.setAttractionId(attractionId);
		planService.updatePlanAttraction(planAttraction);
		return planService.getPlanList(getLoginUser(session).getUserId());
	}*/
	
	/*
	 * TODO : planAttraction의 PK역할을 planId와 attractionId가 하는 게 맞는가?
	 * 관광지 자체는 변경할 수 없는가? 
	 * 없다면 -> 장소 변경을 위해서는 삭제하고 새로운 관광지를 추가하는 방법 뿐 [V]
	 * 있다면 -> PK용 Id 컬럼을 하나 만들어야 구분이 가능(sequence도 변경되니까)[X]
	 */
	@DeleteMapping("{planId}/attraction")
	public Plan deletePlanAttraction(@RequestBody PlanAttraction planAttraction, @PathVariable int planId, @RequestParam int attractionId,HttpSession session) throws SQLException {
		planAttraction.setPlanId(planId);
		planAttraction.setAttractionId(attractionId);
		planService.deletePlanAttraction(planAttraction);
		return planService.getPlan(planId);
	}
}