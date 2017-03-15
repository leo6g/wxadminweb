package com.baidu.ueditor.hunter;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MIMEType;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.StorageManager;
import com.github.sd4324530.fastweixin.api.MediaAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.UploadImgResponse;
import com.lfc.wxadminweb.common.utils.DateUtil;
import com.lfc.wxadminweb.common.utils.FileDownLoadUtil;
import com.lfc.wxadminweb.common.utils.PropertiesUtil;

/**
 * 图片抓取器
 * 
 * @author hancong03@baidu.com
 *
 */
public class ImageHunter {

	private String filename = null;
	private String savePath = null;
	private String rootPath = null;
	private List<String> allowTypes = null;
	private long maxSize = -1;

	private List<String> filters = null;

	public ImageHunter(Map<String, Object> conf) {

		this.filename = (String) conf.get("filename");
		this.savePath = (String) conf.get("savePath");
		this.rootPath = (String) conf.get("rootPath");
		this.maxSize = (Long) conf.get("maxSize");
		this.allowTypes = Arrays.asList((String[]) conf.get("allowFiles"));
		this.filters = Arrays.asList((String[]) conf.get("filter"));

	}

	public State capture(String[] list) {

		MultiState state = new MultiState(true);

		for (String source : list) {
			state.addState(captureRemoteData(source));
		}

		return state;

	}

	public State captureRemoteData(String urlStr) {

		HttpURLConnection connection = null;
		URL url = null;
		String suffix = null;

		try {
			url = new URL(urlStr);

			if (!validHost(url.getHost())) {
				return new BaseState(false, AppInfo.PREVENT_HOST);
			}

			connection = (HttpURLConnection) url.openConnection();

			connection.setInstanceFollowRedirects(true);
			connection.setUseCaches(true);

			if (!validContentState(connection.getResponseCode())) {
				return new BaseState(false, AppInfo.CONNECTION_ERROR);
			}

			suffix = MIMEType.getSuffix(connection.getContentType());

			if (!validFileType(suffix)) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			if (!validFileSize(connection.getContentLength())) {
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			String savePath = this.getPath(this.savePath, this.filename, suffix);
			String physicalPath = this.rootPath + savePath;

			// 这行是上传的动作执行的代码，修改此代码可以更改图片上传行为。可改为上传到微信服务器
//			State state = StorageManager.saveFileByInputStream(connection.getInputStream(), physicalPath);// 这行是原代码
			State state = new BaseState(true);
			state.putInfo( "size", 100);//这里实际用不上
			state.putInfo( "title", "name" );//这里实际用不上
			// 以下是对代码的改动
			// 上传图片到微信服务器。微信公众号appId和secret
			String appId = PropertiesUtil.getString("appId");
			String secret = PropertiesUtil.getString("secret");
			ApiConfig config = new ApiConfig(appId, secret);
			String wxUrl = null;
			if (StringUtils.isNotEmpty(urlStr)) {
				String picSavePath = PropertiesUtil.getString("picture_path") + "wxpic/tmp/" + DateUtil.getCurrYMDHMSS()
						+ suffix;
				// 下载img的src外链的图片到服务器硬盘
				boolean flag = FileDownLoadUtil.downLoadFile(urlStr, picSavePath);
				if (flag) {
					// 下载成功后，上传本地图片到微信服务器，替换图文内容的图片地址
					MediaAPI mediaAPI = new MediaAPI(config);
					UploadImgResponse response = mediaAPI.uploadImg(new File(picSavePath));
					wxUrl = response.getUrl();
					if (StringUtils.isNotEmpty(wxUrl)) {
						Map<String, Object> result = new HashMap<String, Object>();
						result.put("img", wxUrl);
					}
				}
			}
			// 改动结束
			if (state.isSuccess()) {
				state.putInfo("url", wxUrl);
				state.putInfo("source", urlStr);
			}

			return state;

		} catch (Exception e) {
			return new BaseState(false, AppInfo.REMOTE_FAIL);
		}

	}

	private String getPath(String savePath, String filename, String suffix) {

		return PathFormat.parse(savePath + suffix, filename);

	}

	private boolean validHost(String hostname) {
		try {
			InetAddress ip = InetAddress.getByName(hostname);

			if (ip.isSiteLocalAddress()) {
				return false;
			}
		} catch (UnknownHostException e) {
			return false;
		}

		return !filters.contains(hostname);

	}

	private boolean validContentState(int code) {

		return HttpURLConnection.HTTP_OK == code;

	}

	private boolean validFileType(String type) {

		return this.allowTypes.contains(type);

	}

	private boolean validFileSize(int size) {
		return size < this.maxSize;
	}

}
