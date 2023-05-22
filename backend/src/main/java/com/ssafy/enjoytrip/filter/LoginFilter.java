package com.ssafy.enjoytrip.filter;

import com.ssafy.enjoytrip.util.controller.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter {
    private static final String[] whiteList = {
            "/attraction*", "/user/login", "/user"
    };

//    private final JWTUtils jwtUtils;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        String requestURI = httpRequest.getRequestURI();
//
//        try {
//            if (isLoginCheckPath(requestURI) && !httpRequest.getMethod().equals("GET")) {
//                log.info("Headers {}", httpRequest.getParameterNames().toString());
//                String accessToken = httpRequest.getHeader("Access-Token");
//                log.info("accessToken {}", accessToken);
//                Enumeration<String> headerNames = httpRequest.getHeaderNames();
//                while(headerNames.hasMoreElements()) {
//                    String name = (String)headerNames.nextElement();
//                    String value = httpRequest.getHeader(name);
//                    log.info("Header : {},  value : {}", name, value);
//                }
//
//                if (!jwtUtils.checkToken(accessToken)) {
//                    log.warn("미 인증 사용자 요청입니다. {} ", requestURI);
//                    response.setContentType("application/json; charset=UTF-8");
//                    httpResponse.getWriter().write("{ \n\t\"code\": 400, \n\t\"message\": \"로그인이 필요합니다.\" \n}");
//                    return;
//                }
//            }
//            chain.doFilter(request, response);
//        }catch (Exception e){
//            throw e;
//        }
//    }
//
//    private boolean isLoginCheckPath(String requestURI) {
//        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
//    }
}
