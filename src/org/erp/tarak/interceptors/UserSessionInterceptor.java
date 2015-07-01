package org.erp.tarak.interceptors;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserSessionInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	HttpSession session;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		List<String> exceptions=new LinkedList<String>();
		exceptions.add("/ERPSoftware/");
		exceptions.add("/ERPSoftware/main/");
		exceptions.add("/ERPSoftware/main");
		exceptions.add("/ERPSoftware/main/index.jsp");
		exceptions.add("/ERPSoftware/main/createUser");
		exceptions.add("/ERPSoftware/main/createUserDb");
		exceptions.add("/ERPSoftware/main/authenticate");
		if(exceptions.contains(request.getRequestURI()))
		{
			return true;
		}
		if("/ERPSoftware/main/logout".equals(request.getRequestURI()))
		{
			session.removeAttribute("user");
			return true;
		}
		if(session!=null && session.getAttribute("user")==null)
		{
			response.sendRedirect("/ERPSoftware/");
			return false;
		}
		return true;
	}
	

}
