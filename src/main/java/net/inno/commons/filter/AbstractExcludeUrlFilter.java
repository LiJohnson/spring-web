package net.inno.commons.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-31
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractExcludeUrlFilter extends HttpServlet implements Filter {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	protected FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public boolean doExclude(ServletRequest request, ServletResponse response,
                             FilterChain filterChain) throws ServletException,
            IOException {
        String excludeUrls = this.getInitParameter("excludeUrls");
        if(excludeUrls==null){
            return false;
        }
        String[] excludes = excludeUrls.split(";");

        HttpServletRequest httprequest = (HttpServletRequest) request;

        String path = httprequest.getServletPath();
        //需要忽略的URL
        for (int i = 0; i < excludes.length; i++) {
            String regx = excludes[i].replaceAll("\\.", "\\\\.");
            regx = regx.replaceAll("\\*", "\\.*");
            if (excludes[i].endsWith("/")) {
                regx += ".*";
            }

            if (path.matches(regx)) {
                return true;
            }
        }
        return false;

    }

    public boolean doInclude(ServletRequest request, ServletResponse response,
                             FilterChain filterChain) throws ServletException,
            IOException {
        String includeUrls = this.getInitParameter("includeUrls");
        if(StringUtils.isBlank(includeUrls)){
            return true;
        }
        String[] includes = includeUrls.split(";");

        HttpServletRequest httprequest = (HttpServletRequest) request;
        String path = httprequest.getServletPath();
        //需要加载的URL
        for (int i = 0; i < includes.length; i++) {
            String regx = includes[i].replaceAll("\\.", "\\\\.");
            regx = regx.replaceAll("\\*", "\\.*");
            if (includes[i].endsWith("/")) {
                regx += ".*";
            }

            if (path.matches(regx)) {
                return true;
            }
        }
        return false;
    }

    public String getInitParameter(String name) {
        return filterConfig.getInitParameter(name);
    }
}

