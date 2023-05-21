package com.ssafy.enjoytrip.filter;

import com.ssafy.enjoytrip.util.controller.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFilter implements Filter {
    private static final String[] whiteList = {
            "/attraction*", "/user/login", "/user"
    };

    private final JWTUtils jwtUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("Login Filter Start");
            if (isLoginCheckPath(requestURI) && !httpRequest.getMethod().equals("GET")) {
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute("loginUser") == null) {
                    log.warn("미 인증 사용자 요청입니다. {} ", requestURI);
                    response.setContentType("application/json; charset=UTF-8");
                    httpResponse.getWriter().write("{ \n\t\"code\": 400, \n\t\"message\": \"로그인이 필요합니다.\" \n}");
                    return;
                }
            }
            chain.doFilter(request, response);
        }catch (Exception e){
            throw e;
        }
    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
