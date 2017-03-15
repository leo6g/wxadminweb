package com.lfc.wxadminweb.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.ControlConstants;
import com.lfc.core.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * spring mvc 全局处理异常捕获 根据请求区分ajax和普通请求，分别进行响应. 异常信息输出到日志中。
 */
@Component
public class MyExceptionResolver implements HandlerExceptionResolver {

	private static final Logger log = LoggerFactory
			.getLogger(MyExceptionResolver.class);

	/**
	 * 对异常信息进行统一处理，区分异步和同步请求，分别处理
	 */
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		boolean isajax = isAjax(request, response);
		Throwable deepestException = deepestException(ex);
		return processException(request, response, handler, deepestException,
				isajax);
	}

	/**
	 * 判断当前请求是否为异步请求.
	 */
	private boolean isAjax(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("请求方式:" + request.getHeader("X-Requested-With"));
		return (request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1));
	}

	/**
	 * 获取最原始的异常出处，即最初抛出异常的地方
	 */
	private Throwable deepestException(Throwable e) {
		Throwable tmp = e;
		int breakPoint = 0;
		while (tmp.getCause() != null) {
			if (tmp.equals(tmp.getCause())) {
				break;
			}
			tmp = tmp.getCause();
			breakPoint++;
			if (breakPoint > 1000) {
				break;
			}
		}
		return tmp;
	}

	/**
	 * 处理异常.
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param deepestException
	 * @param isajax
	 * @return
	 */
	private ModelAndView processException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Throwable ex,
			boolean isajax) {
		// 步骤一、异常信息记录到日志文件中.
		log.error("全局处理异常捕获:", ex);
		// 步骤二、分普通请求和ajax请求分别处理.
		if (isajax) {
			return processAjax(request, response, handler, ex);
		} else {
			return processNotAjax(request, response, handler, ex);
		}
	}

	/**
	 * ajax异常处理并返回.
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param deepestException
	 * @return
	 */
	private ModelAndView processAjax(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			Throwable deepestException) {
		ModelAndView empty = new ModelAndView();
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		OutputObject outputObject = new OutputObject();
		outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
		outputObject.setReturnMessage("后台服务异常！");
		try {
			PrintWriter pw = response.getWriter();
			pw.write(JsonUtil.convertObject2Json(outputObject));
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		empty.clear();
		return empty;
	}

	/**
	 * 普通页面异常处理并返回.
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param deepestException
	 * @return
	 */
	private ModelAndView processNotAjax(HttpServletRequest request,
			HttpServletResponse response, Object handler, Throwable ex) {
		String exceptionMessage = getThrowableMessage(ex);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("exceptionMessage", exceptionMessage);
		model.put("ex", ex);
		return new ModelAndView("error/error", model);
	}

	/**
	 * 返回错误信息字符串
	 * 
	 * @param ex
	 *            Exception
	 * @return 错误信息字符串
	 */
	public String getThrowableMessage(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
}
