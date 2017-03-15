package com.lfc.wxadminweb.modules.threads;

import com.lfc.core.bean.InputObject;
import com.lfc.wxadminweb.common.control.IControlService;

public class CommonThread extends Thread{
	
//	@Resource(name = "controlService")
	private IControlService controlService; // 前后工程调用服务
	
	private InputObject inputObject;
	
	public CommonThread(InputObject inputObject){
		this.inputObject = inputObject;
	}
	
	
	public void run(){
		execTask();
	}
	
	protected void execTask(){
		controlService.execute(inputObject);
	}


	public IControlService getControlService() {
		return controlService;
	}


	public void setControlService(IControlService controlService) {
		this.controlService = controlService;
	}

}
