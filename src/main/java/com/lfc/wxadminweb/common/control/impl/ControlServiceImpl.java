package com.lfc.wxadminweb.common.control.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lfc.core.bean.InputObject;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.control.IControlService;
import com.lfc.core.util.ControlConstants.RETURN_CODE;

/**
 * 
 * 
 */
public class ControlServiceImpl implements IControlService {
	private com.lfc.core.service.IControlService coreControlService;
	private static final Logger logger = LoggerFactory.getLogger(ControlServiceImpl.class);

	public OutputObject execute(InputObject inputObject) {
		OutputObject outputObject = null;

		try {
			long start = System.currentTimeMillis();
			logger.info("START INVOKE WEBAPP...", "");
			
			// 调用WebApp服务
			outputObject = coreControlService.execute(inputObject);
			
			long end = System.currentTimeMillis();
			logger.info("END INVOKE WEBAPP...", String.valueOf(end - start) + "ms");
		} catch (Exception e) {
			logger.error("Error:", "INVOKE WEBAPP ERROR!", e.getMessage() == null ? e.getCause().getMessage() : e.getMessage());
			outputObject = new OutputObject();
			outputObject.setReturnCode(RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("异常");
			//outputObject.setReturnMessage(e.getMessage() == null ? e.getCause().getMessage() : e.getMessage());
		}
		return outputObject;
	}

	public com.lfc.core.service.IControlService getCoreControlService() {
		return coreControlService;
	}

	public void setCoreControlService(
			com.lfc.core.service.IControlService coreControlService) {
		this.coreControlService = coreControlService;
	}

}
