package com.lfc.wxadminweb.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sd4324530.fastweixin.api.MaterialAPI;
import com.github.sd4324530.fastweixin.api.MediaAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.Article;
import com.github.sd4324530.fastweixin.api.enums.MaterialType;
import com.github.sd4324530.fastweixin.api.response.DownloadMaterialResponse;
import com.github.sd4324530.fastweixin.api.response.UploadImgResponse;
import com.github.sd4324530.fastweixin.api.response.UploadMaterialResponse;


public class WxPicPathReplaceUtil {

	
	private static final Logger logger = LoggerFactory.getLogger(WxPicPathReplaceUtil.class);
	
	
	public static void main(String[] args) throws IOException {
		File input = new File("E:/some-img/tmp.html");
		Document doc = Jsoup.parse(input, "UTF-8");
		System.out.println("原文内容：");
		System.out.println(doc.toString());
		String replacedHtml = getReplacedHtmlContent2(doc.toString());
		testUploadMaterialNews(replacedHtml);
	}
	
	
	//测试上传图文内容，
	public static void testUploadMaterialNews(String replacedHtml) throws IOException{
		//微信公众号appId和secret
		String appId = PropertiesUtil.getString("appId");
		String secret = PropertiesUtil.getString("secret");
		ApiConfig config = new ApiConfig(appId, secret);
		System.out.println("转换为图文内容:");
		System.out.println(replacedHtml);
		MaterialAPI materialAPI = new MaterialAPI(config);
        Article article = new Article("iUaLEWvH_gxfgdlhqXeRzcI8wS1dYXkZkMc3NprHTso", "成龙的公众号(2)-测试", "成龙的公众号(2)-测试", "http://www.baidu.com", replacedHtml, "成龙的公众号(2)-测试新闻。无意义", Article.ShowConverPic.YES);
        UploadMaterialResponse response = materialAPI.uploadMaterialNews(Arrays.asList(article));
        System.out.println(response.getMediaId());
        DownloadMaterialResponse downResponse = materialAPI.downloadMaterial(response.getMediaId(), MaterialType.NEWS);
        System.out.println("图文上传微信服务器地址:-------"+downResponse.getNews().get(0).getUrl());
	}
	
	
	/**
	 * 得到已经替换过图片路径的html图文内容
	 * @param sourceHtml
	 * @return
	 * @throws IOException 
	 */
	public static String getReplacedHtmlContent(String sourceHtml) throws IOException{
		//微信公众号appId和secret
		String appId = PropertiesUtil.getString("appId");
		String secret = PropertiesUtil.getString("secret");
		ApiConfig config = new ApiConfig(appId, secret);
		//解析html内容
		Document doc = Jsoup.parse(sourceHtml);
		//选择img标签，替换img标签的src属性值
		Elements links = doc.getElementsByTag("img");
		for (Element link : links) {
		  String sourcePicUrl = link.attr("src");
		  System.out.println("sourcePicUrl------------"+sourcePicUrl);
		  String picSavePath = PropertiesUtil.getString("picture_path") + "wxpic/tmp/" + DateUtil.getCurrYMDHMSS() + ".jpg";
		  //下载img的src外链的图片到服务器硬盘
		  boolean flag = FileDownLoadUtil.downLoadFile(sourcePicUrl, picSavePath);
		  System.out.println("picSavePath------------"+picSavePath);
		  if(flag){
			  //下载成功后，上传本地图片到微信服务器，替换图文内容的图片地址
		      MediaAPI mediaAPI = new MediaAPI(config);
		      UploadImgResponse response = mediaAPI.uploadImg(new File(picSavePath));
		      String wxUrl = response.getUrl();
		      link.attr("src",wxUrl);
			  System.out.println("wxPicUrl------------"+response.getUrl());
		  }
		}
		//得到转换过图片地址的图文html字符串
		return doc.toString();
	}
	
	
	
	/**
	 * 得到已经替换过图片路径的html图文内容
	 * @param sourceHtml
	 * @return
	 * @throws IOException 
	 */
	public static String getReplacedHtmlContent2(String sourceHtml) throws IOException{
		//微信公众号appId和secret
		String appId = PropertiesUtil.getString("appId");
		String secret = PropertiesUtil.getString("secret");
		ApiConfig config = new ApiConfig(appId, secret);
		//解析html内容
		Pattern p = Pattern.compile("(http://|https://)(.*?)(.jpg|.bmp|.gif|.png|.svg|.jpeg|.ico)"); 
		//匹配查找图片的正则
		Matcher matcher = p.matcher(sourceHtml); 
		int i=0; 
		//使用find()方法查找第一个匹配的对象 
		boolean result = matcher.find(); 
		//使用循环查找所有的匹配项
		while(result) { 
			i++; 
			//得到匹配成功的图片全路径
			String matchedUrlPath = matcher.group(0);
			System.out.println("sourcePicUrl------------"+matchedUrlPath);
			String picSavePath = PropertiesUtil.getString("picture_path") + "wxpic/tmp/" + DateUtil.getCurrYMDHMSS() + ".jpg";
			//下载img的src外链的图片到服务器硬盘
			boolean flag = FileDownLoadUtil.downLoadFile(matchedUrlPath, picSavePath);
			System.out.println("picSavePath------------"+picSavePath);
			if(flag){
				  //下载成功后，上传本地图片到微信服务器，替换图文内容的图片地址
			      MediaAPI mediaAPI = new MediaAPI(config);
			      UploadImgResponse response = mediaAPI.uploadImg(new File(picSavePath));
			      String wxUrl = response.getUrl();
			      //文本内容中原图片路径替换为上传微信服务器后的图片路径
			      sourceHtml = sourceHtml.replace(matchedUrlPath, wxUrl);
				  System.out.println("wxPicUrl------------"+wxUrl);
			}  
			//继续查找下一个匹配对象 
			result = matcher.find(); 
		} 
		System.out.println("替换后图文内容:");
		System.out.println(sourceHtml);
		return sourceHtml;
	}
	
	
	
}
