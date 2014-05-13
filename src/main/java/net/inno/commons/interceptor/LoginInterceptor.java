package net.inno.commons.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author lcs
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object login = request.getSession().getAttribute("login");
		if( login == null ){
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		return true;
	}
}
