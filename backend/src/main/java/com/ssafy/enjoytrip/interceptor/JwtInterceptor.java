package com.ssafy.enjoytrip.interceptor;

import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.util.controller.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@CrossOrigin("*")
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

	private final JWTUtils jwtUtils;

	private static final String[] whiteList = {
			"/attraction*", "/user/login", "/user"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("access-token");
		String requestURI = request.getRequestURI();
		log.info("access-token {}", token);
		if (isLoginCheckPath(requestURI) && !request.getMethod().equals("GET")){
			if (token != null && jwtUtils.checkToken(token)) {
				log.info("토큰 사용 가능 : {}", token);
				return true;
			} else {
				log.info("토큰 사용 불가능 : {}", token);
				throw new UnAuthorizedException();
			}
		}
		return true;
	}

	private boolean isLoginCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}
}
