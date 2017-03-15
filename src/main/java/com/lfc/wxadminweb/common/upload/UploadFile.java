package com.lfc.wxadminweb.common.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * 上传下载模型类
 */
public class UploadFile {
	private String byteField = "attachmentcontent";// 二进制文件内容保存到数据库的对应实体类字段
	private String titleField = "attachmenttitle";// 文件名(标题)保存到数据库的对应实体类字段
	private String basePath = "";// 文件保存目录根路径
	private String realPath = "";// 文件保存在硬盘的全路径对应实体字段
	private String extend = "";// 扩展名
	private boolean view = false;// 是否是预览
	private boolean rename  =true;// 是否重命名
	private String swfpath;// 转换SWF
	private String cusPath;// 文件物理路径自定义子目录
	private byte[] content;// 预览或下载时传入的文件二进制内容
	private String fileKey;// 上传文件ID
	private MultipartHttpServletRequest multipartRequest;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String mediaId;//微信的素材ID
	private String errorMessage;
	private String url;//微信返回的图片url

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public UploadFile(){
		
	}

	public UploadFile(HttpServletRequest request, HttpServletResponse response) {
		this.multipartRequest = (MultipartHttpServletRequest) request;
		this.request = request;
		this.response = response;
		this.cusPath=request.getParameter("cusPath");
		this.basePath=request.getParameter("basePath");
	}
	public String getSwfpath() {
		return swfpath;
	}

	public void setSwfpath(String swfpath) {
		this.swfpath = swfpath;
	}

	/**
	 * @return the realPath
	 */
	public String getRealPath() {
		return realPath;
	}

	/**
	 * @param realPath
	 *            the realPath to set
	 */
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the multipartRequest
	 */
	public MultipartHttpServletRequest getMultipartRequest() {
		return multipartRequest;
	}

	public String get(String name) {
		return getMultipartRequest().getParameter(name);

	}

	/**
	 * @param multipartRequest
	 *            the multipartRequest to set
	 */
	public void setMultipartRequest(MultipartHttpServletRequest multipartRequest) {
		this.multipartRequest = multipartRequest;
	}
	
	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}


	public String getByteField() {
		return byteField;
	}
	public void setByteField(String byteField) {
		this.byteField = byteField;
	}

	public String getTitleField() {
		return titleField;
	}

	public void setTitleField(String titleField) {
		this.titleField = titleField;
	}

	public String getCusPath() {
		return cusPath;
	}

	public void setCusPath(String cusPath) {
		this.cusPath = cusPath;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public boolean isRename() {
		return rename;
	}

	public void setRename(boolean rename) {
		this.rename = rename;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
