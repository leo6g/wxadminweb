package com.lfc.wxadminweb.common.upload;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;


@Repository
public class UploadUtil {




	/**
	 * 文件上传
	 * 
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static UploadFile uploadFile(UploadFile uploadFile) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			String uploadbasepath = uploadFile.getBasePath();// 文件上传根目录
			if (uploadbasepath == null || uploadbasepath == "") {
				uploadbasepath=uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("");
				String fileSeparator = System.getProperties().getProperty("file.separator");//获得文件分割赋
				uploadbasepath = uploadbasepath.substring(0,uploadbasepath.lastIndexOf(fileSeparator))+fileSeparator;
			}
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String path = "upload/";// 文件保存在硬盘的相对路径
			String realPath =uploadbasepath + path;// 文件的硬盘真实路径
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			if (uploadFile.getCusPath() != null) {
				realPath += uploadFile.getCusPath() + "/";
				path += uploadFile.getCusPath() + "/";
				realPath += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
				path += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
				file = new File(realPath);
				if (!file.exists()) {
					file.mkdirs();// 创建文件自定义子目录
				}
			}
			else {
				realPath += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
				path += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
				file = new File(realPath);
				if (!file.exists()) {
					file.mkdir();// 创建文件时间子目录
				}
			}
			String fileName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// 获取上传文件对象
				fileName = mf.getOriginalFilename();// 获取文件名
				String prefix=fileName.substring(fileName.lastIndexOf(".")+1);//文件后缀
				fileName=UUID.randomUUID().toString()+"."+prefix;//修改文件名
				String savePath = realPath + fileName;// 文件保存全路径
				System.out.println(savePath);
				File savefile = new File(savePath);
					// 设置文件数据库的物理路径
					uploadFile.setRealPath(path + fileName);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
    
	@SuppressWarnings("unchecked")
	public static UploadFile uploadFile(UploadFile uploadFile,String realPath) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			String uploadbasepath = uploadFile.getBasePath();// 文件上传根目录
			if (uploadbasepath == null || uploadbasepath == "") {
				uploadbasepath=uploadFile.getMultipartRequest().getSession().getServletContext().getRealPath("");
				String fileSeparator = System.getProperties().getProperty("file.separator");
				uploadbasepath = uploadbasepath.substring(0,uploadbasepath.lastIndexOf(fileSeparator))+fileSeparator;
			}
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径

			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			String fileName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// 获取上传文件对象
				fileName = mf.getOriginalFilename();// 获取文件名
				String prefix=fileName.substring(fileName.lastIndexOf(".")+1);//文件后缀
				fileName=UUID.randomUUID().toString()+"."+prefix;//修改文件名
				fileName= fileName.replaceAll("-", "");
				String savePath = realPath +File.separator+ fileName;// 文件保存全路径
				System.out.println(savePath);
				File savefile = new File(savePath);
					// 设置文件数据库的物理路径
					uploadFile.setRealPath(fileName);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
	
	/**
	 * 文件下载或预览
	 * 
	 * @param request
	 * @throws Exception
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static HttpServletResponse viewOrDownloadFile(UploadFile uploadFile) {
		uploadFile.getResponse().setContentType("UTF-8");
		uploadFile.getResponse().setCharacterEncoding("UTF-8");
		InputStream bis = null;
		BufferedOutputStream bos = null;
		HttpServletResponse response = uploadFile.getResponse();
		HttpServletRequest request = uploadFile.getRequest();
		String ctxPath = request.getSession().getServletContext().getRealPath("/");
		String downLoadPath = "";
		long fileLength = 0;
		if (uploadFile.getRealPath() != null&&uploadFile.getContent() == null) {
			downLoadPath = ctxPath + uploadFile.getRealPath();
			fileLength = new File(downLoadPath).length();
			try {
				bis = new BufferedInputStream(new FileInputStream(uploadFile.getRealPath()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			if (uploadFile.getContent() != null)
				bis = new ByteArrayInputStream(uploadFile.getContent());
			fileLength = uploadFile.getContent().length;
		}
		try {
			if (!uploadFile.isView() && uploadFile.getExtend() != null) {
				if (uploadFile.getExtend().equals("text")) {
					response.setContentType("text/plain;");
				} else if (uploadFile.getExtend().equals("doc")) {
					response.setContentType("application/msword;");
				} else if (uploadFile.getExtend().equals("xls")) {
					response.setContentType("application/ms-excel;");
				} else if (uploadFile.getExtend().equals("pdf")) {
					response.setContentType("application/pdf;");
				} else if (uploadFile.getExtend().equals("jpg") || uploadFile.getExtend().equals("jpeg")) {
					response.setContentType("image/jpeg;");
				} else {
					response.setContentType("application/x-msdownload;");
				}
				response.setHeader("Content-disposition", "attachment; filename=" + new String((uploadFile.getTitleField() + "." + uploadFile.getExtend()).getBytes("GBK"), "ISO8859-1"));
				response.setHeader("Content-Length", String.valueOf(fileLength));
			}
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}
}
