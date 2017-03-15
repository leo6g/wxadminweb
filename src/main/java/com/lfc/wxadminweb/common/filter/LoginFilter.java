package com.lfc.wxadminweb.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.hibernate.validator.internal.util.Contracts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sd4324530.fastweixin.util.PropertiesUtil;
import com.lfc.wxadminweb.common.constant.Constants;

public class LoginFilter implements Filter {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String baseUri = "";

	@Override
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		Session session = SecurityUtils.getSubject().getSession();

		// 获得用户请求的URI
		String pathInfo = servletRequest.getPathInfo() == null ? "" : servletRequest.getPathInfo();
		String url = servletRequest.getServletPath() + pathInfo;
		String contextPath = servletRequest.getContextPath();
		String token = servletRequest.getParameter("token");
		// 无需过滤的页面和目录
		if (filterUrl(url, Constants.noFilterUrls)) {
			//TODO...这里增加apiconfig发送的请求accessToken的请求，如果token不对就过滤掉这个请求
			/*
			if(url.equals("/wx/accesstoken/getToken")){
				if(token == null || !token.equals(PropertiesUtil.getString("apiToken"))){
					String loginUrl = baseUri + contextPath + "/login";
					System.out.println("-==============here============/wx/accesstoken/getToken===============");
					Object user = session.getAttribute(Constants.USER_SESSION.USER);
					if (user == null) {
						// 跳转到登陆页面
						logger.info("loginFilter:path", url);
						servletResponse.sendRedirect(loginUrl);
						return;
					}else{
						chain.doFilter(request, response);
						return;
					}
				}
			}
			*/
			chain.doFilter(request, response);
			return;
		} else {
			String loginUrl = baseUri + contextPath + "/login";
			Object user = session.getAttribute(Constants.USER_SESSION.USER);
			if (user == null) {
				// 跳转到登陆页面
				logger.info("loginFilter:path", url);
				servletResponse.sendRedirect(loginUrl);
				return;
			}else{
				chain.doFilter(request, response);
				return;
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//baseUri = PropertiesUtil.getString("baseUri");
	}

	private static final boolean filterUrl(String path, final String[] filter) {
		for (int i = 0; i < filter.length; i++) {
			if (path.indexOf(filter[i]) > -1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {

	}

}
