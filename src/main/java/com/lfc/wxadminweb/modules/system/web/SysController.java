package com.lfc.wxadminweb.modules.system.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lfc.wxadminweb.common.utils.PropertiesUtil;
import com.lfc.wxadminweb.common.web.BaseController;

@Controller
public class SysController extends BaseController {
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String btnList(HttpServletRequest request) {
		return "index";
	}

	/**
	 * 系统日志下载
	 * @return
	 */
	@RequestMapping(value = "/logs/download")
	@ResponseBody
	public String download(HttpServletRequest request, HttpServletResponse response) {
		String shellCommand = PropertiesUtil.getString("shellFilePath");
		String zipLogsPath = PropertiesUtil.getString("zipLogsPath");
		boolean success = false;
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		// 格式化日期时间，记录日志时使用
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ");
		try {
			stringBuffer.append(dateFormat.format(new Date()))
					.append("准备执行Shell命令 ").append(shellCommand)
					.append(" /r/n");
			Process pid = null;
			// 执行Shell命令
			pid = Runtime.getRuntime().exec(shellCommand);
			if (pid != null) {
				stringBuffer.append("进程号：").append(pid.toString())
						.append("/r/n");
				// bufferedReader用于读取Shell的输出内容 bufferedReader = new
				// BufferedReader(new InputStreamReader(pid.getInputStream()),
				// 1024);
				pid.waitFor();
			} else {
				stringBuffer.append("没有pid/r/n");
			}
			stringBuffer.append(dateFormat.format(new Date())).append(
					"Shell命令执行完毕/r/n执行结果为：/r/n");
			String line = null;
			// 读取Shell的输出内容，并添加到stringBuffer中
			while (bufferedReader != null
					&& (line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line).append("/r/n");
			}
			success = true;
		} catch (Exception ioe) {
			stringBuffer.append("执行Shell命令时发生异常：/r/n").append(ioe.getMessage())
					.append("/r/n");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// shell命令执行成功，执行下载操作
		if (success) {
			try{
				File file = new File(zipLogsPath);// path是根据日志路径和文件名拼接出来的
				String filename = file.getName();// 获取日志文件名称
				InputStream fis = new BufferedInputStream(new FileInputStream(zipLogsPath));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				response.reset();
				// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				OutputStream os = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				os.write(buffer);// 输出文件
				os.flush();
				os.close();
				stringBuffer.append("***********************/r/n文件下载成功!!!!*********************");
			}catch(Exception e){
				stringBuffer.append("***********************/r/n文件下载失败!!!!*********************");
			}
		}
		// 将shell执行结果输出
		return stringBuffer.toString();
	}

}
