package com.lfc.wxadminweb.modules.biz.web;

import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lfc.core.bean.OutputObject;

@Controller
@RequestMapping("/biz")
public class CounterController {
	/**
	 * 存款收益计算器
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "outputInterest")
	public void outputInterest(HttpServletRequest request , HttpServletResponse response) {
		try{
			//每次支出金额
			double tramount = 0;
			//本息总计
			Double depositTotal = null;
			//利息
			double interest = 0;
			//年利率 rate
			String rateForStr = request.getParameter("rate");
			System.out.println("---------------------------------"+rateForStr);
			Double rate  = Double.parseDouble(rateForStr.substring(0, rateForStr.lastIndexOf("%")))/100;
			//存款类型
			String subType = request.getParameter("subType");
			//起初存款金额
			Double deposit = Double.parseDouble(request.getParameter("deposit"));
			
			//结果保留两位小数，四舍五入
			NumberFormat nf = NumberFormat.getNumberInstance();   
		    nf.setMaximumFractionDigits(2);//String =nf.format(double)
		    
			//根据条件从数据库查出利率
			Map<String, Object> map = new HashMap<String, Object>();
			
			if("活期".equals(subType.trim())){
				/*例如
				存100，2016年10月24存入，2016年11月24取出，年利率0.3%
				则利息为：
				100*30*0.3%/360=0.025  利息算到分位，四舍五入，即0.03元*/
				//存入日期
				String beginDate = request.getParameter("beginDate");
				//提取日期
				String endDate = request.getParameter("endDate");
				System.out.println("----"+beginDate+"------"+endDate);
				//存款天数
				long date =(new SimpleDateFormat("yyyy-MM-dd").parse(endDate).getTime() - new SimpleDateFormat("yyyy-MM-dd").parse(beginDate).getTime())/86400000+ 1;
				interest = deposit * date * rate/360;
				depositTotal =interest + deposit;
				//String =nf.format(depositTotal) nf.format(interest)
			}

			if("整存整取".equals(subType.trim())){
				//整存整取利息=本金*利率*时间
				
				//存款期限
				Integer period = Integer.parseInt(request.getParameter("period"));
				interest = deposit * rate * period;
				depositTotal =interest + deposit;
				//String =nf.format(depositTotal) nf.format(interest)
			}

			if("零存整取".equals(subType.trim())){
				/*零存整取利息＝月存金额×累计月积数×月利率。
						其中累计月积数＝（存入次数＋1 ）×存入次数/2。
						例如：
						初始存款金额1000，月存入金额1000，年利率为1.35%，存款期限一年
						月积数=（12+1）*12/2=78
						利息=1000*78*（1.35%/12 )=87.75
						本息合计=13087.75*/
				//月存入金额
				Double depositByMon = Double.parseDouble(request.getParameter("depositByMon"));
				//存款期限
				Integer period = Integer.parseInt(request.getParameter("period"));
				//累计月积数
				Integer product = (period + 1)*period/2;
				interest = depositByMon *product * rate/12;
				depositTotal =interest + deposit + depositByMon*period;
				//String =nf.format(depositTotal) nf.format(interest)
			}
			
			if("存本取息".equals(subType.trim())){
				/*每次支取利息数=本金X存期X利率/支取利息的次数
				例如：
				存5000，存一年，支取频次为1个月，年利率1.35%
				每次支取利息=5000*1*1.35%/12=5.625 四舍五入到分为5.63元
				本息合计=本金+每次支取利息*支取利息次数
				本息合计=5000+5.625*12=5067.50
				 */
				//存款期限 单位为月
				Integer period = Integer.parseInt(request.getParameter("period"));
				//支出频次
				Integer drawfre = Integer.parseInt(request.getParameter("drawfre"));
				interest = deposit *period/12 * rate/(period/drawfre);
				depositTotal =deposit + interest*(period/drawfre);
				//String =nf.format(depositTotal) nf.format(interest)
			}
			
			if("整存零取".equals(subType.trim())){
				//每次支取本金=本金÷约定支取次数
				//到期应付利息=(全部本金+每次支取金额)/2×支取本金次数×每次支取间隔期×月利率 
				/* 例如：
					存款1000元，存1年，支取频次1个月，年利率
					每次支取本金=1000/12=83.33
					利息=（1000+83.33）/2*12*1*1.35%/12=7.31
					本息合计=1000+7.31=1007.31
				 */
				//存款期限
				Integer period = Integer.parseInt(request.getParameter("period"));
				//支出频次
				Integer drawfre = Integer.parseInt(request.getParameter("drawfre"));
				//每次支出金额
				tramount = deposit/(period/drawfre);
				interest = (deposit + tramount)/2*(period/drawfre)*drawfre*rate/12;
				depositTotal = interest + deposit;
				//String =nf.format(depositTotal) nf.format(interest)
			}
			
			if("定活两便".equals(subType.trim())){
				
			}
			
			//输出
			String result = "tramount:"+nf.format(tramount)+","+"interest:"
			+nf.format(interest)+","+"depositTotal:"+nf.format(depositTotal);
			
			//使用OutputObject输出方式
//			OutputObject outputResult = new OutputObject();
//			map.clear();
//			map.put("tramount", nf.format(tramount));
//			map.put("interest", nf.format(interest));
//			map.put("depositTotal", nf.format(depositTotal));
//			outputResult.setBean(map);
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes());
			//os.write(outputResult.toString().getBytes());
			os.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	//测试
	public static void main(String[] args) {
		String rateForS = "1.35%";
		Double rate  = Double.parseDouble(rateForS.substring(0, rateForS.lastIndexOf("%")))/100;
		NumberFormat nf = NumberFormat.getNumberInstance();   
	    nf.setMaximumFractionDigits(2);
	    Double interest,depositTotal = null;
	    double deposit = 1000.00;
	    
	    //存款期限
		Integer period = 12;
		//支出频次
		Integer drawfre = 1;
		//每次支出金额
		double tramount = deposit/(period/drawfre);
		interest = (deposit + tramount)/2*(period/drawfre)*drawfre*rate/12;
		depositTotal = interest + deposit;
		
		System.out.println("----------------"+nf.format(depositTotal));
	}
	
}
