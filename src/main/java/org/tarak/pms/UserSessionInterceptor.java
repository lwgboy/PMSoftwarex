package org.tarak.pms;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
@Component
public class UserSessionInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	HttpSession session;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		List<String> exceptions=new LinkedList<String>();
		exceptions.add("/");
		exceptions.add("/login");
		if(exceptions.contains(request.getRequestURI()))
		{
			return true;
		}
		if("/logout".equals(request.getRequestURI()))
		{
			session.removeAttribute("user");
			return true;
		}
		if(session!=null && session.getAttribute("user")==null)
		{
			response.sendRedirect("/");
			return false;
		}
		return true;
	}
	

}
