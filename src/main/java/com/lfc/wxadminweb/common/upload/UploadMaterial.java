package com.lfc.wxadminweb.common.upload;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.sd4324530.fastweixin.api.MaterialAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.UploadMaterialResponse;

public class UploadMaterial {
	//上传图片
	public static  UploadFile uploadImage(HttpServletRequest request,UploadFile uploadFile,String path,String appId,String secret) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path+"material/";// 文件的硬盘真实路径
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			String fileName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// 获取上传文件对象
				String s = UUID.randomUUID().toString();
				String name = mf.getOriginalFilename();
				String extend = name.substring(name.lastIndexOf("."));
				String imageName=request.getParameter("imageName");
				if(StringUtils.isNotEmpty(imageName)){
					fileName=imageName+extend;
				}else{
					fileName = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24)+extend;// 获取文件名				
				}				
				String savePath = realPath + fileName;//文件保存全路径
				File savefile = new File(savePath);
				// 设置文件数据库的物理路径
				String contextPath = request.getContextPath();
				String showImgPath=contextPath+"/viewImage/viewImage?imgPath=material/"+fileName;
				uploadFile.setRealPath(showImgPath);
				uploadFile.setTitleField(fileName);
			    //第一步：上传到本地
			    FileCopyUtils.copy(mf.getBytes(), savefile);
				//第二步：上传到微信服务器
				ApiConfig ApiConfig = new ApiConfig(appId, secret);
				MaterialAPI materialAPI = new MaterialAPI(ApiConfig);
				UploadMaterialResponse response = materialAPI.uploadMaterialFile(savefile);
                if(StringUtils.isNotEmpty(response.getErrcode())){
                    	uploadFile.setErrorMessage(response.getErrmsg());
                    	break;
                }else{
        				uploadFile.setMediaId(response.getMediaId());
        				uploadFile.setUrl(response.getUrl());
                }
			}
		} catch (Exception e1) {
			uploadFile.setErrorMessage("调用微信服务器异常");
		}
		return uploadFile;
	}
	
	//上传视频
	public static  UploadFile uploadVideo(HttpServletRequest request,UploadFile uploadFile,String path,String appId,String secret) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path+"material/";// 文件的硬盘真实路径
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			String fileName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// 获取上传文件对象
				String s = UUID.randomUUID().toString();
				String name = mf.getOriginalFilename();
				String extend = name.substring(name.lastIndexOf("."));
				fileName = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24)+extend;// 获取文件名				
				String savePath = realPath + fileName;//文件保存全路径
				File savefile = new File(savePath);
				// 设置文件数据库的物理路径
				String contextPath = request.getContextPath();
				String showImgPath=contextPath+"/viewVideo/viewVideo?videoPath=material/"+fileName+"&videoName="+fileName;
				uploadFile.setRealPath(showImgPath);
				uploadFile.setTitleField(fileName);
			    //第一步：上传到本地
			    FileCopyUtils.copy(mf.getBytes(), savefile);
				//第二步：上传到微信服务器
				ApiConfig ApiConfig = new ApiConfig(appId, secret);
				MaterialAPI materialAPI = new MaterialAPI(ApiConfig);
				UploadMaterialResponse response = materialAPI.uploadMaterialFile(savefile,fileName,fileName);
                if(StringUtils.isNotEmpty(response.getErrcode())){
                    	uploadFile.setErrorMessage(response.getErrmsg());
                    	break;
                }else{
        				uploadFile.setMediaId(response.getMediaId());
                }
			}
		} catch (Exception e1) {
			uploadFile.setErrorMessage("调用微信服务器异常");
		}
		return uploadFile;
	}
	
	
	//上传音频
	public static  UploadFile uploadVoice(HttpServletRequest request,UploadFile uploadFile,String path,String appId,String secret) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path+"material/";// 文件的硬盘真实路径
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			String fileName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// 获取上传文件对象
				String s = UUID.randomUUID().toString();
				String name = mf.getOriginalFilename();
				String extend = name.substring(name.lastIndexOf("."));
				fileName = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24)+extend;// 获取文件名				
				String savePath = realPath + fileName;//文件保存全路径
				File savefile = new File(savePath);
				// 设置文件数据库的物理路径
				String contextPath = request.getContextPath();
				String showVideoPath=contextPath+"/viewVideo/viewVideo?videoPath=material/"+fileName+"&videoName="+fileName;
				uploadFile.setRealPath(showVideoPath);
				uploadFile.setTitleField(fileName);
			    //第一步：上传到本地
			    FileCopyUtils.copy(mf.getBytes(), savefile);
				//第二步：上传到微信服务器
				ApiConfig ApiConfig = new ApiConfig(appId, secret);
				MaterialAPI materialAPI = new MaterialAPI(ApiConfig);
				UploadMaterialResponse response = materialAPI.uploadMaterialFile(savefile,fileName,fileName);
                if(StringUtils.isNotEmpty(response.getErrcode())){
                    	uploadFile.setErrorMessage(response.getErrmsg());
                    	break;
                }else{
        				uploadFile.setMediaId(response.getMediaId());
                }
			}
		} catch (Exception e1) {
			uploadFile.setErrorMessage("调用微信服务器异常");
		}
		return uploadFile;
	}
}
