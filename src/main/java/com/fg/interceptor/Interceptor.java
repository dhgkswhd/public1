package com.fg.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println("인터셉터 들어옴");
		
		try {
			// member 라는 세션 key를 가진 정보가 null 이면 main페이지로 이동
			if ( request.getSession().getAttribute("memberVO") == null ) {
				System.out.println("세션 null");
				response.sendRedirect("/main");
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 아니면 그대로 수행
		return true;
	}
	
}
