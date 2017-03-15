package com.lfc.wxadminweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lfc.core.bean.InputObject;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.JsonUtil;
import com.lfc.wxadminweb.common.control.IControlService;

public class InvokeTest {
	private static final Logger logger = LoggerFactory.getLogger(InvokeTest.class);
	
	private IControlService controlService; // 前后工程调用服务
	/** Get the IControlService Object **/
	public IControlService getControlService() {
		return controlService;
	}

	public void setControlService(IControlService controlService) {
		this.controlService = controlService;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/spring-all.xml");
		IControlService controlService = (IControlService) context.getBean("controlService");
		InputObject input = new InputObject();
		  input.setService("testService");
	      input.setMethod("queryAllData");
		OutputObject out=new OutputObject();
		out=controlService.execute(input);
		System.out.println(JsonUtil.convertObject2Json(out));
		
		

	}

}
