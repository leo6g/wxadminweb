package com.lfc.wxadminweb.modules.weixin.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lfc.core.bean.OutputObject;

@Controller
@RequestMapping("/viewVideo")
public class ViewVideoController{
	
	/**
	 * 下载图片
	 * @param request
	 * @param response
	 */
	@Value("#{system.video_path}") 
	private  String path;
	
	@RequestMapping(value = "viewVideo")
	public void viewImage(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("video/mpeg4; charset=utf-8");    
		response.setHeader("Content-Disposition", "attachment;filename="  + request.getParameter("videoName"));    
		String videoPath = path+request.getParameter("videoPath");
		File file = new File(videoPath);
        OutputObject outputObject=new OutputObject();
        OutputStream os;
		try {
			os = response.getOutputStream();
			 byte[] buffer = new byte[2048];
		        FileInputStream fis = new FileInputStream(file.getPath());// 打开图片文件
		        int count;
		        while (-1 != (count = fis.read(buffer, 0, buffer.length))) {
		        	os.write(buffer, 0, count);
				}
		        if(fis !=null){
		        	fis.close();
		        }
		        outputObject.setReturnMessage("下载成功!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			outputObject.setReturnMessage("下载失败!");
			e.printStackTrace();
		}
		//return outputObject;
	}

}
