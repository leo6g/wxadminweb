package com.lfc.wxadminweb.common.upload;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadImg {
	/**
	 * 文件上传
	 * 
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static UploadFile upload(UploadFile uploadFile) {
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
				String s = UUID.randomUUID().toString();
				String name = mf.getOriginalFilename();
				String extend = name.substring(name.lastIndexOf("."));
				fileName = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24)+extend;// 获取文件名				
				String savePath = realPath + fileName;// 文件保存全路径
				System.out.println("----------------------"+savePath);
				File savefile = new File(savePath);
					// 设置文件数据库的物理路径
					uploadFile.setRealPath(realPath + fileName);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
}
