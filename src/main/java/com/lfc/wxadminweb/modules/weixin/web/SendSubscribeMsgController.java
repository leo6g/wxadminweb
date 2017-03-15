package com.lfc.wxadminweb.modules.weixin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.sd4324530.fastweixin.api.MessageAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.web.BaseController;

/**
 * 发送订阅消息类
 * 1. 目前只支持发送图文消息。
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping(value = "wx/sendmsg")
public class SendSubscribeMsgController extends BaseController{
	
	@Value("#{system.appId}")
	private String appId;

	@Value("#{system.secret}")
	private String secret;
	
	@Value("#{system.viewImage}")
	private String viewImage;

	@Value("#{system.local_server}")
	private String localServer;
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value = "sendMsgToAllUser")
	@ResponseBody
	public OutputObject sendMsgToAllUser(String newsTempId){
        ApiConfig config = new ApiConfig(appId, secret);
		MessageAPI msgApi = new MessageAPI(config);
		NewsMsg msg =  new NewsMsg();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newsTempId", newsTempId);
		map.put("orderByClause", "sort");
		
		OutputObject outputObjectItm = getOutputObject(map, "wXNewsItemsService", "getAll");
		List<Map<String, Object>> newsItem = outputObjectItm.getBeans();
		
		List<Article> articleList = new ArrayList<Article>();
		
		String contextPath = request.getContextPath();
		for (int i = 0; i < newsItem.size(); i++) {
			Article article = new Article();

			article.setTitle((String) newsItem.get(i).get("title"));
			article.setDescription((String) newsItem.get(i).get("content"));
			article.setPicUrl(localServer + contextPath + viewImage + (String) newsItem.get(i).get("imagePath"));
			
			String url = (String) newsItem.get(i).get("url");
			if(url != null && !url.trim().equals("")){
				article.setUrl(url);
			}else{
				String articleId= (String) newsItem.get(i).get("itemId");
				article.setUrl(localServer+contextPath+"/wx/article/outputArticle?articleId="+articleId);
			}
			System.out.println(article.getUrl());
			articleList.add(article);
		}
		
		
		
		msg.setArticles(articleList);
		return outputObjectItm;
		//msgApi.sendMessageToUser(msg, true, null);
	}
	
	/**
	 * 按照用户的分组来进行群发
	 * @param newsTempId
	 * @param groupId
	 */
	@RequestMapping(value = "sendMsgByGroup")
	@ResponseBody
	public OutputObject sendMsgByGroup(String newsTempId,String groupId){
		ApiConfig config = new ApiConfig(appId, secret);
		MessageAPI msgApi = new MessageAPI(config);
		NewsMsg msg =  new NewsMsg();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newsTempId", newsTempId);
		map.put("orderByClause", "sort");
		
		OutputObject outputObjectItm = getOutputObject(map, "wXNewsItemsService", "getAll");
		List<Map<String, Object>> newsItem = outputObjectItm.getBeans();
		
		List<Article> articleList = new ArrayList<Article>();
		
		String contextPath = request.getContextPath();
		for (int i = 0; i < newsItem.size(); i++) {
			Article article = new Article();

			article.setTitle((String) newsItem.get(i).get("title"));
			article.setDescription((String) newsItem.get(i).get("content"));
			article.setPicUrl(localServer + contextPath + viewImage + (String) newsItem.get(i).get("imagePath"));
			
			String url = (String) newsItem.get(i).get("url");
			if(url != null && !url.trim().equals("")){
				article.setUrl(url);
			}else{
				String articleId= (String) newsItem.get(i).get("itemId");
				article.setUrl(localServer+contextPath+"/wx/article/outputArticle?articleId="+articleId);
			}
			articleList.add(article);
		}
		
		msg.setArticles(articleList);
		// 这里根据用户的分组ID来获取订阅用户列表，然后循环发送。
		for(int i=0;i<10;i++){
			msg.setToUserName("toUserName");
			// 这里还需要查一下 sendMessageToUser()方法的API
			//msgApi.sendMessageToUser(msg, false, "tag");
		}
		return outputObjectItm;
	}

}
