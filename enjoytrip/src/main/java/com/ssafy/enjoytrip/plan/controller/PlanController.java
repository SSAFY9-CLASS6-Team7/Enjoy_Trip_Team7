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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.plan.model.Plan;
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
	public Plan getPlan(@PathVariable String planId) throws SQLException {
		return planService.getPlan(planId);
	}
	
	// TODO : 플랜 등록 폼으로 이동 -> 페이지 이동만이니까 contoller구현 없어도 되나?(jsp가 없음)
	/*
	 * @GetMapping public String createPlan() { return "plan/create"; }
	 */

	@PostMapping
	public List<Plan> createPlan(@RequestBody Plan plan, HttpSession session) throws SQLException {
		planService.createPlan(plan);
		return planService.getPlanList(getLoginUser(session).getUserId());
	}
	
	@PostMapping("{planId}")
	public List<Plan> updatePlan(@RequestBody Plan plan, HttpSession session) throws SQLException {
		planService.updatePlan(plan);
		return planService.getPlanList(getLoginUser(session).getUserId());
	}
	
	@DeleteMapping("{planId}")
	public List<Plan> deletePlan(@PathVariable String planId, HttpSession session) throws SQLException {
		planService.deletePlan(planId);
		return planService.getPlanList(getLoginUser(session).getUserId());
	}
}
/*
 * List<Plan> getPlanList() throws SQLException;
 * Plan getPlan(String planId) throws SQLException;
 * void createPlan(Plan plan) throws SQLException;
 * void updatePlan(Plan plan) throws SQLException;
 * void deletePlan(String planId) throws SQLException;
 */