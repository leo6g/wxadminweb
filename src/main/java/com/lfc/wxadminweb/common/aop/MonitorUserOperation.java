package com.lfc.wxadminweb.common.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.ModelAndView;

@Aspect 
public class MonitorUserOperation {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@After("execution(* com.lfc.wxadminweb.modules..*.*(..))") 
    public void checkValidity(JoinPoint joinPoint) {

		logger.info("请求的类的全路径==》  "+joinPoint.getTarget().getClass().getName());
		logger.info("请求类当中的方法==》  "+joinPoint.getSignature().getName());
		
		MethodSignature ms = ((MethodSignature)joinPoint.getSignature());
		Class returnType = ms.getReturnType();
		if(returnType.getName().contains("ModelAndView")){
			Object[] args = joinPoint.getArgs();
			try{
				for(Object arg: args){
					if(arg.getClass().getName().equals("org.springframework.web.servlet.ModelAndView")){
						ModelAndView mv= (ModelAndView) arg;
						logger.info("===HTML 文件路径===>WEB-INF/velocity/"+mv.getViewName()+".html");
						break;
					}
				}
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
    }  
	
}
