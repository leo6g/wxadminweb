package com.lfc.wxadminweb.common.control;

import com.lfc.core.bean.InputObject;
import com.lfc.core.bean.OutputObject;

/**
 * 
 * 
 */
public interface IControlService {
	/**
	 * Call WebService Unified Method
	 * 
	 * @param inputObject
	 * @return OutputObject
	 */
	OutputObject execute(InputObject inputObject);
}
