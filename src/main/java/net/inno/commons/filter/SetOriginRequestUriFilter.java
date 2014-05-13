package net.inno.commons.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-9
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
public class SetOriginRequestUriFilter extends AbstractExcludeUrlFilter {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4105075218347623048L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    	
        if (!this.doInclude(request, response, filterChain)) {
        	filterChain.doFilter(request, response);
            return;
        }

        //对配置的excludeUrls参数包含的URL进行过滤,例如：/OurHome/;/OurHome/*;/OurHome/*.jpg
        if (this.doExclude(request, response, filterChain)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            request.setAttribute("originurl",httpRequest.getRequestURI());
            filterChain.doFilter(request, response);
        } catch (ServletException sx) {
            filterConfig.getServletContext().log(sx.getMessage());
        } catch (IOException iox) {
            filterConfig.getServletContext().log(iox.getMessage());
        }
    }

    //Clean up resources
    public void destroy() {
    }
}
